package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/19.
 */

public class AirBlockLandingInstruction extends AirBlockInstruction {
    private static final byte cmd = 0x0b;
    public static final byte Landing = 0x05;
    public static final byte TakeOff = 0x06;
    public static final byte Manual = 0x01;

    private final byte mode;

    public AirBlockLandingInstruction(byte mode) {
        this.mode = mode;
    }

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, mode));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE * 2;
    }

    @Override
    public String toString() {
        return "AirBlock启动命令";
    }
}
