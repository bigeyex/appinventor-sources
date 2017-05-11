package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/3/31.
 */

public class RJ25QueryFormInstruction extends RJ25Instruction {

    private static final int length = 4;

    public static final byte INDEX = (byte) 131;    //查询套件模式：蓝牙，红外，超声波自动避障，自平衡，巡线
    private static final byte device = 0x3c;
    private static final byte sub = 0x71;

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(device);
        byteBuffer.put(sub);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ": 通用查询形态指令";
    }
}
