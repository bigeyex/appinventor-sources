package com.makeblock.appinventor.brandnew;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by xuexin on 2017/4/14.
 */

public class RJ25RespondParser extends RespondParser {

    private static final byte[] HEAD = RJ25Instruction.Head;
    private static final byte[] TAIL = new byte[]{0x0d, 0x0a};
    private OnRespondReceiveListener onRespondReceiveListener;


    public RJ25RespondParser() {
        super(HEAD, TAIL);
    }

    @Override
    protected void packData(byte[] bytes) {
        RJ25Respond respond = null;
        switch ((bytes[2])) {
            case 0: //舵机不返回index，特殊处理，把它放进下一个,也就是固件信息里面
                if (bytes.length < 4 || bytes[3] != 0x04)      //0x04表示接的值为字符串
                    break;
            case QueryFirmwareInstruction.INDEX:
                String firmwareVersion = decodeFirmwareVersion(bytes);
                if (firmwareVersion != null && !firmwareVersion.isEmpty())
                    respond = new FirmwareRespond(firmwareVersion);
                break;
            case RJ25QueryFormInstruction.INDEX:
                if (bytes[3] == 1) {
                    respond = new RJ25FormRespond(bytes[4]);
                }
                break;
            case ProtocolUtils.INDEX_QUERY_BATTERY_LIFE: {
                float battery = ByteBuffer.wrap(bytes, 4, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                break;
            }
            case ProtocolUtils.INDEX_QUERY_ULTRASONIC_READING:
                float reading = decodeFloatReading(bytes);
                respond = new UltrasonicRespond(reading);
                break;
            case RJ25Instruction.INDEX_ULTRASONIC://超声波
                final float distance = decodeFloatReading(bytes);
                respond = new UltrasonicRespond(distance);
                break;
            case RJ25Instruction.INDEX_TEMPERATURE://温度
                final float temperature = decodeFloatReading(bytes);
                respond = new TemperatureRespond(temperature);
                break;
            case RJ25Instruction.INDEX_LIGHT://光线
                final float light = decodeFloatReading(bytes);
                respond = new LightRespond(light);
                break;
            case RJ25Instruction.INDEX_POTENTIOMETER://电位器
                final float potentiometer = decodeFloatReading(bytes);
                respond = new PotentiometerRespond(potentiometer);
                break;
            case RJ25Instruction.INDEX_ROCKER://摇杆
                final float rocker = decodeFloatReading(bytes);
                respond = new JoystickSensorRespond(rocker, bytes[bytes.length - 1]);
                break;

            case RJ25Instruction.INDEX_GESTURE://姿态
                final float gesture = decodeFloatReading(bytes);
                respond = new GestureRespond(gesture);
                break;
            case RJ25Instruction.INDEX_VOLUME://音量
                final float volume = decodeFloatReading(bytes);
                respond = new VolumeRespond(volume);
                break;
            case RJ25Instruction.INDEX_INFRARED://红外
                final float infrared = decodeFloatReading(bytes);
                respond = new InfraredRespond(infrared);
                break;
            case RJ25Instruction.INDEX_HUNTING_LINE://巡线
                final float line = decodeFloatReading(bytes);
                respond = new HuntingLineRespond(line);
                break;
            case RJ25Instruction.INDEX_LIMITING_STOPPER://限位器
                final float limitingStopper = decodeFloatReading(bytes);
                respond = new LimitSwitchRespond(limitingStopper);
                break;
            case RJ25Instruction.INDEX_ELECTRONIC_COMPASS://电子罗盘
                final float compass = decodeFloatReading(bytes);
                respond = new ElectronicCompassRespond(compass);
                break;
            case RJ25Instruction.INDEX_TEMPERATURE_HUMIDITY://温湿度
                final float temperatureHumidity = decodeIntReading(bytes);
                respond = new TemperatureHumidityRespond(temperatureHumidity);
                break;
            case RJ25Instruction.INDEX_FLAME://火焰
                final float flame = decodeFloatReading(bytes);
                respond = new FlameRespond(flame);
                break;
            case RJ25Instruction.INDEX_GAS://气体
                final float gas = decodeFloatReading(bytes);
                respond = new GasRespond(gas);
                break;
            case RJ25Instruction.INDEX_QUERY_DIGITAL_PIN://数字管脚
                final float pin = decodeFloatReading(bytes);
                respond = new DigitalPinRespond(pin);
                break;
            case RJ25Instruction.INDEX_QUERY_DEVICE_RUNTIME://查询设备运行时间
                final float runtime = decodeFloatReading(bytes);
                respond = new DeviceRuntimeRespond(runtime);
                break;
            case RJ25Instruction.INDEX_QUERY_TOUCH_STATUS://查询触摸状态
                final int touchStatus = decodeIntReading(bytes);
                respond = new TouchStatusRespond(touchStatus);
                break;
            case RJ25Instruction.INDEX_QUERY_BOARD_KEY_STATUS://查询板载按键状态
                final int boardKeyStatus = decodeIntReading(bytes);
                respond = new BoardKeyRespond(boardKeyStatus);
                break;
            case RJ25Instruction.INDEX_QUERY_KEY_STATUS://查询按键状态
                final int keyStatus = decodeIntReading(bytes);
                respond = new KeyRespond(keyStatus);
                break;
        }

        if (onRespondReceiveListener != null && respond != null) {
            onRespondReceiveListener.onInstructionReceive(respond);
        }
    }

    public void setOnRespondReceiveListener(OnRespondReceiveListener listener) {
        onRespondReceiveListener = listener;
    }


    public interface OnRespondReceiveListener {
        void onInstructionReceive(RJ25Respond rj25ReceiveInstruction);
    }

    private static String decodeFirmwareVersion(byte[] bytes) {
        int len = bytes[4];
        String firmwareVersion = "";
        try {
            for (int j = 0; j < len; j++) {
                firmwareVersion += String.format("%c", bytes[5 + j]);
            }
        } catch (Exception e) {
            firmwareVersion = "";
        }

        if (!assertFirmwareValid(firmwareVersion)) {
            firmwareVersion = "";
        }
        return firmwareVersion;
    }

    private static boolean assertFirmwareValid(String firmwareVersion) {
        //验证拿到的版本号是否有效，判断是否有两个'.',分割后是否都为十六进制，都满足则返回true，不满足返回false
        try {
            Scanner scanner = new Scanner(firmwareVersion).useDelimiter("\\.");
            scanner.nextInt(16);
            scanner.nextInt(16);
            scanner.nextInt(16);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static float decodeFloatReading(byte[] data) {
        List<Byte> mRx = new ArrayList<Byte>();
        for (byte aData : data) {
            mRx.add(aData);
        }

        int len4Msg = mRx.size();
        int[] msg = new int[len4Msg];

        //打印数据
        Byte[] rxbtyes = mRx.toArray(new Byte[mRx.size()]);
        for (int i1 = 0; i1 < rxbtyes.length; i1++) {
            msg[i1] = rxbtyes[i1];
        }

        float reading = 0;
        if (msg.length >= 7) {
            reading = 0.0f;
            if (msg[3] == 2) {
                if (msg.length > 7) {
                    int tint = (msg[4] & 0xff) + ((msg[5] & 0xff) << 8) + ((msg[6] & 0xff) << 16) + ((msg[7] & 0xff) << 24);
                    reading = Float.intBitsToFloat(tint);
                }
            } else if (msg[3] == 1) {
                reading = (msg[4] & 0xff);
            } else if (msg[3] == 3) {
                reading = (msg[4] & 0xff) + ((msg[5] & 0xff) << 8);
            }

        }
        return reading;
    }

    private static int decodeIntReading(byte[] data) {
        int reading = 0;
        if (data.length >= 4) {
            reading = data[4] & 0xff;
        }
        return reading;
    }

}
