package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class PotentiometerRespond extends RJ25Respond {
    public final float potentiometer;

    public PotentiometerRespond(float potentiometer) {
        this.potentiometer = potentiometer;
    }

    @Override
    public String toString() {
        return super.toString() + ",电位器:" + potentiometer;
    }
}
