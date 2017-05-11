package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 超声波查距离
 */

public class UltrasonicInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x01;
    private static final int length = 4;
    private final byte port;//端口

    public UltrasonicInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ULTRASONIC);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",超声波,port:" + port;
    }
}
