package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/24.
 */

public class RJ25FormRespond extends RJ25Respond {
    public static final int BLUETOOTH = 0;  //蓝牙,或者说手动
    public static final int OBSTACLE_AVOIDANCE = 1; //避障
    public static final int SELF_BALANCING = 2; //自平衡
    public static final int INFRARED = 3; //红外,应该是那个遥控器
    public static final int LINE_PATROL = 4; //巡线

    public final int mode;

    public RJ25FormRespond(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        String modeString;
        switch (mode) {
            case BLUETOOTH:
                modeString = "手动模式";
                break;
            case OBSTACLE_AVOIDANCE:
                modeString = "避障模式";
                break;
            case SELF_BALANCING:
                modeString = "自平衡";
                break;
            case INFRARED:
                modeString = "红外";
                break;
            case LINE_PATROL:
                modeString = "巡线";
                break;
            default:
                modeString = "";
        }
        return super.toString() + ", 模式:" + modeString;
    }
}
