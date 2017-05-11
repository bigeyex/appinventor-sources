package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/19.
 */

public class AirBlockCalibrationInstruction extends AirBlockInstruction {

    private static final byte cmd = 0x3a;
    private static final byte TYPE_BOARD = 0x02;            //板卡?什么鬼,协议这么写的,调平
    private static final byte TYPE_GYROSCOPE = 0x01;        //陀螺仪,暂时没使用

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, TYPE_BOARD));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE * 2;

    }

    @Override
    public String toString() {
        return "AirBlock调平指令";
    }
}
