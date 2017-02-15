package com.makeblock.appinventor;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by liaoyuhao on 2016/7/15.
 */
public class UnifiedBluetoothManager {

    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    private static final String TAG = UnifiedBluetoothManager.class.getSimpleName();
    private ArrayList<DeviceBean> devices = new ArrayList<DeviceBean>();

    public DeviceBean getConnectedDevice() {
        return connectedDevice;
    }

    private DeviceBean connectedDevice;

    private byte[] dataReceived;

    private UnifiedBluetoothAdapter bluetoothAdapter;

    private MBotBase mBotBase;

    private static UnifiedBluetoothManager unifiedBluetoothManager = new UnifiedBluetoothManager();

    public static UnifiedBluetoothManager getInstance() {
        return unifiedBluetoothManager;
    }

    public boolean isBluetoothEnable() {
        if (checkSupport()) {
            return bluetoothAdapter.isEnable();
        }
        return false;
    }

    private boolean checkSupport() {
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isSupport()) {
                return true;
            } else {
                bluetoothAdapter = null;
            }
        }
        return false;
    }

    private UnifiedBluetoothManager() {

    }

    public void init(Activity activity) {
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            if (Build.VERSION.SDK_INT < 21) {
//                bluetoothAdapter = new BluetoothAdapter_BLE_Old();
//            } else {
            bluetoothAdapter = new BluetoothAdapter_BLE(activity);
//            }
        }
        checkSupport();
        if (bluetoothAdapter != null) {
            bluetoothAdapter.setConnectedStateListener(new UnifiedBluetoothAdapter.StateChangeListener() {
                @Override
                public void onConnectedDevice(DeviceBean bluetoothDevice) {
                    update4BluetoothConnected(bluetoothDevice);
                }

                @Override
                public void onDisconnectDevice() {
                    connectedDevice = null;
//                    ControllerManager.onConnectDisconnected();
                }

                @Override
                public void onReceiveData(byte[] msg) {
                    dataReceived = msg;
                    Log.e("wbp", "onReceiveData" + MBotBase.byteArrayToHex(msg));
                    if (dataReceivedListener != null) {
                        dataReceivedListener.onDataReceived(msg);
                    }
//                    ((MBot) mBotBase).AfterReceive(String.valueOf(mBotBase.extractFloat(msg)));
//                    ControllerManager.receiveData(msg);
                }

                @Override
                public void onFoundDevice(DeviceBean device) {
                    int index = getContainedIndex(device);
                    if (index == -1) {
                        device.updateTime = System.currentTimeMillis();
                        devices.add(device);
//                        ControllerManager.onSearchEvent(SearchEvent.DISCOVER_FOUND);
                    } else {
                        devices.get(index).distance = device.distance;
                        devices.get(index).setRssi(device.getRssi());
                        devices.get(index).updateTime = System.currentTimeMillis();
                    }
                }

                @Override
                public void onSearchEvent(int type) {
//                    ControllerManager.onSearchEvent(type);
                }
            });
        }
    }

    private DataReceivedListener dataReceivedListener;

    public void setOnDataReceicedListener(DataReceivedListener dataReceicedListener) {
        this.dataReceivedListener = dataReceicedListener;
    }

    public byte[] getDataReceived() {
        return dataReceived;
    }

    public interface DataReceivedListener {
        void onDataReceived(byte[] dataReceived);
    }

    /**
     * -1表示不支持, 2表示2.0, 4表示BLE
     *
     * @return
     */
    public int getSupportBluetoothVersion() {
        if (bluetoothAdapter == null)
            return -1;
        if (bluetoothAdapter instanceof BluetoothAdapter_BLE)
            return 4;
        return -1;
    }

    public void stopDiscovery() {
        if (checkSupport()) {
            bluetoothAdapter.stopDiscovery();
        }
    }

    public void startDiscovery() {
        if (checkSupport()) {
            clearDevices();
            bluetoothAdapter.startDiscovery();
        }
    }

    private void clearDevices() {
        if (connectedDevice != null) {
            Iterator<DeviceBean> iterator = devices.iterator();
//        List<BluetoothDevice> removeList = new ArrayList<>();
            while (iterator.hasNext()) {
                DeviceBean bluetoothDevice = iterator.next();
                if (!bluetoothDevice.equals(connectedDevice)) {
                    iterator.remove();
                }
            }
        } else {
            devices.clear();
        }
    }

    public Boolean isDiscovering() {
        if (checkSupport()) {
            return bluetoothAdapter.isDiscovering();
        }
        return false;
    }

    //蓝牙连接:开启ConnectThread线程连接
    public void connect(DeviceBean bluetoothDevice) {
        if (checkSupport()) {
            bluetoothAdapter.connect(bluetoothDevice);
        }
    }

    public void disconnectBluetooth() {
        if (checkSupport()) {
            bluetoothAdapter.disconnect();
        }
        connectedDevice = null;
//        ControllerManager.onConnectDisconnected();
    }

    /*---------------------发送数据给固件端------------------------*/
    public void writeToBluetooth(final byte[] bytes) {
        if (checkSupport()) {
            bluetoothAdapter.write(bytes);
        }
    }

    public boolean isConnected() {
        return connectedDevice != null;
    }

    public DeviceBean getDevice(String address) {
        for (DeviceBean item : devices) {
            if (item.bluetoothDevice.getAddress().equals(address))
                return item;
        }
        return null;
    }

    public DeviceBean getDevice(int index) {
        try {
            return devices.get(index);
        } catch (Exception e) {

        }
        return null;
    }

    //蓝牙连接成功：开启数据解析Thread
    private void update4BluetoothConnected(DeviceBean bluetoothDevice) {
        connectedDevice = bluetoothDevice;
//        EventBus.getDefault().post(new SearchEvent(SearchEvent.CONNECT_SUCCEED));
    }

    public void needGotoSystemBluetooth() {
//        ControllerManager.gotoSystemBluetooth();
    }

    public void onExit() {
        disconnectBluetooth();
        devices.clear();
    }

    public int getBluetoothConnectionState() {
        if (checkSupport()) {
            return bluetoothAdapter.getConnectionState();
        }
        return STATE_DISCONNECTED;
    }

    private int getContainedIndex(DeviceBean deviceBean) {
        if (devices.size() != 0) {
            int index = 0;
            for (DeviceBean item : devices) {
                if (item.equals(deviceBean)) {
                    return index;
                }
                ++index;
            }
        }
        return -1;
    }

    public ArrayList<DeviceBean> getDevices() {
        return devices;
    }

    public int getBluetoothRssi() {
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.getRssi();
        }
        return -100;
    }
}
