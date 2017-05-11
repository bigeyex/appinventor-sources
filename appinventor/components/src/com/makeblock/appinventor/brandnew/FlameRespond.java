package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class FlameRespond extends RJ25Respond {
    public final float flame;

    public FlameRespond(float flame) {
        this.flame = flame;
    }

    @Override
    public String toString() {
        return super.toString() + ",火焰:" + flame;
    }
}
