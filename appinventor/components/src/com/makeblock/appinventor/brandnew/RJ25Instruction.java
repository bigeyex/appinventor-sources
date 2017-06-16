package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by xuexin on 2017/3/31.
 */

public abstract class RJ25Instruction extends Instruction {

    //index
    public static final byte INDEX_BUZZER = 0x1;
    public static final byte INDEX_JOYSTICK = 0x2;
    public static final byte INDEX_LED = 0x3;
    public static final byte INDEX_SET_FORM = 0x4;
    public static final byte INDEX_DC_MOTOR = 0x5;
    public static final byte INDEX_SET_MODE = 0x6;
    public static final byte INDEX_ULTRASONIC = 0x7;
    public static final byte INDEX_TEMPERATURE = 0x8;
    public static final byte INDEX_LIGHT = 0x9;
    public static final byte INDEX_POTENTIOMETER = 0x10;
    public static final byte INDEX_ROCKER = 0x11;
    public static final byte INDEX_GESTURE = 0x12;
    public static final byte INDEX_DIGITAL_RUDDER = 0x13;
    public static final byte INDEX_DIGITAL_TUBE = 0x14;
    public static final byte INDEX_SHUTTER_RELEASE = 0x15;
    public static final byte INDEX_DIGITAL_PIN = 0x16;
    public static final byte INDEX_PWM = 0x17;
    public static final byte INDEX_DIGITAL_RUDDER2 = 0x18;
    public static final byte INDEX_RESET_DEVICE_RUNTIME = 0x19;
    public static final byte INDEX_VOLUME = 0x20;
    public static final byte INDEX_INFRARED = 0x21;
    public static final byte INDEX_HUNTING_LINE = 0x22;
    public static final byte INDEX_LIMITING_STOPPER = 0x23;
    public static final byte INDEX_ELECTRONIC_COMPASS = 0x24;
    public static final byte INDEX_TEMPERATURE_HUMIDITY = 0x25;
    public static final byte INDEX_FLAME = 0x26;
    public static final byte INDEX_GAS = 0x27;
    public static final byte INDEX_QUERY_DIGITAL_PIN = 0x28;
    public static final byte INDEX_QUERY_DEVICE_RUNTIME = 0x29;
    public static final byte INDEX_QUERY_TOUCH_STATUS = 0x30;
    public static final byte INDEX_QUERY_BOARD_KEY_STATUS = 0x31;
    public static final byte INDEX_QUERY_KEY_STATUS = 0x32;
    public static final byte INDEX_FACE = 0x33;
    public static final byte INDEX_9G_SERVO = 0x34;
    public static final byte INDEX_MINI_FAN = 0x35;
    public static final byte INDEX_ON_BOARD_ENCODER_MOTOR = 0x36;
    public static final byte INDEX_ENCODER_MOTOR = 0x37;
    public static final byte INDEX_STEP_MOTOR = 0x38;

    //mode
    protected static final byte MODE_WRITE = 0x2;
    protected static final byte MODE_READ = 0x1;

    public static final byte[] Head = new byte[]{(byte) 0xff, (byte) 0x55};

    protected final ByteBuffer getByteBuffer(int length) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Head.length + length + 1);  // length本身也占一个byte
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(Head);
        byteBuffer.put((byte) length);
        return byteBuffer;
    }

    @Override
    public String toString() {
        return "RJ25指令";
    }
}
