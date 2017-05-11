package com.makeblock.appinventor.brandnew;

import android.util.Log;

/**
 * Created by xuexin on 2017/3/29.
 */

public abstract class BleNotifyListener {

    private final String serviceUUID, characteristicUUID;

    public BleNotifyListener(String serviceUUID, String characteristicUUID) {
        super();
        this.serviceUUID = serviceUUID;
        this.characteristicUUID = characteristicUUID;
    }

    public void onReceiveData(String serviceUUID, String characteristicUUID, byte[] data) {
        try {
            if (this.serviceUUID.equals(serviceUUID)
                    && this.characteristicUUID.equals(characteristicUUID)) {
                onReceiveData(data);
            }
        } catch (Exception e) {
            Log.e("lyh", e.toString());
        }
    }

    public abstract void onReceiveData(byte[] data);
}

