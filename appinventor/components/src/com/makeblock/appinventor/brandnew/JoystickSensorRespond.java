package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 2017/4/28.
 */

public class JoystickSensorRespond extends RJ25Respond {
    public float xValue;
    public float yValue;

    public JoystickSensorRespond(float value, byte axle) {
        if (axle == 0x01) {
            xValue = value;
        } else if (axle == 0x02){
            yValue = value;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ",摇杆:" + "X:" + xValue + " Y:" + yValue;
    }
}
