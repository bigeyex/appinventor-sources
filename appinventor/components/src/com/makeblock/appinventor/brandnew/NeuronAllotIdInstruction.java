package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/13.
 */

public class NeuronAllotIdInstruction extends NeuronInstruction {
    private static final byte DATA = 0x0;
    private static final byte TYPE_NO = 0x10;   //通知每个组件分配NO,对airblock来说,只有一个
    private static final byte BLOCK_NO_BROADCAST = (byte) 0xff; //对所有组件的广播

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(BLOCK_NO_BROADCAST);
        byteBuffer.put(TYPE_NO);
        byteBuffer.put(DATA);
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE * 3;
    }

    @Override
    public String toString() {
        return "airblock心跳包,其实是神经元分配ID的命令";
    }
}
