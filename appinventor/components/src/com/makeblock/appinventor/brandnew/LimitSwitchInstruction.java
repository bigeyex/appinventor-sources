package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 限位器
 */

public class LimitSwitchInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x15;
    private static final int length = 5;
    private final byte port;//1 2 3 4
    private final byte slot;//01 02

    public LimitSwitchInstruction(byte port, byte slot) {
        this.port = port;
        this.slot = slot;

    }


    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_LIMITING_STOPPER);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",巡线传感器,port:" + port;
    }
}
