package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/5/8.
 */

public class BuzzerFrequencyInstruction extends RJ25Instruction {
    private static final byte DEVICE_BUZZER = 0x22;
    private static final int length = 7;
    private byte port;
    private final int frequency;
    private final short duration;
    public static final byte PORT_AURIGA = 0x2d;

    public BuzzerFrequencyInstruction(byte port, int frequency, short duration) {
        this.port = port;
        this.frequency = frequency;
        this.duration = duration;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX_BUZZER);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_BUZZER);
        if (port == PORT_AURIGA) {
            byteBuffer.put(port);
        }
        byteBuffer.putShort((short) frequency);
        byteBuffer.putShort(duration);
        return byteBuffer.array();
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return super.toString() + ", 蜂鸣器指令, 音调:" + frequency + ", 持续时间:" + duration;
    }
}
