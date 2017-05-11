package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class DigitalPinRespond extends RJ25Respond {
    public final float pin;

    public DigitalPinRespond(float pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return super.toString() + ",数字管脚:" + pin;
    }
}
