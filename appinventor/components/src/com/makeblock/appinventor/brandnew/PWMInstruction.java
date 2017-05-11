package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 数字管脚
 */

public class PWMInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x20;
    private static final int length = 5;
    private final byte port;//端口  5
    private final byte pwm;// 0 ~ 255

    public PWMInstruction(byte port, byte pwm) {
        this.port = port;
        this.pwm = pwm;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_PWM);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(pwm);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",PWM,port:" + port + ",pwm:" + pwm;
    }
}
