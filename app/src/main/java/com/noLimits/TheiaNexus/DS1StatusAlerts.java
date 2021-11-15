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

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nolimits.ds1library.DS1Service;

import java.io.FileDescriptor;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class DS1StatusAlerts extends DS1ServiceActionACtivity {

    private AlertsAdapter mAdapter;
    private RecyclerView mRecycler;
    private Timer mTimer;

    private Timer timer;

    TimerTask clearTask;

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
    protected void onGotService()
    {
        if (mDS1Service != null)
            mDS1Service.enableAlertNotifications();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (mDS1Service != null)
            mDS1Service.enableAlertNotifications();

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mDS1Service != null) {
            mDS1Service.disableAlertNotifications();
            mDS1Service.clearQueue();
        }
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

                        if ((alerts.size() == 0) || (alerts.get(0).detected == false))
                        {
                            if (mTimer != null) {
                                try {
                                    mTimer.cancel();
                                    clearTask.cancel();
                                }
                                catch (Exception e){}
                            }
                            mTimer = new Timer();
                            clearTask = new TimerTask() {

                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Vector<DS1Service.RD_Alert> alerts = mDS1Service.getmAlerts();
                                            alerts.clear();
                                            if (alerts != null) {
                                                mAdapter.setAlertList(alerts);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                };
                            };
                            mTimer.schedule(clearTask, 6000);
                            return;
                        }
                        else
                        {
                            try {
                                if (mTimer != null) {
                                    mTimer.cancel();
                                    clearTask.cancel();
                                }
                            }
                            catch (Exception e) {};
                        }

                        mAdapter.setAlertList(alerts);
                        mAdapter.notifyDataSetChanged();



                        if (alerts.size() > 0)
                        {
                            AssetFileDescriptor afd;

                            int t = 1;

                            if(alerts.get(0).detected)
                            {
                                switch (t) {
                                    default:

                                        afd = getResources().openRawResourceFd(R.raw.s1);
                                        break;
                                    case 2:
                                        afd = getResources().openRawResourceFd(R.raw.s2);
                                        break;
                                    case 3:
                                        afd = getResources().openRawResourceFd(R.raw.s3);
                                        break;
                                    case 4:
                                        afd = getResources().openRawResourceFd(R.raw.s4);
                                        break;
                                    case 5:
                                        afd = getResources().openRawResourceFd(R.raw.s5);
                                        break;
                                    case 6:
                                        afd = getResources().openRawResourceFd(R.raw.s6);
                                        break;
                                    case 7:
                                        afd = getResources().openRawResourceFd(R.raw.s7);
                                        break;
                                    case 8:
                                        afd = getResources().openRawResourceFd(R.raw.s8);
                                        break;
                                    case 9:
                                        afd = getResources().openRawResourceFd(R.raw.s9);
                                        break;
                                    case 10:
                                        afd = getResources().openRawResourceFd(R.raw.s10);
                                        break;

                                }

                                FileDescriptor fd = afd.getFileDescriptor();
                                MediaPlayer player = new MediaPlayer();
                                try {
                                    player.setDataSource(fd, afd.getStartOffset(),
                                            afd.getLength());
                                    player.setLooping(false);
                                    player.prepare();
                                    player.start();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            });
        }
    }

}


