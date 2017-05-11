package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/9.
 * 9g小舵机
 */

public class NineGServoInstruction extends RJ25Instruction {

    public static final byte DEVICE_INSTRUCTION = 0x0b;
    private static final int length = 6;
    private final byte port;
    private final byte slot;
    private final byte angle;

    public NineGServoInstruction(byte port, byte slot, byte angle) {
        this.port = port;
        this.slot = slot;
        this.angle = angle;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_9G_SERVO);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        byteBuffer.put(angle);
        return byteBuffer.array();
    }
}
