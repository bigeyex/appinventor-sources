package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class BoardKeyRespond extends RJ25Respond {
    public final int status;

    public BoardKeyRespond(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + ",板载按键状态:" + status;
    }
}
