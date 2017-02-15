package com.makeblock.appinventor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 17/2/6.
 */

public class PackDataHelper {

    private static List<Byte> mRx = new ArrayList<Byte>();

    public static byte[] packData(final byte[] data) {
        if (data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                byte c = data[i];
                mRx.add(c);

                //读到数据的结尾了，则开始解析缓存mRx中的数据，解析完毕，clear 检测到13 10
                if ((c == 10) && (mRx.size() > 3) && (mRx.get(mRx.size() - 2)) == 13) {
                    int len4Msg = mRx.size();
                    final byte[] msg = new byte[len4Msg];

                    //打印数据
//                                    Loger.e(TAG, "====== Receive =======");
                    for (int j = 0; j < len4Msg; j++) {
//                                        Loger.e(TAG, "" + j + ":" + (mRx.get(j) & 0xff));
                        try {
                            msg[j] = (byte) (mRx.get(j) & 0xff);
                        } catch (Exception e) {
                            mRx.clear();
                            return null;
                        }
                    }
//                                    Loger.e(TAG, "============");

                    //0.数据长度不够
                    if (len4Msg < 4) {
//                        Loger.e(TAG, "数据长度小于4 break");
                        mRx.clear();
                        break;
                    }

                    //1.OK码，清空数据。OK码:255 85 13 10
                    if (len4Msg == 4 && (msg[0] & 0xff) == 0xff && (msg[1] & 0xff) == 0x55 && (msg[2] & 0xff) == 13 && (msg[3] & 0xff) == 10) {
//                                        Loger.e(TAG, "是OK码 break");
                        mRx.clear();
                        break;
                    }

                    //2.检验头尾 255 85 13 10
                    if ((msg[0] & 0xff) != 0xff || (msg[1] & 0xff) != 0x55 || (msg[len4Msg - 2] & 0xff) != 13 || (msg[len4Msg - 1] & 0xff) != 10) {
//                        Loger.e(TAG, "检验头尾，无效 break");
                        mRx.clear();
                        break;
                    }
                    //清空数据buffer
                    mRx.clear();
                    return msg;
//                    Observable
//                            .just(data)
//                            .subscribeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Action1<byte[]>() {
//                                @Override
//                                public void call(byte[] data) {
//                                    handleData(msg);
//                                }
//                            });
                }
            }
        }
        return null;
    }
}
