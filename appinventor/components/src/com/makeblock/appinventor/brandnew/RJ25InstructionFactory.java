package com.makeblock.appinventor.brandnew;


/**
 * Created by tom on 17/4/7.
 */

public class RJ25InstructionFactory {

    public static Instruction createBuzzerInstruction(byte port, BuzzerTone tone, short tempo) {
        return new BuzzerInstruction(port, tone, tempo);
    }

    public static Instruction createBuzzerFrequencyInstruction(int port, int frequency, int duration) {
        return new BuzzerFrequencyInstruction((byte) port, frequency, (short) duration);
    }

    public static Instruction createJoystickInstruction(short speed1, short speed2) {
        return new JoystickInstruction(speed1, speed2);
    }

    public static Instruction createLedInstruction(byte port, byte slot, byte ledIndex, byte r, byte g, byte b) {
        return new LedInstruction(port, slot, ledIndex, r, g, b);
    }

    public static InstructionWrap createInstructionWrap(Instruction instruction, long duration) {
        return new InstructionWrap(instruction, duration);
    }

    public static Instruction createSetModeInstruction(byte device, byte mode) {
        return new ModeInstruction(mode, device);
    }

    public static Instruction createDCMotorInstruction(byte port, short speed) {
        return new DCMotorInstruction(port, speed);
    }

    public static Instruction createQueryUltrasonicInstruction(byte port) {
        return new UltrasonicInstruction(port);
    }

    public static Instruction createQueryLightnessInstruction(byte port) {
        return new LightInstruction(port);
    }

    public static Instruction createFaceCharInstruction(byte port, byte x, byte y, byte[] chars) {
        return new FaceCharInstruction(port, x, y, chars);
    }

    public static Instruction createFaceSmilingInstruction(byte port, byte x, byte y, byte[] smileBytes) {
        return new FaceSmilingInstruction(port, x, y, smileBytes);
    }

    public static Instruction createFaceTimeInstruction(byte port, byte hour, byte minute) {
        return new FaceTimeInstruction(port, hour, minute);
    }

    public static Instruction createFaceDigitalInstruction(byte port, float digital) {
        return new FaceDigitalInstruction(port, digital);
    }

    public static Instruction createLineFollowerInstruction(byte port) {
        return new HuntingLineInstruction(port);
    }

    public static Instruction createQueryVolumeInstruction(byte port) {
        return new QueryVolumeInstruction(port);
    }

    public static Instruction createQueryTemperatureHumidity_temperatureInstruction(byte port) {
        return new QueryTemperatureHumidityInstruction(port, QueryTemperatureHumidityInstruction.TYPE_TEMPERATURE);
    }

    public static Instruction createQueryTemperatureHumidity_humidityInstruction(byte port) {
        return new QueryTemperatureHumidityInstruction(port, QueryTemperatureHumidityInstruction.TYPE_HUMIDITY);
    }

    public static Instruction createQueryTouchSensorInstruction(byte port) {
        return new QueryTouchStatusInstruction(port);
    }

    public static Instruction createQueryBoardKeyStatusInstruction(byte port, byte status) {
        return new QueryBoardKeyStatusInstruction(port, status);
    }

    public static Instruction createQueryGasSensorInstruction(byte port) {
        return new QueryGasInstruction(port);
    }

    public static Instruction createQueryFlameSensorInstruction(byte port) {
        return new QueryFlameInstruction(port);
    }

    public static Instruction createQueryButtonSensorInstruction(byte port, byte index) {
        return new QueryKeyStatusInstruction(port, index);
    }

    public static Instruction createQueryTemperatureSensorInstruction(byte port, byte slot) {
        return new QueryTemperatureInstruction(port, slot);
    }

    public static Instruction createQueryJoystickSensorInstruction(byte port, byte axle) {
        return new JoystickSensorInstruction(port, axle);
    }

    public static Instruction createQueryPotentiometerSensorInstruction(byte port) {
        return new PotentiometerInstruction(port);
    }

    public static Instruction createDigitalServoInstruction(byte port, byte slot, byte angle) {
        return new DigitalServoInstruction(port, slot, angle);
    }

    public static Instruction create7SegmentsInstruction(byte port, float number) {
        return new DigitalTubeInstruction(port, number);
    }

    public static Instruction createCompassInstruction(byte port) {
        return new ElectronicCompassInstruction(port);
    }

    public static Instruction createLimitSwitchInstruction(byte port, byte slot) {
        return new LimitSwitchInstruction(port, slot);
    }

    public static Instruction create9GServoInstruction(byte port, byte slot, byte angle) {
        return new NineGServoInstruction(port, slot, angle);
    }

    public static Instruction createMiniFanInstruction(byte port, byte action) {
        return new MiniFanInstruction(port, action);
    }

    public static Instruction createEncoderMotorInstruction(byte port, byte slot, short speed) {
        return new OnBoardEncoderMotorInstruction(port, slot, speed);
    }

    public static Instruction createOnBoardEncoderMotorPositionInstruction(byte slot, long position, short speed) {
        return new OnBoardEncoderMotorPositionInstruction(slot, position, speed);
    }

    public static Instruction createOnBoardEncoderMotorPositionResetInstruction(byte slot) {
        return new OnBoardEncoderMotorPositionResetInstruction(slot);
    }
}
