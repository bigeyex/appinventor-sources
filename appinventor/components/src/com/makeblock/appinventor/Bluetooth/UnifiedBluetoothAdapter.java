package com.google.appinventor.components.runtime.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import java.util.Arrays;

/**
 * Created by liaoyuhao on 2016/7/15.
 */
public abstract class UnifiedBluetoothAdapter {


    protected int mConnectionState = UnifiedBluetoothManager.STATE_DISCONNECTED;

    public abstract boolean isSupport();

    public abstract boolean isEnable();

    public abstract void startDiscovery();

    public abstract void stopDiscovery();

    public abstract void connect(DeviceBean bluetoothDevice);

    public abstract void disconnect();

    protected StateChangeListener listener;

    public abstract boolean isDiscovering();

    public void setConnectedStateListener(StateChangeListener listener) {
        this.listener = listener;
    }

    public abstract void write(byte[] bytes);

    public abstract int getRssi();

    public interface StateChangeListener {
        void onConnectedDevice(DeviceBean bluetoothDevice);

        void onDisconnectDevice();

        void onReceiveData(byte[] data);

        void onFoundDevice(DeviceBean device);

        void onSearchEvent(int type);
    }


    protected void transmitSearchEvent(int type) {
        if (listener != null) {
            listener.onSearchEvent(type);
        }
    }

    protected boolean isOurDevice(BluetoothDevice device) {
        //name: 1.不能为空  2.包含Makeblock
        if (TextUtils.isEmpty(device.getName())) {
            return false;
        }
        if (!(device.getName().contains("Makeblock")) && !(device.getName().contains("makeblock"))) {
            return false;
        }
        return true;

    }

    public int getConnectionState() {
        return mConnectionState;
    }

    protected synchronized void receiveData(byte[] buffer, int bytesCount) {
        if (listener != null) {
            byte[] data = Arrays.copyOf(buffer, bytesCount);
            listener.onReceiveData(data);
        }
    }

}
