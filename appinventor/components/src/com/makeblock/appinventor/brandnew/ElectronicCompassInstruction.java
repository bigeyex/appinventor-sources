package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 电子罗盘
 */

public class ElectronicCompassInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x1a;
    private static final int length = 5;
    private final byte port;//1 2 3 4

    public ElectronicCompassInstruction(byte port) {
        this.port = port;
    }


    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ELECTRONIC_COMPASS);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",电子罗盘,port:" + port;
    }
}
