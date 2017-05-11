package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class ElectronicCompassRespond extends RJ25Respond {
    public final float compass;

    public ElectronicCompassRespond(float compass) {
        this.compass = compass;
    }

    @Override
    public String toString() {
        return super.toString() + ",电子罗盘:" + compass;
    }
}
