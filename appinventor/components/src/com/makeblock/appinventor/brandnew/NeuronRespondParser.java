package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/14.
 */

public class NeuronRespondParser extends RespondParser {
    private static final byte[] HEAD = new byte[]{NeuronInstruction.HEAD};
    private static final byte[] TAIL = new byte[]{NeuronInstruction.TAIL};
    private static final int POSITION_CMD = 3;

    private OnRespondReceiveListener onRespondReceiveListener;

    public NeuronRespondParser() {
        super(HEAD, TAIL);
    }

    @Override
    protected void packData(byte[] bytes) {
        NeuronRespond respond = null;
        if (check(bytes)) {
            int index = POSITION_CMD + 1;
            int data;
            switch (bytes[POSITION_CMD]) {
                case AirBlockElectricRespond.cmd:
                    data = NeuronByteUtil.convert7to8(bytes, index, index + NeuronByteUtil.SIZE_float);
                    float voltage = Float.intBitsToFloat(data);
                    index += NeuronByteUtil.SIZE_float;
                    data = NeuronByteUtil.convert7to8(bytes, index, index + NeuronByteUtil.SIZE_float);
                    float percent = Float.intBitsToFloat(data);
                    respond = new AirBlockElectricRespond(voltage, percent);
                    break;
                case AirBlockPowerStateRespond.cmd:
                    data = NeuronByteUtil.convert7to8(bytes, index, index + NeuronByteUtil.SIZE_BYTE);
                    int mode = data;
                    respond = new AirBlockPowerStateRespond(mode);
            }
        }
        if (onRespondReceiveListener != null && respond != null) {
            onRespondReceiveListener.onRespondReceive(respond);
        }
    }


    private boolean check(byte[] bytes) {
        if (bytes == null || bytes.length <= 3)
            return false;
        byte checkSum = 0;
        for (int i = 1; i < bytes.length - 2; ++i) {
            checkSum += bytes[i];
        }
        checkSum &= 0x7f;
        return checkSum == bytes[bytes.length - 2];
    }


    public void setOnRespondReceiveListener(OnRespondReceiveListener listener) {
        onRespondReceiveListener = listener;
    }

    public interface OnRespondReceiveListener {
        void onRespondReceive(NeuronRespond respond);
    }
}
