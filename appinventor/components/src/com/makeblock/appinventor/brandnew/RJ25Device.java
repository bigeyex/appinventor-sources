package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/24.
 */

public abstract class RJ25Device extends Device {
    public RJ25Device(String boardName, String deviceName, FirmwareRespond firmwareRespond) {
        super(boardName, deviceName, firmwareRespond);
    }

    @Override
    protected RespondParser createInstructionParser() {
        RJ25RespondParser rj25RespondParser = new RJ25RespondParser();
        rj25RespondParser.setOnRespondReceiveListener(new RJ25RespondParser.OnRespondReceiveListener() {
            @Override
            public void onInstructionReceive(RJ25Respond rj25Respond) {
                handleRespond(rj25Respond);
            }
        });
        return rj25RespondParser;
    }

    protected abstract void handleRespond(RJ25Respond rj25Respond);

}
