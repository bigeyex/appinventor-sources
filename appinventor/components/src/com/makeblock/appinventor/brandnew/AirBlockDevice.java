package com.makeblock.appinventor.brandnew;

import android.util.Log;

import com.google.common.annotations.VisibleForTesting;
import com.makeblock.appinventor.brandnew.AirBlockElectricRespond;
import com.makeblock.appinventor.brandnew.AirBlockPowerStateRespond;
import com.makeblock.appinventor.brandnew.FirmwareRespond;
import com.makeblock.appinventor.brandnew.NeuronDevice;
import com.makeblock.appinventor.brandnew.NeuronRespond;

/**
 * Created by hupihuai on 17/4/13.
 */

public class AirBlockDevice extends NeuronDevice {

    private int rssi;
    private AirBlockElectricRespond batteryRespond;
    private AirBlockPowerStateRespond powerStateRespond;
    private OnReceivePowerStateListener onReceivePowerStateListener;

    public AirBlockDevice(FirmwareRespond firmwareRespond) {
        super("crystal", "Airblock", firmwareRespond);
    }

    @Override
    protected void handleRespond(NeuronRespond neuronRespond) {
        Log.d("BLE", "收到指令：" + neuronRespond);
        if (neuronRespond instanceof AirBlockElectricRespond) {
            this.batteryRespond = (AirBlockElectricRespond) neuronRespond;
        }
        if (neuronRespond instanceof AirBlockPowerStateRespond) {
            this.powerStateRespond = (AirBlockPowerStateRespond) neuronRespond;
            onReceivePowerStateListener.onReceivePowerState(powerStateRespond.mode == AirBlockPowerStateRespond.STATE_ON);
        }
    }

    @VisibleForTesting
    public void setBatteryRespond(AirBlockElectricRespond respond) {
        this.batteryRespond = respond;
    }

    public float getBattery() {
        if (batteryRespond != null) {
            return batteryRespond.percent;
        }
        return 0;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }

    public void setOnReceivePowerStateListener(OnReceivePowerStateListener onReceivePowerStateListener) {
        this.onReceivePowerStateListener = onReceivePowerStateListener;
    }

    public interface OnReceivePowerStateListener {
        void onReceivePowerState(boolean powerState);
    }
}
