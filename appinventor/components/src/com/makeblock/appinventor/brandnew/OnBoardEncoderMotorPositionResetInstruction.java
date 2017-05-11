package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/10.
 */

public class OnBoardEncoderMotorPositionResetInstruction extends RJ25Instruction {

    private static final byte DEVICE_INSTRUCTION = 0x3e;
    private static final byte subCommand = 0x03;
    private final byte slot;
    private static final byte length = 0x05;

    public OnBoardEncoderMotorPositionResetInstruction(byte slot) {
        this.slot = slot;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ENCODER_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(subCommand);
        byteBuffer.put(slot);
        return byteBuffer.array();
    }
}
