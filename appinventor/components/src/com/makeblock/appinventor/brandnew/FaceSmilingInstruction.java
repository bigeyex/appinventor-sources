package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 表情面板显示表情
 */

public class FaceSmilingInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x29;
    private static final int length = 0x17;
    private final byte type = 2;
    private final byte x;//X偏移
    private final byte y;//Y偏移
    private final byte port;//端口 1,2,3,4
    private final byte[] smile;//笑脸 16byte

    public FaceSmilingInstruction(byte port, byte x, byte y, byte[] smile) {
        this.x = x;
        this.y = y;
        this.port = port;
        this.smile = smile;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_FACE);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(type);
        byteBuffer.put(x);
        byteBuffer.put(y);
        int length = smile.length;
        for (int i = 0; i < length; i++) {
            byteBuffer.put(smile[i]);
        }
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",表情面板,port:" + port + ",x:" + x + ",y:" + y + ",smile:" + smile.toString();
    }
}
