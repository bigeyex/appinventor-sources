package com.makeblock.appinventor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.appinventor.components.runtime.*;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.ListPickerActivity;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangyu on 5/19/16.
 */
public class MBotBluetoothClient implements ActivityResultListener {

  private static final String LIST_ACTIVITY_CLASS = ListPickerActivity.class.getName();
  static final String LIST_ACTIVITY_ARG_NAME = LIST_ACTIVITY_CLASS + ".list";
  static final String LIST_ACTIVITY_RESULT_NAME = LIST_ACTIVITY_CLASS + ".selection";

  /*---------------------蓝牙的主动扫描，连接，断开------------------------*/
  private final static String TAG = "MakeblockBluetooth";
  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private InputStream inputStream;
  private OutputStream outputStream;
  private BluetoothSocket socket;
  private ConnectThread connectThread;
  private ComponentContainer componentContainer;
  protected int requestCode = 0;

  protected Boolean mIsConnected = false;

  /**
   * constructor.
   * @param container container passed as a App Inventor Component.
   *                  used to show toasts etc. provide andorid context.
   */
  public MBotBluetoothClient(ComponentContainer container){
    componentContainer = container;
  }


  /**
   * Reads a number of bytes from the input stream.
   *
   * If numberOfBytes is negative, this method reads until a delimiter byte
   * value is read. The delimiter byte is included in the returned array.
   *
   * @param numberOfBytes the number of bytes to read; a negative number
   *        indicates to read until a delimiter byte value is read
   */
  public byte[] read(int numberOfBytes) {
    if (!IsConnected()) {
      logMessage("Bluetooth not Connected to Device");
      return new byte[0];
    }

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    if (numberOfBytes >= 0) {
      // Read <numberOfBytes> bytes.
      byte[] bytes = new byte[numberOfBytes];
      int totalBytesRead = 0;
      while (totalBytesRead < numberOfBytes) {
        try {
          int numBytesRead = inputStream.read(bytes, totalBytesRead, bytes.length - totalBytesRead);
          if (numBytesRead == -1) {
            logMessage("Bluetooth end of stream");
            break;
          }
          totalBytesRead += numBytesRead;
        } catch (IOException e) {
          logMessage("Bluetooth unable to read");
          break;
        }
      }
      buffer.write(bytes, 0, totalBytesRead);
    } else {
      // Read one byte at a time until a delimiter byte is read.
      while (true) {
        try {
          int value = inputStream.read();
          if (value == -1) {
            logMessage("Bluetooth end of stream");
            break;
          }
          buffer.write(value);
          // break on delimiter
//          if (value == delimiter) {
//            break;
//          }
        } catch (IOException e) {
          logMessage("Bluetooth unable to read");
          break;
        }
      }
    }

    return buffer.toByteArray();
  }

  /**
   * Writes the given bytes to the output stream.
   *
   * @param bytes the bytes to write
   */
  public void write(byte[] bytes) {
    if (!IsConnected()) {
      logMessage("Bluetooth not Connected to Device");
      return;
    }

    try {
      outputStream.write(bytes);
      outputStream.flush();
    } catch (IOException e) {
      logMessage("Bluetooth unable to write");
    }
  }

  public void openSelectDeviceDialog(){

    if (requestCode == 0) { // only need to register once
      requestCode = componentContainer.$form().registerForActivityResult(this);
    }
    disconnectBluetooth();
    Intent intent = new Intent();

    // for now, app inventor does not support custom activities.
    // use List Picker instead, and pre-populate choices with bond bluetooth devices. Sad.
    Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
    ArrayList items = new ArrayList<String>();
    if (bluetoothAdapter != null) {
      if (BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
        for (Object bluetoothDevice : BluetoothReflection.getBondedDevices(bluetoothAdapter)) {
          if (isBluetoothDeviceAcceptable((BluetoothDevice) bluetoothDevice)) {
            String name = BluetoothReflection.getBluetoothDeviceName(bluetoothDevice);
            String address = BluetoothReflection.getBluetoothDeviceAddress(bluetoothDevice);
            items.add(address);
          }
        }
      }
    }
    intent.setClassName(componentContainer.$context(), LIST_ACTIVITY_CLASS);
    intent.putExtra(LIST_ACTIVITY_ARG_NAME, items);

    // commented for future use: create a real bluetooth picker activity
    /*
    intent.setClassName(componentContainer.$context(), MakeblockBluetoothPickerActivity.class.getName());
    */
    String openAnim = componentContainer.$form().getOpenAnimType();
    AnimationUtil.ApplyOpenScreenAnimation(componentContainer.$context(), openAnim);
    componentContainer.$context().startActivityForResult(intent, requestCode);
  }

  /**
   * Callback method to get the result returned by the list picker activity
   *
   * @param requestCode a code identifying the request.
   * @param resultCode a code specifying success or failure of the activity
   * @param data the returned data, in this case an Intent whose data field
   *        contains the selected item.
   */
  @Override
  public void resultReturned(int requestCode, int resultCode, Intent data) {
    if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
      if (data.hasExtra(LIST_ACTIVITY_RESULT_NAME)) {
        String selectedAddress = data.getStringExtra(LIST_ACTIVITY_RESULT_NAME);
        startConnect(selectedAddress);
      }
    }
  }

  /**
   * Returns the list of paired Bluetooth devices. Each element of the returned
   * list is a String consisting of the device's address, a space, and the
   * device's name.
   *
   * This method calls isDeviceClassAcceptable to determine whether to include
   * a particular device in the returned list.
   *
   * @return a List representing the addresses and names of paired
   *         Bluetooth devices
   */
  public List<String> addresses() {
    List<String> addresses = new ArrayList<String>();

    Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
    if (bluetoothAdapter != null) {
      if (BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
        for (Object bluetoothDevice : BluetoothReflection.getBondedDevices(bluetoothAdapter)) {
          if (isBluetoothDeviceAcceptable((BluetoothDevice) bluetoothDevice)) {
//            String name = BluetoothReflection.getBluetoothDeviceName(bluetoothDevice);
            String address = BluetoothReflection.getBluetoothDeviceAddress(bluetoothDevice);
            addresses.add(address);
          }
        }
      }
    }

    return addresses;
  }

  // connect to a paired device. copied from bluetoothClient
  public void connect(String deviceAddress)  {
    Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
    Object bluetoothDeviceToConnect = BluetoothReflection.getRemoteDevice(bluetoothAdapter, deviceAddress);
    if (!BluetoothReflection.isBonded(bluetoothDeviceToConnect)) {
      logMessage("bluetooth not paired");
      return;
    }


    disconnect();

    Object bluetoothSocket;
    try {
      bluetoothSocket = BluetoothReflection.createRfcommSocketToServiceRecord(
              bluetoothDeviceToConnect, MY_UUID);
      BluetoothReflection.connectToBluetoothSocket(bluetoothSocket);
      socket = (BluetoothSocket)bluetoothSocket;
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();
      mIsConnected = true;
    } catch (IOException ex){
      logMessage("cannot connect to bluetooth socket");
      return;
    }


    logMessage("connected to bluetooth device");
  }

  public final void disconnect() {
    if (socket != null) {
      try {
        BluetoothReflection.closeBluetoothSocket(socket);
        Log.i(TAG, "Disconnected from Bluetooth device.");
      } catch (IOException e) {
        Log.w(TAG, "Error while disconnecting: " + e.getMessage());
      }
      socket = null;
    }
    inputStream = null;
    outputStream = null;
  }

  /**
   * method to log message. Toast by default
   * @param message
   */
  public void logMessage(String message){
    Toast.makeText(componentContainer.$context(), message,
            Toast.LENGTH_SHORT).show();
  }

  //蓝牙连接:开启ConnectThread线程连接
  public void startConnect(String deviceAddress) {
    if (connectThread != null && connectThread.isRunning) {
      Log.e(TAG, "connectThread 正在运行中");
    } else {
      //instance不能为null，必须先初始化
      BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
      connectThread = new ConnectThread(bluetoothDevice);
      connectThread.start();
      logMessage("Connecting");
    }
  }

  //蓝牙连接子线程
  private class ConnectThread extends Thread {
    private BluetoothDevice bluetoothDevice;
    private Boolean isRunning;
    private int retryTime;

    public ConnectThread(BluetoothDevice bluetoothDevice) {
      super();
      this.bluetoothDevice = bluetoothDevice;
      this.isRunning = false;
      retryTime = 0;
    }

    public void run() {
      Log.e(TAG, "connectThread start run");
      this.isRunning = true;
      //1.停止搜索
      if (BluetoothAdapter.getDefaultAdapter().isDiscovering()) {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
      }

      retryTime++;
      if (!connect()) {
        retryTime++;
        connect();
        retryTime = 0;
      }
      this.isRunning = false;
    }

    private Boolean connect() {
      Log.e(TAG, "retryTime=" + retryTime);
      if (this.bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
        try {
          Log.e(TAG, "此设备没有配对  开始配对");
          setBluetoothPairingPin(bluetoothDevice);
          Method createBondMethod = bluetoothDevice.getClass().getMethod("createBond");
          createBondMethod.invoke(bluetoothDevice);
          setBluetoothPairingPin(bluetoothDevice);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      } else {
        Log.e(TAG, "此设备已经配对");
        try {
          socket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
          socket.connect();
        } catch (Exception e) {
          if (retryTime > 1) {
            Log.e(TAG, "连接retry");
          } else {
            Log.e(TAG, "连接失败");
          }
          e.printStackTrace();
          return false;
        }
        Log.e(TAG, "socket连接成功");

        Log.e(TAG, "开始初始化:mmInStream,mmOutStream");

        try {
          Log.e(TAG, "开始初始化:mmInStream,mmOutStream");
          inputStream = socket.getInputStream();
          outputStream = socket.getOutputStream();
          logMessage("Connected to Bluetooth");
        } catch (IOException e) {
          Log.e(TAG, "初始化:mmInStream,mmOutStream 异常 return");
          if (retryTime > 1) {
            Log.e(TAG, "连接retry");
          } else {
            Log.e(TAG, "连接失败");
          }
          e.printStackTrace();
          return false;
        }

        Log.e(TAG, "初始化:mmInStream,mmOutStream 成功");
        update4BluetoothConnected();
        return true;
      }
    }

    public void setBluetoothPairingPin(BluetoothDevice device) {
      byte[] pinBytes = ("0000").getBytes();
      try {
        Log.e(TAG, "Try to set the PIN");
        Method m = device.getClass().getMethod("setPin", byte[].class);
        m.invoke(device, pinBytes);
        Log.e(TAG, "Success to add the PIN.");
        try {
          device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
          Log.e(TAG, "Success to setPairingConfirmation.");
        } catch (Exception e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  //蓝牙断开，释放资源，切换模式，停止数据解析Thread
  protected void update4BluetoothDisconnected() {
    Log.e(TAG, "update4BluetoothDisconnected");
    mIsConnected = false;
  }

  protected void update4BluetoothConnected(){
    mIsConnected = true;
  }

  //主动断开
  public void disconnectBluetooth() {
    if (socket == null) {
      Log.e(TAG, "socket disconnected");
      update4BluetoothDisconnected();
      return;
    }
    try {
      outputStream.close();
      inputStream.close();
      socket.close();
    } catch (IOException e) {
      Log.e(TAG, "exception in disconnecting");
      e.printStackTrace();
      return;
    }
    outputStream = null;
    inputStream = null;
    socket = null;
    update4BluetoothDisconnected();
    Log.e(TAG, "disconnect successful");
  }

  /*---------------------get/set 方法------------------------*/

  public Boolean IsConnected() { return isConnected(); }

  public Boolean isConnected() {
    return mIsConnected;
  }

  public Boolean isDiscovering() {
    return BluetoothAdapter.getDefaultAdapter().isDiscovering();
  }

  public static Boolean isBluetoothDeviceAcceptable(BluetoothDevice device){
    if (android.os.Build.VERSION.SDK_INT >= 18) {
      //BLE的蓝牙不显示
      if (device.getType() == BluetoothDevice.DEVICE_TYPE_LE) {  //device.getType()   Added in API level 18
        //							Loger.e(TAG, "搜索到蓝牙是BLE，return");
        return false;
      }
    }
    Log.e(TAG, "******found device:name=" + device.getName() + " mac=" + device.getAddress() + "******");
    //name: 1.不能为空  2.包含Makeblock
      Log.e(TAG, "name is Null，return");    if (TextUtils.isEmpty(device.getName())) {

        Log.e(TAG, "*************************************************************");
      return false;
    }
    if (!(device.getName().contains("Makeblock")) && !(device.getName().contains("makeblock"))) {
      Log.e(TAG, "name does not contains Makeblock/makeblock，return");
      Log.e(TAG, "*************************************************************");
      return false;
    }
    //部分手机(SDK_INT<18)会搜索到BLE的设备，因此以名字作为过滤条件
    if (device.getName().contains("Makeblock_LE")) {
      Log.e(TAG, "name includes Makeblock_LE，return");
      Log.e(TAG, "*************************************************************");
      return false;
    }

    return true;

  }

}
