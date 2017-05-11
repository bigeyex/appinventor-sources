package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 17/3/30.
 * 节拍
 */

public class BuzzerTempo {
    public final short ONE;
    public final short TWO;
    public final short ONE_OF_EIGHT;
    public final short ONE_OF_FOUR;
    public final short ONE_OF_TWO;

    public BuzzerTempo(int timeOfOne) {
        ONE = (short) timeOfOne;
        TWO = (short) (timeOfOne * 2);
        ONE_OF_EIGHT = (short)(timeOfOne / 8);
        ONE_OF_FOUR = (short)(timeOfOne / 4);
        ONE_OF_TWO = (short)(timeOfOne / 2);

    }

}
