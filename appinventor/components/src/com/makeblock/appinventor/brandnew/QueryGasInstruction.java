package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 气体传感器 查询红外钱
 */

public class QueryGasInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x19;
    private static final int length = 4;
    private final byte port;//端口3,4

    public QueryGasInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_GAS);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",气体传感器,port:" + port;
    }
}
