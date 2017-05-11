package com.makeblock.appinventor.brandnew;

import android.os.CountDownTimer;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;

/**
 * Created by tom on 17/5/10.
 */
@DesignerComponent(version = 1, // have to use magic numbers since constant file cannot be motidifed
        description = "Component to control Makeblock Airblock educational(may be not) robot. (Note: Only Android 4.3 and above supported)",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "http://appinventor.makeblock.com/mbot-icon.png")
@SimpleObject(external = true)
public class AirBlock extends MakeblockBase {

    private ActionExecutor actionExecutor;
    private static final int MODE_AIR = 0;
    private static final int MODE_LAND = 1;
    private static final int MODE_WATER = 2;
    private int mode = MODE_AIR;

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
    public AirBlock(ComponentContainer container) {
        super(container, "Airblock");
        actionExecutor = new ActionExecutor();
        timer.start();
    }

    @SimpleFunction(description = "set your Airblock to Air Mode, this need to be done before you power on the motors when you want it to fly in the sky")
    public void SetAirblockModeToAir() {
        Action action = AirBlockActionFactory.createSetFormToAirAction();
        actionExecutor.executeAction(action);
        mode = MODE_AIR;
    }

    @SimpleFunction(description = "set your Airblock to Land Mode, this need to be done before you power on the motors when you want it to run on the ground")
    public void SetAirblockModeToLand() {
        Action action = AirBlockActionFactory.createSetFormToLandAction();
        actionExecutor.executeAction(action);
        mode = MODE_LAND;
    }

    @SimpleFunction(description = "set your Airblock to Water Mode, this need to be done before you power on the motors when you want it to swim in the water")
    public void SetAirblockModeToWater() {
        Action action = AirBlockActionFactory.createSetFormToWaterAction();
        actionExecutor.executeAction(action);
        mode = MODE_WATER;
    }

    @SimpleFunction(description = "tell your Airblock to locate motors, this need to be called after set mode and before power on")
    public void LocateMotors() {
        Action action = AirBlockActionFactory.createAllotIdAction();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "power on your Airblock motors, remember, you need to set mode and locate motors first")
    public void PowerOn() {
        Action action = AirBlockActionFactory.createPowerSwitchAction(true);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "power off your Airblock motors")
    public void PowerOff() {
        Action action = AirBlockActionFactory.createPowerSwitchAction(false);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "control your Airblock to takeoff, be careful about the takeoff time or you might need to buy a new one if you tell him to takeoff for too long, it's not that cheap")
    public void TakeOff(int seconds) {
        Action action = AirBlockActionFactory.createTakeOffAction(seconds);
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "force your Airblock to land immediately, call me when you want to bring the thing down to the ground, and you can not change it into a undersea boat when it is in water mode, never try it")
    public void Land() {
        Action action = AirBlockActionFactory.createLandingAction();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to do a rolling trick, be sure that your baby is high enough or it might hit the ground, no effect on land and water mode, don't even think about it")
    public void RollOver() {
        Action action = AirBlockActionFactory.createRollSkill();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to do a shake trick, for air mode only")
    public void Shake() {
        Action action = AirBlockActionFactory.createShakeSkillAction();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to do a fly around trick, for air mode only")
    public void FlyAround() {
        Action action = AirBlockActionFactory.createCircleSkill();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to do a S move trick, for land mode only")
    public void SMove() {
        Action action = AirBlockActionFactory.createSMoveAction();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to do a shift trick, for land mode only")
    public void Shift() {
        Action action = AirBlockActionFactory.createShiftAction();
        actionExecutor.executeAction(action);
    }

    @SimpleFunction(description = "tell your Airblock to go up, for air mode only")
    public void GoUp() {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirVerticalJoystickAction(AirBlockActionFactory.CONTROL_UP);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to go down, for air mode only")
    public void GoDown() {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirVerticalJoystickAction(AirBlockActionFactory.CONTROL_DOWN);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to rotate left, for all the modes. Air mode does not support specify angle, pass in any integer you want")
    public void RotateLeft(int angle) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirVerticalJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_LEFT);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandTurnJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_LEFT, -angle);
            actionExecutor.executeAction(action);
        } else {
            Action action = AirBlockActionFactory.createWaterTurnJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_LEFT, -angle);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to rotate right, for all the modes. Air mode does not support specify angle, pass in any integer you want")
    public void RotateRight(int angle) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirVerticalJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_RIGHT);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandTurnJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_RIGHT, angle);
            actionExecutor.executeAction(action);
        } else {
            Action action = AirBlockActionFactory.createWaterTurnJoystickAction(AirBlockActionFactory.CONTROL_ROTATE_RIGHT, angle);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to go forward, for all the modes. speed(0 ~ 1)")
    public void Forward(float speed) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirHorizontalJoystickAction(AirBlockActionFactory.CONTROL_FORWARD, speed);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandMoveJoystickAction(AirBlockActionFactory.CONTROL_FORWARD, speed);
            actionExecutor.executeAction(action);
        } else {
            Action action = AirBlockActionFactory.createWaterMoveJoystickAction(AirBlockActionFactory.CONTROL_FORWARD, speed);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to go backward, for all the modes. speed(0 ~ 1)")
    public void Backward(float speed) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirHorizontalJoystickAction(AirBlockActionFactory.CONTROL_BACKWARD, speed);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandMoveJoystickAction(AirBlockActionFactory.CONTROL_BACKWARD, speed);
            actionExecutor.executeAction(action);
        } else {
            Action action = AirBlockActionFactory.createWaterMoveJoystickAction(AirBlockActionFactory.CONTROL_BACKWARD, speed);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to go left, for air and land mode only. speed(0 ~ 1)")
    public void Left(float speed) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirHorizontalJoystickAction(AirBlockActionFactory.CONTROL_LEFT, speed);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandMoveJoystickAction(AirBlockActionFactory.CONTROL_LEFT, speed);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to go right, for air and land mode only. speed(0 ~ 1)")
    public void Right(float speed) {
        if (mode == MODE_AIR) {
            Action action = AirBlockActionFactory.createAirHorizontalJoystickAction(AirBlockActionFactory.CONTROL_RIGHT, speed);
            actionExecutor.executeAction(action);
        } else if (mode == MODE_LAND) {
            Action action = AirBlockActionFactory.createLandMoveJoystickAction(AirBlockActionFactory.CONTROL_RIGHT, speed);
            actionExecutor.executeAction(action);
        }
    }

    @SimpleFunction(description = "tell your Airblock to stop moving, this does not cause your Airblock crash to the ground when you fly it, it will stay put in where it belongs")
    public void Stop() {
        Action action = AirBlockActionFactory.createBalanceAction();
        actionExecutor.executeAction(action);
    }

    private void updateReadings() {
        timer.start();
        if (!(ControllerManager.getInstance().getConnectedDevice() instanceof AirBlockDevice)) {
            return;
        }
    }
}
