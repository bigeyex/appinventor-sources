package com.makeblock.appinventor;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Camera;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyu on 5/27/16.
 */
@UsesPermissions(permissionNames = "android.permission.CAMERA")
@DesignerComponent(version = 1, // have to use magic numbers since constant file cannot be motidifed
        description = "Component for simple vision techniques.",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "http://appinventor.makeblock.com/mbot-icon.png")
@SimpleObject
public class CamVision extends AndroidNonvisibleComponent implements Component, Deleteable {

  protected final String TAG = "CamVision";
  protected final int TotalSamples = 30;
  protected android.hardware.Camera mCamera;
  android.hardware.Camera.Face[] mFaces = null;
  ComponentContainer mContainer;
  int mFrameWidth, mFrameHeight;
  protected Bitmap mBitmap = null;
  protected byte[] mRawPictureData = null;
  protected String mBingApiKey = null;
  protected int mRGBResult[] = null;
  protected JSONObject mVisionResult = null;
  protected String mVisionRawResult = null;

  protected android.hardware.Camera.PictureCallback mPictureCallback = new android.hardware.Camera.PictureCallback() {
    @Override
    public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
      mFrameWidth = camera.getParameters().getPictureSize().width;
      mFrameHeight = camera.getParameters().getPictureSize().height;
      mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
      mRawPictureData = data;

      AfterPictureTaken();
    }
  };

  public CamVision(ComponentContainer container) {
    super(container.$form());
    mContainer = container;
  }

  /** A safe way to get an instance of the Camera object. */
  public android.hardware.Camera getCameraInstance(){
    android.hardware.Camera c = null;
    try {
      releaseCameraAndPreview();
      c = android.hardware.Camera.open(); // attempt to get a Camera instance
    }
    catch (Exception e){
      // Camera is not available (in use or does not exist)
      Log.e("Vision", e.toString());
    }
    return c; // returns null if camera is unavailable
  }

  private void releaseCameraAndPreview() {

    if (mCamera != null) {
      mCamera.stopPreview();
      mCamera.release();
      mCamera = null;
    }
  }

  public void logMessage(String message){
    Toast.makeText(mContainer.$context(), message,
            Toast.LENGTH_SHORT).show();
  }


  @SimpleFunction(description = "Open a camera and prepare to record or take a picture")
  public void OpenCamera(){
    // Create an instance of Camera
    for(int retry=0;retry<100;retry++){
      mCamera = getCameraInstance();
      if(mCamera != null){
        break;
      }
    }

    if(mCamera != null) {
      // Create our Preview view and set it as the content of our activity.
      SurfaceTexture surfaceTexture = new SurfaceTexture(10);
      try {
        mCamera.setPreviewTexture(surfaceTexture);
        mCamera.startPreview();

        // set camera picture to the minimal resolution.
        // for fast processing and internet transmission using apis
        android.hardware.Camera.Parameters params = mCamera.getParameters();
        List<android.hardware.Camera.Size> sizes = params.getSupportedPictureSizes();

        android.hardware.Camera.Size mSize = null;

        for(android.hardware.Camera.Size size: sizes){
          if(mSize == null || size.width < mSize.width){
            mSize = size;
          }
        }

        params.setPictureSize(mSize.width, mSize.height);
        mCamera.setParameters(params);

        mCamera.setFaceDetectionListener(new MyFaceDetectionListener());
      }
      catch (Exception e){
        Log.e(TAG, "Cannot start preview");
      }
    }
    else{
      logMessage("Camera open failed. Please check if there's any other apps using the camera.");
    }
  }

  @SimpleFunction(description = "take a picture and prepare to analyze colors")
  public void TakeAPicture(){
    if(mCamera != null) {
      mBitmap = null;
      try {
        mCamera.takePicture(null, null, mPictureCallback);
      }
      catch (Exception ex){
        logMessage("error in taking picture");
      }
    }
    else{
      logMessage("please open the camera first");
      Log.e("CamVision", "Error: mCamera is null");
    }
  }


  @SimpleFunction(description = "Start face detection and prepare to read faces data")
  public void StartFaceDetection(){
    // Try starting Face Detection
    android.hardware.Camera.Parameters params = mCamera.getParameters();

    // start face detection only *after* preview has started
    if (params.getMaxNumDetectedFaces() > 0){
      // camera supports face detection, so can start it:
      mCamera.startFaceDetection();
    }
    else{
      logMessage("Your camera doesn't support face detection");
    }

  }

  @SimpleFunction(description = "Number of faces detected")
  public int NumberOfFaces(){
    if(mCamera == null){
      logMessage("You need to start the camera first");
      return 0;
    }
    if(mFaces == null){
      logMessage("You need to start face detection first");
      return 0;
    }
    else{
      return mFaces.length;
    }
  }

  @SimpleFunction(description = "Get the X location (-1000~1000) of the detected face." +
          " faceNumber starts at 0")
  public int CenterXOfFace(int faceNumber){
    if(faceNumber >= mFaces.length){
      logMessage("Not enough faces!");
      return 0;
    }
    else{
      return mFaces[faceNumber].rect.centerX();
    }
  }

  @SimpleFunction(description = "Get the Y location (-1000~1000) of the detected face." +
          " faceNumber starts at 0")
  public int CenterYOfFace(int faceNumber){
    if(faceNumber >= mFaces.length){
      logMessage("Not enough faces!");
      return 0;
    }
    else{
      return mFaces[faceNumber].rect.centerY();
    }
  }

  @SimpleFunction(description = "Get the width (-1000~1000) of the detected face." +
          " faceNumber starts at 0")
  public int WidthOfFace(int faceNumber){
    if(faceNumber >= mFaces.length){
      logMessage("Not enough faces!");
      return 0;
    }
    else{
      return mFaces[faceNumber].rect.width();
    }
  }

  @SimpleFunction(description = "Get the height (-1000~1000) of the detected face." +
          " faceNumber starts at 0")
  public int HeightOfFace(int faceNumber){
    if(faceNumber >= mFaces.length){
      logMessage("Not enough faces!");
      return 0;
    }
    else{
      return mFaces[faceNumber].rect.height();
    }
  }

  @SimpleFunction(description = "enter Bing API key for computer vision")
  public void PrepareComputerVisionWithBingApiKey(String apiKey){
    mBingApiKey = apiKey;
  }

  @SimpleFunction(description = "submit the taken picture for computer vision analysis")
  public void SubmitPictureForComputerVision() {
    new ComputerVisionTask().execute(mRawPictureData);
  }

  @SimpleFunction(description = "get a list of tags from computer vision result")
  public YailList TagsFromCV(){
    JSONArray tags = mVisionResult.optJSONArray("tags");
    ArrayList<String> tagList = new ArrayList<String>();
    try {
      if (tags != null) {
        for (int i = 0; i < tags.length(); i++) {
          JSONObject tag = tags.optJSONObject(i);
          tagList.add(tag.getString("name"));
        }
      }
    } catch (JSONException jsex){
      logMessage("Error in reading json result");
    }

    return YailList.makeList(tagList);
  }

  @SimpleFunction(description = "get a description from the computer vision result")
  public String DescriptionFromCV(){
    try {
      JSONObject descObject = mVisionResult.getJSONObject("description");
      JSONArray captions = descObject.getJSONArray("captions");
      if(captions.length() > 0){
        return captions.getJSONObject(0).getString("text");
      }
    } catch (JSONException jsex){
      logMessage("Error in reading json result");
    }
    return "I don't know";
  }

  @SimpleFunction(description = "get dominate foreground color name from the computer vision result")
  public String ForegroundColorNameFromCV(){
    JSONObject color = mVisionResult.optJSONObject("color");
    if(color != null){
      return color.optString("dominantColorForeground");
    }
    return "Unknown";
  }

  @SimpleFunction(description = "get dominate background color name from the computer vision result")
  public String BackgroundColorNameFromCV(){
    JSONObject color = mVisionResult.optJSONObject("color");
    if(color != null){
      return color.optString("dominantColorBackground");
    }
    return "Unknown";
  }


  @SimpleFunction(description = "analyze color information of certain point (0~1, 0~1) of the picture")
  public void AnalyzePointColorOfThePicture(float x, float y){ getRGBValueOfImage(x, y); }

  @SimpleFunction(description = "analyze color information of the whole picture")
  public void AnalyzeColorOfThePicture(){ getRGBValueOfImage(); }

  @SimpleFunction(description = "get red value (0-255) of the analyzed color. 0 if picture is void")
  public int GetRedValue(){
    if(mRGBResult == null) return 0;
    return mRGBResult[0];
  }

  @SimpleFunction(description = "get green value (0-255) of the analyzed color. 0 if picture is void")
  public int GetGreenValue(){
    if(mRGBResult == null) return 0;
    return mRGBResult[1];
  }

  @SimpleFunction(description = "get blue value (0-255) of the analyzed color. 0 if picture is void")
  public int GetBlueValue(){
    if(mRGBResult == null) return 0;
    return mRGBResult[2];
  }

  @SimpleFunction(description = "check whether the camera has successfully opened")
  public boolean IsCameraOpen(){
    return !(mCamera == null);
  }

  @SimpleFunction(description = "check whether a picture has taken")
  public boolean IsPictureTaken(){
    return !(mBitmap == null);
  }

  @SimpleEvent
  public void AfterFaceDetection() {
    EventDispatcher.dispatchEvent(this, "AfterFaceDetection");
  }

  @SimpleEvent
  public void AfterPictureTaken() {
    EventDispatcher.dispatchEvent(this, "AfterPictureTaken");
  }

  @SimpleEvent
  public void AfterComputerVisionResult() {
    EventDispatcher.dispatchEvent(this, "AfterComputerVisionResult");
  }

  protected void getRGBValueOfImage(){
    if(mBitmap == null){
      logMessage("Please take a picture first");
      return;
    }

    long redValue = 0, blueValue = 0, greenValue = 0;
    long valuesCount = 0;
    int imageWidth = mBitmap.getWidth()-1, imageHeight = mBitmap.getHeight()-1;

    int xStep = imageWidth / TotalSamples, yStep = imageHeight / TotalSamples;
    for(int i=0; i<TotalSamples; i++){
      for(int j=0; j<TotalSamples; j++){
        int pixel = mBitmap.getPixel(xStep*i, yStep*j);
        redValue += Color.red(pixel);
        blueValue += Color.blue(pixel);
        greenValue += Color.green(pixel);
        valuesCount++;
        Log.e(TAG, "Adding Pixel: R:"+Color.red(pixel)+" G:"+Color.green(pixel)+" B:"+Color.blue(pixel)+" rV:"+redValue+" gV:"+greenValue+" bV:"+blueValue+" c"+valuesCount);
      }
    }

    if(valuesCount > 0) {
      redValue = redValue / valuesCount;
      greenValue = greenValue / valuesCount;
      blueValue = blueValue / valuesCount;
    }
    mRGBResult = new int[]{(int)redValue, (int)greenValue, (int)blueValue};
  }

  protected void getRGBValueOfImage(float px, float py){
    if(mBitmap == null){
      logMessage("Please take a picture first");
      return;
    }

    long redValue = 0, blueValue = 0, greenValue = 0;
    int valuesCount = 0;
    int imageWidth = mBitmap.getWidth()-1, imageHeight = mBitmap.getHeight()-1;
    int x = (int)(imageWidth*px);
    int y = (int)(imageHeight*py);

//    int pixel = mBitmap.getPixel(x, y);
//    Log.e(TAG, "Adding Pixel: R:"+Color.red(pixel)+" G:"+Color.green(pixel)+" B:"+Color.blue(pixel));
//    return new int[]{(int)Color.red(pixel), (int)Color.green(pixel), (int)Color.blue(pixel)};

    for(int i=-3;i<=3;i++){
      for(int j=-3;j<=3;j++){
        if(x+i > 0 && y+j > 0 && x+i < mBitmap.getWidth() && y+j < mBitmap.getHeight()) {
          int pixel = mBitmap.getPixel(x, y);
          Log.e(TAG, "Adding Pixel: R:"+Color.red(pixel)+" G:"+Color.green(pixel)+" B:"+Color.blue(pixel));
          redValue += Color.red(pixel);
          blueValue += Color.blue(pixel);
          greenValue += Color.green(pixel);
          valuesCount++;
        }
      }
    }
    if(valuesCount > 0) {
      redValue /= valuesCount;
      blueValue /= valuesCount;
      redValue /= valuesCount;
    }
    mRGBResult = new int[]{(int)redValue, (int)greenValue, (int)blueValue};
  }

  class MyFaceDetectionListener implements android.hardware.Camera.FaceDetectionListener {
    @Override
    public void onFaceDetection(android.hardware.Camera.Face[] faces, android.hardware.Camera camera) {
      mFaces = faces;
      AfterFaceDetection();
      if (faces.length > 0){
        Log.d("FaceDetection", "face detected: "+ faces.length +
                " Face 1 Location X: " + faces[0].rect.centerX() +
                "Y: " + faces[0].rect.centerY() );
      }
      else{
        Log.d("FaceDetection", "No face");
      }
    }
  }


  protected class ComputerVisionTask extends AsyncTask<byte[], Void, Void> {

    @Override
    protected Void doInBackground(byte[]... data) {
      try{
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost request = new HttpPost("https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories,Tags,Description,Color");
//        HttpPost request = new HttpPost("https://api.projectoxford.ai/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false&returnFaceAttributes=age,gender,smile,glasses");
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("Ocp-Apim-Subscription-Key", mBingApiKey);


        // Request body
        HttpEntity reqEntity = new ByteArrayEntity(data[0]);
        request.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null)
        {
          mVisionRawResult = EntityUtils.toString(entity);
          System.out.println(mVisionRawResult);

          // parse vision results
          mVisionResult = new JSONObject(mVisionRawResult);
          publishProgress();
        }
        return null;
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage());
        return null;
      }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
      AfterComputerVisionResult();
    }
  }

  @Override
  public void onDelete() {
    releaseCameraAndPreview();
  }
}
