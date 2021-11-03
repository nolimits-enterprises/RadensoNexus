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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nolimits.ds1library.DS1Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class DS1StatusAlerts extends DS1ServiceActionACtivity {

    private AlertsAdapter mAdapter;
    private RecyclerView mRecycler;
    private Timer mTimer;

    public void refreshClick(View v)
    {

        //mAdapter.setAlertList(mTheiaService.getAlertList());
        mAdapter.notifyDataSetChanged();
        //mTheiaService.checkAlerts();
    }

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Alerts Table");

        setContentView(R.layout.activity_alerts);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerAlerts);
        mAdapter = new AlertsAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    void onGotResult()
    {

        // process alerts
        if(mDS1Service != null)
        {
            Vector<DS1Service.RD_Alert> alerts = mDS1Service.getmAlerts();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (alerts != null) {
                        mAdapter.setAlertList(alerts);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

}
