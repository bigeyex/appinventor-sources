package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/4/24.
 */

public class DCMotorInstruction extends RJ25Instruction {

    private static final byte DEVICE_DC_MOTOR = 0x0a;

    public static final byte DC_MOTOR_LEFT = 0x09;
    public static final byte DC_MOTOR_RIGHT = 0x0a;

    private static final int length = 6;
    private final byte port;
    private final short speed;

    public DCMotorInstruction(byte port, short speed) {
        this.port = port;
        this.speed = speed;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX_DC_MOTOR);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_DC_MOTOR);
        byteBuffer.put(port);
        byteBuffer.putShort(speed);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ", DC Motor:" + "port:" + port + "  speed:" + speed;
    }
}
