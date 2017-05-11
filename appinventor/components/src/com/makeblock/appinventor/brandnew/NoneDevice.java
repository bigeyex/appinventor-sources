package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/17.
 * 没有连上设备
 */

public class NoneDevice extends Device {

    public NoneDevice() {
        super("", "", new FirmwareRespond(""));
    }

    @Override
    protected RespondParser createInstructionParser() {
        return null;
    }
}
