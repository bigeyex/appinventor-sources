package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 摇杆
 */

public class JoystickSensorInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x05;
    private static final int length = 5;
    private final byte port;//端口
    private final byte axle;//轴 x 01 y 02

    public JoystickSensorInstruction(byte port, byte axle) {
        this.port = port;
        this.axle = axle;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ROCKER);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(axle);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",摇杆,port:" + port + ",axle:" + axle;
    }
}
