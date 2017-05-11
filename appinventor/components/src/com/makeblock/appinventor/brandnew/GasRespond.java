package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class GasRespond extends RJ25Respond {
    public final float gas;

    public GasRespond(float gas) {
        this.gas = gas;
    }

    @Override
    public String toString() {
        return super.toString() + ",气体:" + gas;
    }
}
