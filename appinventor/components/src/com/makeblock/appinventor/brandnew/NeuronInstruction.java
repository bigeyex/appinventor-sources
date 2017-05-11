package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by xuexin on 2017/4/12.
 */

public abstract class NeuronInstruction extends Instruction {
    protected static final byte HEAD = (byte) 0xf0;
    protected static final byte TAIL = (byte) 0xf7;

    private final static int LENGTH = 3;
//    static final byte TYPE_


    @Override
    public final byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer();
        putData(byteBuffer);
        byteBuffer.put(NeuronByteUtil.getCheckSum(byteBuffer.array(), 1, byteBuffer.position()));
        return byteBuffer.array();
    }

    protected abstract void putData(ByteBuffer byteBuffer);

    protected abstract int getLength();

    protected int getTotalLength() {
        return getLength() + LENGTH; // 头尾各占一个byte,checksum一个
    }

    protected ByteBuffer getByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getTotalLength());
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
                .put(HEAD)
                .put(byteBuffer.limit() - 1, TAIL)
                .position(1);
        return byteBuffer;
    }

    @Override
    public String toString() {
        return "神经元指令";
    }
}
