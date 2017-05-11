package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/19.
 */

public abstract class AirBlockInstruction extends NeuronInstruction {
    private static final byte TYPE_AIRBLOCK = 0x5f;   //节点类型, airblock
    private static final byte NO = 0x01;        //airblock基于神经元,只是其中一个节点,所以no始终为1

    @Override
    protected final ByteBuffer getByteBuffer() {
        ByteBuffer byteBuffer = super.getByteBuffer();
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, NO));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, TYPE_AIRBLOCK));
        return byteBuffer;
    }

    @Override
    protected int getTotalLength() {
        return super.getTotalLength() + NeuronByteUtil.SIZE_BYTE * 2; //多塞了两个字节的数据
    }

    protected final int parseToIntBit(float f) {
        return Float.floatToIntBits(f);
    }

    @Override
    public String toString() {
        return "AirBlock指令";
    }
}
