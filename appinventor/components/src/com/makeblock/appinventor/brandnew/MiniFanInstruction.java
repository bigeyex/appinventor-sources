package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/9.
 */

public class MiniFanInstruction extends RJ25Instruction {

    public static final byte DEVICE_INSTRUCTION = 0x1e;
    private static final byte length = 5;
    private byte port;
    private byte action;

    public static final byte ACTION_CLOCKWISE = 0x01;
    public static final byte ACTION_ANTI_CLOCKWISE = 0x00;

    public MiniFanInstruction(byte port, byte action) {
        this.port = port;
        this.action = action;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_MINI_FAN);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put((byte) 0x0b);
        byteBuffer.put(action);
        return byteBuffer.array();
    }
}
