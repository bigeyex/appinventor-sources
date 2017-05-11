package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class VolumeRespond extends RJ25Respond {
    public final float volume;

    public VolumeRespond(float volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return super.toString() + ",音量:" + volume;
    }
}
