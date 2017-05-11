package com.makeblock.appinventor.brandnew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 17/4/19.
 */

public class AirBlockActionFactory {

    private static final int ACTION_MOTOR = 1;
    private static final int ACTION_POWER = 1 << 1;
    private static final int ACTION_SET_FORM = 1 << 2;
    private static final int ACTION_ALLOTID = 1 << 3;
    private static final int ACTION_BATTERY = 1 << 4;
    private static final int ACTION_REQUEST_POWER_STATE = 1 << 5;
    private static final int ACTION_CALIBRATION = 1 << 6;

    public static final int CONTROL_BALANCE = 0;
    public static final int CONTROL_UP = 1;
    public static final int CONTROL_DOWN = 2;
    public static final int CONTROL_FORWARD = 3;
    public static final int CONTROL_BACKWARD = 4;
    public static final int CONTROL_LEFT = 5;
    public static final int CONTROL_RIGHT = 6;
    public static final int CONTROL_ROTATE_LEFT = 7;
    public static final int CONTROL_ROTATE_RIGHT = 8;

    private static final short MAX_SPEED_LAND = 2000;
    private static final short MAX_SPEED_SHIP = 1600;
    private static final short MAX_SPEED_AIR = 1600;
    private static final short HOVER_SPEED_LAND = 1400;
    private static final short HOVER_SPEED_SHIP = 0;
    private static final short HOVER_SPEED_AIR = 0;


    public static Action createAllotIdAction() {
        return new InfiniteAction(ACTION_ALLOTID, 500, NeuronInstructionFactory.createAirBlockAllotIdInstruction(), null);
    }

    public static Action createBatteryAction() {
        return new InfiniteAction(ACTION_BATTERY, 1000, NeuronInstructionFactory.createAirBlockBatteryInstruction(), null);
    }

    public static Action createPowerSwitchAction(boolean powerOn) {
        AirBlockInstruction instruction;
        if (powerOn) {
            instruction = NeuronInstructionFactory.createAirBlockLaunchInstruction();
        } else {
            instruction = NeuronInstructionFactory.createAirBlockStopInstruction();
        }
        return new SingleAction(ACTION_POWER, 0, instruction, null, null);
    }

    public static Action createBalanceAction() {
        Instruction instruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BALANCE, 1);
        return new SingleAction(ACTION_MOTOR, 0, instruction, null, null);
    }

    public static Action createTakeOffAction(int seconds) {
        AirBlockInstruction instructionUp = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_UP, 1);
        AirBlockInstruction instructionStop = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BALANCE, 1);
        InstructionWrap instructionUpWrap = new InstructionWrap(instructionUp, seconds);
        InstructionWrap instructionStopWrap = new InstructionWrap(instructionStop, 0);
        List<InstructionWrap> list = new ArrayList<InstructionWrap>();
        list.add(instructionUpWrap);
        list.add(instructionStopWrap);
        return new OrderedAction(ACTION_MOTOR, list, null, null);
    }

    public static Action createAirVerticalJoystickAction(int control) {
        AirBlockInstruction instruction;
        switch (control) {
            case CONTROL_ROTATE_LEFT:
                instruction = NeuronInstructionFactory.createAirBlockControlWordRotateLeftInstruction(NeuronInstructionFactory.AIRBLOCK_AIR_ROTATE_LEFT_ANGLE, 0);
                break;
            case CONTROL_ROTATE_RIGHT:
                instruction = NeuronInstructionFactory.createAirBlockControlWordRotateRightInstruction(NeuronInstructionFactory.AIRBLOCK_AIR_ROTATE_RIGHT_ANGLE, 0);
                break;
            case CONTROL_UP:
                instruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_UP, 1);
                break;
            case CONTROL_DOWN:
            default:
                instruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_DOWN, 1);
                break;
        }
        return new SingleAction(ACTION_MOTOR, 0, instruction, null, null);
    }

    public static Action createAirHorizontalJoystickAction(int control, float percentR) {
        AirBlockInstruction initInstruction;
        AirBlockInstruction speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction((short) (percentR * MAX_SPEED_AIR), HOVER_SPEED_AIR);
        switch (control) {
            case CONTROL_FORWARD:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 1);
                break;
            case CONTROL_BACKWARD:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BACKWARD, 1);
                break;
            case CONTROL_LEFT:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_LEFT, 1);
                break;
            case CONTROL_RIGHT:
            default:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_RIGHT, 1);
                break;
        }
        return createOrderedAction(initInstruction, speedInstruction);
    }

    public static Action createLandTurnJoystickAction(int control, int angle) {
        AirBlockInstruction initInstruction;
        AirBlockInstruction speedInstruction;
        switch (control) {
            case CONTROL_ROTATE_LEFT:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordRotateLeftInstruction(angle, 0);
                break;
            case CONTROL_ROTATE_RIGHT:
            default:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordRotateRightInstruction(angle, 0);
                break;
        }
        speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction(MAX_SPEED_LAND, HOVER_SPEED_LAND);
        return createOrderedAction(initInstruction, speedInstruction);
    }

    public static Action createLandMoveJoystickAction(int control, float percentR) {
        AirBlockInstruction initInstruction;
        switch (control) {
            case CONTROL_FORWARD:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 1);
                break;
            case CONTROL_BACKWARD:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BACKWARD, 1);
                break;
            case CONTROL_LEFT:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_LEFT, 1);
                break;
            case CONTROL_RIGHT:
            default:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_RIGHT, 1);
                break;
        }
        AirBlockInstruction speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction((short) (MAX_SPEED_LAND * percentR), HOVER_SPEED_LAND);
        return createOrderedAction(initInstruction, speedInstruction);
    }

    public static Action createWaterTurnJoystickAction(int control, int angle) {
        AirBlockInstruction initInstruction;
        switch (control) {
            case CONTROL_ROTATE_LEFT:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordRotateLeftInstruction(angle, 0);
                break;
            case CONTROL_ROTATE_RIGHT:
            default:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordRotateRightInstruction(angle, 0);
                break;
        }
        AirBlockInstruction speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction(MAX_SPEED_SHIP, HOVER_SPEED_SHIP);
        return createOrderedAction(initInstruction, speedInstruction);
    }

    public static Action createWaterMoveJoystickAction(int control, float percentR) {
        AirBlockInstruction initInstruction;
        switch (control) {
            case CONTROL_FORWARD:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 1);
                break;
            case CONTROL_BACKWARD:
            default:
                initInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BACKWARD, 1);
                break;
        }
        AirBlockInstruction speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction((short) (MAX_SPEED_SHIP * percentR), HOVER_SPEED_SHIP);
        return createOrderedAction(initInstruction, speedInstruction);
    }

    public static Action createShakeSkillAction() {
        AirBlockInstruction instruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_SHAKE, 0.5f);
        return new SingleAction(AirBlockActionFactory.ACTION_MOTOR, 0, instruction, null, null);
    }

    public static Action createRollSkill() {
        AirBlockInstruction instruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_ROLL, 0);
        return new SingleAction(AirBlockActionFactory.ACTION_MOTOR, 0, instruction, null, null);
    }

    public static Action createCircleSkill() {
        AirBlockInstruction instruction = NeuronInstructionFactory.createAirBlockCircleSkillInstruction();
        return new SingleAction(AirBlockActionFactory.ACTION_MOTOR, 0, instruction, null, null);
    }

    public static Action createShiftAction() {
        AirBlockInstruction speedInstruction = NeuronInstructionFactory.createAirBlockSpeedInstruction(AirBlockActionFactory.MAX_SPEED_LAND, AirBlockActionFactory.HOVER_SPEED_LAND);
        AirBlockInstruction forwardInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_FORWARD, 1.5f);
        AirBlockInstruction turnInstruction = NeuronInstructionFactory.createAirBlockControlWordRotateRightInstruction(150, 1.2f);
        AirBlockInstruction stopInstruction = NeuronInstructionFactory.createAirBlockControlWordInstruction(AirBlockControlWordInstruction.WORD_BALANCE, 0);

        InstructionWrap speedInstructionWrap = new InstructionWrap(speedInstruction, 0);
        InstructionWrap forwardInstructionWrap = new InstructionWrap(forwardInstruction, (long) 1200);
        InstructionWrap turnInstructionWrap = new InstructionWrap(turnInstruction, (long) 1200);
        InstructionWrap stopInstructionWrap = new InstructionWrap(stopInstruction, 0);
        List<InstructionWrap> list = new ArrayList<InstructionWrap>();
        list.add(speedInstructionWrap);
        list.add(forwardInstructionWrap);
        list.add(speedInstructionWrap);
        list.add(turnInstructionWrap);
        list.add(stopInstructionWrap);
        return new OrderedAction(AirBlockActionFactory.ACTION_MOTOR, list, null, null);
    }

    public static Action createSMoveAction() {
        AirBlockControlWordListInstruction instruction1 = new AirBlockControlWordListInstruction(AirBlockControlWordListInstruction.WORD_LEFT, -10, 0.4f, 0, 0, 0, 0);
        AirBlockControlWordListInstruction instruction2 = new AirBlockControlWordListInstruction(AirBlockControlWordListInstruction.WORD_FORWARD, 80, 0.7f, 0, 0, 0, 0);
        AirBlockControlWordListInstruction instruction3 = new AirBlockControlWordListInstruction(AirBlockControlWordListInstruction.WORD_FORWARD, -120, 0.8f, 0, 0, 0, 0);
        AirBlockControlWordListInstruction instruction4 = new AirBlockControlWordListInstruction(AirBlockControlWordListInstruction.WORD_FORWARD, 120, 1, 0, 0, 0, 0);
        InstructionWrap instruction1Wrap = new InstructionWrap(instruction1, 100);
        InstructionWrap instruction2Wrap = new InstructionWrap(instruction2, 100);
        InstructionWrap instruction3Wrap = new InstructionWrap(instruction3, 100);
        InstructionWrap instruction4Wrap = new InstructionWrap(instruction4, 100);
        List<InstructionWrap> list = new ArrayList<InstructionWrap>();
        list.add(instruction1Wrap);
        list.add(instruction2Wrap);
        list.add(instruction3Wrap);
        list.add(instruction4Wrap);
        return new OrderedAction(ACTION_MOTOR, list, null, null);
    }

    private static Action createOrderedAction(AirBlockInstruction instruction1, AirBlockInstruction instruction2) {
        InstructionWrap initInstructionWrap = new InstructionWrap(instruction1, 0);
        InstructionWrap speedInstructionWrap = new InstructionWrap(instruction2, 0);
        List<InstructionWrap> list = new ArrayList<InstructionWrap>();
        list.add(speedInstructionWrap);
        list.add(initInstructionWrap);
        return new OrderedAction(ACTION_MOTOR, list, null, null);
    }

    public static Action createLandingAction() {
        return new SingleAction(ACTION_MOTOR, 0, NeuronInstructionFactory.createAirBlockLandingInstruction(), null, null);
    }

    public static Action createSetFormToAirAction() {
        return new SingleAction(ACTION_SET_FORM, 0, NeuronInstructionFactory.createAirBlockSetFormInstruction(AirBlockSetFormInstruction.FORM_AIR), null, null);
    }

    public static Action createSetFormToLandAction() {
        return new SingleAction(ACTION_SET_FORM, 0, NeuronInstructionFactory.createAirBlockSetFormInstruction(AirBlockSetFormInstruction.FORM_CAR), null, null);
    }

    public static Action createSetFormToWaterAction() {
        return new SingleAction(ACTION_SET_FORM, 0, NeuronInstructionFactory.createAirBlockSetFormInstruction(AirBlockSetFormInstruction.FORM_SHIP), null, null);
    }

    public static Action createAirBlockCalibrationAction() {
        return new SingleAction(ACTION_CALIBRATION, 0, NeuronInstructionFactory.createAirBlockCalibrationInstruction(), null, null);
    }

    public static Action createRequestPowerStateAction() {
        return new InfiniteAction(ACTION_REQUEST_POWER_STATE, 500, NeuronInstructionFactory.createAirBlockRequestPowerStateInstruction(), null);
    }

}
