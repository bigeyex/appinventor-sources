package com.google.appinventor.components.runtime.Bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * 蓝牙设备的Bean
 *
 * @author liuming
 */
public class DeviceBean {

    private static final int MIN_RSSI = -10000;

    public BluetoothDevice bluetoothDevice;
    /**
     * 2.0该值为-1
     */
    public float distance;
    /**
     * 用来标记上次更新距离的时间,时间戳
     */
    public long updateTime = -1;

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        rssi_pre2 = rssi_pre1;
        rssi_pre1 = this.rssi;
        this.rssi = rssi;
    }

    private int rssi = MIN_RSSI;
    private int rssi_pre1 = MIN_RSSI;
    private int rssi_pre2 = MIN_RSSI;


    public DeviceBean(BluetoothDevice device) {
        bluetoothDevice = device;
        this.distance = -1;
        this.rssi = MIN_RSSI;
    }


    public DeviceBean(BluetoothDevice device, float distance, int rssi) {
        bluetoothDevice = device;
        this.distance = distance;
        this.rssi = rssi;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeviceBean) {
            return bluetoothDevice.equals(((DeviceBean) o).bluetoothDevice);
        }
        if (o instanceof BluetoothDevice) {
            return bluetoothDevice.equals(o);
        }
        return false;
    }

    public int getStableRssi() {
        return (rssi + rssi_pre1 + rssi_pre2) / 3;
    }

    public int getMaxRssi() {
        return Math.max(Math.max(rssi, rssi_pre2), rssi_pre1);
    }
}
