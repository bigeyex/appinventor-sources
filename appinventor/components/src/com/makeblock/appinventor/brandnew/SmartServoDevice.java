package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 17/4/13.
 */

public class SmartServoDevice extends NeuronDevice {


    public SmartServoDevice(FirmwareRespond firmwareRespond) {
        super("octopus", "Smart Servo", firmwareRespond);
    }

    @Override
    protected void handleRespond(NeuronRespond rj25Respond) {

    }
}
