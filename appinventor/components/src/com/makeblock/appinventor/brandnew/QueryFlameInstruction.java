package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 火焰传感器
 */

public class QueryFlameInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x18;
    private static final int length = 4;
    private final byte port;//端口3,4

    public QueryFlameInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_FLAME);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",火焰传感器,port:" + port;
    }
}
