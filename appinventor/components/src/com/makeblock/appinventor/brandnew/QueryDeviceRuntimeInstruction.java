package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 用于查询固件运行时间
 */

public class QueryDeviceRuntimeInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x32;
    private static final int length = 3;

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_QUERY_DEVICE_RUNTIME);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",查询固件运行时间";
    }
}
