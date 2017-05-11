package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class KeyRespond extends RJ25Respond {
    public final int status;

    public KeyRespond(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + ",按键状态:" + status;
    }
}
