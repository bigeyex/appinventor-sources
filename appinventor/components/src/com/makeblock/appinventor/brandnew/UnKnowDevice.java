package com.makeblock.appinventor.brandnew;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by hupihuai on 2017/4/17.
 * 刚连上设备 还不知掉具体是什么设备
 */

public class UnKnowDevice extends RJ25Device {

    public UnKnowDevice() {
        super(null, null, null);
    }

    public void queryFirmwareVersion() {
        QueryFirmwareInstruction instruction = new QueryFirmwareInstruction();
        BleManager.getInstance().sendInstruction(instruction);
    }

    @Override
    protected void handleRespond(RJ25Respond rj25Respond) {
        Log.e("wbp", "UnKnowDevice Receive Data:" + rj25Respond.toString());
        if (rj25Respond instanceof FirmwareRespond) {
            FirmwareRespond firmwareRespond = (FirmwareRespond) rj25Respond;
            String firmwareVersion = firmwareRespond.firmwareVersion;
            Device device;
            switch (getBoardName(firmwareVersion)) {
                case orion:
                    device = new StarterDevice(firmwareRespond);
                    break;
                case megaPi:
                    device = new Ultimate2Device(firmwareRespond);
                    break;
                case mcore:
                    device = new MBotDevice(firmwareRespond);
                    break;
                case auriga:
                    device = new MBotRangerDevice(firmwareRespond);
                    break;
                case airblock:
                    device = new AirBlockDevice(firmwareRespond);
                    break;
                case octopus:
                    device = new SmartServoDevice(firmwareRespond);
                    break;
                case unknown:
                default:
                    device = new UnKnowDevice();
            }
            ControllerManager.getInstance().setConnectedDevice(device);
        }
    }

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

}

