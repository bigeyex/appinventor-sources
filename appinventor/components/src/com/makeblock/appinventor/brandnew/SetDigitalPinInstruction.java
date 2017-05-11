package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 数字管脚
 */

public class SetDigitalPinInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x1e;
    private static final int length = 5;
    private final byte port;//端口  9
    private final byte level;// 低电平(00)  高电平(01)

    public SetDigitalPinInstruction(byte port, byte shutter) {
        this.port = port;
        this.level = shutter;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_DIGITAL_PIN);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(level);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",数字管脚,port:" + port + ",shutter:" + level;
    }
}
