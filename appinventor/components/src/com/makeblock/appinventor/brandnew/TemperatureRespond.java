package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class TemperatureRespond extends RJ25Respond {
    public final float temperature;

    public TemperatureRespond(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString() + ",温度:" + temperature;
    }
}
