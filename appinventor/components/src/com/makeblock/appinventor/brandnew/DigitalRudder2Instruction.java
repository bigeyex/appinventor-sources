package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 数字舵机2
 */

public class DigitalRudder2Instruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x21;
    private static final int length = 5;
    private final byte port;//端口
    private final byte angle;//角度 0 - 180

    public DigitalRudder2Instruction(byte port, byte angle) {
        this.port = port;
        this.angle = angle;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_DIGITAL_RUDDER2);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(angle);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",数字舵机2,port:" + port + ",angle:" + angle;
    }
}
