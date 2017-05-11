package com.makeblock.appinventor.brandnew;

import android.text.TextUtils;

import java.nio.ByteBuffer;


/**
 * 基于硬件通信协议的工具类,发送给固件的byte数组
 * Makeblock - 硬件通信协议: https://shimo.im/doc/R3xesVC3b7kRNhPJ
 * PS:随着传感器的增加,需求的增加,会经常补充,修改
 *
 * @author liuming
 */
public class ProtocolUtils {
    private static final String TAG = "ProtocolUtils";

    //命令的两种模式:read/write
    public static final int READ = 1;
    public static final int WRITE = 2;

    //blockly端:index的取值范围是[0.125]  native端:index范围是[126,255]; index使用不当,会导致很难查找的bug
    public static final int INDEX_QUERY_LINEFOLLOW_STATE = 132;    //查询巡线传感器状态
    public static final byte INDEX_QUERY_ULTRASONIC_READING = (byte) 133;   //查询超声波传感器的值
    public static final int INDEX_SET_RGB_VALUE = 134;
    public static final byte INDEX_QUERY_BATTERY_LIFE = (byte) 135; //查询电池电量（单位v）
//    public static final int INDEX_QUERY_DEVICE_FORM = 131;
//    public static final int INDEX_QUERY_DEVICE_FORM = 131;

    public static final int DEV_VERSION = 0;
    public static final int DEV_JOYSTICK = 0x34;
    public static final int DEV_DCMOTOR = 10;
    public static final int DEV_COMMON = 0x3c; //针对套件的命令
    public static final int DEV_ENCODER_BOARD = 0x3d;//板载编码电机

//    public static final int DEV_GYRO = 6;
//    public static final int DEV_SOUNDSENSOR = 7;
//    public static final int DEV_RGBLED = 8;
//    public static final int DEV_SEVSEG = 9;
//
//    public static final int DEV_SERVO = 11;
//    public static final int DEV_ENCODER = 12;
//    public static final int DEV_PIRMOTION = 15;
//    public static final int DEV_INFRADRED = 16;
//    public static final int DEV_LINEFOLLOWER = 17;
//    public static final int DEV_BUTTON = 18;
//    public static final int DEV_LIMITSWITCH = 19;
//    public static final int DEV_SHUTTER = 20;
//    public static final int DEV_PINDIGITAL = 30;
//    public static final int DEV_PINANALOG = 31;
//    public static final int DEV_PINPWM = 32;
//    public static final int DEV_PINANGLE = 33;
//    public static final int DEV_CAR_CONTROLLER = 40;
//    public static final int DEV_GRIPPER_CONTROLLER = 41;
//    public static final int TONE = 34;

    //    public static final int PORT_NULL = 0;
//    public static final int PORT_1 = 1;
//    public static final int PORT_2 = 2;
//    public static final int PORT_3 = 3;
//    public static final int PORT_4 = 4;
//    public static final int PORT_5 = 5;
//    public static final int PORT_6 = 6;
//    public static final int PORT_7 = 7;
//    public static final int PORT_8 = 8;
    public static final int PORT_M1 = 9; //LEFT
    public static final int PORT_M2 = 10;//RIGHT

    /*---------------------普通命令------------------------*/

    /**
     * 读取巡线传感器状态
     */
    public static byte[] getBytes_queryLineFollowState() {
        int length = 4;
        int index = INDEX_QUERY_LINEFOLLOW_STATE;
        int mode = READ;
        int commandId = 17;
        int port = 2;
        byte[] cmd = new byte[7];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) mode;
        cmd[5] = (byte) commandId;
        cmd[6] = (byte) port;
        return cmd;
    }

    /**
     * 读取超声波传感器的值
     */
    public static byte[] getBytes_queryUltrasonicReading() {
        int length = 4;
        int index = INDEX_QUERY_ULTRASONIC_READING;
        int mode = 1;
        int commandId = 1;
        int port = 3;
        byte[] cmd = new byte[7];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) mode;
        cmd[5] = (byte) commandId;
        cmd[6] = (byte) port;
        return cmd;
    }

    /**
     * 设置RGB灯的颜色
     */
    public static byte[] getBytes_setRGBValue(int port, int slot, int R, int G, int B) {
        int length = 9;
        int index = INDEX_SET_RGB_VALUE;
        int mode = 2;
        int commandId = 8;
        int RGB_index = 0;  //00 代表全亮,当前协议不支持单独设置LED灯颜色
        int redValue = Integer.valueOf(String.valueOf(R), 16);
        int greenValue = Integer.valueOf(String.valueOf(G), 16);
        int blueValue = Integer.valueOf(String.valueOf(B), 16);
        byte[] cmd = new byte[12];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) mode;
        cmd[5] = (byte) commandId;
        cmd[6] = (byte) port;
        cmd[7] = (byte) slot;
        cmd[8] = (byte) RGB_index;
        cmd[9] = (byte) redValue;
        cmd[10] = (byte) greenValue;
        cmd[11] = (byte) blueValue;
        return cmd;
    }


    /*---------------------设置套件form------------------------*/
    //蓝牙模式
    public static byte[] getBytes_setDeviceFormToBluetooth(String firmwareVersion) {
        byte subCmd = getSubCmdForSetDeviceForm(getBoardName(firmwareVersion));
        int form = 0;
        return getBytes_setDeviceForm(subCmd, form);
    }

    //超声波模式
    public static byte[] getBytes_setDeviceFormToUltrasonic(String firmwareVersion) {
        byte subCmd = getSubCmdForSetDeviceForm(getBoardName(firmwareVersion));
        int form = 1;
        return getBytes_setDeviceForm(subCmd, form);
    }

    //自平衡模式
    public static byte[] getBytes_setDeviceFormToBalance(String firmwareVersion) {
        byte subCmd = getSubCmdForSetDeviceForm(getBoardName(firmwareVersion));
        int form = 2;
        return getBytes_setDeviceForm(subCmd, form);
    }

    //红外线模式
    public static byte[] getBytes_setDeviceFormToInfraredRay(String firmwareVersion) {
        byte subCmd = getSubCmdForSetDeviceForm(getBoardName(firmwareVersion));
        int form = 3;
        return getBytes_setDeviceForm(subCmd, form);
    }

    //巡线模式
    public static byte[] getBytes_setDeviceFormToLineFollow(String firmwareVersion) {
        byte subCmd = getSubCmdForSetDeviceForm(getBoardName(firmwareVersion));
        int form = 4;
        return getBytes_setDeviceForm(subCmd, form);
    }

    /**
     * 设置套件模式
     *
     * @param subCmd megapi:0x72  default:0x71 根据board设置
     * @param form   00:蓝牙模式(手动模式)  01:超声波自动  02:自平衡  03:红外模式  04:巡线模式
     * @return
     */
    private static byte[] getBytes_setDeviceForm(int subCmd, int form) {
        int index = 0;
        int length = 5;
        int action = WRITE;
        int device = DEV_COMMON; //针对套件的Dev
        byte[] cmd = new byte[8];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) action;
        cmd[5] = (byte) device;
        cmd[6] = (byte) subCmd;
        cmd[7] = (byte) form;
        return cmd;
    }

    private static byte getSubCmdForSetDeviceForm(BoardName boardName) {
        byte subCmd = 0;
        switch (boardName) {
            case orion:
                subCmd = 0x10;
                break;
            case megaPi:
                subCmd = 0x12;
                break;
            case auriga:
                subCmd = 0x11;
                break;
        }
        return subCmd;
    }

    /*---------------------设置电机------------------------*/

    /**
     * 设置直流电机速度,板载和自定义都支持
     *
     * @param port  mBot/starter/ultimate1.0:左电机为9，右电机为10. 或者根据board:mCore/Orion. 目前的套件,都是左电机为9，右电机为10. 自定义的设备,port则自定义.
     * @param speed
     * @return
     */
    public static byte[] getBytes_setDCMotorSpeed(int port, int speed) {
        int index = 0;
        int length = 6;
        int action = WRITE;
        int device = DEV_DCMOTOR;//通用DC motor
        byte[] cmd = new byte[9];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) action;
        cmd[5] = (byte) device;
        cmd[6] = (byte) (port & 0xff);
        final ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort((short) speed);
        buf.position(0);
        final byte b1 = buf.get();
        final byte b2 = buf.get();
        cmd[8] = b1;
        cmd[7] = b2;
        return cmd;
    }

    /**
     * 设置板载(mcore/orion)的DC motor的right speed
     *
     * @param speed
     * @return
     */
    public static byte[] getBytes_setDCMotorSpeedRightForMcoreOrion(int speed) {
        int port = PORT_M2;
        return getBytes_setDCMotorSpeed(port, speed);
    }

    /**
     * 设置板载(mcore/orion)的DC motor的left speed
     *
     * @param speed
     * @return
     */
    public static byte[] getBytes_setDCMotorSpeedLeftForMcoreOrion(int speed) {
        int port = PORT_M1;
        return getBytes_setDCMotorSpeed(port, speed);
    }

    /**
     * 设置mZero板载编码电机。板载的电机和通用的不同，通用的使用RJ25连接. 板载编码电机和通用编码电机的port不同
     *
     * @param speed
     * @param slot  1:left  2:right
     * @return
     */
    private static byte[] getBytes_setEncoderMotorSpeedLeftForMzero(int speed, int slot) {
        int index = 0;
        int length = 7;
        int action = WRITE;
        int device = DEV_ENCODER_BOARD;
        int port = 0;
        byte[] cmd = new byte[10];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) action;
        cmd[5] = (byte) device;
        cmd[6] = (byte) port;
        cmd[7] = (byte) slot;
        final ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort((short) speed);
        buf.position(0);
        final byte b1 = buf.get();
        final byte b2 = buf.get();
        cmd[9] = b1;
        cmd[8] = b2;
        return cmd;
    }

    /**
     * 设置mZero板载编码电机left speed
     *
     * @param speed
     * @return
     */
    public static byte[] getBytes_setEncoderMotorSpeedLeftForMzero(int speed) {
        int slot = 1;
        return getBytes_setEncoderMotorSpeedLeftForMzero(speed, slot);
    }

    /**
     * 设置mZero板载编码电机right speed
     *
     * @param speed
     * @return
     */
    public static byte[] getBytes_setEncoderMotorSpeedRightForMzero(int speed) {
        int slot = 2;
        return getBytes_setEncoderMotorSpeedLeftForMzero(speed, slot);
    }

    /**
     * 设置mZero板载编码电机left speed degrees
     */
    public static byte[] getBytes_setEncoderMotorSpeedDegreesLeftForMzero(int speed, int degrees) {
        int negativeFlagForSlot;
        if (degrees >= 0) {
            negativeFlagForSlot = 0x00;
        } else {
            negativeFlagForSlot = 0xff;
        }

        final ByteBuffer buf_speed = ByteBuffer.allocate(2);
        buf_speed.putShort((short) speed);
        buf_speed.position(0);
        final byte speed_b1 = buf_speed.get();
        final byte speed_b2 = buf_speed.get();

        final ByteBuffer buf_distance = ByteBuffer.allocate(2);
        buf_distance.putShort((short) degrees);
        buf_distance.position(0);
        final byte distance_b1 = buf_distance.get();
        final byte distance_b2 = buf_distance.get();

        byte[] bytes = new byte[14];
        bytes[0] = (byte) 0xff;
        bytes[1] = (byte) 0x55;
        bytes[2] = (byte) 0x0b;
        bytes[3] = (byte) 0x00;
        bytes[4] = (byte) 0x02;
        bytes[5] = (byte) 0x3e;
        bytes[6] = (byte) 0x01;
        bytes[7] = (byte) 0x02;
        bytes[8] = distance_b2;
        bytes[9] = distance_b1;
        bytes[10] = (byte) negativeFlagForSlot;
        bytes[11] = (byte) negativeFlagForSlot;
        bytes[12] = speed_b2;
        bytes[13] = speed_b1;

        return bytes;
    }

    /**
     * 设置mZero板载编码电机right speed degrees
     */
    public static byte[] getBytes_setEncoderMotorSpeedDegreesRightForMzero(int speed, int degrees) {
        int negativeFlagForSlot;
        if (degrees >= 0) {
            negativeFlagForSlot = 0x00;
        } else {
            negativeFlagForSlot = 0xff;
        }

        final ByteBuffer buf_speed = ByteBuffer.allocate(2);
        buf_speed.putShort((short) speed);
        buf_speed.position(0);
        final byte speed_b1 = buf_speed.get();
        final byte speed_b2 = buf_speed.get();

        final ByteBuffer buf_distance = ByteBuffer.allocate(2);
        buf_distance.putShort((short) degrees);
        buf_distance.position(0);
        final byte distance_b1 = buf_distance.get();
        final byte distance_b2 = buf_distance.get();

        byte[] bytes = new byte[14];
        bytes[0] = (byte) 0xff;
        bytes[1] = (byte) 0x55;
        bytes[2] = (byte) 0x0b;
        bytes[3] = (byte) 0x00;
        bytes[4] = (byte) 0x02;
        bytes[5] = (byte) 0x3e;
        bytes[6] = (byte) 0x01;
        bytes[7] = (byte) 0x01;
        bytes[8] = distance_b2;
        bytes[9] = distance_b1;
        bytes[10] = (byte) negativeFlagForSlot;
        bytes[11] = (byte) negativeFlagForSlot;
        bytes[12] = speed_b2;
        bytes[13] = speed_b1;

        return bytes;
    }

    public static byte[] getBytes_queryMZeroBatteryLife() {
        int length = 4;
        int index = INDEX_QUERY_BATTERY_LIFE;
        byte[] cmd = new byte[11];
        cmd[0] = (byte) 0xff;
        cmd[1] = 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = 0x01;
        cmd[5] = 0x3c;
        cmd[6] = 0x70;
        return cmd;
    }

    public static byte[] getBytes_resetAllSmartServos() {
        byte[] cmd = new byte[9];
        cmd[0] = (byte) 0xff;
        cmd[1] = 0x55;
        cmd[2] = 0x06;
        cmd[3] = 0x00;
        cmd[4] = 0x02;
        cmd[5] = 0x40;
        cmd[6] = 0x07;
        cmd[7] = 0x05;
        cmd[8] = (byte) 0xff;
        return cmd;
    }

    public static byte[] getBytes_unlockAllSmartServos() {
        byte[] cmd = new byte[10];
        cmd[0] = (byte) 0xff;
        cmd[1] = 0x55;
        cmd[2] = 0x07;
        cmd[3] = 0x00;
        cmd[4] = 0x02;
        cmd[5] = 0x40;
        cmd[6] = 0x01;
        cmd[7] = 0x05;
        cmd[8] = (byte) 0xff;
        cmd[9] = 0x01;
        return cmd;
    }


    /*---------------------设置摇杆------------------------*/

    /**
     * 设置摇杆(目前只有平衡车)
     *
     * @param x 取值范围 [-255,255]
     * @param y 取值范围 [-255,255]
     * @return
     */
    public static byte[] getBytes_setJoystick(int x, int y) {
        int index = 0;
        int length = 8;
        int action = WRITE;
        int device = DEV_JOYSTICK;
        int port = 0;
        byte[] cmd = new byte[11];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) length;
        cmd[3] = (byte) index;
        cmd[4] = (byte) action;
        cmd[5] = (byte) device;
        cmd[6] = (byte) port;
        final ByteBuffer bufx = ByteBuffer.allocate(2);
        bufx.putShort((short) x);
        bufx.position(0);
        final byte bx1 = bufx.get();
        final byte bx2 = bufx.get();
        cmd[8] = bx1;
        cmd[7] = bx2;
        final ByteBuffer bufy = ByteBuffer.allocate(2);
        bufy.putShort((short) y);
        bufy.position(0);
        final byte by1 = bufy.get();
        final byte by2 = bufy.get();
        cmd[10] = by1;
        cmd[9] = by2;
        return cmd;
    }

//    /**
//     * 读取通用sensor的数据
//     * @return
//     */
//    public static byte[] getBytes_querySensor(int ) {
//        int index = 0;
//
//    }


    /*---------------------其他------------------------*/

    /**
     * 解析固件版本号,转成EnumDeviceBoardName
     *
     * @param firmwareVersion
     * @return
     */
    public static BoardName getBoardName(String firmwareVersion) {

        if (TextUtils.isEmpty(firmwareVersion)) {
            return BoardName.unknown;
        }
        //03.01.000  deviceCode.protocolCode.buildCode
        String[] deviceStrings = firmwareVersion.split("\\."); //需要转义
        if (deviceStrings.length == 0) {
            return BoardName.unknown;
        }
        try {
            String deviceCodeStr = deviceStrings[0].trim();
            int deviceCode = Integer.valueOf(deviceCodeStr, 16); //String解析为16进制
            BoardName enumDeviceBoardName;
            switch (deviceCode) {
                case 1:     // starter
                case 2:     // 音乐套件
                case 3:     // 电子套件
                case 4:     // Ultimate1.0
                case 5:     // XY套件
                case 0x0A:  // Orion
                    enumDeviceBoardName = BoardName.orion;
                    break;
                case 0x0e:
                    enumDeviceBoardName = BoardName.megaPi;
                    break;
                case 6:     // mBot
                    enumDeviceBoardName = BoardName.mcore;
                    break;
                case 9:     // Ranger
                    enumDeviceBoardName = BoardName.auriga;
                    break;
                case 0x22:
                    enumDeviceBoardName = BoardName.airblock;
                    break;
                case 0x20:
                    enumDeviceBoardName = BoardName.octopus;
                    break;
                default:
                    enumDeviceBoardName = BoardName.unknown;

            }
            return enumDeviceBoardName;
        } catch (Exception e) {
            return BoardName.unknown;
        }
    }

    public enum BoardName {
        orion,
        mcore,
        megaPi,
        auriga,
        unknown,
        airblock,
        octopus
    }

}
