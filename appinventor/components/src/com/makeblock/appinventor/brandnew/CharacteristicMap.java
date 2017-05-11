package com.makeblock.appinventor.brandnew;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import java.util.List;

/**
 * Created by xuexin on 2017/3/8.
 */

public class CharacteristicMap extends UuidMap<BluetoothGattCharacteristic> {

    public void setCharacteristics(List<BluetoothGattService> services) {
        if (services == null) {
            throw new RuntimeException("List of services is null");
        }
        for (BluetoothGattService bluetoothGattService : services) {
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic :
                    bluetoothGattService.getCharacteristics()) {
                String serviceUUID = bluetoothGattService.getUuid().toString();
                String characteristicUUID = bluetoothGattCharacteristic.getUuid().toString();
                put(serviceUUID, characteristicUUID, bluetoothGattCharacteristic);
            }
        }
    }
}
