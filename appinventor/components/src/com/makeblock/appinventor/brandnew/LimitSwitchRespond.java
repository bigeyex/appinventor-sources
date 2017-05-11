package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class LimitSwitchRespond extends RJ25Respond {
    public final float limitingStopper;

    public LimitSwitchRespond(float limitingStopper) {
        this.limitingStopper = limitingStopper;
    }

    @Override
    public String toString() {
        return super.toString() + ",限位器:" + limitingStopper;
    }
}
