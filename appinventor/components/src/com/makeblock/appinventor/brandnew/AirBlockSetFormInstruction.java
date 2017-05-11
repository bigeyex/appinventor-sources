package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by xuexin on 2017/4/19.
 */

public class AirBlockSetFormInstruction extends AirBlockInstruction {

    private static final byte cmd = 0x0e;
    public static final byte FORM_CAR = 0x01;
    public static final byte FORM_AIR = 0x02;
    public static final byte FORM_CAR_DIY = 0x03;
    public static final byte FORM_DIY = 0x05;
    public static final byte FORM_SHIP = 0x06;
    public static final byte FORM_TEST = 0x70;

    private final byte form;

    public AirBlockSetFormInstruction(byte form) {
        this.form = form;
    }

    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, form));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE * 2;
    }

    @Override
    public String toString() {
        String formString = null;
        switch (form) {
            case FORM_CAR:
                formString = "气垫车";
                break;
            case FORM_AIR:
                formString = "无人机";
                break;
            case FORM_CAR_DIY:
                formString = "气垫车DIY";
                break;
            case FORM_SHIP:
                formString = "气垫船";
                break;
            case FORM_DIY:
                formString = "DIY";
                break;
            case FORM_TEST:
                formString = "测试";
                break;
        }
        return "设置AirBlock模式命令(" + formString + ")";
    }
}
