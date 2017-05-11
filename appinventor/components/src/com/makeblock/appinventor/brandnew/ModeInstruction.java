package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by tom on 17/4/24.
 */

public class ModeInstruction extends RJ25Instruction {

    public static final byte MODE_BLUETOOTH = 0x00;
    public static final byte MODE_OBSTACLE_VOID = 0x01;
    public static final byte MODE_BALANCE = 0x02;
    public static final byte MODE_INFRARED = 0x03;
    public static final byte MODE_LINE_FOLLOW = 0x04;
    public static final byte DEVICE_STARTER = 0x10;
    public static final byte DEVICE_AURIGA = 0x11;
    public static final byte DEVICE_MEGAPI = 0x12;
    private static final byte DEVICE_SET_FORM = 0x3c;


    private static final int length = 5;
    private byte subCommand;
    private byte mode;

    public ModeInstruction(byte mode, byte subCommand) {
        this.mode = mode;
        this.subCommand = subCommand;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        //data
        byteBuffer.put(INDEX_SET_FORM);
        byteBuffer.put(MODE_WRITE);
        byteBuffer.put(DEVICE_SET_FORM);
        byteBuffer.put(subCommand);
        byteBuffer.put(mode);

        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ", 设置模式, subCommand:" + subCommand + ", mode:" + mode;
    }
}
