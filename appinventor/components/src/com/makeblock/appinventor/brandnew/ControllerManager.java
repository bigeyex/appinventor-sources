package com.makeblock.appinventor.brandnew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 17/5/5.
 */

public class ControllerManager{

    public static final int CONNECT_OK = 0;
    public static final int CONNECT_WRONG = 1;
    public static final int DISCONNECT = 2;

    private static ControllerManager controllerManager = new ControllerManager();

    private Device connectedDevice = new NoneDevice();

    private List<OnConnectStateChangeListener> onConnectStateChangeListeners = new ArrayList<OnConnectStateChangeListener>();

    private ControllerManager() {}

    public static ControllerManager getInstance() {
        return controllerManager;
    }

    public void connect() {

    }

    public void setConnectedDevice(Device device) {
        if (device instanceof UnKnowDevice) {
            ((UnKnowDevice) device).queryFirmwareVersion();
        }
        this.connectedDevice = device;
        notifyOnConnectStateChange(device);
    }

    public Device getConnectedDevice() {
        return connectedDevice;
    }

    public int getConnectingState() {
        if (connectedDevice instanceof NoneDevice) {
            return DISCONNECT;
        } else if (connectedDevice instanceof UnKnowDevice) {
            return CONNECT_WRONG;
        }
        return CONNECT_OK;
    }

    public void onConnectDisconnected() {
        setConnectedDevice(new NoneDevice());
    }

    public void onSearchEvent(int type) {

    }

    public void onDeviceConnected() {
        if (connectedDevice instanceof UnKnowDevice) {
            ((UnKnowDevice) connectedDevice).queryFirmwareVersion();
        } else {
            setConnectedDevice(new UnKnowDevice());
        }
    }

    public void registerOnConnectStateChangeListener(OnConnectStateChangeListener listener) {
        onConnectStateChangeListeners.add(listener);
    }

    public void unRegisterOnConnectStateChangeListener(OnConnectStateChangeListener listener) {
        onConnectStateChangeListeners.remove(listener);
    }

    private void notifyOnConnectStateChange(Device device) {
        for (OnConnectStateChangeListener listener : onConnectStateChangeListeners) {
            listener.onConnectStateChange(device);
        }
    }

    public interface OnConnectStateChangeListener {
        void onConnectStateChange(Device device);
    }
}
