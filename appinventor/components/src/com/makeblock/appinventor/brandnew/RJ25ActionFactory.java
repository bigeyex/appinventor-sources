package com.makeblock.appinventor.brandnew;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xuexin on 2017/3/29.
 */

public class RJ25ActionFactory {

    public static final int ACTION_MOTOR = 1;
    public static final int ACTION_BUZZER = 1 << 1;
    public static final int ACTION_LED = 1 << 2;
    public static final int ACTION_READ_LIGHT_SENSOR = 1 << 3;
    public static final int ACTION_READ_ULTRASONIC = 1 << 4;
    public static final int ACTION_READ_SOUNDNESS = 1 << 5;
    public static final int ACTION_DISPLAY_FACE = 1 << 6;
    public static final int ACTION_READ_LINE_FOLLOW = 1 << 7;
    public static final int ACTION_READ_TEMPERATURE_HUMIDITY = 1 << 8;
    public static final int ACTION_READ_TOUCH_SENSOR = 1 << 9;
    public static final int ACTION_READ_REMOTE_CONTROLLER = 1 << 10;
    public static final int ACTION_READ_GAS_SENSOR = 1 << 11;
    public static final int ACTION_READ_FLAME_SENSOR = 1 << 12;
    public static final int ACTION_READ_BUTTON_SENSOR = 1 << 13;
    public static final int ACTION_READ_TEMPERATURE_SENSOR = 1 << 14;
    public static final int ACTION_READ_JOYSTICK_SENSOR = 1 << 15;
    public static final int ACTION_READ_POTENTIOMETER_SENSOR = 1 << 16;
    public static final int ACTION_DIGITAL_SERVO = 1 << 17;
    public static final int ACTION_7_SEGMENTS = 1 << 18;
    public static final int ACTION_COMPASS_SENSOR = 1 << 19;
    public static final int ACTION_READ_LIMIT_SWITCH_SENSOR = 1 << 20;
    public static final int ACTION_9G_servo = 1 << 21;
    public static final int ACTION_MINI_FAN = 1 << 22;
//    public static final int ACTION_DANCE = ACTION_MOTOR | ACTION_BUZZER;

    public static final short MAX_SPEED_JOYSTICK = 255;
    public static final short MAX_SPEED_SPRINT = 255;

    public static Action createJoystickAction(short speed1, short speed2) {
        Instruction instruction = RJ25InstructionFactory.createJoystickInstruction(speed1, speed2);
        return createSingleAction(ACTION_MOTOR, Action.DURATION_IMMEDIATELY, instruction, null, null);
    }

    public static Action createSprintSkillAction() {
        Instruction goForwardInstruction = RJ25InstructionFactory.createJoystickInstruction((short) -MAX_SPEED_SPRINT, MAX_SPEED_SPRINT);
        Instruction stopInstruction = RJ25InstructionFactory.createJoystickInstruction((short) 0, (short) 0);
        List<InstructionWrap> sprintInstructionList = new ArrayList<InstructionWrap>();
        sprintInstructionList.add(RJ25InstructionFactory.createInstructionWrap(goForwardInstruction, (long) 3000));
        return createOrderedAction(ACTION_MOTOR,
                sprintInstructionList,
                new Instruction[]{stopInstruction},
                new Instruction[]{stopInstruction});
    }

    public static Action createSpinSkillAction() {
        Instruction spinInstruction = RJ25InstructionFactory.createJoystickInstruction((short) -MAX_SPEED_SPRINT, (short) -MAX_SPEED_SPRINT);
        Instruction spinFinishInstruction = RJ25InstructionFactory.createJoystickInstruction((short) 0, (short) 0);
        List<InstructionWrap> spinInstructionList = new ArrayList<InstructionWrap>();
        spinInstructionList.add(RJ25InstructionFactory.createInstructionWrap(spinInstruction, (long) 3000));
        return createOrderedAction(ACTION_MOTOR,
                spinInstructionList,
                new Instruction[]{spinFinishInstruction},
                new Instruction[]{spinFinishInstruction});
    }

    public static Action createShakeSkillAction() {
        Instruction shakeLeftInstruction = RJ25InstructionFactory.createJoystickInstruction(MAX_SPEED_SPRINT, MAX_SPEED_SPRINT);
        Instruction shakeRightInstruction = RJ25InstructionFactory.createJoystickInstruction((short) -MAX_SPEED_SPRINT, (short) -MAX_SPEED_SPRINT);
        Instruction shakeFinishInstruction = RJ25InstructionFactory.createJoystickInstruction((short) 0, (short) 0);
        List<InstructionWrap> shakeInstructionList = new ArrayList<InstructionWrap>();
        shakeInstructionList.add(RJ25InstructionFactory.createInstructionWrap(shakeLeftInstruction, (long) 500));
        shakeInstructionList.add(RJ25InstructionFactory.createInstructionWrap(shakeRightInstruction, (long) 1000));
        shakeInstructionList.add(RJ25InstructionFactory.createInstructionWrap(shakeLeftInstruction, (long) 500));
        return createOrderedAction(ACTION_MOTOR,
                shakeInstructionList,
                new Instruction[]{shakeFinishInstruction},
                new Instruction[]{shakeFinishInstruction});
    }

    public static Action createTurnOnLedAction() {
        Instruction ledTurnOnInstruction = RJ25InstructionFactory.createLedInstruction((byte)7, (byte)2, (byte) 0, (byte) 100, (byte) 100, (byte) 100);
        Instruction ledCancelInstruction = RJ25InstructionFactory.createLedInstruction((byte)7, (byte)2, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
        return createSingleAction(ACTION_LED,
                Action.DURATION_INFINITE,
                ledTurnOnInstruction,
                null,
                ledCancelInstruction);
    }

    public static Action createTurnOffLedAction() {
        Instruction ledTurnOffInstruction = RJ25InstructionFactory.createLedInstruction((byte)7, (byte)2, (byte) 0, (byte) 0, (byte) 0, (byte) 0);
        return createSingleAction(ACTION_LED,
                Action.DURATION_IMMEDIATELY,
                ledTurnOffInstruction,
                null,
                null);
    }

    public static Action createBuzzerAction() {
        short one_of_four = new BuzzerTempo(1000).ONE_OF_FOUR;
        Instruction buzzerInstruction = RJ25InstructionFactory.createBuzzerInstruction((byte) -1, BuzzerTone.C5, one_of_four);
        return createSingleAction(ACTION_BUZZER, Action.DURATION_IMMEDIATELY, buzzerInstruction, null, null);
    }

    public static Action createPianoKeyAction(BuzzerTone tone) {
        short one_of_four = new BuzzerTempo(1000).ONE_OF_FOUR;
        Instruction instruction = RJ25InstructionFactory.createBuzzerInstruction((byte) -1, tone, one_of_four);
        return createSingleAction(ACTION_BUZZER, Action.DURATION_IMMEDIATELY, instruction, null, null);
    }

    public static Action createBuzzerFrequencyAction(int port, int frequency, short duration) {
        return createSingleAction(ACTION_BUZZER, Action.DURATION_IMMEDIATELY, RJ25InstructionFactory.createBuzzerFrequencyInstruction((byte) port, frequency, duration), null, null);
    }

    public static Action createBuzzerCustomAction(byte port, BuzzerTone tone, BuzzerTempo tempo) {
        return new SingleAction(ACTION_BUZZER, 0, new BuzzerInstruction(port, tone, tempo.ONE), null, null);
    }

    public static Action createLedAction(int port, int slot, int index, int r, int g, int b) {
        return new SingleAction(ACTION_LED,
                0,
                RJ25InstructionFactory.createLedInstruction((byte) port, (byte) slot, (byte) index, (byte) r, (byte) g, (byte) b),
                null,
                RJ25InstructionFactory.createLedInstruction((byte) port, (byte) slot, (byte) 0, (byte) 0, (byte) 0, (byte) 0));
    }

    public static Action createDCMotorAction(int leftSpeed, int rightSpeed) {
        Instruction leftInstruction = RJ25InstructionFactory.createDCMotorInstruction(DCMotorInstruction.DC_MOTOR_LEFT, (short) leftSpeed);
        Instruction rightInstruction = RJ25InstructionFactory.createDCMotorInstruction(DCMotorInstruction.DC_MOTOR_RIGHT, (short) rightSpeed);
        InstructionWrap leftInstructionWrap = RJ25InstructionFactory.createInstructionWrap(leftInstruction, 0);
        InstructionWrap rightInstructionWrap = RJ25InstructionFactory.createInstructionWrap(rightInstruction, 0);
        List<InstructionWrap> list = new ArrayList<InstructionWrap>();
        list.add(leftInstructionWrap);
        list.add(rightInstructionWrap);
        return new OrderedAction(ACTION_MOTOR, list, null, null);
    }

    public static Action createDigitalServoAction(int port, int slot, int angle) {
        return new SingleAction(ACTION_DIGITAL_SERVO, 0, RJ25InstructionFactory.createDigitalServoInstruction((byte) port, (byte) slot, (byte) angle), null, null);
    }

    public static Action create9gServoAction(int port, int slot, int angle) {
        return new SingleAction(ACTION_9G_servo, 0, RJ25InstructionFactory.create9GServoInstruction((byte) port, (byte) slot, (byte) angle), null, null);
    }

    public static Action createMiniFanAction(int port, int action) {
        return new SingleAction(ACTION_MINI_FAN, 0, RJ25InstructionFactory.createMiniFanInstruction((byte) port, (byte) action), null, null);
    }

    public static Action createEncoderMotorAction(int port, int slot, int speed) {
        return new SingleAction(ACTION_MOTOR, 0, RJ25InstructionFactory.createEncoderMotorInstruction((byte) port, (byte) slot, (short) speed), null, null);
    }

    public static Action createEncoderMotorRotateAngleAction(int slot, int speed, float angle) {
        return new SingleAction(ACTION_MOTOR, 0, RJ25InstructionFactory.createEncoderMotorRotateAngleInstruction((byte) slot, (short) speed, angle), null, null);
    }

    public static Action createStepMotorAction(int port, short speed, int step) {
        return new SingleAction(ACTION_MOTOR, 0, RJ25InstructionFactory.createStepMotorInstruction((byte) port, speed, step), null, null);
    }

    public static Action createOnBoardEncoderMotorPositionAction(int slot, long position, int speed) {
        return new SingleAction(ACTION_MOTOR, 0, RJ25InstructionFactory.createOnBoardEncoderMotorPositionInstruction((byte) slot, position, (short) speed), null, null);
    }

    public static Action createOnBoardEncoderMotorPositionResetAction(int slot) {
        return new SingleAction(ACTION_MOTOR, 0, RJ25InstructionFactory.createOnBoardEncoderMotorPositionResetInstruction((byte) slot), null, null);
    }

    public static Action createQueryUltrasonicInfiniteAction(int port) {
        return new InfiniteAction(ACTION_READ_ULTRASONIC, 200, RJ25InstructionFactory.createQueryUltrasonicInstruction((byte) port), null);
    }

    public static Action createQueryLightnessInfiniteAction(int port) {
        return new InfiniteAction(ACTION_READ_LIGHT_SENSOR, 200, RJ25InstructionFactory.createQueryLightnessInstruction((byte) port), null);
    }

    public static Action createDisplayMessageOnLEDPanelAction(int port, int x, int y, byte[] message) {
        return new SingleAction(ACTION_DISPLAY_FACE, 0, RJ25InstructionFactory.createFaceCharInstruction((byte) port, (byte) x, (byte) y, message), null, null);
    }

    public static Action createDisplaySmilingOnLEDPanelAction(int port, int x, int y) {
        return new SingleAction(ACTION_DISPLAY_FACE, 0, RJ25InstructionFactory.createFaceSmilingInstruction(
                (byte) port,
                (byte) x,
                (byte) y,
                new byte[]{0x00, 0x00, 0x40, 0x48, 0x44, 0x42, 0x02, 0x02, 0x02, 0x02, 0x42, 0x44, 0x48, 0x40, 0x00, 0x00}),
                null,
                null);
    }

    public static Action createDisplayTimeOnLEDPanelAction(int port, int hour, int minute) {
        return new SingleAction(ACTION_DISPLAY_FACE, 0, RJ25InstructionFactory.createFaceTimeInstruction((byte) port, (byte) hour, (byte) minute), null, null);
    }

    public static Action createDisplayDigitsOnLEDPanelAction(int port, float digits) {
        return new SingleAction(ACTION_DISPLAY_FACE, 0, RJ25InstructionFactory.createFaceDigitalInstruction((byte) port, digits), null, null);
    }

    public static Action createDisplayNumberOn7Segments(int port, float number) {
        return new SingleAction(ACTION_7_SEGMENTS, 0, RJ25InstructionFactory.create7SegmentsInstruction((byte) port, number), null, null);
    }

    public static Action createQueryHuntingLineAction(int port) {
        return new InfiniteAction(ACTION_READ_LINE_FOLLOW, 200, RJ25InstructionFactory.createLineFollowerInstruction((byte) port), null);
    }

    public static Action createQueryVolumeAction(int port) {
        return new InfiniteAction(ACTION_READ_SOUNDNESS, 200, RJ25InstructionFactory.createQueryVolumeInstruction((byte) port), null);
    }

    public static Action createQueryTemperatureHumidity_temperatureAction(int port) {
        return new InfiniteAction(ACTION_READ_TEMPERATURE_HUMIDITY, 200, RJ25InstructionFactory.createQueryTemperatureHumidity_temperatureInstruction((byte) port), null);
    }

    public static Action createQueryTemperatureHumidity_humidityAction(int port) {
        return new InfiniteAction(ACTION_READ_TEMPERATURE_HUMIDITY, 200, RJ25InstructionFactory.createQueryTemperatureHumidity_humidityInstruction((byte) port), null);
    }

    public static Action createQueryTouchSensorAction(int port) {
        return new InfiniteAction(ACTION_READ_TOUCH_SENSOR, 200, RJ25InstructionFactory.createQueryTouchSensorInstruction((byte) port), null);
    }

    public static Action createQueryBoardKeyStatusAction(int port, int status) {
        return new InfiniteAction(ACTION_READ_REMOTE_CONTROLLER, 200, RJ25InstructionFactory.createQueryBoardKeyStatusInstruction((byte) port, (byte) status), null);
    }

    public static Action createQueryGasSensorAction(int port) {
        return new InfiniteAction(ACTION_READ_GAS_SENSOR, 200, RJ25InstructionFactory.createQueryGasSensorInstruction((byte) port), null);
    }

    public static Action createQueryFlameSensorAction(int port) {
        return new InfiniteAction(ACTION_READ_FLAME_SENSOR, 200, RJ25InstructionFactory.createQueryFlameSensorInstruction((byte) port), null);
    }

    public static Action createQueryButtonSensorAction(int port, int index) {
        return new InfiniteAction(ACTION_READ_BUTTON_SENSOR, 200, RJ25InstructionFactory.createQueryButtonSensorInstruction((byte) port, (byte) index), null);
    }

    public static Action createQueryTemperatureSensorAction(int port, int slot) {
        return new InfiniteAction(ACTION_READ_TEMPERATURE_SENSOR, 200, RJ25InstructionFactory.createQueryTemperatureSensorInstruction((byte) port, (byte) slot), null);
    }

    public static Action createQueryJoystickSensorAction(int port, int axle) {
        return new InfiniteAction(ACTION_READ_JOYSTICK_SENSOR, 200, RJ25InstructionFactory.createQueryJoystickSensorInstruction((byte) port, (byte) axle), null);
    }

    public static Action createQueryPotentiometerSensorAction(int port) {
        return new InfiniteAction(ACTION_READ_POTENTIOMETER_SENSOR, 200, RJ25InstructionFactory.createQueryPotentiometerSensorInstruction((byte) port), null);
    }

    public static Action createQueryCompassSensorAction(int port) {
        return new InfiniteAction(ACTION_COMPASS_SENSOR, 200, RJ25InstructionFactory.createCompassInstruction((byte) port), null);
    }

    public static Action createQueryLimitSwitchSensorAction(int port, int slot) {
        return new InfiniteAction(ACTION_READ_LIMIT_SWITCH_SENSOR, 200, RJ25InstructionFactory.createLimitSwitchInstruction((byte) port, (byte) slot), null);
    }

    private static Action createOrderedAction(int actionIndex, List<InstructionWrap> instructionList, Instruction[] cancelInstructions, Instruction[] finishInstructions) {
        return new OrderedAction(actionIndex,
                instructionList,
                cancelInstructions,
                finishInstructions);
    }

    private static Action createSingleAction(int actionIndex, long duration, Instruction executeInstruction, Instruction finishInstruction, Instruction cancelInstruction) {
        return new SingleAction(actionIndex, duration, executeInstruction, finishInstruction, cancelInstruction);
    }

    public static boolean isActionConflict(Action action1, Action action2) {
        int i = (action1.getIndex() & action2.getIndex());
        return (action1.getIndex() & action2.getIndex()) != 0;
    }

}
