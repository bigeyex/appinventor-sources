package com.makeblock.appinventor.brandnew;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;

import java.util.ArrayList;

/**
 * Created by tom on 17/5/5.
 */

/**
 * Created by tom on 16/12/6.
 * Base class for Makeblock robot for AppInventor
 * Implements Bluetooth connection here
 */
@SimpleObject
@UsesPermissions(permissionNames =
        "android.permission.BLUETOOTH, " +
                "android.permission.BLUETOOTH_ADMIN")
public class MakeblockBase extends AndroidNonvisibleComponent implements Component, Deleteable, ControllerManager.OnConnectStateChangeListener {

    private Activity activity;
    private Handler handler = new Handler();
    private Toast customToast;

    private int CONNECT_RSSI = -50;

    private Runnable connectRunnable = new Runnable() {
        @Override
        public void run() {
            if (BleManager.getInstance().getBluetoothConnectionState() == BleManager.STATE_CONNECTING) {
                showToast("Device is connecting.");
            } else if (BleManager.getInstance().getConnectedDevice() != null) {
                mRetryRequestVersionRunnable.run();
            } else {
                connect();
            }
            handler.removeCallbacks(connectRunnable);
            if (!BleManager.getInstance().isConnected()) {
                handler.postDelayed(this, 200);
            }
        }
    };

    private int retryRequestCount;
    private Runnable mRetryRequestVersionRunnable = new Runnable() {
        @Override
        public void run() {
            if (ControllerManager.getInstance().getConnectedDevice() instanceof UnKnowDevice) {
                if (++retryRequestCount > 100) {
                    return;
                }
                ControllerManager.getInstance().onDeviceConnected();
                handler.postDelayed(this, 100);
            }
        }
    };

    /**
     * Creates a new AndroidNonvisibleComponent.
     *
     * @param container the container that this component will be placed in
     */
    protected MakeblockBase(ComponentContainer container, String log) {
        super(container.$form());
        ControllerManager.getInstance().registerOnConnectStateChangeListener(this);
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Connect to your robot's Bluetooth.")
    public void ConnectToRobot() {
        if (BleManager.getInstance().isConnected()) {
            showToast("Device already connected. Please disconnect first.");
            return;
        }
        openBluetooth();
        BleManager.getInstance().init(activity);
        //开始搜索
        if (!BleManager.getInstance().isDiscovering()) {
            startDiscovery();
            showToast("Bluetooth starts discovery.");
        }
        handler.removeCallbacks(connectRunnable);
        handler.postDelayed(connectRunnable, 1000);
    }

    @SimpleFunction(description = "Disconnect your robot.")
    public void DisconnectRobot() {
        handler.removeCallbacks(connectRunnable);
        if (BleManager.getInstance().isConnected()) {
            BleManager.getInstance().disconnectBluetooth();
        } else {
            showToast("No device connected.");
        }
    }

    @SimpleFunction(description = "Set RSSI value for connection. (RSSI is a value represented bluetooth signal " +
            "which is returned by Bluetooth adapter. -60 ~ -30 is the recommended range, " +
            "NO LOWER THAN -80, or your robot's Bluetooth address could not be found by the adapter.)")
    public void SetConnectRSSI(int CONNECT_RSSI) {
        this.CONNECT_RSSI = CONNECT_RSSI;
    }

    @SimpleEvent(description = "When your device successfully connected to the robot, this method will be called.")
    public void ConnectedToRobot() {
        EventDispatcher.dispatchEvent(MakeblockBase.this, "ConnectedToRobot");
    }

    private void openBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            showToast("Bluetooth is turned on.");
        } else {
            showToast("Bluetooth was already on.");
        }
    }

    private void startDiscovery() {
        if (BleManager.getInstance().isDiscovering()) {
            showToast("Bluetooth is discovering.");
        } else {
            BleManager.getInstance().startDiscovery();
            showToast("Bluetooth start discovery.");
        }
    }

    private void connect() {
        if (BleManager.getInstance().isConnected()) {
            showToast("Please disconnect your device first.");
            return;
        }
        BleDevice deviceToConnect = getClosestDevice();
        if (deviceToConnect != null) {
            if (deviceToConnect.getRssi() > CONNECT_RSSI) {
                BleManager.getInstance().connect(deviceToConnect, null);
                showToast("Device starts connecting.");
            } else {
                showToast("Please get closer to your Bluetooth device.");
            }
        } else {
            showToast("No Bluetooth device found.");
        }
    }

    private BleDevice getClosestDevice() {
        ArrayList<BleDevice> devices = BleManager.getInstance().getDevices();
        if (devices.size() <= 0) {
            return null;
        }
        BleDevice currentDeviceBean = devices.get(0);
        for (int i = 0; i < devices.size(); i++) {
            if (i > 0) {
                if (devices.get(i).getRssi() > currentDeviceBean.getRssi()) {
                    currentDeviceBean = devices.get(i);
                }
            }
        }
        return currentDeviceBean;

    }


    private void showToast(String message) {
        if (customToast != null) {
            customToast.cancel();
        }
        customToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        customToast.show();
    }

    @Override
    public void onConnectStateChange(Device device) {
        if (device instanceof NoneDevice) {
            //disconnected
            showToast("Device disconnected");
        } else if (device instanceof UnKnowDevice) {
            //unsupported device
        } else {
            //supported device
            showToast("Device " + device.getDeviceName() + " connected");
            ConnectedToRobot();
        }
    }

    @Override
    public void onDelete() {
        ControllerManager.getInstance().unRegisterOnConnectStateChangeListener(this);
    }
}
