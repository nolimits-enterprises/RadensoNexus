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

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RadioButton;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

import androidx.appcompat.app.AppCompatActivity;

public class DS1ServiceActionACtivity extends AppCompatActivity{


    protected String devAddr;
    //protected DS1Service mDS1Service;
    protected com.nolimits.ds1library.DS1Service mDS1Service;
    protected TextView mConnectedText = null;
    protected ServiceConnection mServCon;
    boolean running = false;

    protected void onConnectedTask(){}

    protected void onGotService()
    {

    }

    @Override
    public  void onCreate (Bundle b) {
        super.onCreate(b);

        mServCon = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder s)
            {
                mDS1Service = ((DS1Service.ThisBinder) s).getService();

                onConnectedTask();
                onGotService();

                if (false &&  !mDS1Service.connectTo(devAddr))
                {
                    finish();
                }

                if (mConnectedText == null)
                {
                    if (mDS1Service.isConnected())
                    {
                        mDS1Service.requestSettings();
                    }
                    return;
                }

                if (mDS1Service == null)
                {
                    mConnectedText.setText("Disconnected");
                    mConnectedText.setTextColor(Color.RED);
                }
                else if (!mDS1Service.isConnected())
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

        Intent theiaServiceIntent = new Intent(this, DS1Service.class);
        bindService(theiaServiceIntent, mServCon, BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DS1Service.DS1_GOT_RESULT);
        filter.addAction(DS1Service.DS1_DISCONNECTED);
        registerReceiver(mDS1Receiver, filter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (mDS1Service != null)
        {
            mDS1Service.enableSettingNotifications();
        }

        running = true;

        // TODO: Register receiver here

    }

    @Override
    protected void onPause()
    {
        running = false;
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mServCon);
        mDS1Service = null;
    }

    void onGotResult()
    {

        int test = 1;

    }

    private final BroadcastReceiver mDS1Receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == DS1Service.DS1_GOT_RESULT)
            {
                onGotResult();
            }
            else if (action == DS1Service.DS1_DISCONNECTED)
            {
                if (running) {
                    Intent i = new Intent(DS1ServiceActionACtivity.this, TheiaScanActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }
    };

}


