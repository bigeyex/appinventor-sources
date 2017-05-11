package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 查询音量
 */

public class QueryVolumeInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x07;
    private static final int length = 4;
    private final byte port;//端口

    public QueryVolumeInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_VOLUME);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",查询音量,port:" + port;
    }
}
