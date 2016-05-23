// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.BluetoothReflection;

import com.makeblock.appinventor.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;


/**
 *
 * A new list picker activity handling bluetooth discovery
 * I sincerely hope I can just extend the default ListPicker activity,
 * but they delcared listView and closeAnim as private, so there's no choice but copy-paste
 *
 * @author bigeyex@gmail.com (Wang Yu)
 *
 * Modified from ListPickerActivity class:
 *
 * ListPickerActivity class - Brings up a list of items specified in an intent
 * and returns the selected item as the result.
 *
 * @author sharon@google.com (Sharon Perl)
 * @author M. Hossein Amerkashi (kkashi01@gmail.com)
 */
public class MakeblockBluetoothPickerActivity extends Activity implements AdapterView.OnItemClickListener {

  private String closeAnim = "";
  private ListView listView;

  // Listview Adapter
  BluetoothDevicesListAdapter adapter;
  private ArrayList<String> deviceAddresses = new ArrayList<String>();

  static int itemColor;
  static int backgroundColor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LinearLayout viewLayout = new LinearLayout(this);
    viewLayout.setOrientation(LinearLayout.VERTICAL);

    Intent myIntent = getIntent();
 
    listView = new ListView(this);
    listView.setOnItemClickListener(this);
    listView.setScrollingCacheEnabled(false);

    itemColor = ListPicker.DEFAULT_ITEM_TEXT_COLOR;
    backgroundColor = ListPicker.DEFAULT_ITEM_BACKGROUND_COLOR;

    viewLayout.setBackgroundColor(backgroundColor);

    // Adding items to listview
    adapter = new BluetoothDevicesListAdapter(this, deviceAddresses);
    listView.setAdapter(adapter);

    viewLayout.addView(listView);

    this.setContentView(viewLayout);
    viewLayout.requestLayout();

    //hide the keyboard
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    // start bluetooth discovery
    adapter.startDiscovery();
  }

  @Override
  protected void onDestroy() {
    adapter.stopDiscovery();
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    String selected = (String) parent.getAdapter().getItem(position);
    Intent resultIntent = new Intent();
    resultIntent.putExtra(ListPicker.LIST_ACTIVITY_RESULT_NAME, selected);
    resultIntent.putExtra(ListPicker.LIST_ACTIVITY_RESULT_INDEX, position + 1);
    closeAnim = selected;
    setResult(RESULT_OK, resultIntent);
    finish();
    AnimationUtil.ApplyCloseScreenAnimation(this, closeAnim);
  }

  // Capture the hardware back button to make sure the screen animation
  // still applies. (In API level 5, we can override onBackPressed instead)
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      boolean handled = super.onKeyDown(keyCode, event);
      AnimationUtil.ApplyCloseScreenAnimation(this, closeAnim);
      return handled;
    }
    return super.onKeyDown(keyCode, event);
  }



  private static class BluetoothDevicesListAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private final String TAG = "MakeblockBluetooth";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ArrayList<String> mDataSource = null;
    private static int discoveryTimes = 0;


    public BluetoothDevicesListAdapter(final Context context, ArrayList<String> dataSource) {
      super(context, android.R.layout.activity_list_item, dataSource);
      mDataSource = dataSource;
      mContext = context;
    }

    public void logMessage(String message){
      Toast.makeText(mContext, message,
              Toast.LENGTH_SHORT).show();
    }


    public void stopDiscovery() {
      if (BluetoothAdapter.getDefaultAdapter() == null) {
        return;
      }
      BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
    }

    public void startDiscovery() {
      Log.e(TAG, "******startDiscovery******");
      if (BluetoothAdapter.getDefaultAdapter() == null) {
        Log.e(TAG, "此设备不支持蓝牙 return");
        logMessage("This device does not support Bluetooth");
        return;
      }

      if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
        // TODO: issue bluetooth is not opened event
        return;
      }

      while (!BluetoothAdapter.getDefaultAdapter().startDiscovery()) {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        if (discoveryTimes > 3) {
          Log.e(TAG, "startDiscovery failed 3次，发送msg:BLUETOOTH_STATE_DISCOVERY_FAILED");
          logMessage("Fail to discover bluetooth devices");
          // TODO: issue discovery failed event
          discoveryTimes = 0;
          return;
        }
        discoveryTimes++;
        Log.e(TAG, "startDiscovery failed");
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      Log.e(TAG, "startDiscovery succeed");
      discoveryTimes = 0;

      // register listener to receive bluetooth event
      BluetoothBroadcastReceiver bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
      IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
      mContext.registerReceiver(bluetoothBroadcastReceiver, intentFilter);
    }

    @Override
    public long getItemId(final int position) {
      return getItem(position).hashCode();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      TextView tv = (TextView) convertView;
      if (tv == null) {
        tv = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
      }
      tv.setText(getItem(position));
      tv.setTextColor(itemColor);
      return tv;
    }

    private class BluetoothBroadcastReceiver extends BroadcastReceiver {
      public void onReceive(Context context, Intent intent) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        //API Level>=18，在系统软件上才支持BLE
        // TODO if acceptable
        if(!MBotBluetoothClient.isBluetoothDeviceAcceptable(device)){
          return;
        }

        //根据mac地址去重复
        String deviceAddress = BluetoothReflection.getBluetoothDeviceAddress(device);

        for (String address : mDataSource) {
          if (address.equals(deviceAddress)) {
            Log.e(TAG, "device is already on the List中，return");
            Log.e(TAG, "*************************************************************");
            return;
          }
        }
        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
          Log.e(TAG, "added paired bluetooth:" + device.getName() + " " + device.getAddress());
        } else {
          Log.e(TAG, "added unpaired bluetooth:" + device.getName() + " " + device.getAddress());
        }

        mDataSource.add(deviceAddress);
        notifyDataSetChanged();
      }
    } // class BluetoothBroadcastReceiver



  }   // class BluetoothDeviceListAdapter


}
