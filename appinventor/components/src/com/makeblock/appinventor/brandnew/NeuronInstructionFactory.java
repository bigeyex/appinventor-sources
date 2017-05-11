package com.makeblock.appinventor.brandnew;


/**
 * Created by tom on 17/4/20.
 */

public class NeuronInstructionFactory {

    public static final int AIRBLOCK_AIR_ROTATE_LEFT_ANGLE = -10;
    public static final int AIRBLOCK_AIR_ROTATE_RIGHT_ANGLE = 10;

    public static AirBlockInstruction createAirBlockLaunchInstruction() {
        return new AirBlockLaunchInstruction();
    }

    public static AirBlockInstruction createAirBlockStopInstruction() {
        return new AirBlockStopInstruction();
    }

    public static AirBlockInstruction createAirBlockControlWordRotateLeftInstruction(int angle, float duration) {
        return new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_ROTATE, angle, duration, 0, 0, 0, 0);
    }

    public static AirBlockInstruction createAirBlockControlWordRotateRightInstruction(int angle, float duration) {
        return new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_ROTATE, angle, duration, 0, 0, 0, 0);
    }

    public static AirBlockInstruction createAirBlockControlWordInstruction(int word, float duration) {
        AirBlockInstruction instruction = null;
        switch (word) {
            case AirBlockControlWordInstruction.WORD_BACKWARD:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BACKWARD, 0, duration, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_FORWARD:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 0, duration, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_LEFT:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_LEFT, 0, duration, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_RIGHT:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_RIGHT, 0, duration, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_UP:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_UP, 0, duration, 0, 1.5f, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_DOWN:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_DOWN, 0, duration, 0, -0.8f, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_BALANCE:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BALANCE, 0, duration, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_ROLL:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_ROLL, 0, 0, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_SHAKE:
                instruction = new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_SHAKE, 20, 0.5f, 0, 0, 0, 0);
                break;
            case AirBlockControlWordInstruction.WORD_FREE:

                break;
            case AirBlockControlWordInstruction.WORD_GROUP:

                break;
            case AirBlockControlWordInstruction.WORD_HOVER:

                break;

        }

        return instruction;
    }

    public static AirBlockInstruction createAirBlockCircleSkillInstruction() {
        return new AirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 3, 360, 0, 0, 0, 0);
    }

    public static AirBlockInstruction createAirBlockLandingInstruction() {
        return new AirBlockLandingInstruction(AirBlockLandingInstruction.Landing);
    }

    public static AirBlockInstruction createAirBlockSpeedInstruction(short speed, short hoverSpeed) {
        return new AirBlockSpeedInstruction(speed, hoverSpeed, hoverSpeed, speed);
    }

    public static AirBlockInstruction createAirBlockSetFormInstruction(byte form) {
        return new AirBlockSetFormInstruction(form);
    }

    public static AirBlockInstruction createAirBlockCalibrationInstruction() {
        return new AirBlockCalibrationInstruction();
    }

    public static AirBlockInstruction createAirBlockBatteryInstruction() {
        return new AirBlockBatteryInstruction();
    }

    public static Instruction createAirBlockAllotIdInstruction() {
        return new NeuronAllotIdInstruction();
    }

    public static Instruction createAirBlockRequestPowerStateInstruction() {
        return new AirBlockRequestPowerStateInstruction();
    }

}
