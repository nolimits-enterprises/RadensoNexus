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

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getSupportActionBar().hide();

        Handler mHandler = new Handler();

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

        BluetoothManager man = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if (man == null) {
            finish();
            return;
        }


        BluetoothAdapter bluetoothAdapter;
        bluetoothAdapter = man.getAdapter();
        if (bluetoothAdapter.isEnabled() == false)
        {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
        }

        mHandler.postDelayed( r, 3000);
    }


}
