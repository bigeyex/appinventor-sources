package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/19.
 */

public class AirBlockSpeedInstruction extends AirBlockInstruction {
    private static final byte cmd = 0x26;
    private final short speed1;
    private final short speed2;
    private final short speed3;
    private final short speed4;

    public AirBlockSpeedInstruction(short speed1, short speed2, short speed3, short speed4) {
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.speed3 = speed3;
        this.speed4 = speed4;
    }

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_SHORT, speed1));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_SHORT, speed2));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_SHORT, speed3));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_SHORT, speed4));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE + NeuronByteUtil.SIZE_SHORT * 4;

    }

    @Override
    public String toString() {
        return "AirBlock设置油门值: speed1:" + speed1 + ", speed2:" + speed2 + ", speed3:" + speed3 + ", speed4:" + speed4;
    }
}
