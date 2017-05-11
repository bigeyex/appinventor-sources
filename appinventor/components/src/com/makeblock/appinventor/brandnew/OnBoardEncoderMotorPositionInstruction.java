package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/10.
 * 板载编码电机PID指令，设置以指定速度运行至指定位置，可以与复位板载编码电机位置指令一起服用
 */

public class OnBoardEncoderMotorPositionInstruction extends RJ25Instruction {

    private static final byte DEVICE_INSTRUCTION = 0x3e;
    private static final byte length = 0x0b;
    private long position;
    private final byte slot;
    private final byte subCommand = 0x01;
    private final short speed;

    public OnBoardEncoderMotorPositionInstruction(byte slot, long position, short speed) {
        this.slot = slot;
        this.position = position;
        this.speed = speed;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ENCODER_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(subCommand);
        byteBuffer.put(slot);
        byteBuffer.putLong(position);
        byteBuffer.putShort(speed);
        return byteBuffer.array();
    }
}
