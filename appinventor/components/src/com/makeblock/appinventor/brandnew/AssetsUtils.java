package com.makeblock.appinventor.brandnew;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Xml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by liuming on 16/5/3.
 */
public class AssetsUtils {

    /**
     * 读取Assets中的图片
     *
     * @param context
     * @param fileName "Cat_Blink/cat_blink0000.png"
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * convert an ascii Hex file to binary file, in NSData form.
     * 把用字符串表示十六进制数据的hex文件转化为真正的二进制文件
     * hex file looks like this:
     * :1003B000F0E0EE0FFF1FEC55FF4FA591B4919FB7F2
     * :1003C000F8948C91611103C01095812301C0812B99
     * :1003D0008C939FBF0F90DF91CF911F910F91089544
     * :1003E00008950E941D010E94F0010E947000C0E06B
     * :1003F000D0E00E9474002097E1F30E940000F9CF42
     * :060400000895F894FFCFFF
     * :00000001FF
     * <p>
     * each line means:
     * [':'][lengthOfData:2][address:8][data:...][CRC:2]
     */
    private static final int hexFileDataLengthIndex = 1;   // 跳过冒号':'
    private static final int hexFileDataContentIndex = 9; // 跳过地址

    public static byte[] getFirmwareFromAssetsFile(Context context, String fileName) {
        AssetManager am = context.getResources().getAssets();
        byte[] buffer = new byte[1000000];
        int index = 0;
        try {
            InputStream inputStream = am.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);
            byte[] lineBuffer = new byte[20];
            String lineData = bufferedInputStream.readLine();
            while (lineData != null) {
                char[] charData = lineData.toCharArray();
                int lineBufferIndex = 0;
                // 每行的长度是由第2-3个字符决定的
                byte dataLength = duoHexToByte(charData[hexFileDataLengthIndex], charData[hexFileDataLengthIndex + 1]);
                for (char i = 0; i < dataLength; i++) {
                    // 从真正的数据部分开始，把每两个字节转换成真正字节加到缓冲区中
                    lineBuffer[lineBufferIndex] = duoHexToByte(charData[hexFileDataContentIndex + 2 * i], charData[hexFileDataContentIndex + 2 * i + 1]);
                    lineBufferIndex++;
                }
                // 循环结束时，lineBufferIndex正好是lineBuffer的长度
                if (lineBufferIndex > 0) {
                    System.arraycopy(lineBuffer, 0, buffer, index, lineBufferIndex);
                    index += lineBufferIndex;
                }
                lineData = bufferedInputStream.readLine();
            }
//            NSMutableData * binData =[NSMutableData data];
//            // 创建一个char buffer，用于储存转换来的byte数据，避免频繁向NSMutableData插入数据
//            char lineBuffer[ 20];
//            u_int8_t lineBufferIndex = 0;
//            NSArray * lines =[hexData componentsSeparatedByString:@ "\n"];
//            for (NSString * line in lines){
//                const char*cline =[line UTF8String];
//                lineBufferIndex = 0;
//                // 每行的长度是由第2-3个字符决定的
//                char dataLength =[self duoHexToByte:cline[hexFileDataLengthIndex]
//                and:
//                cline[hexFileDataLengthIndex + 1]];
//                for (char i = 0; i < dataLength; i++) {
//                    // 从真正的数据部分开始，把每两个字节转换成真正字节加到缓冲区中
//                    lineBuffer[lineBufferIndex] =[self duoHexToByte:
//                    cline[hexFileDataContentIndex + 2 * i]
//                    and:
//                    cline[hexFileDataContentIndex + 2 * i + 1]];
//                    lineBufferIndex++;
//                }
//                // 循环结束时，lineBufferIndex正好是lineBuffer的长度
//                if (lineBufferIndex > 0) {
//                    [binData appendBytes:lineBuffer length:lineBufferIndex];
//                }
//            }
//
//            return binData;


            bufferedInputStream.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = new byte[index];
        System.arraycopy(buffer, 0, data, 0, index);
        return data;
    }

    /**
     * convert double hex chars to one byte
     * <p>
     * eg: '3''F' --> 0x3f
     */
    private static byte duoHexToByte(char hex1, char hex2) {
        byte byte1 = hexToByte(hex1);
        byte byte2 = hexToByte(hex2);
        return (byte) ((byte1 << 4) | byte2);
    }

    /**
     * convert single hex char to one byte
     * <p>
     * eg: 'F' --> 0x0f
     */
    private static byte hexToByte(char hex) {
        if (hex > '9') {// 'A' ~ 'F'
            return (byte) (hex - 65 + 10); // 'A' -> 0x0a
        } else {
            return (byte) (hex - 48); //'2' -> 0x02
        }
    }

}
