package com.makeblock.appinventor.brandnew;

/**
 * Created by liaoyuhao on 2016/4/25.
 */
public class SearchEvent {
    public int eventType;
    public static final int DISCOVER_FAIL = -1;
    public static final int DISCOVER_START = 0;
    public static final int DISCOVER_FINISH = 1;
    public static final int DISCOVER_FOUND = 2;
    public static final int CONNECT_START = 3;
//    public static final int CONNECT_RETRY = 4;
    public static final int CONNECT_FAIL = 5;
    public static final int CONNECT_SUCCEED = 6;
    public static final int CONNECT_DISCONNECTED = 7;
    public static final int OPEN_BLUETOOTH = 8;
    public static final int BOND_BONDING = 9;
    public static final int BOND_BONDED = 10;
    public static final int BOND_CANCEL = 11;

    public SearchEvent(int eventType) {
        super();
        this.eventType = eventType;
    }
}
