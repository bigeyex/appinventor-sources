package com.makeblock.appinventor.brandnew;


/**
 * Created by hupihuai on 17/4/13.
 */

public abstract class Device {

    private static final String TAG = Device.class.getSimpleName();
    //形态
    public static final int FORM_UNKNOWN = -1; //蓝牙刚连接(还没来得及查form),蓝牙断开时,处于此form
    public static final int FORM_BLUETOOTH = 0; //手动模式
    public static final int FORM_ULTRASOINIC = 1;
    public static final int FORM_BALANCE = 2;
    public static final int FORM_INFRARED = 3;
    public static final int FORM_LINE_FOLLOW = 4;
    public static final int FORM_DRONE = 4;
    public static final int FORM_CAR = 5;
    public static final int FORM_CUSTOMIZE = 6;
    public static final int FORM_HOVERCRAFT = 7;
    public int currentForm = FORM_UNKNOWN;

    //设备名
    public static final String auriga = "auriga";
    public static final String megaPi = "megaPi";
    public static final String mcore = "mcore";
    public static final String orion = "orion";
    public static final String airblock = "crystal";
    public static final String smartServo = "octopus";

    //模式
    public static final int MODE_BLUETOOTH = 0;
    public static final int MODE_OBSTACLE_VOID = 1;
    public static final int MODE_LINE_FOLLOW = 2;
    public static final int MODE_SELF_BALANCE = 3;

    /*公有属性*/

    private int mode;//模式

    private int form;//形态

    public final FirmwareRespond firmwareRespond;//硬件

    private String boardName;// 主板？mcore

    private String deviceName;//设备名

    //指令解析
    protected RespondParser respondParser;

    public Device(String boardName, String deviceName, FirmwareRespond firmwareRespond) {
        this.boardName = boardName;
        this.deviceName = deviceName;
        this.firmwareRespond = firmwareRespond;
        respondParser = createInstructionParser();
    }


    protected abstract RespondParser createInstructionParser();

    /*公有行为*/

    public void parseBytes(byte[] bytes) {
        respondParser.parseBytes(bytes);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getFirmwareVersion() {
        if (firmwareRespond != null) {
            return firmwareRespond.firmwareVersion;
        }
        return null;

    }

    @Override
    public String toString() {
        return "当前设备 {" +
                "mode=" + mode +
                ", form=" + form +
                ", firmwareRespond='" + firmwareRespond + '\'' +
                ", boardName='" + boardName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

    public void disconnect() {
        BleManager.getInstance().disconnectBluetooth();
    }

}
