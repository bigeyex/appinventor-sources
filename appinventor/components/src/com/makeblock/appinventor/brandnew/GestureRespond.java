package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class GestureRespond extends RJ25Respond {
    public final float gesture;

    public GestureRespond(float gesture) {
        this.gesture = gesture;
    }

    @Override
    public String toString() {
        return super.toString() + ",姿态:" + gesture;
    }
}
