package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/3/8.
 */

public interface CharacteristicNotifyListener {
    void onReceive(String serviceUUID, String characteristicUUID, byte[] value);
}
