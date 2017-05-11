package com.makeblock.appinventor.brandnew;

import android.util.Log;

/**
 * Created by hupihuai on 17/4/13.
 */

public class MBotDevice extends RJ25Device {

    private UltrasonicRespond ultrasonicRespond;
    private LightRespond lightRespond;
    private HuntingLineRespond huntingLineRespond;
    private VolumeRespond volumeRespond;
    private TemperatureHumidityRespond temperatureHumidityRespond;
    private TouchStatusRespond touchStatusRespond;
    private BoardKeyRespond boardKeyRespond;
    private GasRespond gasRespond;
    private FlameRespond flameRespond;
    private KeyRespond keyRespond;
    private TemperatureRespond temperatureRespond;
    private JoystickSensorRespond joystickSensorRespond;
    private PotentiometerRespond potentiometerRespond;
    private ElectronicCompassRespond electronicCompassRespond;
    private LimitSwitchRespond limitSwitchRespond;

    public MBotDevice(FirmwareRespond firmwareRespond) {
        super("mcore", "mBot", firmwareRespond);
    }

    @Override
    protected void handleRespond(RJ25Respond rj25Respond) {
        Log.e("wbp", "mBot receive respond:" + rj25Respond.toString());
        if (rj25Respond instanceof UltrasonicRespond) {
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
        } else if (rj25Respond instanceof BoardKeyRespond) {
            this.boardKeyRespond = (BoardKeyRespond) rj25Respond;
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

    public int getBoardKeyStatus() {
        if (boardKeyRespond != null) {
            return boardKeyRespond.status;
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
