package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/3/31.
 */

public class BuzzerInstruction extends RJ25Instruction {

    private static final byte DEVICE_BUZZER = 0x22;
    private static final int length = 7;
    private final BuzzerTone buzzerTone;
    private final short duration;
    private byte port;
    public static final byte PORT_AURIGA = 0x2d;

    public BuzzerInstruction(byte port, BuzzerTone buzzerTone, short duration) {
        this.port = port;
        this.buzzerTone = buzzerTone;
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
        byteBuffer.putShort((short) buzzerTone.frequency);
        byteBuffer.putShort(duration);
        return byteBuffer.array();
    }

    public BuzzerTone getBuzzerTone() {
        return buzzerTone;
    }

    @Override
    public String toString() {
        return super.toString() + ", 蜂鸣器指令, 音调:" + buzzerTone.name() + ", 持续时间:" + duration;
    }
}
