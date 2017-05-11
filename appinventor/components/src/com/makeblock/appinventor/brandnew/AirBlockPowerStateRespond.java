package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/20.
 */

public class AirBlockPowerStateRespond extends NeuronRespond {
    public static final byte cmd = 0x03;
    public static final int STATE_ON = 0x1;
    public static final int STATE_OFF = 0x00;

    public final int mode;

    public AirBlockPowerStateRespond(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        String modeString;
        switch (mode) {
            case STATE_ON:
                modeString = "打开";
                break;
            case STATE_OFF:
                modeString = "关闭";
                break;
            default:
                modeString = null;
        }
        return super.toString() + ", Airblock运行状态, 状态:" + modeString;
    }
}
