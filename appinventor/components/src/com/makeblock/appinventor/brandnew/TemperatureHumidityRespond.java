package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class TemperatureHumidityRespond extends RJ25Respond {
    public final float value;

    public TemperatureHumidityRespond(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() + ",温度:" + value;
    }
}
