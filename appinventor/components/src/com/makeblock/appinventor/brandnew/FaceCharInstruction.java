package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 表情面板显示表情
 */

public class FaceCharInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x29;
    private int length = 8;
    private final byte port;//端口 1,2,3,4
    private final byte type = 1;
    private final byte x;//X偏移
    private final byte y;//Y偏移
    private final int charLength;//字符长度
    private final byte[] chars;//字符 *byte

    public FaceCharInstruction(byte port, byte x, byte y, byte[] chars) {
        this.x = x;
        this.y = y;
        this.port = port;
        this.chars = chars;
        this.charLength = chars.length;
        this.length += charLength;
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
        byteBuffer.put((byte) charLength);
        for (int i = 0; i < charLength; i++) {
            byteBuffer.put(chars[i]);
        }
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",表情面板,port:" + port + ",x:" + x + ",y:" + y + ",charLength:" + charLength + ",chars:" + new String(chars);
    }
}
