package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 数码管
 */

public class DigitalTubeInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x09;
    private static final int length = 8;
    private final byte port;//端口 1,2,3,4
    private final float digital;//数值

    public DigitalTubeInstruction(byte port, float digital) {
        this.port = port;
        this.digital = digital;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_DIGITAL_TUBE);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.putFloat(digital);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",数码管,port:" + port + ",digital:" + digital;
    }
}
