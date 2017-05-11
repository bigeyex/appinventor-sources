package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 表情面板显示时间
 */

public class FaceTimeInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x29;
    private static final int length = 8;
    private final byte port;//端口 1,2,3,4
    private final byte type = 3;
    private final byte separator = 0x01;
    private final byte hour;//时
    private final byte minute;//分

    public FaceTimeInstruction(byte port, byte hour, byte minute) {
        this.port = port;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_FACE);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(type);
        byteBuffer.put(separator);
        byteBuffer.put(hour);
        byteBuffer.put(minute);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",表情面板,port:" + port + ",hour:" + hour + ",minute:" + minute;
    }
}
