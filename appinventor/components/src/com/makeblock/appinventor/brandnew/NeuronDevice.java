package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/24.
 */

public abstract class NeuronDevice extends Device {
    public NeuronDevice(String boardName, String deviceName, FirmwareRespond firmwareRespond) {
        super(boardName, deviceName, firmwareRespond);
    }

    @Override
    protected RespondParser createInstructionParser() {
        NeuronRespondParser neuronRespondParser = new NeuronRespondParser();
        neuronRespondParser.setOnRespondReceiveListener(new NeuronRespondParser.OnRespondReceiveListener() {
            @Override
            public void onRespondReceive(NeuronRespond neuronRespond) {
                handleRespond(neuronRespond);
            }
        });
        return neuronRespondParser;
    }

    protected abstract void handleRespond(NeuronRespond rj25Respond);

}
