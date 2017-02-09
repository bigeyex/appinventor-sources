package com.google.appinventor.components.runtime.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * API 21 以上,可以设置ScanMode,默认ScanSetting为 LOW_LATENCY(低延迟,最高功耗)
 * 因为发现原生系统有bug,不会重复发现信号较强的设备,所以添加 ScanModeHelper 内部类,在判断可能不正常工作的情况下切换到 LOW_POWER
 * Created by liaoyuhao on 2016/7/15.
 */
public class BluetoothAdapter_BLE extends UnifiedBluetoothAdapter {

    private static final UUID SERVICE_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    private static final UUID READ_UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb");
    private static final UUID WRITE_UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb");

    private static final UUID PROGRAM_UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb");
    private static final UUID PROGRAM_CHARACTERISTICS_UUID = UUID.fromString("0000ffe5-0000-1000-8000-00805f9b34fb");

    private static final String TAG = BluetoothAdapter_BLE.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter;
    protected BluetoothGatt bluetoothGatt;
    private ScanModeHelper mScanModeHelper;
    protected BluetoothGattCharacteristic readCharacteristic;
    protected BluetoothGattCharacteristic writeCharacteristic;
    protected BluetoothGattCharacteristic programCharacteristic;
    private ScanCallback mScanCallback = null;
    private boolean isProgramming = false;
    private int rssi = -100;

    private boolean isFirstSend = true;
    private boolean hasConnected = false;

    private Activity activity;

    private BluetoothAdapter.LeScanCallback mOldScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            onFoundDevice(device, rssi);
        }
    };

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] receiveData = characteristic.getValue();
            int count = receiveData.length;
            String s = "";
            for (int i = 0; i < receiveData.length; ++i) {
                s += String.format(" %02x", receiveData[i] & 0x0ff);
            }
            Log.i("lyh", "收到数据:" + s);
            if (isProgramming) {
            } else {
                receiveData(receiveData, count);
            }
            hasConnected = true;
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnectionState = UnifiedBluetoothManager.STATE_CONNECTED;
                bluetoothGatt.discoverServices();
                isFirstSend = true;

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                if (hasConnected) {
                    //在搜索页断连
                    transmitSearchEvent(SearchEvent.CONNECT_DISCONNECTED);
                    if (listener != null) {
                        listener.onDisconnectDevice();
                    }
                } else {
                    if (listener != null) {
                        listener.onSearchEvent(SearchEvent.CONNECT_FAIL);
                    }
                }
                hasConnected = false;
                mConnectionState = UnifiedBluetoothManager.STATE_DISCONNECTED;
                if (bluetoothGatt != null)
                    bluetoothGatt.disconnect();
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            isFirstSend = false;
            long now = System.currentTimeMillis();
            String s = "";
            for (int i = 0; i < characteristic.getValue().length; ++i) {
                s += String.format(" %02x", characteristic.getValue()[i] & 0x0ff);
            }
            Log.d("lyh", "写数据:" + s + ", 长度:" + characteristic.getValue().length + "间隔:" +(now - test));
            test = now;
        }

        private long test = 0;

        @Override
        // New services discovered
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                readCharacteristic = bluetoothGatt.getService(SERVICE_UUID).getCharacteristic(READ_UUID);
                writeCharacteristic = bluetoothGatt.getService(SERVICE_UUID).getCharacteristic(WRITE_UUID);
                programCharacteristic = bluetoothGatt.getService(PROGRAM_UUID).getCharacteristic(PROGRAM_CHARACTERISTICS_UUID);
                enableNotification(readCharacteristic);
                if (listener != null) {
                    listener.onConnectedDevice(UnifiedBluetoothManager.getInstance().getDevice(gatt.getDevice().getAddress()));
                }
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            BluetoothAdapter_BLE.this.rssi = rssi;
        }
    };

    private void initScanCallback() {
        if (mScanCallback == null) {
            mScanCallback = new ScanCallback() {

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    BluetoothDevice device = result.getDevice();
                    int rssi = result.getRssi();
                    onFoundDevice(device, rssi);
                }
            };
            mScanModeHelper = new ScanModeHelper();
        }
    }


    protected double calculateAccuracy(int txPower, double rssi) {
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

    public BluetoothAdapter_BLE(Activity activity) {
        this.activity = activity;
        final BluetoothManager bluetoothManager =
                (BluetoothManager) this.activity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public boolean isSupport() {
        return mBluetoothAdapter != null;
    }

    @Override
    public boolean isEnable() {
        if (isSupport()) {
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }

    @Override
    public void startDiscovery() {
        if (isEnable()) {
            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.startLeScan(mOldScanCallback);
            } else {
                initScanCallback();
//                mBluetoothAdapter.getBluetoothLeScanner().startScan(mScanCallback);
//                mBluetoothAdapter.getBluetoothLeScanner().startScan(null, new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build(), mScanCallback);
                mBluetoothAdapter.getBluetoothLeScanner().startScan(null, new ScanSettings.Builder().setScanMode(mScanModeHelper.getScanMode()).build(), mScanCallback);
                mScanModeHelper.onStartScan();
            }
            transmitSearchEvent(SearchEvent.DISCOVER_START);
        } else {
            transmitSearchEvent(SearchEvent.OPEN_BLUETOOTH);
        }

    }

    @Override
    public void stopDiscovery() {
        if (isEnable()) {
            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.stopLeScan(mOldScanCallback);
            } else {
                mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallback);
                mScanModeHelper.onStopScan();
            }
            transmitSearchEvent(SearchEvent.DISCOVER_FINISH);
        }
    }

    @Override
    public void connect(DeviceBean deviceBean) {
        if (isEnable()) {
            stopDiscovery();
            connectNewDevice(deviceBean);
//            BluetoothDevice lastDevice = null;
//            if (bluetoothGatt != null) {
//                lastDevice = bluetoothGatt.getDevice();
//            }
//            if (deviceBean.equals(lastDevice)) {
//                boolean ok = bluetoothGatt.connect();
//                if (ok) {
//                    transmitSearchEvent(SearchEvent.CONNECT_START);
//                } else {
//                    connectNewDevice(deviceBean);
//                }
//            } else {
//                connectNewDevice(deviceBean);
//            }
        }
    }

    private void connectNewDevice(DeviceBean deviceBean) {
        mConnectionState = UnifiedBluetoothManager.STATE_CONNECTING;
        bluetoothGatt = deviceBean.bluetoothDevice.connectGatt(activity, false, mGattCallback);
        transmitSearchEvent(SearchEvent.CONNECT_START);
    }

    @Override
    public void disconnect() {
        if (isEnable()) {
            if (bluetoothGatt != null) {
                if (mConnectionState == UnifiedBluetoothManager.STATE_DISCONNECTED || mConnectionState == UnifiedBluetoothManager.STATE_CONNECTING) {
                    if (listener != null) {
                        listener.onSearchEvent(SearchEvent.CONNECT_FAIL);
                    }
                }
                readCharacteristic = null;
                writeCharacteristic = null;
                programCharacteristic = null;
                bluetoothGatt.disconnect();
                bluetoothGatt.close();
            } else {
                if (listener != null) {
                    listener.onSearchEvent(SearchEvent.CONNECT_FAIL);
                }
            }
            mConnectionState = UnifiedBluetoothManager.STATE_DISCONNECTED;
        }
    }

    @Override
    public boolean isDiscovering() {
        return mBluetoothAdapter.isDiscovering();
    }

    // lyh 如果一条数据重试10次还未发送成功,将会被丢弃
    @Override
    public synchronized void write(byte[] bytes) {
        if (isEnable()) {
            int retryCount = 10;
            try {
                writeCharacteristic.setValue(bytes);
                while (!bluetoothGatt.writeCharacteristic(writeCharacteristic) || isFirstSend) {
                    if (--retryCount < 0) {
                        //丢数据了
                        Log.d("lyh", "丢数据了");
                        break;
                    }
                }
            } catch (NullPointerException e) {
                //有其他的异步操作,判空也没办法完全避免空指针
                disconnect();
            }
        }
    }

    private boolean enableNotification(BluetoothGattCharacteristic characteristic) {
        if (bluetoothGatt == null || characteristic == null)
            return false;
        if (!bluetoothGatt.setCharacteristicNotification(characteristic, true))
            return false;
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptors().get(0);
        if (clientConfig == null)
            return false;
        clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        return bluetoothGatt.writeDescriptor(clientConfig);
    }


    private void onFoundDevice(BluetoothDevice device, int rssi) {
//        if (device.getAddress().equals("D7:B8:33:50:7D:63")) {
//            Log.e("lyh", "name:" + device.getName() + ",address:" + device.getAddress() + ",rssi:" + rssi);
//        }
        if (isOurDevice(device) && listener != null) {
            float distance = -1;
            try {
                int txPower = -59;      //lyh 弃疗了,蓝牙距离这样还不够精确,就得找专业人士来指导了
                distance = (float) calculateAccuracy(txPower, rssi);
                if (distance > 20) {
                    distance = 20;
                }
            } catch (Exception e) {

            }
            listener.onFoundDevice(new DeviceBean(device, distance, rssi));
        }
//        Log.e("wbp", "onFoundDevice:" + rssi + "::" + device.getAddress());
    }

    /**
     * API 21 及以上才会进到这里
     * 这玩意放这里破坏代码结构,但是暂时没想到好的处理方式
     * 经验证,为5.x原生系统的bug,在6.0的 nexus 5 未发现这个问题
     * https://code.google.com/p/android/issues/detail?id=82463
     */
    private class ScanModeHelper {
        private boolean isChanged = false;
        private int scanMode = ScanSettings.SCAN_MODE_LOW_LATENCY;
        private Timer checkScanSettingTimer;

        public ScanModeHelper() {
            super();
            //这里可以加一些机型的判断
//            if () {
//                changeMode();
//            }
        }

        public int getScanMode() {
            return scanMode;
        }

        public void changeMode() {
            if (isChanged) {
                return;
            }
            Log.d("lyh", "changemode");
            isChanged = true;
            scanMode = ScanSettings.SCAN_MODE_LOW_POWER;
            stopDiscovery();
            startDiscovery();
        }

        public void onStartScan() {
            if (isChanged) {
                return;
            }
            //启动一个定时器检验数据
            try {
                if (checkScanSettingTimer != null) {
                    checkScanSettingTimer.cancel();
                    checkScanSettingTimer = null;
                }
            } catch (Exception e) {

            }
            checkScanSettingTimer = new Timer("checkScanSetting");
            checkScanSettingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        long currentTime = System.currentTimeMillis();
                        ArrayList<DeviceBean> list = UnifiedBluetoothManager.getInstance().getDevices();
                        for (DeviceBean deviceBean : list) {
                            //为保险(即时把不该切换的切了,也不能有没切到的情况),分两种情况
                            //第一种,可以理解为就被发现了一次,这个是测试机可以重现的, 正常搜索到的话,不可能会小于-128(见API注释), 而设备之间不能距离太远,极限距离确实可能只发现一次,暂时设置为-80
                            if (deviceBean.updateTime > 0 && currentTime - deviceBean.updateTime > 1000 && deviceBean.getRssi() > -80 && deviceBean.getStableRssi() < -128) {
                                changeMode();
                                return;
                            }
                            //第二种,处于YY中的情况,开始重复发现了几次,但是后面没有再被重复发现,等同于搜索到蓝牙几次,然后蓝牙突然断电的情况,这种情况不一定真的有,但是为保险加上
                            if (deviceBean.updateTime > 0 && currentTime - deviceBean.updateTime > 3000 && deviceBean.getRssi() > -60) {
                                changeMode();
                                return;
                            }
                        }
                    } catch (Exception e) {
                        //这里整个catch一次,有点看不懂
                        Toast.makeText(activity, "ScanModeHelper执行出错" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, 0, 300);

        }

        public void onStopScan() {
            //注销定时器
            if (checkScanSettingTimer != null) {
                try {
                    checkScanSettingTimer.cancel();
                } catch (Exception e) {

                } finally {
                    checkScanSettingTimer = null;
                }
            }
        }
    }

    @Override
    public int getRssi() {
        bluetoothGatt.readRemoteRssi();
        return rssi;
    }
}
