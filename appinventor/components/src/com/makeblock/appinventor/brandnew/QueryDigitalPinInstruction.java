package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 查询数字管脚
 */

public class QueryDigitalPinInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x1e;
    private static final int length = 4;
    private final byte port;//0 GPIO  9

    public QueryDigitalPinInstruction(byte port) {
        this.port = port;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_QUERY_DIGITAL_PIN);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",查询数字管脚,port:" + port;
    }
}
