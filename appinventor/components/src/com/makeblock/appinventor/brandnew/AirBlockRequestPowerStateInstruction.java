package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/28.
 */

public class AirBlockRequestPowerStateInstruction extends AirBlockInstruction {
    private static final byte cmd = 0x03;

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE;
    }

    @Override
    public String toString() {
        return super.toString() + ", 获取运行状态";
    }
}
