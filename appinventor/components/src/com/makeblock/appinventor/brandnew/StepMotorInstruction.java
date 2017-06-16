package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/6/16.
 */

public class StepMotorInstruction extends RJ25Instruction {

    private static final byte INSTRUCTION_STEP_MOTOR = 0x28;
    private static final int length = 10;

    private byte port;
    private short speed;
    private int step;

    public StepMotorInstruction(byte port, short speed, int step) {
        this.port = port;
        this.speed = speed;
        this.step = step;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_STEP_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(INSTRUCTION_STEP_MOTOR);
        byteBuffer.put(port);
        byteBuffer.putShort(speed);
        byteBuffer.putInt(step);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + " 步进电机，port:" + port + "  speed:" + speed + "  step:" + step;
    }
}
