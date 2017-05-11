package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 用于查询触摸传感器值
 */

public class QueryTouchStatusInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x33;
    private static final int length = 4;
    private final byte port;//1,2,3,4

    public QueryTouchStatusInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_QUERY_TOUCH_STATUS);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",用于查询触摸传感器值,port:" + port;
    }
}
