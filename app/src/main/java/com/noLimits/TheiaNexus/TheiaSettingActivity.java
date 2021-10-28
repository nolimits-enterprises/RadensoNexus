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

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TheiaSettingActivity extends TheiaServiceActionActivity
{
    private EditText wifiName;
    private EditText wifiPW;



    public void onWifiClick(View v)
    {
        wifiName = (EditText) findViewById(R.id.ssidText);
        wifiPW = (EditText) findViewById(R.id.pwText);
        String ssid = wifiName.getText().toString();
        String pw   = wifiPW.getText().toString();
        mTheiaService.writeWifi(ssid, pw);
    }

    @Override
    public void onCreate (Bundle b)
    {
        super.onCreate(b);

        setTitle("Wifi Settings");

        setContentView(R.layout.activity_theia_setting);
        wifiName = (EditText) findViewById(R.id.ssidText);
        wifiPW = (EditText) findViewById(R.id.pwText);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}