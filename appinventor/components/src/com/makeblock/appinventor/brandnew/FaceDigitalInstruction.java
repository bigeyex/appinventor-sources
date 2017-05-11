package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 表情面板显示字符
 */

public class FaceDigitalInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x29;
    private static final int length = 9;
    private final byte type = 4;
    private final byte port;//端口 1,2,3,4
    private final float digital;//数值

    public FaceDigitalInstruction(byte port, float digital) {
        this.port = port;
        this.digital = digital;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_FACE);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(type);
        byteBuffer.putFloat(digital);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",表情面板,port:" + port + ",digital:" + digital;
    }
}
