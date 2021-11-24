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

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import com.nolimits.ds1library.DS1Service;

public class TheiaScanActivity extends AppCompatActivity {

    // Actions used during broadcasts to the activity
    public static final String THEIA_CONNECTED    = "com.noLimits.TheiaNexus.THEIA_DISCONNECTED";
    public static final String THEIA_DISCONNECTED =  "com.noLimits.TheiaNexus.THEIA_CONNECTED";

    private static final String TAG = "TheiaScan";
    private Handler mHandler;
    private ListView mTheiaDevList;
    private boolean mScanning;
    private BluetoothLeScanner mScanner;
    private TextView mConnectedText;
    private ProgressBar mProgress;

    private BluetoothAdapter mBluetoothAdapter;

    List<BluetoothDevice> mDeviceList;
    List<String> mDeviceNameList;
    ArrayAdapter<String> mDeviceArrayAdapter;
    List<DEVICE_TYPE> mDeviceTypeList;

    private TheiaService mTheiaService;
    private DS1Service mDS1Service;

    SharedPreferences sharedPref;

    String devAddr;

    public enum DEVICE_TYPE
    {
        THEIA_TYPE,
        DS1_TYPE,
    }

    DEVICE_TYPE selected_device;

    private ServiceConnection mDS1Con = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder s)
        {
            mDS1Service = ((DS1Service.ThisBinder) s).getService();
            mDS1Service.disconnect();


            if (mDS1Service == null)
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
            else if (!mDS1Service.isConnected() && ( (mTheiaService == null) || !mTheiaService.isConnected()))
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
            else
            {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName n)
        {
            mDS1Service = null;
        }

    };

    private ServiceConnection mServCon = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder s)
        {
            mTheiaService = ((TheiaService.ThisBinder) s).getService();

            if (mTheiaService == null)
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
            else if (!mTheiaService.isConnected() && ( (mDS1Service == null) || !mDS1Service.isConnected()))
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
            else
            {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName n)
        {
            mTheiaService = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        mHandler = new Handler();
        mTheiaDevList = (ListView)findViewById(R.id.btTheiaList);
        mConnectedText = (TextView) findViewById(R.id.textScanConnected);
        mProgress = (ProgressBar) findViewById(R.id.progressBarScan);

        BluetoothManager man = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        if (man == null) {
            Log.i(TAG, "failed to get bluetooth manager");
            return;
        }




        mBluetoothAdapter = man.getAdapter();
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(this, "Failed to get bluetooth adapter", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Grant Fine Location Access Please");
            b.setTitle("Location Access");
            b.setPositiveButton(android.R.string.ok, null);
            b.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            });
            b.show();
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Grant Fine Location Access Please");
            b.setTitle("Location Access");
            b.setPositiveButton(android.R.string.ok, null);
            b.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                }
            });
            b.show();
        }





        /*
        if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Grant Background Location Please");
            b.setTitle("Location Access");
            b.setPositiveButton(android.R.string.ok, null);
            b.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
                }
            });
            b.show();
        }

         */



        Intent theiaServiceIntent = new Intent(this, TheiaService.class);
        Intent DS1ServiceIntent = new Intent(this, DS1Service.class);

        bindService(theiaServiceIntent, mServCon, BIND_AUTO_CREATE);
        bindService(DS1ServiceIntent, mDS1Con, BIND_AUTO_CREATE);


    }


    String connectAddress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int got = resultCode;
        got++;

        /*

        // set click listener here
        mTheiaDevList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id)
            {
                mConnectedText.setText("Connecting");
                mConnectedText.setTextColor(Color.YELLOW);
                Intent intent = new Intent(TheiaScanActivity.this, TheiaActionActivity.class);
                if (mDeviceTypeList.get(pos) == DEVICE_TYPE.DS1_TYPE)
                {
                    selected_device = DEVICE_TYPE.DS1_TYPE;
                    Semaphore bondSem = new Semaphore(0, true);
                    if (mDeviceList.get(pos).getBondState() != BluetoothDevice.BOND_BONDED) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TheiaScanActivity.this);
                        builder.setMessage("Please make sure DS1 is in pairing mode")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try{
                                            mDeviceList.get(pos).createBond();
                                            connectAddress = mDeviceList.get(pos).getAddress();
                                            mDS1Service.connectTo(mDeviceList.get(pos).getAddress());
                                        }
                                        catch (Exception e)
                                        {

                                        }
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        try {
                            //bondSem.acquire();
                            //mDeviceList.get(pos).createBond();
                        }
                        catch (Exception e)
                        {

                        }
                    }
                    else {
                        connectAddress = mDeviceList.get(pos).getAddress();
                        mDS1Service.connectTo(mDeviceList.get(pos).getAddress());
                    }
                }else {
                    selected_device = DEVICE_TYPE.THEIA_TYPE;
                    connectAddress = mDeviceList.get(pos).getAddress();
                    mTheiaService.connectTo(mDeviceList.get(pos).getAddress());
                }
                intent.putExtra("DEV_ADDR", mDeviceList.get(pos).getAddress());
                stopScan();
                //startActivity(intent);
            }
        });



        IntentFilter filter = new IntentFilter();
        filter.addAction(TheiaService.THEIA_CONNECTED);
        filter.addAction(TheiaService.THEIA_DISCONNECTED);
        filter.addAction(DS1Service.DS1_CONNECTED);
        filter.addAction(DS1Service.DS1_DISCONNECTED);
        registerReceiver(mTheiaReceiver, filter);
        // here scan
        startScan();
*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mTheiaService != null) {
            mTheiaService.disconnect();
        }

        if (mDS1Service != null) {
            if (mDS1Service.isConnected()) {
                mDS1Service.disconnect();
            }
        }

        stopScan();
        mDeviceList = new ArrayList<>();
        mDeviceNameList = new ArrayList<>();
        mDeviceTypeList = new ArrayList<>();
        mDeviceArrayAdapter = new ArrayAdapter<>(this, R.layout.dev_list, R.id.dev_item, mDeviceNameList);
        mTheiaDevList.setAdapter(mDeviceArrayAdapter);

        if (mBluetoothAdapter.isEnabled() == false)
        {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
        }



        // set click listener here
        mTheiaDevList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id)
            {
                mConnectedText.setText("Connecting");
                mConnectedText.setTextColor(Color.YELLOW);
                Intent intent = new Intent(TheiaScanActivity.this, TheiaActionActivity.class);
                if (mDeviceTypeList.get(pos) == DEVICE_TYPE.DS1_TYPE)
                {
                    selected_device = DEVICE_TYPE.DS1_TYPE;
                    Semaphore bondSem = new Semaphore(0, true);
                    if (mDeviceList.get(pos).getBondState() != BluetoothDevice.BOND_BONDED) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TheiaScanActivity.this);
                        builder.setMessage("Please make sure DS1 is in pairing mode")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try{
                                            mDeviceList.get(pos).createBond();
                                            connectAddress = mDeviceList.get(pos).getAddress();
                                            mDS1Service.connectTo(mDeviceList.get(pos).getAddress());
                                        }
                                        catch (Exception e)
                                        {

                                        }
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        try {
                            //bondSem.acquire();
                            //mDeviceList.get(pos).createBond();
                        }
                        catch (Exception e)
                        {

                        }
                    }
                    else {
                        connectAddress = mDeviceList.get(pos).getAddress();
                        mDS1Service.connectTo(mDeviceList.get(pos).getAddress());
                    }
                }else {
                    selected_device = DEVICE_TYPE.THEIA_TYPE;
                    connectAddress = mDeviceList.get(pos).getAddress();
                    mTheiaService.connectTo(mDeviceList.get(pos).getAddress());
                }
                intent.putExtra("DEV_ADDR", mDeviceList.get(pos).getAddress());
                stopScan();
                //startActivity(intent);
            }
        });



        IntentFilter filter = new IntentFilter();
        filter.addAction(TheiaService.THEIA_CONNECTED);
        filter.addAction(TheiaService.THEIA_DISCONNECTED);
        filter.addAction(DS1Service.DS1_CONNECTED);
        filter.addAction(DS1Service.DS1_DISCONNECTED);
        registerReceiver(mTheiaReceiver, filter);
        // here scan
        startScan();




    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTheiaReceiver != null)
            unregisterReceiver(mTheiaReceiver);
        onStop();
    }

    private void startScan()
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                stopScan();
            }
        };

        mHandler.postDelayed( r, 20000);

        List<ScanFilter> filters = new ArrayList<>();
        mScanner = mBluetoothAdapter.getBluetoothLeScanner();
        ScanSettings scanSetting = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();

        filters.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(TheiaService.TheiaServiceUUID)).build());
        //mScanner.startScan(filters, scanSetting, mScanCB);

        mScanner.startScan(mScanCB);
        mScanning = true;

        mProgress.setVisibility(View.VISIBLE);

        invalidateOptionsMenu();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
            }
        }
    } //End of section for Android 6.0 (Marshmallow)

    private void stopScan()
    {

        mProgress.setVisibility(View.INVISIBLE);


        if (mScanning)
        {
            if (mScanner != null)
                mScanner.stopScan(mScanCB);
            mScanning = false;
        }

        invalidateOptionsMenu();
    }

    private ScanCallback mScanCB = new ScanCallback()
    {
        @Override public void onScanResult(int t, ScanResult r)
        {

            boolean deviceFound = false;

            if (mDeviceList.contains(r.getDevice()))
                return;


            if (r.getDevice().getName() == null)
                return;

            if (r.getDevice().getName().compareTo("Theia") == 0) {
                // found a theia

                deviceFound = true;
                mDeviceTypeList.add(DEVICE_TYPE.THEIA_TYPE);
            }
            else {

                //return;

                if (r.getDevice().getAddress().startsWith("E0:00:01") && r.getDevice().getName().startsWith("DS1"))
                {
                    deviceFound = true;
                    mDeviceTypeList.add(DEVICE_TYPE.DS1_TYPE);

                    // attempt to connect if auto connect enabled and same device
                    if (sharedPref.contains(getString(R.string.key_autoconnect)))
                    {
                        if (sharedPref.getBoolean(getString(R.string.key_autoconnect), false) == true)
                        {
                            // if autoconnect enabled, see if we should
                            if (sharedPref.contains(getString(R.string.key_autoconnect_address)))
                            {
                                String auto_address = sharedPref.getString(getString(R.string.key_autoconnect_address), "");
                                if(auto_address.compareTo(r.getDevice().getAddress()) == 0)
                                {
                                    selected_device = DEVICE_TYPE.DS1_TYPE;
                                    connectAddress = auto_address;
                                    mDS1Service.connectTo(auto_address);
                                }
                            }
                        }
                    }


                }


            }

            if (!deviceFound)
            {
                return;
            }

            mDeviceNameList.add(r.getDevice().getName().concat(" ".concat(r.getDevice().getAddress())));
            mDeviceList.add(r.getDevice());
            mDeviceArrayAdapter.notifyDataSetChanged();
        }
    };

    public void refreshClick(View v)
    {
        stopScan();
        mDeviceList.clear();
        mDeviceNameList.clear();
        mDeviceArrayAdapter.notifyDataSetChanged();
        startScan();
    }

    private final BroadcastReceiver mTheiaReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == DS1Service.DS1_CONNECTED)
            {
                // here start the ds1 activity
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
                Intent intentStart = new Intent(TheiaScanActivity.this, DS1ActionACtivity.class);

                // register autoreconnect
                SharedPreferences p = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = p.edit();
                editor.putBoolean(getString(R.string.key_autoconnect), true);
                editor.putString(getString(R.string.key_autoconnect_address), connectAddress);
                editor.apply();

                startActivity(intentStart);
                finish();
            }
            if (action == TheiaService.THEIA_CONNECTED)
            {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
                if (selected_device == DEVICE_TYPE.THEIA_TYPE) {
                    // register autoreconnect
                    SharedPreferences p = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.putBoolean(getString(R.string.key_autoconnect), true);
                    editor.putString(getString(R.string.key_autoconnect_address), connectAddress);
                    editor.apply();
                    Intent intentStart = new Intent(TheiaScanActivity.this, TheiaActionActivity.class);
                    startActivity(intentStart);
                }
                else
                {
                    // here start the ds1 activity
                    // register autoreconnect
                    SharedPreferences p = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.putBoolean(getString(R.string.key_autoconnect), true);
                    editor.putString(getString(R.string.key_autoconnect_address), connectAddress);
                    editor.apply();
                    Intent intentStart = new Intent(TheiaScanActivity.this, DS1ActionACtivity.class);
                    startActivity(intentStart);
                }
                finish();
            }
            else if (action == TheiaService.THEIA_DISCONNECTED)
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
        }
    };

    @Override
    public void onStop()
    {
        super.onStop();
        finish();
    }

}
