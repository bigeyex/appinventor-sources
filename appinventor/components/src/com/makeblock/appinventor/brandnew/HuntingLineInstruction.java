package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 巡线传感器
 */

public class HuntingLineInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x11;
    private static final int length = 4;
    private final byte port;//1 2 3 4

    public HuntingLineInstruction(byte port) {
        this.port = port;
    }


    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_HUNTING_LINE);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",巡线传感器,port:" + port;
    }
}
