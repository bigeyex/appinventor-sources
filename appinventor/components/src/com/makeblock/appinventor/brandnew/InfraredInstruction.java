package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 红外传感器(接收端)
 */

public class InfraredInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x0d;
    private static final int length = 5;
    private final byte port;//板载(00)
    private final byte key;//A键

    public InfraredInstruction(byte port, byte key) {
        this.port = port;
        this.key = key;
    }


    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_INFRARED);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(key);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",红外传感器(接收端),port:" + port + ",按键,key:" + key;
    }
}
