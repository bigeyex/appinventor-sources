package com.makeblock.appinventor.brandnew;

import android.util.Log;

/**
 * Created by hupihuai on 17/4/13.
 */

public class Ultimate2Device extends RJ25Device {

    private UltrasonicRespond ultrasonicRespond;
    private LightRespond lightRespond;
    private HuntingLineRespond huntingLineRespond;
    private VolumeRespond volumeRespond;
    private TemperatureHumidityRespond temperatureHumidityRespond;
    private TouchStatusRespond touchStatusRespond;
    private GasRespond gasRespond;
    private FlameRespond flameRespond;
    private KeyRespond keyRespond;
    private TemperatureRespond temperatureRespond;
    private JoystickSensorRespond joystickSensorRespond;
    private PotentiometerRespond potentiometerRespond;
    private ElectronicCompassRespond electronicCompassRespond;
    private LimitSwitchRespond limitSwitchRespond;

    public Ultimate2Device(FirmwareRespond firmwareRespond) {
        super("megaPi", "Ultimate 2.0", firmwareRespond);
    }

    @Override
    protected void handleRespond(RJ25Respond rj25Respond) {
        Log.e("wbp", "Ultimate2.0 receive respond:" + rj25Respond.toString());
        if (rj25Respond instanceof RJ25FormRespond) {
            setForm(((RJ25FormRespond) rj25Respond).mode);
        }else if (rj25Respond instanceof UltrasonicRespond) {
            //超声波传感器数值
            this.ultrasonicRespond = (UltrasonicRespond) rj25Respond;
        } else if (rj25Respond instanceof LightRespond) {
            //光线传感器数值
            this.lightRespond = (LightRespond) rj25Respond;
        } else if (rj25Respond instanceof HuntingLineRespond) {
            this.huntingLineRespond = (HuntingLineRespond) rj25Respond;
        } else if (rj25Respond instanceof VolumeRespond) {
            this.volumeRespond = (VolumeRespond) rj25Respond;
        } else if (rj25Respond instanceof TemperatureHumidityRespond) {
            this.temperatureHumidityRespond = (TemperatureHumidityRespond) rj25Respond;
        } else if (rj25Respond instanceof TouchStatusRespond) {
            this.touchStatusRespond = (TouchStatusRespond) rj25Respond;
        } else if (rj25Respond instanceof GasRespond) {
            this.gasRespond = (GasRespond) rj25Respond;
        } else if (rj25Respond instanceof FlameRespond) {
            this.flameRespond = (FlameRespond) rj25Respond;
        } else if (rj25Respond instanceof KeyRespond) {
            this.keyRespond = (KeyRespond) rj25Respond;
        } else if (rj25Respond instanceof TemperatureRespond) {
            this.temperatureRespond = (TemperatureRespond) rj25Respond;
        } else if (rj25Respond instanceof JoystickSensorRespond) {
            this.joystickSensorRespond = (JoystickSensorRespond) rj25Respond;
        } else if (rj25Respond instanceof PotentiometerRespond) {
            this.potentiometerRespond = (PotentiometerRespond) rj25Respond;
        } else if (rj25Respond instanceof ElectronicCompassRespond) {
            this.electronicCompassRespond = (ElectronicCompassRespond) rj25Respond;
        } else if (rj25Respond instanceof LimitSwitchRespond) {
            this.limitSwitchRespond = (LimitSwitchRespond) rj25Respond;
        }
    }

    public void queryForm() {
        Instruction instruction = new RJ25QueryFormInstruction();
        BleManager.getInstance().sendInstruction(instruction);
    }

    @Override
    public void setMode(int mode) {
        super.setMode(mode);
        Instruction instruction;
        switch (mode) {
            case MODE_OBSTACLE_VOID:
                instruction = new ModeInstruction(ModeInstruction.MODE_OBSTACLE_VOID, (byte) 0x12);
                break;
            case MODE_LINE_FOLLOW:
                instruction = new ModeInstruction(ModeInstruction.MODE_LINE_FOLLOW, (byte) 0x12);
                break;
            case MODE_SELF_BALANCE:
                instruction = new ModeInstruction(ModeInstruction.MODE_BALANCE, (byte) 0x12);
                break;
            case MODE_BLUETOOTH:
            default:
                instruction = new ModeInstruction(ModeInstruction.MODE_BLUETOOTH, (byte) 0x12);
                break;
        }
        BleManager.getInstance().sendInstruction(instruction);
    }

    public float getUltrasonicReading() {
        if (ultrasonicRespond != null) {
            return ultrasonicRespond.distance;
        }
        return 0;
    }

    public float getLightnessReading() {
        if (lightRespond != null) {
            return lightRespond.light;
        }
        return 0;
    }

    public float getHuntingLineReading() {
        if (huntingLineRespond != null) {
            return huntingLineRespond.line;
        }
        return 0;
    }

    public float getVolumeReading() {
        if (volumeRespond != null) {
            return volumeRespond.volume;
        }
        return 0;
    }

    public float getTemperatureHumidity_temperatureReading() {
        if (temperatureHumidityRespond != null) {
            return temperatureHumidityRespond.value;
        }
        return 0;
    }

    public float getTemperatureHumidity_humidityReading() {
        if (temperatureHumidityRespond != null) {
            return temperatureHumidityRespond.value;
        }
        return 0;
    }

    public int getTouchSensorReading() {
        if (touchStatusRespond != null) {
            return touchStatusRespond.status;
        }
        return 0;
    }

    public float getGasSensorValue() {
        if (gasRespond != null) {
            return gasRespond.gas;
        }
        return 0;
    }

    public float getFlameSensorValue() {
        if (flameRespond != null) {
            return flameRespond.flame;
        }
        return 0;
    }

    public float getKeySensorValue() {
        if (keyRespond != null) {
            return keyRespond.status;
        }
        return 0;
    }

    public float getTemperatureSensorValue() {
        if (temperatureRespond != null) {
            return temperatureRespond.temperature;
        }
        return 0;
    }

    public float getJoystickSensorXValue() {
        if (joystickSensorRespond != null) {
            return joystickSensorRespond.xValue;
        }
        return 0;
    }

    public float getJoystickSensorYValue() {
        if (joystickSensorRespond != null) {
            return joystickSensorRespond.yValue;
        }
        return 0;
    }

    public float getPotentiometerSensorValue() {
        if (potentiometerRespond != null) {
            return potentiometerRespond.potentiometer;
        }
        return 0;
    }

    public float getCompassSensorValue() {
        if (electronicCompassRespond != null) {
            return electronicCompassRespond.compass;
        }
        return 0;
    }

    public float getLimitSwitchSensorValue() {
        if (limitSwitchRespond != null) {
            return limitSwitchRespond.limitingStopper;
        }
        return 0;
    }
}
