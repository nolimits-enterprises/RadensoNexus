/*
 * Copyright (c) 2021 NoLimits Enterprises
 *               brock@radenso.com
 *
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.noLimits.TheiaNexus;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;


public class TheiaService extends Service {

    public static final String TAG = "THEIA_NEXUS";
    public static final String THEIA_CONNECTED = "com.noLimits.TheiaNexus.THEIA_CONNECTED";
    public static final String THEIA_DISCONNECTED = "com.noLimits.TheiaNexus.THEIA_DISCONNECTED";
    public static final String THEIA_GOT_RESULT = "com.noLimits.TheiaNexus.THEIA_RESULT";

    public static final UUID TheiaServiceUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b0501a");
    public static final UUID TheiaSettingUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b0501e");
    public static final UUID TheiaWifiUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b05032");
    public static final UUID TheiaAlertUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b05034");
    public static final UUID TheiaGetAlertUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b0502a");
    public static final UUID TheiaAlertElementsUUID = UUID.fromString("8a7eeeb6-36e8-420e-bcbd-fb59e7b05028");

    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mAddress;

    private BluetoothGattCharacteristic mWifiChar;
    private BluetoothGattCharacteristic mAlertChar;
    private BluetoothGattCharacteristic mSettingChar;

    int alertTableSize = 0;
    int alertTableIndex = 0;

    private boolean connected = false;


    public class QEntry
    {
        BluetoothGattCharacteristic c;
        boolean isTx; // 0 if rx
    }

    private final Queue<QEntry> rxtxQ = new LinkedList<QEntry>();


    private List<AlertEntry>  alertList = new ArrayList<AlertEntry>();
    public List<AlertEntry> getAlertList()
    {
        return alertList;
    }

    private class TheiaGattCallback extends BluetoothGattCallback
    {
         @Override
         public void onConnectionStateChange(BluetoothGatt gatt, int status, int state)
         {
             if (BluetoothProfile.STATE_CONNECTED == state)
             {
                 Log.i(TAG, "THEIA_CONNECTED");
                 sendBroadcast(new Intent(THEIA_CONNECTED));
                 mBluetoothGatt.discoverServices();
                 connected = true;

                 sendBroadcast(new Intent(THEIA_CONNECTED));
             }
             else if (BluetoothProfile.STATE_DISCONNECTED == state)
             {
                 Log.i(TAG, "THEIA_DISCONNECTED");
                 sendBroadcast(new Intent(THEIA_DISCONNECTED));
                 connected = false;
                 sendBroadcast(new Intent(THEIA_DISCONNECTED));
                 rxtxQ.clear();
             }
         }

         @Override
        public void onServicesDiscovered(BluetoothGatt g, int s)
         {
             if (BluetoothGatt.GATT_SUCCESS == s)
             {
                 BluetoothGattService service = mBluetoothGatt.getService(TheiaServiceUUID);
                 if (service == null)
                     return;

                 mWifiChar = service.getCharacteristic(TheiaWifiUUID);
                 mAlertChar = service.getCharacteristic(TheiaAlertUUID);
                 mSettingChar = service.getCharacteristic(TheiaSettingUUID);

                 Log.i(TAG, "Got wifi characteristic");
             }
         }



         private void processRxTxQueue()
         {

             if (rxtxQ.size() > 0)
             {
                 QEntry entry = rxtxQ.element();
                 if (entry.isTx)
                     mBluetoothGatt.writeCharacteristic(entry.c);
                 else
                     mBluetoothGatt.readCharacteristic(entry.c);
             }
         }


         @Override
         public void onCharacteristicWrite(BluetoothGatt g, BluetoothGattCharacteristic c, int s)
         {
             if (0 == c.getUuid().compareTo(TheiaGetAlertUUID))
             {
                 BluetoothGattCharacteristic characteristic = g.getService(TheiaServiceUUID).getCharacteristic(TheiaGetAlertUUID);
                 readChar(characteristic);
             }

             rxtxQ.remove();
             processRxTxQueue();
         }

         @Override
         public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
         {
             if (0 == characteristic.getUuid().compareTo(TheiaSettingUUID))
             {
                 Intent gotResultIntent = new Intent(THEIA_GOT_RESULT);

                 gotResultIntent.putExtra("RESULT", Integer.valueOf(characteristic.getStringValue(0)));
                 sendBroadcast(gotResultIntent);

             }

             if (0 == characteristic.getUuid().compareTo(TheiaGetAlertUUID)) {
                 try
                 {
                     String res = characteristic.getStringValue(0);

                     JSONObject jo = new JSONObject(res);


                     AlertEntry e = new AlertEntry();

                     e.alert_class = jo.getInt("alert_class");
                     e.dir = jo.getInt("dir");
                     e.intensity = (float)(jo.getDouble("intensity"));
                     e.band = jo.getInt("band");
                     e.frequency = (float)(jo.getDouble("frequency"));

                     alertList.add(e);

                     // if the index is less than size, get again
                     alertTableIndex++;

                     if (alertTableIndex < alertTableSize) {
                         BluetoothGattCharacteristic c = gatt.getService(TheiaServiceUUID).getCharacteristic(TheiaGetAlertUUID);
                         c.setValue(String.valueOf(alertTableIndex));
                         writeChar(c);
                     }
                     else
                     {
                         // TODO: notify that the alert table was updated

                     }

                 }
                 catch (Exception e)
                 {
                     // did not succesfully retrieve this element
                 }
             }
             else if (0 == characteristic.getUuid().compareTo(TheiaAlertElementsUUID)) {

                 try {
                     String res = characteristic.getStringValue(0);
                     // convert to int and store how many elements we have

                     int n = Integer.parseInt(res);
                     alertTableSize = n;
                     alertTableIndex = 0;

                     // now we want to read the entire table
                     BluetoothGattCharacteristic c = gatt.getService(TheiaServiceUUID).getCharacteristic(TheiaGetAlertUUID);
                     c.setValue("0");
                     if (alertTableSize > 0)
                     {
                         alertList.clear();
                         // start getting alert table if it is non-zero in size
                         //Thread.sleep(50);
                         writeChar(c);
                     }
                     else
                     {
                         // we have the alert table, and it is empty
                         // TODO: notify update alert table
                         alertList.clear();
                     }


                 } catch (Exception e)
                 {
                     alertTableSize = 0;
                     alertTableIndex = 0;
                 }

             }

             rxtxQ.remove();
             processRxTxQueue();
         }
    }

    private TheiaGattCallback mGattCB = new TheiaGattCallback();

    public class ThisBinder extends Binder
    {
        TheiaService getService()
        {
            return TheiaService.this;
        }
    }

    private IBinder mBinder = new ThisBinder();

     public IBinder onBind(Intent intent)
     {
          return mBinder;
     }

     public boolean getAdapter()
     {

          if (null == mBluetoothManager)
          {
              mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
          }

          if (null == mBluetoothManager)
          {
              Log.i(TAG,"Failed to get bluetooth manager");
              return false;
          }

          mBluetoothAdapter = mBluetoothManager.getAdapter();

          if (null == mBluetoothAdapter)
          {
              Log.i(TAG, "Failed to get the bluetooth adapter");
              return false;
          }

          return true;
     }

     public boolean connectTo(String address)
     {
         getAdapter();

         if (address == null)
         {
             Log.i(TAG, "null address");
             return false;
         }

         if (mBluetoothAdapter == null)
         {
             Log.i(TAG, "no adapter");
             return false;
         }

         BluetoothDevice d = mBluetoothAdapter.getRemoteDevice(address);
         if (null == d)
         {
             Log.i(TAG, "unable to connect");
             return false;
         }

         mAddress = address;
         mBluetoothGatt = d.connectGatt(this, false, mGattCB);

         connected = true;

         return true;
     }

     public void disconnect()
     {
         if (mBluetoothGatt == null)
             return;

         Log.d("THEIA_service","disconnected");

         rxtxQ.clear();

         mBluetoothGatt.disconnect();
         connected = false;
     }

     public void close()
     {
         if (mBluetoothGatt == null)
             return;

         mBluetoothGatt.close();
         mBluetoothGatt = null;
     }

     private void writeChar(BluetoothGattCharacteristic characteristic)
     {
         if (mBluetoothGatt == null)
             return;

         QEntry e = new QEntry();
         e.c = characteristic;
         e.isTx = true;
         rxtxQ.add(e);

         if (1 == rxtxQ.size())
             mBluetoothGatt.writeCharacteristic(characteristic);
     }

     private void readChar(BluetoothGattCharacteristic characteristic)
     {
         if (mBluetoothGatt == null)
             return;

         QEntry e = new QEntry();
         e.c = characteristic;
         e.isTx = false;
         rxtxQ.add(e);

         if (1 == rxtxQ.size())
         {
             mBluetoothGatt.readCharacteristic(characteristic);
         }
     }

     public void writeWifi(String ssid, String pw)
     {
         if (mWifiChar == null)
             return;

         mWifiChar.setValue(ssid.concat(":").concat(pw));
         writeChar(mWifiChar);
     }

     public void writeColor(int i)
     {
         if (mSettingChar == null)
             return;

         mSettingChar.setValue("display/color:".concat(String.valueOf(i)));
         writeChar(mSettingChar);
     }

     public void writeGeneric(String name, int value)
     {
         if (mSettingChar == null)
             return;

         name = name.concat(":");
         mSettingChar.setValue(name.concat(String.valueOf(value)));
         writeChar(mSettingChar);
     }

     public void writeAlert(int type, int per)
     {
         if (mAlertChar != null)
         {
             mAlertChar.setValue(String.valueOf(type).concat(":").concat(String.valueOf(per)));
             writeChar(mAlertChar);
         }
     }

     public void readValue(String s)
     {
         if (mSettingChar != null)
         {
             mSettingChar.setValue(s);
             writeChar(mSettingChar);
             readChar(mSettingChar);
         }
     }

     public void checkAlerts()
     {

         rxtxQ.clear();

         if (mBluetoothGatt == null)
             return;

         BluetoothGattService s = mBluetoothGatt.getService(TheiaServiceUUID);
         if (s == null)
             return;

         BluetoothGattCharacteristic c = s.getCharacteristic(TheiaAlertElementsUUID);
         if (c != null)
             readChar(c);
     }

     public boolean isConnected()
     {
         return connected;

         /*
         if (mBluetoothManager == null)
             return false;
         if (mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT) == null)
             return false;
         if (mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT).size() > 0)
             return true;
         return false;
          */
     }

     protected void onStop()
     {
         // disconnect here
         disconnect();
     }

}
