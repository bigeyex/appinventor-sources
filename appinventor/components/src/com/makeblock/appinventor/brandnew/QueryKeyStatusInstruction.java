package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 用于查询按键状态
 */

public class QueryKeyStatusInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x16;
    private static final int length = 5;
    private final byte port;//3,4
    private final byte key;//一共有4个按键，分别编号为 1，2，3，4

    public QueryKeyStatusInstruction(byte port, byte key) {
        this.port = port;
        this.key = key;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_QUERY_KEY_STATUS);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(key);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",按键状态,port:" + port + ",key:" + key;
    }
}
