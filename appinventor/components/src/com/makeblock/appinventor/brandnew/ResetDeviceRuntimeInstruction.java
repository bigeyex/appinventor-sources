package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 用于重置固件运行时间，该命令配合其读命令来使用
 */

public class ResetDeviceRuntimeInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x32;
    private static final int length = 3;

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_RESET_DEVICE_RUNTIME);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",重置固件运行时间";
    }
}
