package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;

/**
 * 鬼知道这条协议和AirBlockControlWordInstruction对应的有什么区别,换个了cmd,按协议文档的说法是会加进队列,so?
 * 那条同样也有延时,两条看上去完全一样的协议,基本没有说明,这就是传说中嵌入式大牛的协议设计水平
 * Created by xuexin on 2017/4/19.
 */

public class AirBlockControlWordListInstruction extends AirBlockInstruction {

    private static final byte cmd = 0x1a;
    public static final byte WORD_FORWARD = 0x0;
    public static final byte WORD_BACKWARD = 0x1;
    public static final byte WORD_LEFT = 0x2;
    public static final byte WORD_RIGHT = 0x3;
    public static final byte WORD_UP = 0x4;
    public static final byte WORD_DOWN = 0x5;
    public static final byte WORD_ROTATE = 0x6;
    public static final byte WORD_HOVER = 0x9;
    public static final byte WORD_ROLL = 0x0b;
    public static final byte WORD_GROUP = 0x0c;
    public static final byte WORD_SHAKE = 0x0d;
    public static final byte WORD_FREE = 0x1e;
    public static final byte WORD_BALANCE = 0x0a;

    private final byte word;
    private final float data1;
    private final float data2;
    private final float data3;
    private final float data4;
    private final float data5;
    private final float data6;

    public AirBlockControlWordListInstruction(byte word, float data1, float data2, float data3, float data4, float data5, float data6) {
        this.word = word;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        this.data5 = data5;
        this.data6 = data6;
    }


    @Override
    protected void putData(ByteBuffer byteBuffer) {
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, cmd));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_BYTE, word));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data1)));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data2)));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data3)));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data4)));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data5)));
        byteBuffer.put(NeuronByteUtil.convert8to7(NeuronByteUtil.TYPE_float, parseToIntBit(data6)));
    }

    @Override
    protected int getLength() {
        return NeuronByteUtil.SIZE_BYTE * 2 + 6 * NeuronByteUtil.SIZE_float;
    }

    @Override
    public String toString() {
        return "AirBlock控制字队列指令,控制字:" + word +
                ", data1:" + data1 + "data2:" + data2 +
                ", data3:" + data3 + "data4:" + data4 +
                ", data5:" + data5 + "data6:" + data6;
    }
}
