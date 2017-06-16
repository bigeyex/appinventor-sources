package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/6/16.
 */

public class EncoderMotor extends RJ25Instruction {

    private static final byte INSTRUCTION_ENCODER_MOTOR = 0x0c;
    private static final int length = 11;
    private static final byte i2c = 0x08;
    private byte slot;
    private float angle;
    private short speed;

    public EncoderMotor(byte slot, short speed, float angle) {
        this.slot = slot;
        this.speed = speed;
        this.angle = angle;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_ENCODER_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(INSTRUCTION_ENCODER_MOTOR);
        byteBuffer.put(i2c);
        byteBuffer.put(slot);
        byteBuffer.putShort(speed);
        byteBuffer.putFloat(angle);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + "编码电机，slot：" + slot + "  speed:" + speed + "  angle:" + angle ;
    }
}
