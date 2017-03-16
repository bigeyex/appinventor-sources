package com.makeblock.appinventor;
// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

import android.util.Log;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

import java.io.IOException;

/**
 * A component that provides both high- and low-level interfaces to
 * control the motors on LEGO MINDSTORMS EV3.
 *
 * @author jerry73204@gmail.com (jerry73204)
 * @author spaded06543@gmail.com (Alvin Chang)
 */
@DesignerComponent(version = 1, // have to use magic numbers since constant file cannot be motidifed
        description = "Component to control Makeblock mBot educational robot. (Note: Only Android 4.3 and above supported)",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "http://appinventor.makeblock.com/mbot-icon.png")
@SimpleObject(external = true)
public class MBot extends MBotBase {
    /**
     * Creates a new Ev3Motors component.
     */
    public MBot(ComponentContainer container) {
        super(container, "MBot");
        bluetoothManager.setOnDataReceicedListener(new UnifiedBluetoothManager.DataReceivedListener() {
            @Override
            public void onDataReceived(final byte[] dataReceived) {
                form.$context().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //组包
                        byte[] data = PackDataHelper.packData(dataReceived);
                        if (data == null) {
                            return;
                        }
                        String convertedValue = String.valueOf(extractFloat(data));
                        int indexReceived = data[2] & 0xff;
                        switch (indexReceived) {
                            case DEVICE_ULTRASONIC:
                                ReceiveUltrasonicValue(convertedValue);
                                break;
                            case DEVICE_LIGHTNESS:
                                ReceiveLightnessValue(convertedValue);
                                break;
                            case DEVICE_LINE_FOLLOWER:
                                ReceiveLineFollowerValue(convertedValue);
                                break;
                        }
                    }
                });


            }
        });
    }

    @SimpleFunction(description = "tell mBot to move forward")
    public void MoveForward(int speed) {
        setMotorSpeed("MoveForward", PORT_LEFT_MOTOR, -speed);
        setMotorSpeed("MoveForward", PORT_RIGHT_MOTOR, speed);
    }

    @SimpleFunction(description = "tell mBot to move backward")
    public void MoveBackward(int speed) {
        setMotorSpeed("MoveBackward", PORT_LEFT_MOTOR, speed);
        setMotorSpeed("MoveBackward", PORT_RIGHT_MOTOR, -speed);
    }

    @SimpleFunction(description = "tell mBot to turn left")
    public void TurnLeft(int speed) {
        setMotorSpeed("TurnLeft", PORT_LEFT_MOTOR, speed);
        setMotorSpeed("TurnLeft", PORT_RIGHT_MOTOR, speed);
    }

    @SimpleFunction(description = "tell mBot to turn right")
    public void TurnRight(int speed) {
        setMotorSpeed("TurnRight", PORT_LEFT_MOTOR, -speed);
        setMotorSpeed("TurnRight", PORT_RIGHT_MOTOR, -speed);
    }

    @SimpleFunction(description = "tell mBot to stop moving")
    public void StopMoving() {
        setMotorSpeed("StopMoving", PORT_LEFT_MOTOR, 0);
        setMotorSpeed("StopMoving", PORT_RIGHT_MOTOR, 0);
    }

    @SimpleFunction(description = "set mBot's motor speed")
    public void SetMotorSpeed(int leftSpeed, int rightSpeed) {
        setMotorSpeed("TurnRight", PORT_LEFT_MOTOR, leftSpeed);
        setMotorSpeed("TurnRight", PORT_RIGHT_MOTOR, rightSpeed);
    }

    @SimpleFunction(description = "change mBot RGB LED color (0:both, 1:right, 2:left)")
    public void SetRGBLEDColor(int whichLight, int red, int green, int blue) {
        setRGBLED("SetRGBLEDColor", whichLight, red, green, blue);
    }

    @SimpleFunction(description = "play note at a certain frequency (C4:262)")
    public void PlayNote(int frequency, int duration) {
        setBuzzer("PlayNote", frequency, duration);
    }

    @SimpleFunction(description = "set 9g servo to a certain degrees")
    public void Set9gServo(int port, int slot, int degrees) {
        set9gServo("Set9gServo", port, slot, degrees);
    }

    @SimpleFunction(description = "read ultrasonic sensor value")
    public void ReadUltrasonicSensorValue(int port) throws IOException {
        ultrasonicSensorValue("QueryUltrasonicSensorValue", port);
    }

    @SimpleFunction(description = "read lightness sensor value")
    public void ReadLightnessSensorValue() throws IOException {
        lightnessSensorValue("QueryLightnessSensorValue");
    }

    @SimpleFunction(description = "read linefollower sensor value")
    public void ReadLineFollowerValue(int port) throws IOException {
        lineFollowerStatus("QueryLineFollowerValue", port);
    }

    @SimpleFunction(description = "stop reading ultrasonic sensor value")
    public void StopReadingUltrasonicValue(int port) throws IOException {
        stopReadingSensors(DEVICE_ULTRASONIC);
    }

    @SimpleFunction(description = "stop reading lightness sensor value")
    public void StopReadingLightnessValue() throws IOException {
        stopReadingSensors(DEVICE_LIGHTNESS);
    }

    @SimpleFunction(description = "stop reading linefollower sensor value")
    public void StopReadingLineFollowerValue(int port) throws IOException {
        stopReadingSensors(DEVICE_LINE_FOLLOWER);
    }

    @SimpleEvent(description = "get ultrasonic sensor value")
    public void ReceiveUltrasonicValue(String value) {
        Log.e("wbp", "Receive Ultrasonic Value:" + value);
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveUltrasonicValue", value);
    }

    @SimpleEvent(description = "get lightness sensor value")
    public void ReceiveLightnessValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveLightnessValue", value);
    }

    @SimpleEvent(description = "get line follower sensor value")
    public void ReceiveLineFollowerValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveLineFollowerValue", value);
    }


}