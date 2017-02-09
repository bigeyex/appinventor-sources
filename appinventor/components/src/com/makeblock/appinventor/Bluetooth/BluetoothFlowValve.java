package com.google.appinventor.components.runtime.Bluetooth;

import android.util.Log;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xuexin on 2016/11/29.
 */

public class BluetoothFlowValve {
    private static final int DataMaxLength = 20; //单次最多发送的字节数

    private static final int TimeGap = 10;      //蓝牙发送的时间间隔,实际测试可以到6,测试中发现5固件升级有几率蓝牙挂了(最老的芯片)

    private PriorityQueue<Command> commandQueue = new PriorityQueue<Command>(100, new ComparatorByPriority());

    private byte[] dataCache = new byte[1024];

    private int cacheCount = 0;

    private Timer sendDataTimer;

    public BluetoothFlowValve() {
        super();
        sendDataTimer = new Timer("bluetoothWriterThread", true);
        sendDataTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (cacheCount < DataMaxLength && commandQueue.size() > 0) {
                    Command nextCommand = commandQueue.poll();
                    byte[] nextData = nextCommand.bytes;
                    System.arraycopy(nextData, 0, dataCache, cacheCount, nextData.length);
                    cacheCount += nextData.length;
                }
                //队列已空或者数据够多,发送数据
                int sendCount = cacheCount > DataMaxLength ? DataMaxLength : cacheCount;
                if (sendCount == 0) {
                    return;
                } else {
                    byte[] data = new byte[sendCount];
                    System.arraycopy(dataCache, 0, data, 0, sendCount);
                    if (UnifiedBluetoothManager.getInstance().getBluetoothConnectionState() != UnifiedBluetoothManager.STATE_CONNECTED) {
                        String s = "";
                        for (int i = 0; i < data.length; ++i) {
                            s += String.format(" %02x", data[i] & 0x0ff);
                        }
                        Log.w("lyh", "未连接,丢掉数据:" + s);
                        return;
                    } else {
                        UnifiedBluetoothManager.getInstance().writeToBluetooth(data);
                    }
                    cacheCount -= sendCount;
                    //移位操作
                    if (cacheCount > 0) {
                        System.arraycopy(dataCache, sendCount, dataCache, 0, cacheCount);
                    } else {

                    }
                }
            }
        }, 0, TimeGap);
    }


    public synchronized void pushCommand(Command command) {
        //将指令添加到优先级队列
        commandQueue.add(command);
        //todo 后续改成触发式的
    }

    private static class ComparatorByPriority implements Comparator<Command> {

        @Override
        public int compare(Command commandA, Command commandB) {
            return commandB.priorityLevel - commandA.priorityLevel;
        }
    }
}
