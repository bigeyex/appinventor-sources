package com.makeblock.appinventor.brandnew;


import com.makeblock.appinventor.brandnew.BleDevice;

import java.util.List;

/**
 * Created by xuexin on 2017/3/3.
 */

public interface ScanDevicesHelper {

    /**
     * This method will be always called at UI thread.
     * @param bleDeviceList List of filtered devices
     */
    void reportDevices(List<BleDevice> bleDeviceList);

    /**
     *
     * @param bleDevice
     * @return return false will ignore the device
     */
    boolean deviceFilter(BleDevice bleDevice);


    /**
     * per report time(ms)
     * @return
     */
    long getReportPeriod();

}
