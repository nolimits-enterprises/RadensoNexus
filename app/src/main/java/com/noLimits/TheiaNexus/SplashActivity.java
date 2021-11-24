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
 *
 */

package com.noLimits.TheiaNexus;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




public class SplashActivity  extends AppCompatActivity {

    private final int LOC_CODE = 1;
    private final int SCAN_CODE = 2;
    private final int ENABLE_CODE = 3;

    Handler mHandler = new Handler();

    protected void checkLocationPermission()
    {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Grant Fine Location Access Please");
            b.setTitle("Location Access");
            b.setPositiveButton(android.R.string.ok, null);
            b.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOC_CODE);
                }
            });
            b.show();
        }
        else
        {
            checkScanPermission();
        }
    }

    protected void checkScanPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Grant Fine Location Access Please");
                b.setTitle("Location Access");
                b.setPositiveButton(android.R.string.ok, null);
                b.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, SCAN_CODE);
                    }
                });
                b.show();
            } else {
                checkBTEnable();
            }
        }
        else
        {
            checkBTEnable();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ENABLE_CODE)
        {
            if (resultCode == RESULT_OK)
                startSplashTimer();
            else {
                // TODO: say shit whack
                finish();
            }
        }
    }

    private void startSplashTimer()
    {

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                Intent intentStart = new Intent(SplashActivity.this, TheiaScanActivity.class);
                startActivity(intentStart);
                finish();
            }
        };

        mHandler.postDelayed( r, 3000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults)
    {

        if (requestCode == LOC_CODE)
        {
            if (grantResults.length > 0)
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkScanPermission();
                }
                else
                {
                    // TODO: Say permission not granted, and fail
                    finish();
                    return;
                }
            }
            else
            {
                finish();
            }
        }
        else if (requestCode == SCAN_CODE)
        {
            if (grantResults.length > 0)
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkBTEnable();
                }
                else
                {
                    // TODO: Say permission not granted, and fail
                    finish();
                    return;
                }
            }
            else
            {
                finish();
                return;
            }
        }
    }

    protected void checkBTEnable()
    {
        BluetoothManager man = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if (man == null) {
            finish();
            return;
        }


        BluetoothAdapter bluetoothAdapter;
        bluetoothAdapter = man.getAdapter();
        if (bluetoothAdapter.isEnabled() == false)
        {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 3);
        }
        else
        {
            startSplashTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getSupportActionBar().hide();

        checkLocationPermission();


    }


}
