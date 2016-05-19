// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import android.util.Log;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;

/**
 * @author bigeyex@gmail.com (Wang Yu)
 *
 * Rewrote from LegoMindstormEv3Base, original author:
 *
 * @author jerry73204@gmail.com (jerry73204)
 * @author spaded06543@gmail.com (Alvin Chang)
 */
@SimpleObject
public class MakeblockMBotBase extends AndroidNonvisibleComponent
  implements BluetoothConnectionListener, Component, Deleteable {

  private static final int MBOT_ROBOT = 0x0904;
  private static final byte PREFIX_A = (byte)0xff;
  private static final byte PREFIX_B = (byte)0x55;
  private static final byte SUFFIX_A = (byte)0x0d;
  private static final byte SUFFIX_B = (byte)0x0a;

  private static final int LENGTH_INDEX_ACTION_DEVICE = 3;
  private static final int LENGTH_PREFIX_PREFIX_LENGTH = 3;
  private static final int LENGTH_WRITE_REPLY = 4;
  private static final int DEFAULT_WRITE_INDEX = 0x01;
  private static final int DEFAULT_ULTRASONIC_INDEX = 0x00;
  private static final int REPLY_DATA_INDEX = 4;
  private static final int MIN_READ_INDEX = 0x02;
  private static final int MAX_READ_INDEX = 0xfe;

  private static final int REPLY_LENGTH_FLOAT = 10;

  private static final byte ACTION_READ = (byte)0x01;
  private static final byte ACTION_WRITE = (byte)0x02;

  private static final int DEVICE_ULTRASONIC  = 1;
  private static final int DEVICE_TEMPERATURE = 2;
  private static final int DEVICE_LIGHTNESS = 3;
  private static final int DEVICE_LOUDNESS = 7;
  private static final int DEVICE_LINE_FOLLOWER = 17;
  private static final int DEVICE_TOP_BUTTON = 22;

  private static final int DEVICE_DCMOTOR = 10;
  private static final int DEVICE_RGBLED = 8;
  private static final int DEVICE_BUZZER = 34;

  protected static final int PORT_LEFT_MOTOR = 9;
  protected static final int PORT_RIGHT_MOTOR = 10;
  protected static final int PORT_ONBOARD_LOUDNESS_SENSOR = 0x0e;
  protected static final int PORT_ONBOARD_LIGHTNESS_SENSOR = 0x06;
  protected static final int PORT_ONBOARD_RGBLED = 0x07;

  protected static final int SLOT_ONBOARD_RGBLED = 0x02;

  protected static final int SUBINDEX_ONBOARD_LED_LEFT = 0x01;
  protected static final int SUBINDEX_ONBOARD_LED_RIGHT = 0x02;
  protected static final int SUBINDEX_ONBOARD_LED_ALL = 0x00;


  protected final String logTag;
  protected BluetoothClient bluetooth;

  private int readIndex = MIN_READ_INDEX;

  protected MakeblockMBotBase(ComponentContainer container, String logTag) {
    super(container.$form());
    this.logTag = logTag;
  }

  protected MakeblockMBotBase() {
    super(null);
    logTag = null;
  }

  @SimpleProperty(description = "The BluetoothClient component that should be used for communication.",
                  category = PropertyCategory.BEHAVIOR)
  public BluetoothClient BluetoothClient() {
    return bluetooth;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BLUETOOTHCLIENT,
                    defaultValue = "")
  @SimpleProperty
  public void BluetoothClient(BluetoothClient bluetoothClient) {
    if (bluetooth != null) {
      bluetooth.removeBluetoothConnectionListener(this);
      bluetooth.detachComponent(this);
      bluetooth = null;
    }

    if (bluetoothClient != null) {
      bluetooth = bluetoothClient;
      bluetooth.attachComponent(this, Collections.singleton(MBOT_ROBOT));
      bluetooth.addBluetoothConnectionListener(this);
      if (bluetooth.IsConnected()) {
        // We missed the real afterConnect event.
        afterConnect(bluetooth);
      }
    }
  }

  protected final boolean isBluetoothConnected(String functionName) {
    if (bluetooth == null) {
      form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE);
      return false;
    }

    if (!bluetooth.IsConnected()) {
      form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE);
      return false;
    }

    return true;
  }

  protected final byte[] sendCommand(String functionName, byte[] command, int replyBytes) {
    // check connectivity
    if (!isBluetoothConnected(functionName))
      return null;

    bluetooth.write(functionName, command);
    return bluetooth.read(functionName, replyBytes);
  }

  protected final void sendWriteCommand(String functionName, int deviceType, byte[] commandContent) {
    if (!isBluetoothConnected(functionName))
      return;

    int commandLength = commandContent.length + LENGTH_INDEX_ACTION_DEVICE; // calculate message length

    byte bytesToWrite[] = new byte[LENGTH_PREFIX_PREFIX_LENGTH+commandLength];
    byte header[] = new byte[]{PREFIX_A, PREFIX_B, (byte)commandLength, (byte)DEFAULT_WRITE_INDEX, ACTION_WRITE, (byte)deviceType};
    System.arraycopy(header, 0, bytesToWrite, 0, header.length);
    System.arraycopy(commandContent, 0, bytesToWrite, header.length, commandContent.length);

    bluetooth.write(functionName, bytesToWrite);
    bluetooth.read(functionName, LENGTH_WRITE_REPLY);
  }

  protected final byte[] readSensor(String functionName, int deviceType, byte[] commandContent, int replyLength) throws IOException {
    if (!isBluetoothConnected(functionName))
      return null;

    int commandLength = commandContent.length + LENGTH_INDEX_ACTION_DEVICE; // calculate message length

    // calculate index
    int index = readIndex;
    if(deviceType == DEVICE_ULTRASONIC){
      index = DEFAULT_ULTRASONIC_INDEX;
    }
    else{
      readIndex++;
      if(readIndex > MAX_READ_INDEX)
        readIndex = MIN_READ_INDEX;
    }

    byte bytesToWrite[] = new byte[LENGTH_PREFIX_PREFIX_LENGTH+commandLength];
    byte header[] = new byte[]{PREFIX_A, PREFIX_B, (byte)commandLength, (byte)index, ACTION_READ, (byte)deviceType};
    System.arraycopy(header, 0, bytesToWrite, 0, header.length);
    System.arraycopy(commandContent, 0, bytesToWrite, header.length, commandContent.length);

    Log.w("mbot", "send" + byteArrayToHex(bytesToWrite));
    bluetooth.write(functionName, bytesToWrite);
    return bluetooth.read(functionName, replyLength);
  }

  private final float extractFloat(byte[] replyBytes){
    byte reversedBytes[] = new byte[]{replyBytes[REPLY_DATA_INDEX+3], replyBytes[REPLY_DATA_INDEX+2],
                                      replyBytes[REPLY_DATA_INDEX+1], replyBytes[REPLY_DATA_INDEX]};
    Log.w("mbot", "reversed" + byteArrayToHex(reversedBytes));
    return ByteBuffer.wrap(reversedBytes).getFloat();
  }

  protected final void setMotorSpeed(String functionName, int port, int speed) {
    sendWriteCommand(functionName, DEVICE_DCMOTOR, new byte[]{(byte)port, (byte)speed});
  }

  protected final void setRGBLED(String functionName, int index, int red, int green, int blue) {
    sendWriteCommand(functionName, DEVICE_RGBLED,
            new byte[]{(byte)PORT_ONBOARD_RGBLED, (byte)SLOT_ONBOARD_RGBLED, (byte)index,
                       (byte)red, (byte)green, (byte)blue});
  }

  protected final void setBuzzer(String functionName, int pitch, int beat){
    byte pitchHigh = (byte)((pitch >> 8) & 0xff);
    byte pitchLow = (byte)(pitch & 0xff);
    byte beatHigh = (byte)((beat >> 8) & 0xff);
    byte beatLow = (byte)(beat & 0xff);
    sendWriteCommand(functionName, DEVICE_BUZZER, new byte[]{pitchLow, pitchHigh, beatLow, beatHigh});
  }

  protected final float ultrasonicSensorValue(String functionName, int port) throws IOException{
    return extractFloat(readSensor(functionName, DEVICE_ULTRASONIC, new byte[]{(byte)port}, REPLY_LENGTH_FLOAT));
  }

  protected final float lightnessSensorValue(String functionName) throws IOException{
    return extractFloat(readSensor(functionName, DEVICE_LIGHTNESS,
            new byte[]{(byte)PORT_ONBOARD_LIGHTNESS_SENSOR}, REPLY_LENGTH_FLOAT));
  }

  protected final float loudnessSensorValue(String functionName, int port) throws IOException{
    return extractFloat(readSensor(functionName, DEVICE_LOUDNESS,
            new byte[]{(byte)PORT_ONBOARD_LOUDNESS_SENSOR}, REPLY_LENGTH_FLOAT));
  }


  public static String byteArrayToHex(byte[] a) {
    StringBuilder sb = new StringBuilder(a.length * 2);
    for(byte b: a)
      sb.append(String.format("%02x", b & 0xff));
    return sb.toString();
  }



  @Override
  public void afterConnect(BluetoothConnectionBase bluetoothConnection) {
    // Subclasses may wish to do something.
  }

  @Override
  public void beforeDisconnect(BluetoothConnectionBase bluetoothConnection) {
    // Subclasses may wish to do something.
  }

  // interface Deleteable implementation
  @Override
  public void onDelete() {
    if (bluetooth != null) {
      bluetooth.removeBluetoothConnectionListener(this);
      bluetooth.detachComponent(this);
      bluetooth = null;
    }
  }
}
