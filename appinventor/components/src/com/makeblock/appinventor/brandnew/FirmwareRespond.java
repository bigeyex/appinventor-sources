package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/24.
 */

public class FirmwareRespond extends RJ25Respond {
    public final String firmwareVersion;

    public FirmwareRespond(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @Override
    public String toString() {
        return super.toString() + ", 固件版本:" + firmwareVersion;
    }
}
