package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class UltrasonicRespond extends RJ25Respond {
    public final float distance;

    public UltrasonicRespond(float distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return super.toString() + ",距离:" + distance;
    }
}
