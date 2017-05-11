package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * Created by hupihuai on 2017/4/28.
 * 温湿度
 */

public class QueryTemperatureHumidityInstruction extends RJ25Instruction {
    public static final byte DEVICE_INSTRUCTION = 0x17;
    public static final byte TYPE_HUMIDITY = 0x00;
    public static final byte TYPE_TEMPERATURE = 0x01;
    private static final int length = 5;
    private final byte port;//端口1,2,3,4
    private final byte type;//湿度(00)   温度(01)

    public QueryTemperatureHumidityInstruction(byte port, byte type) {
        this.port = port;
        this.type = type;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer byteBuffer = getByteBuffer(length);
        byteBuffer.put(INDEX_TEMPERATURE_HUMIDITY);
        byteBuffer.put(MODE_READ);
        byteBuffer.put(DEVICE_INSTRUCTION);
        byteBuffer.put(port);
        byteBuffer.put(type);
        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return super.toString() + ",温湿度,port:" + port + ",type:" + type;
    }
}
