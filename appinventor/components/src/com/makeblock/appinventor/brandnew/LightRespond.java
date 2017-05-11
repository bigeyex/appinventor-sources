package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class LightRespond extends RJ25Respond {
    public final float light;

    public LightRespond(float light) {
        this.light = light;
    }

    @Override
    public String toString() {
        return super.toString() + ",光线:" + light;
    }
}
