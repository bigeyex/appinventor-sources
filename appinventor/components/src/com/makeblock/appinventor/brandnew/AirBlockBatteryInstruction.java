package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/4/25.
 */

public class AirBlockBatteryInstruction extends AirBlockInstruction {

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
        return super.toString() + ", 请求电量指令";
    }
}
