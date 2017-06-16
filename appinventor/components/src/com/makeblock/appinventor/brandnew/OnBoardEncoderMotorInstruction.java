package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/10.
 */

public class OnBoardEncoderMotorInstruction extends RJ25Instruction {

    public static final byte INSTRUCTION_ENCODER_MOTOR = 0x3d;
    private static final byte length = 7;
    private final byte port;
    private final byte slot;
    private final short speed;

    public OnBoardEncoderMotorInstruction(byte port, byte slot, short speed) {
        this.port = port;
        this.slot = slot;
        this.speed = speed;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ON_BOARD_ENCODER_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(INSTRUCTION_ENCODER_MOTOR);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        byteBuffer.putShort(speed);
        return byteBuffer.array();
    }
}
