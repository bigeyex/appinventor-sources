package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 数字舵机
 */

public class DigitalServoInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x0b;
    private static final int length = 6;
    private final byte port;//端口
    private final byte slot;//
    private final byte angle;//角度 0 - 180

    public DigitalServoInstruction(byte port, byte slot, byte angle) {
        this.port = port;
        this.slot = slot;
        this.angle = angle;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_DIGITAL_RUDDER);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        byteBuffer.put(angle);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",数字舵机,port:" + port + ",slot:" + slot + ",angle:" + angle;
    }
}
