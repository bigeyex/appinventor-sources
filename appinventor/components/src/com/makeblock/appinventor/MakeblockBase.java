package com.makeblock.appinventor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.widget.Toast;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.makeblock.appinventor.Bluetooth.BluetoothAdapter_BLE;
import com.makeblock.appinventor.Bluetooth.BluetoothFlowValve;
import com.makeblock.appinventor.Bluetooth.bluetoothManager;
import com.makeblock.appinventor.Bluetooth.Command;
import com.makeblock.appinventor.Bluetooth.DeviceBean;
import com.makeblock.appinventor.Bluetooth.PackDataHelper;
import com.makeblock.appinventor.Bluetooth.SearchEvent;
import com.makeblock.appinventor.Bluetooth.UnifiedBluetoothManager;
import com.makeblock.appinventor.Bluetooth.UnifiedBluetoothAdapter;

import java.util.ArrayList;

/**
 * Created by tom on 16/12/6.
 * Base class for Makeblock robot for AppInventor
 * Implements Bluetooth connection here
 */
@SimpleObject
@UsesPermissions(permissionNames =
        "android.permission.BLUETOOTH, " +
                "android.permission.BLUETOOTH_ADMIN")
public class MakeblockBase extends AndroidNonvisibleComponent
        implements Component, Deleteable {

    protected static final int DEVICE_ULTRASONIC = 1;
    protected static final int DEVICE_TEMPERATURE = 2;
    protected static final int DEVICE_LIGHTNESS = 3;
    protected static final int DEVICE_LOUDNESS = 7;
    protected static final int DEVICE_LINE_FOLLOWER = 17;
    protected static final int DEVICE_TOP_BUTTON = 22;

    private Activity activity;
    protected UnifiedBluetoothManager bluetoothManager;
    private Toast customToast;
    private Handler handler = new Handler();
    private boolean isConnected = false;
    private int connectRSSI = -30;  //default borderline for Bluetooth automatic connecting
    protected static BluetoothFlowValve bluetoothFlowValve = new BluetoothFlowValve();

    protected final String logTag;

    private Runnable connectRunnable = new Runnable() {
        @Override
        public void run() {
            if (bluetoothManager.getBluetoothConnectionState() == UnifiedBluetoothManager.STATE_CONNECTING) {
                showToast("Device is connecting.");
            } else if (bluetoothManager.getConnectedDevice() != null) {
                showToast("Device " + bluetoothManager.getConnectedDevice().bluetoothDevice.getAddress() + " is connected.");
                isConnected = true;
            } else {
                connect();
            }
            handler.removeCallbacks(connectRunnable);
            if (!isConnected) {
                handler.postDelayed(this, 200);
            }
        }
    };

    protected MakeblockBase(ComponentContainer container, String logTag) {
        super(container.$form());
        this.logTag = logTag;
        this.activity = container.$context();
        bluetoothManager = UnifiedBluetoothManager.getInstance();
    }

    protected MakeblockBase() {
        super(null);
        logTag = null;
    }

    @SimpleFunction(description = "Connect to your robot's Bluetooth.")
    public void ConnectToRobot() {
        if (isConnected) {
            showToast("Device already connected. Please disconnect first.");
            return;
        }
        openBluetooth();
        bluetoothManager.init(activity);
        //开始搜索
        if (!bluetoothManager.isDiscovering()) {
            startDiscovery();
            showToast("Bluetooth starts discovery.");
        }
        handler.removeCallbacks(connectRunnable);
        handler.postDelayed(connectRunnable, 1000);
    }

    @SimpleFunction(description = "Disconnect your robot.")
    public void DisconnectRobot() {
        handler.removeCallbacks(connectRunnable);
        if (isConnected) {
            bluetoothManager.disconnectBluetooth();
            isConnected = false;
            showToast("Device disconnected");
        } else {
            showToast("No device connected.");
        }
    }

    @SimpleFunction(description = "Set RSSI value for connection. (RSSI is a value represented bluetooth signal " +
            "which is returned by Bluetooth adapter. -60 ~ -30 is the recommended range, " +
            "NO LOWER THAN -80, or your robot's Bluetooth address could not be found by the adapter.)")
    public void SetConnectRSSI(int connectRSSI) {
        this.connectRSSI = connectRSSI;
    }

    protected final boolean isBluetoothConnected(String functionName) {
        if (!bluetoothManager.isConnected()) {
            form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE);
            return false;
        }

        return true;
    }

    @Override
    public void onDelete() {
        if (handler != null) {
            handler.removeCallbacks(connectRunnable);
        }
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

    private DeviceBean getDeviceBean() {
        ArrayList<DeviceBean> devices = getDevices();
        DeviceBean currentDeviceBean = devices.get(0);
        for (int i = 0; i < devices.size(); i++) {
            if (i > 0) {
                if (devices.get(i).getRssi() > currentDeviceBean.getRssi()) {
                    currentDeviceBean = devices.get(i);
                }
            }
        }
        return currentDeviceBean;

    }

    private ArrayList<DeviceBean> getDevices() {
        return bluetoothManager.getDevices();
    }

    private void startDiscovery() {
        if (bluetoothManager.isDiscovering()) {
            showToast("Bluetooth is discovering.");
        } else {
            bluetoothManager.startDiscovery();
            showToast("Bluetooth start discovery.");
        }
    }

    private void connect() {
        if (bluetoothManager.isConnected()) {
            showToast("Please disconnect your device first.");
            return;
        }
        DeviceBean deviceToConnect = getDeviceBean();
        if (deviceToConnect != null) {
            if (deviceToConnect.getRssi() > connectRSSI) {
                bluetoothManager.connect(deviceToConnect);
                showToast("Device starts connecting.");
            } else {
                showToast("Please get closer to your Bluetooth device.");
            }
        } else {
            showToast("No Bluetooth device found.");
        }
    }

    private void showToast(String message) {
        if (customToast != null) {
            customToast.cancel();
        }
        customToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        customToast.show();
    }
}
