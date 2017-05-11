package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/3/31.
 */

public class JoystickInstruction extends RJ25Instruction {

    private static final byte DEVICE_JOYSTICK = 0x5;

    private static final int length = 7;
    private final short speed1;
    private final short speed2;

    public JoystickInstruction(short speed1, short speed2) {
        this.speed1 = speed1;
        this.speed2 = speed2;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX_JOYSTICK);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_JOYSTICK);
        byteBuffer.putShort(speed1);
        byteBuffer.putShort(speed2);
        return byteBuffer.array();
    }

    public short getSpeed1() {
        return speed1;
    }

    public short getSpeed2() {
        return speed2;
    }

    @Override
    public String toString() {
        return super.toString() + ", 摇杆指令, speed1:" + speed1 + ", speed2:" + speed2;
    }

}
