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
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TheiaServiceActionActivity extends AppCompatActivity {
    protected String devAddr;
    protected TheiaService mTheiaService;
    protected TextView mConnectedText = null;
    protected ServiceConnection mServCon;

    protected void onConnectedTask(){}

    @Override
    public  void onCreate (Bundle b) {
        super.onCreate(b);

        mServCon = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder s)
            {
                mTheiaService = ((TheiaService.ThisBinder) s).getService();

                onConnectedTask();

                if (false &&  !mTheiaService.connectTo(devAddr))
                {
                    finish();
                }

                if (mConnectedText == null)
                {
                    return;
                }

                if (mTheiaService == null)
                {
                    mConnectedText.setText("Disconnected");
                    mConnectedText.setTextColor(Color.RED);
                }
                else if (!mTheiaService.isConnected())
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

        Intent theiaServiceIntent = new Intent(this, TheiaService.class);
        bindService(theiaServiceIntent, mServCon, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // TODO: Register receiver here

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mServCon);
        mTheiaService = null;
    }

}
