package com.makeblock.appinventor.brandnew;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liaoyuhao on 2016/7/15.
 */
public class BleManager {

    public static final String SERVICE_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final String NOTIFY_UUID = "0000ffe2-0000-1000-8000-00805f9b34fb";
    public static final String WRITE_UUID = "0000ffe3-0000-1000-8000-00805f9b34fb";

    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    private static final String TAG = BleManager.class.getSimpleName();
    private List<BleDevice> devices = new ArrayList<BleDevice>();
    private CharacteristicNotifyListener characteristicNotifyListener;
    private List<BleNotifyListener> bleNotifyListenerList = new ArrayList<BleNotifyListener>();
    private List<SendInstructionListener> sendInstructionListenerList = new ArrayList<SendInstructionListener>();
    private BleNotifyListener mainNotifyListener = new BleNotifyListener(SERVICE_UUID, NOTIFY_UUID) {
        @Override
        public void onReceiveData(byte[] data) {
            ControllerManager.getInstance().getConnectedDevice().parseBytes(data);
        }
    };

    private ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
        @Override
        public void onStateChange(int state) {
            switch (state) {
                case ConnectCallback.STATE_CONNECTED:
                    ControllerManager.getInstance().onDeviceConnected();
                    break;
                case ConnectCallback.STATE_SERVICES_DISCOVERED:
                case ConnectCallback.STATE_CONNECTING:
                case ConnectCallback.STATE_DISCONNECTING:
                case ConnectCallback.STATE_SERVICES_DISCOVERED_FAIL:
                case ConnectCallback.STATE_UNINIT:
                    break;
                case ConnectCallback.STATE_DISCONNECTED:
                    ControllerManager.getInstance().onConnectDisconnected();
                    currentRssi = -100;
                    break;
                case ConnectCallback.STATE_CONNECT_OVERTIME:

            }
        }
    };
    private int currentRssi = -100;
    private BleConsultant bleConsultant;

    public BleDevice getConnectedDevice() {
        return connectedDevice;
    }

    private BleDevice connectedDevice;

    private static BleManager instance = new BleManager();

    public static BleManager getInstance() {
        return instance;
    }

    public boolean isBluetoothEnable() {
        if (checkSupport()) {
            return bleConsultant.isEnabled();
        }
        return false;
    }

    private boolean checkSupport() {
        if (bleConsultant != null) {
            return true;
        }
        return false;
    }

    public BleManager() {
        bleNotifyListenerList.add(mainNotifyListener);
    }

    /**
     * -1表示不支持, 2表示2.0, 4表示BLE
     *
     * @return
     */
    public int getSupportBluetoothVersion() {
        if (bleConsultant == null) {
            return -1;
        } else {
            return 4;
        }
    }

    public void stopDiscovery() {
        if (checkSupport()) {
            bleConsultant.setScanDevicesHelper(null);
        }
    }

    public void startDiscovery() {
        if (checkSupport()) {
            clearDevices();
            boolean success = bleConsultant.setScanDevicesHelper(new ScanDevicesHelper() {
                @Override
                public void reportDevices(List<BleDevice> bleDeviceList) {
                    devices = bleDeviceList;
                }

                @Override
                public boolean deviceFilter(BleDevice bleDevice) {
                    //name: 1.不能为空  2.包含Makeblock
                    String name = bleDevice.getName();
                    if (TextUtils.isEmpty(name)) {
                        return false;
                    }
                    if (!(name.contains("Makeblock")) && !(name.contains("makeblock"))) {
                        return false;
                    }
                    return true;
                }

                @Override
                public long getReportPeriod() {
                    return 1000;
                }
            });
            if (success) {
                transmitSearchEvent(SearchEvent.DISCOVER_START);
            } else {
                transmitSearchEvent(SearchEvent.OPEN_BLUETOOTH);
            }
        }

    }

    private void transmitSearchEvent(int type) {
        ControllerManager.getInstance().onSearchEvent(type);
    }

    private void clearDevices() {
        if (connectedDevice != null) {
            Iterator<BleDevice> iterator = devices.iterator();
//        List<BluetoothDevice> removeList = new ArrayList<>();
            while (iterator.hasNext()) {
                BleDevice bluetoothDevice = iterator.next();
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
            return bleConsultant.isDiscovering();
        }
        return false;
    }

    //蓝牙连接:开启ConnectThread线程连接
    public void connect(final BleDevice bluetoothDevice, final BleConnectCallback callback) {
        if (checkSupport()) {
            bleConsultant.connect(bluetoothDevice, new ConnectCallback() {
                @Override
                public void onStateChange(int state) {
                    switch (state) {
                        case STATE_CONNECTED:
                            break;
                        case STATE_SERVICES_DISCOVERED:
                            registerNotify(SERVICE_UUID, NOTIFY_UUID);
                            connectedDevice = bluetoothDevice;
                            break;
                        case STATE_DISCONNECTED:
                            break;
                    }
                    if (callback != null) {
                        callback.onStateChange(state);
                    }
                }

                @Override
                public void onOvertime() {
                    disconnectBluetooth();
                    if (callback != null) {
                        callback.onStateChange(ConnectCallback.STATE_CONNECT_OVERTIME);
                    }
                }

                @Override
                public long getOvertimeTime() {
                    return 5000;
                }
            });
        }
    }

    private void registerNotify(String serviceUuid, String notifyUuid) {
        if (characteristicNotifyListener == null) {
            characteristicNotifyListener = new CharacteristicNotifyListener() {
                @Override
                public void onReceive(String serviceUUID, String characteristicUUID, byte[] value) {
                    for (BleNotifyListener listener : bleNotifyListenerList) {
                        listener.onReceiveData(serviceUUID, characteristicUUID, value);
                    }
                }
            };
        }
        bleConsultant.setNotifyListener(characteristicNotifyListener);
        bleConsultant.registerNotify(serviceUuid, notifyUuid);
    }


    public void disconnectBluetooth() {
        ControllerManager.getInstance().onConnectDisconnected();
        if (checkSupport()) {
            bleConsultant.disconnect();
        }
        currentRssi = -100;
        connectedDevice = null;
    }

    /**
     * 写入通用的
     *
     * @param bytes
     */
    public boolean writeToBluetooth(byte[] bytes) {
//        byte[] send = new byte[] {(byte) 0xf0, 0x01, 0x10, 0x00, 0x0f, (byte) 0xf7};
        return writeToBluetooth(SERVICE_UUID, WRITE_UUID, bytes);
    }

    public void addSendInstructionListener(SendInstructionListener listener) {
        sendInstructionListenerList.add(listener);
    }

    public void removeSendInstructionListener(SendInstructionListener listener) {
        sendInstructionListenerList.remove(listener);
    }

    public boolean sendInstruction(Instruction instruction) {
        if (instruction != null && writeToBluetooth(instruction.getBytes())) {
            for (SendInstructionListener listener : sendInstructionListenerList) {
                listener.onInstructionSending(instruction);
            }
            Log.d("BLE", "发送指令:" + instruction);
            return true;
        }
        Log.d("BLE", "没有发送指令:" + instruction);
        return false;
    }

    public boolean writeToBluetooth(String serviceUUID, String characteristicUUID, byte[] bytes) {
        if (getSupportBluetoothVersion() == 4) {
            return bleConsultant.sendToBle(serviceUUID, characteristicUUID, bytes);
        }
        return false;
    }

    public boolean readCharacteristic(String service, String characteristic) {
        if (getSupportBluetoothVersion() == 4) {
            return bleConsultant.readCharacteristic(service, characteristic, new ReadCallback() {
                @Override
                public void onCharacteristicRead(int status, byte[] data) {

                }

                @Override
                public void onOvertime() {

                }

                @Override
                public long getOvertimeTime() {
                    return 0;
                }
            }, true);
        }
        return false;
    }

    public boolean isConnected() {
        return connectedDevice != null;
    }

    public BleDevice getDevice(String address) {
        for (BleDevice item : devices) {
            if (item.getAddress().equals(address))
                return item;
        }
        return null;
    }

    public BleDevice getDevice(int index) {
        try {
            return devices.get(index);
        } catch (Exception e) {

        }
        return null;
    }

    public void onExit() {
        disconnectBluetooth();
        devices.clear();
    }

    public int getBluetoothConnectionState() {
        if (checkSupport()) {
            switch (bleConsultant.getBleStatus()) {
                case CONNECTED:
                    return STATE_CONNECTED;
                case DISCONNECTED:
                    return STATE_DISCONNECTED;
                case CONNECTING:
                    return STATE_CONNECTING;
            }
        }
        return STATE_DISCONNECTED;
    }

    public ArrayList<BleDevice> getDevices() {
        return new ArrayList<BleDevice>(devices);
    }

    public int getBluetoothRssi() {
        if (checkSupport()) {
            bleConsultant.requestCurrentRssi(new RequestRssiCallback() {
                @Override
                public void onReadRemoteRssi(int rssi, int status) {
                    currentRssi = rssi;
                }

                @Override
                public void onOvertime() {

                }

                @Override
                public long getOvertimeTime() {
                    return 500;
                }
            });
        }
        return currentRssi;
    }

    public void addNotifyListener(BleNotifyListener listener) {
        bleNotifyListenerList.add(listener);
    }

    public void removeNotifyListener(BleNotifyListener listener) {
        bleNotifyListenerList.remove(listener);
    }

    public void stopProgram() {
        bleConsultant.setScanDevicesHelper(null);
    }

    public void init(Context context) {
        try {
            BleLog.DEBUG = true;
            bleConsultant = BleConsultant.getInstance();
            bleConsultant.init(context);
            bleConsultant.setConnectionStateListener(connectionStateListener);
        } catch (Exception e) {
            bleConsultant = null;
        }
    }

    public double getDistance(BleDevice device) {
        return calculateAccuracy(-59, device.getRssi());
    }

    private double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }
        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            double accuracy = Math.pow(ratio, 10);
            return accuracy;
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
//            double accuracy = (0.6) * Math.pow(ratio, 7) + 0.2;
            return accuracy;
        }
    }

    public void requestCurrentRssi(RequestRssiCallback callback) {
        BleConsultant.getInstance().requestCurrentRssi(callback);
    }

}
