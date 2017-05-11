package com.makeblock.appinventor.brandnew;


/**
 * Created by xuexin on 2017/4/17.
 * 代码中大量16进制数字,看不懂是正常的,打开计算器,对着二进制看就明白了
 * <p>
 * 文档如下:
 * 离线模式的包头和包尾分别为0xf1和0xf6，在线模式的包头和包尾分别为0xf0和0xf7，其他字段中需要避免出现包头和包尾标识符，因此，部分数据需要进行数据转换，低位字节的最高位挪至上一高位字节的最低位，上一高位字节的各位依次向前挪一位。规定以下几种数据类型：
 * byte：1byte数据(数值范围：-128~+127)，转换成7bit/byte的2bytes来表示。
 * 例：-1，二进制(1111 1111)，转换成（0000 0001 0111 1111）
 * BYTE：数值范围 0~127，第8bit为0，不需要进行数据转换。
 * short：2bytes(数值范围-32768~-1, 0~32767)，转换成7bit/byte的3bytes来表示。
 * 例：30000，二进制（0111 0101 0011 0000），转换成（0000 0001 0110 1010 0011 0000）。
 * SHORT：2bytes(数值范围0-16383)，转换成7bit/byte的2bytes来表示。
 * 例：1023，二进制（0000 0111 1101 0000），转换成（ 0000 1111 0101 0000）
 * long: 4bytes(数值范围-2147483648-2147483647)，转换成7bit/byte的5bytes来表示。
 * float:4bytes浮点数，转换成7bit/byte的5bytes来表示。
 * 说明：使用哪种数据类型，不以改数据的实际大小为依据，而是以其取值范围为依据，如光线传感器测得的值是20，虽然可以用一个字节表示，但测得数据也可能是289，需转成2个字节，为方便进行数据解析，统一转成2个字节。
 */

public class NeuronByteUtil {
    static final int TYPE_BYTE = 0x1;
    static final int TYPE_byte = 0x2;
    static final int TYPE_SHORT = 0x3;
    static final int TYPE_short = 0x4;
    static final int TYPE_long = 0x5;
    static final int TYPE_float = 0x6;

    static final int SIZE_BYTE = 1;
    static final int SIZE_byte = 2;
    static final int SIZE_SHORT = 2;
    static final int SIZE_short = 3;
    static final int SIZE_long = 5;
    static final int SIZE_float = 5;

    public static int convert7to8(byte[] bytes, int start, int end) {
        int data = 0;
        for (int i = 0; i < end - start; ++i) {
            data |= bytes[i + start] << i * 7;
        }
        return data;
    }

    public static byte[] convert8to7(int type, int data) {
        byte[] convertData;
        switch (type) {
            case TYPE_BYTE:
                convertData = new byte[SIZE_BYTE];
                convertData[0] = (byte) (data & 0x7f);
                break;
            case TYPE_SHORT:
                convertData = new byte[SIZE_SHORT];
                convertData[1] = (byte) ((data & 0x3f80) >>> 7);
                convertData[0] = (byte) (data & 0x7f);
                break;
            case TYPE_byte:
                convertData = new byte[SIZE_byte];
                convertData[1] = (byte) ((data & 0x80) >>> 7);
                convertData[0] = (byte) (data & 0x7f);
                break;
            case TYPE_short:
                convertData = new byte[SIZE_short];
                convertData[0] = (byte) (data & 0x7f);
                convertData[1] = (byte) ((data & 0x3f80) >>> 7);
                convertData[2] = (byte) ((data & 0xc000) >>> 14);
                break;
            case TYPE_long:
                convertData = new byte[SIZE_long];
                convertData[0] = (byte) (data & 0x7f);
                convertData[1] = (byte) ((data & 0x3f80) >>> 7);
                convertData[2] = (byte) ((data & 0x1fc000) >>> 14);
                convertData[3] = (byte) ((data & 0xfe00000) >>> 21);
                convertData[4] = (byte) ((data & 0xf0000000) >>> 28);
                break;
            case TYPE_float:
                convertData = new byte[SIZE_float];
                convertData[0] = (byte) (data & 0x7f);
                convertData[1] = (byte) ((data & 0x3f80) >>> 7);
                convertData[2] = (byte) ((data & 0x1fc000) >>> 14);
                convertData[3] = (byte) ((data & 0xfe00000) >>> 21);
                convertData[4] = (byte) ((data & 0xf0000000) >>> 28);
                break;
            default:
                convertData = null;
        }
        return convertData;
    }

    public static byte getCheckSum(byte[] bytes, int start, int end) {
        if (bytes == null || start <= 0 || end >= bytes.length) {
            throw new RuntimeException("数据不对怎么算校验");
        }
        byte checksum = 0;
        for (int i = start; i < end; ++i) {
            checksum += bytes[i];
        }
        checksum &= 0x7f;
        return checksum;
    }


}
