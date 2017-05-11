package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class HuntingLineRespond extends RJ25Respond {
    public final float line;

    public HuntingLineRespond(float line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return super.toString() + ",巡线:" + line;
    }
}
