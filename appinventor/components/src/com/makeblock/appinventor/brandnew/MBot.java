package com.makeblock.appinventor.brandnew;

import android.os.CountDownTimer;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

import java.io.IOException;

/**
 * Created by tom on 17/5/5.
 */
@DesignerComponent(version = 1, // have to use magic numbers since constant file cannot be motidifed
        description = "Component to control Makeblock mBot educational robot. (Note: Only Android 4.3 and above supported)",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "http://appinventor.makeblock.com/mbot-icon.png")
@SimpleObject(external = true)
public class MBot extends MakeblockBase {

    private ActionExecutor actionExecutor;

    private CountDownTimer timer = new CountDownTimer(200, 100) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            updateReadings();
        }
    };

    /**
     * Creates a new AndroidNonvisibleComponent.
     *
     * @param container the container that this component will be placed in
     */
    public MBot(ComponentContainer container) {
        super(container, "mBot");
        actionExecutor = new ActionExecutor();
        timer.start();
    }

    @SimpleFunction(description = "tell mBot to move forward in a specified speed(-255 ~ 255)")
    public void MoveForward(int speed) {
        Action action = RJ25ActionFactory.createJoystickAction((short) -speed, (short) speed);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell mBot to move backward in a specified speed(-255 ~ 255)")
    public void MoveBackward(int speed) {
        Action action = RJ25ActionFactory.createJoystickAction((short) speed, (short) -speed);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell mBot to turn left in a specified speed(-255 ~ 255)")
    public void TurnLeft(int speed) {
        Action action = RJ25ActionFactory.createJoystickAction((short) speed, (short) speed);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell mBot to turn right in a specified speed(-255 ~ 255)")
    public void TurnRight(int speed) {
        Action action = RJ25ActionFactory.createJoystickAction((short) -speed, (short) -speed);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell mBot to stop moving")
    public void StopMoving() {
        Action action = RJ25ActionFactory.createJoystickAction((short) 0, (short) 0);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "set mBot's motor speed(-255 ~ 255)")
    public void SetMotorSpeed(int leftSpeed, int rightSpeed) {
        Action action = RJ25ActionFactory.createDCMotorAction(leftSpeed, rightSpeed);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "change mBot RGB LED color (0:both, 1:right, 2:left)")
    public void SetRGBLEDOnBoardColor(int whichLight, int red, int green, int blue) {
        Action action = RJ25ActionFactory.createLedAction(7, 2, whichLight, red, green, blue);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "set LED color")
    public void setLEDColor(int port, int slot, int whichLight, int red, int green, int blue) {
        Action action = RJ25ActionFactory.createLedAction(0, 0, whichLight, red, green, blue);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "set 9g servo move to a specified angle(0 ~ 180)")
    public void set9gServoMove(int port, int slot, int angle) {
        Action action = RJ25ActionFactory.create9gServoAction(port, slot, angle);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "set mini-fan action, 1 for clockwise rotate, 0 for anticlockwise rotate")
    public void setMiniFanAction(int port, int action) {
        Action miniFanAction = RJ25ActionFactory.createMiniFanAction(port, action);
        actionExecutor.executeAction(miniFanAction);
    }

    @SimpleFunction(description = "play note at a certain frequency (C4:262)")
    public void PlayNote(int frequency, int duration) {
        Action action = RJ25ActionFactory.createBuzzerFrequencyAction(-1, frequency, (short) duration);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query ultrasonic sensor value, must be used with #When ReceiveUltrasonicValue# block")
    public void QueryUltrasonicSensorValue(int port) throws IOException {
        Action action = RJ25ActionFactory.createQueryUltrasonicInfiniteAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query lightness sensor value, the port of the lightness sensor on board is 6, must be used with #When ReceiveLightnessValue# block")
    public void QueryLightnessSensorValue(int port) throws IOException {
        Action action = RJ25ActionFactory.createQueryLightnessInfiniteAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query linefollower sensor value, must be used with #When ReceiveLineFollowerValue# block")
    public void QueryLineFollowerValue(int port) throws IOException {
        Action action = RJ25ActionFactory.createQueryHuntingLineAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query soundness sensor value, must be used with #When ReceiveSoundnessValue# block")
    public void QuerySoundnessSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryVolumeAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query temperature & humidity sensor temperature value, must be used with #When ReceiveTemperatureHumidity_temperatureValue# block")
    public void QueryTemperatureHumidity_temperatureValue(int port) {
        Action action = RJ25ActionFactory.createQueryTemperatureHumidity_temperatureAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query temperature & humidity sensor humidity value, must be used with #When ReceiveTemperatureHumidity_humidityValue# block")
    public void QueryTemperatureHumidity_humidityValue(int port) {
        Action action = RJ25ActionFactory.createQueryTemperatureHumidity_humidityAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query touch sensor value, must be used with #When ReceiveTouchSensorValue# block")
    public void QueryTouchSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryTouchSensorAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query gas sensor value, must be used with #When ReceiveGasSensorValue# block")
    public void QueryGasSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryGasSensorAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query flame sensor value, must be used with #When ReceiveFlameSensorValue# block")
    public void QueryFlameSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryFlameSensorAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query button on board value, must be used with #When ReceiveButtonOnBoardValue# block")
    public void QueryButtonOnBoardStatusValue(int port) {
        Action action = RJ25ActionFactory.createQueryBoardKeyStatusAction(port, 0);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query button sensor value, must be used with #When ReceiveButtonSensorValue# block")
    public void QueryButtonSensorValue(int port, int index) {
        Action action = RJ25ActionFactory.createQueryButtonSensorAction(port, index);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query temperature sensor value, must be used with #When ReceiveTemperatureSensorValue# block")
    public void QueryTemperatureSensorValue(int port, int slot) {
        Action action = RJ25ActionFactory.createQueryTemperatureSensorAction(port, slot);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query joystick sensor value, pass 1 to query horizontal movement value of the joystick, pass 2 for the vertical movement value, must be used with #When ReceiveJoystickSensorValue# block")
    public void QueryJoystickSensorValue(int port, int axle) {
        Action action = RJ25ActionFactory.createQueryJoystickSensorAction(port, axle);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query potentiometer sensor value, must be used with #When ReceivePotentiometerSensorValue# block")
    public void QueryPotentiometerSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryPotentiometerSensorAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query compass sensor value, must be used with #When ReceiveCompassSensorValue# block")
    public void QueryCompassSensorValue(int port) {
        Action action = RJ25ActionFactory.createQueryCompassSensorAction(port);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "query limit switch sensor value, must be used with #When ReceiveLimitSwitchSensorValue# block")
    public void QueryLimitSwitchSensorValue(int port, int slot) {
        Action action = RJ25ActionFactory.createQueryLimitSwitchSensorAction(port, slot);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "display a message on LED panel, set x, y to control the shifting of your message")
    public void DisplayMessageOnLEDPanel(int port, int x, int y, String message) {
        byte[] messageByte = message.getBytes();
        Action action = RJ25ActionFactory.createDisplayMessageOnLEDPanelAction(port, x, y + 7, messageByte);    //表情面板协议对应的默认显示区域不在面板上，需要Y轴+7特殊处理
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "display a message on LED panel, set x, y to control the shifting of the face")
    public void DisplaySmilingFaceOnLEDPanel(int port, int x, int y) {
        Action action = RJ25ActionFactory.createDisplaySmilingOnLEDPanelAction(port, x, y);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "display a time on LED panel")
    public void DisplayTimeOnLEDPanel(int port, int hour, int minute) {
        Action action = RJ25ActionFactory.createDisplayTimeOnLEDPanelAction(port, hour, minute);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "display a number on LED panel")
    public void DisplayNumberOnLEDPanel(int port, float digits) {
        Action action = RJ25ActionFactory.createDisplayDigitsOnLEDPanelAction(port, digits);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "display number on 7 segments")
    public void DisplayNumberOn7Segments(int port, float number) {
        Action action = RJ25ActionFactory.createDisplayNumberOn7Segments(port, number);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "play the song #Happy Birthday#")
    public void PlayHappyBirthday() {
        Action action = new HappyBirthdayFactory().createAction((byte) -1);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "play the song #Little Stars#")
    public void PlayLittleStars() {
        Action action = new LittleStarsFactory().createAction((byte) -1);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "play the song #Two Tigers#")
    public void PlayTwoTigers() {
        Action action = new TwoTigersFactory().createAction((byte) -1);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "play the song #Christmas#")
    public void PlayChristmas() {
        Action action = new ChristmasFactory().createAction((byte) -1);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "stop all actions, including all the queries and commands")
    public void StopAllActions() {
        actionExecutor.cancelAllActions();
    }

    @SimpleEvent(description = "get ultrasonic sensor value")
    public void ReceiveUltrasonicValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveUltrasonicValue", value);
    }

    @SimpleEvent(description = "get lightness sensor value")
    public void ReceiveLightnessValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveLightnessValue", value);
    }

    @SimpleEvent(description = "get line follower sensor value")
    public void ReceiveLineFollowerValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveLineFollowerValue", value);
    }

    @SimpleEvent(description = "get soundness sensor value")
    public void ReceiveSoundnessValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveSoundnessValue", value);
    }

    @SimpleEvent(description = "get temperature & humidity temperature value")
    public void ReceiveTemperatureHumidity_temperatureValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveTemperatureHumidity_temperatureValue", value);
    }

    @SimpleEvent(description = "get temperature & humidity humidity value")
    public void ReceiveTemperatureHumidity_humidityValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveTemperatureHumidity_humidityValue", value);
    }

    @SimpleEvent(description = "get touch sensor status value")
    public void ReceiveTouchSensorStatusValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveTouchSensorStatusValue", value);
    }

    @SimpleEvent(description = "get key on board status changed value, 1 indicates that the key is pressed, 0 indicates that the key is released")
    public void ReceiveKeyOnBoardStatusValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveKeyOnBoardStatusValue", value);
    }

    @SimpleEvent(description = "get gas sensor value, 1 indicates that the sensor returns a positive result, 0 indicates a negative result")
    public void ReceiveGasSensorValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveGasSensorValue", value);
    }

    @SimpleEvent(description = "get flame sensor value, 1 indicates that the sensor returns a positive result, 0 indicates a negative result")
    public void ReceiveFlameSensorValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveFlameSensorValue", value);
    }

    @SimpleEvent(description = "get button sensor value, 1 indicates that the button is pressed, 0 indicates that the button is released")
    public void ReceiveButtonSensorValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveButtonSensorValue", value);
    }

    @SimpleEvent(description = "get temperature sensor value")
    public void ReceiveTemperatureSensorValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveTemperatureSensorValue", value);
    }

    @SimpleEvent(description = "get joystick sensor horizontal value")
    public void ReceiveJoystickSensorHorizontalValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveJoystickSensorHorizontalValue", value);
    }

    @SimpleEvent(description = "get joystick sensor vertical value")
    public void ReceiveJoystickSensorVerticalValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveJoystickSensorVerticalValue", value);
    }

    @SimpleEvent(description = "get potentiometer sensor value")
    public void ReceivePotentiometerSensorValue(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceivePotentiometerSensorValue", value);
    }

    @SimpleEvent(description = "get compass sensor value")
    public void ReceiveCompassSensorReading(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveCompassSensorReading", value);
    }

    @SimpleEvent(description = "get limit switch sensor value")
    public void ReceiveLimitSwitchSensorReading(String value) {
        EventDispatcher.dispatchEvent(MBot.this, "ReceiveLimitSwitchSensorReading", value);
    }

    private void updateReadings() {
        timer.start();
        if (!(ControllerManager.getInstance().getConnectedDevice() instanceof MBotDevice)) {
            return;
        }
        MBotDevice mBotDevice = (MBotDevice) ControllerManager.getInstance().getConnectedDevice();
        ReceiveUltrasonicValue(String.valueOf(mBotDevice.getUltrasonicReading()));
        ReceiveLightnessValue(String.valueOf(mBotDevice.getLightnessReading()));
        ReceiveLineFollowerValue(String.valueOf(mBotDevice.getHuntingLineReading()));
        ReceiveSoundnessValue(String.valueOf(mBotDevice.getVolumeReading()));
        ReceiveTemperatureHumidity_temperatureValue(String.valueOf(mBotDevice.getTemperatureHumidity_temperatureReading()));
        ReceiveTemperatureHumidity_humidityValue(String.valueOf(mBotDevice.getTemperatureHumidity_humidityReading()));
        ReceiveTouchSensorStatusValue(String.valueOf(mBotDevice.getTouchSensorReading()));
        ReceiveKeyOnBoardStatusValue(String.valueOf(mBotDevice.getBoardKeyStatus()));
        ReceiveGasSensorValue(String.valueOf(mBotDevice.getGasSensorValue()));
        ReceiveFlameSensorValue(String.valueOf(mBotDevice.getFlameSensorValue()));
        ReceiveButtonSensorValue(String.valueOf(mBotDevice.getKeySensorValue()));
        ReceiveTemperatureSensorValue(String.valueOf(mBotDevice.getTemperatureSensorValue()));
        ReceiveJoystickSensorHorizontalValue(String.valueOf(mBotDevice.getJoystickSensorXValue()));
        ReceiveJoystickSensorVerticalValue(String.valueOf(mBotDevice.getJoystickSensorYValue()));
        ReceivePotentiometerSensorValue(String.valueOf(mBotDevice.getPotentiometerSensorValue()));
        ReceiveCompassSensorReading(String.valueOf(mBotDevice.getCompassSensorValue()));
        ReceiveLimitSwitchSensorReading(String.valueOf(mBotDevice.getLimitSwitchSensorValue()));
    }
}
