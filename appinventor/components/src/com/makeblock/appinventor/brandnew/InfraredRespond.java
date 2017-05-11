package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class InfraredRespond extends RJ25Respond {
    public final float infrared;

    public InfraredRespond(float infrared) {
        this.infrared = infrared;
    }

    @Override
    public String toString() {
        return super.toString() + ",红外:" + infrared;
    }
}
