package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/20.
 */

public class AirBlockRequestElectricInstruction extends AirBlockInstruction {
    private static final byte cmd = 0x40;
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
        return "Airblock请求电量指令";
    }
}
