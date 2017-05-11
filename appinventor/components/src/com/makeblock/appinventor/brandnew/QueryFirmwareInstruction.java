package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/3/31.
 */

public class QueryFirmwareInstruction extends RJ25Instruction {

    private static final int length = 3;
    public static final byte INDEX = (byte) 130;
    private static final byte device = 0x0;

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(device);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ": 通用查询版本号指令";
    }
}
