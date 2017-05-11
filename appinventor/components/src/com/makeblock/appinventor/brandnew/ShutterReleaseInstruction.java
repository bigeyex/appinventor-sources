package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 快门线
 */

public class ShutterReleaseInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x14;
    private static final int length = 5;
    private final byte port;//端口  1,2,3,4
    private final byte shutter;//快门  松开快门(00) 按下快门(01) 停止对焦(02) 开始对焦(03)

    public ShutterReleaseInstruction(byte port, byte shutter) {
        this.port = port;
        this.shutter = shutter;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_SHUTTER_RELEASE);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(shutter);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",快门线,port:" + port + ",shutter:" + shutter;
    }
}
