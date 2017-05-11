package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 温度
 */

public class QueryTemperatureInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x02;
    private static final int length = 5;
    private final byte port;//端口1,2,3,4
    private final byte slot;//slot1(1), slot2(2)

    public QueryTemperatureInstruction(byte port, byte slot) {
        this.port = port;
        this.slot = slot;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_TEMPERATURE);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(slot);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",温度,port:" + port + ",slot:" + slot;
    }
}
