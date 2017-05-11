package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class TouchStatusRespond extends RJ25Respond {
    public final int status;

    public TouchStatusRespond(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + ",触摸状态:" + status;
    }
}
