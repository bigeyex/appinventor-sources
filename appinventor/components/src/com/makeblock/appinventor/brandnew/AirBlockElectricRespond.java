package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/20.
 */

public class AirBlockElectricRespond extends NeuronRespond {
    public static final byte cmd = 0x40;

    public final float voltage;
    public final float percent;

    public AirBlockElectricRespond(float voltage, float percent) {
        this.voltage = voltage;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return super.toString() + ": 电量查询, 电压:" + voltage + ", 百分比:" + percent;
    }
}
