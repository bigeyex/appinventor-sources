package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 用于查询板载按键状态
 */

public class QueryBoardKeyStatusInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x23;
    private static final int length = 5;
    private final byte port;//07(板载按键)
    private final byte status;//按键状态 01 已松  00 已按下

    public QueryBoardKeyStatusInstruction(byte port, byte status) {
        this.port = port;
        this.status = status;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_QUERY_BOARD_KEY_STATUS);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(status);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",板载按键状态,port:" + port + ",status:" + status;
    }
}
