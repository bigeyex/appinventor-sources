package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/3/31.
 */

public class LedInstruction extends RJ25Instruction {

    private static final byte DEVICE_LED = 0x08;
    private static final int length = 9;
    private final byte port;
    private final byte slot;
    private final byte ledIndex;
    private final byte r;
    private final byte g;
    private final byte b;

    public LedInstruction(byte port, byte slot, byte ledIndex, byte r, byte g, byte b) {
        this.port = port;
        this.slot = slot;
        this.ledIndex = ledIndex;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX_LED);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_LED);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        byteBuffer.put(ledIndex);
        byteBuffer.put(r);
        byteBuffer.put(g);
        byteBuffer.put(b);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ", LED, port:" + port + "slot:" + slot + ", index:" + ledIndex + ", rgb:" + r + "," + g + "," + b;
    }
}
