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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nolimits.ds1library.DS1Service;

import java.util.Timer;
import java.util.TimerTask;

public class DS1UpdateTest extends DS1ServiceActionACtivity {

    TextView currentVersion;
    TextView updateVersion;
    TextView updateProgressText;
    TextView updateTimeRemaining;
    Button updateButton;
    ProgressBar updateProgress;
    CheckBox recoverCheck;

    boolean latestPosted = false;
    boolean versionRequested = false;

    boolean lastupdateEnable = false;

    Timer updateTimer;

    boolean lastChecked = false;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("System Update");

        setContentView(R.layout.activity_ds1update);
        currentVersion = (TextView)findViewById(R.id.textCurrentVersion);
        updateVersion  = (TextView)findViewById(R.id.textNewestVersion);
        updateProgressText = (TextView)findViewById(R.id.txtUpdateProgress);

        updateButton = (Button)findViewById(R.id.btnUpdate);
        updateProgress = (ProgressBar)findViewById(R.id.progressUpdate);
        recoverCheck = (CheckBox)findViewById(R.id.checkRecover);
        updateTimeRemaining = (TextView)findViewById(R.id.txtTimeRemaining);

        recoverCheck.setChecked(false);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        //recoverCheck.setChecked(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        recoverCheck.setChecked(false);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Warning: Update could take significantly longer.  Unless you are sure, we recommend you do this only if advised by Radenso support.  Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener);


        recoverCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton b, boolean s)
            {
                if (recoverCheck.isChecked()) {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }

    public void onUpdatePress(View v)
    {
        mDS1Service.runUpdate(recoverCheck.isChecked());
    }
    public void onRecoverPress(View v)
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //mDS1Service.requestSettings();

        updateTimeRemaining.setText("");

        versionRequested = false;

        updateTimer = new Timer();
        TimerTask t = new TimerTask() {
            public void run() {
                if (mDS1Service != null)
                {
                    if (mDS1Service.mVersion != null)
                    {
                        if (mDS1Service.mVersion.ui != null)
                        {
                            DS1UpdateTest.this.runOnUiThread(new Runnable() {

                                public void run() {
                                    currentVersion.setText("Current version  : " + mDS1Service.mVersion.ui + " [ gps_db : " + mDS1Service.mVersion.gps_db + " ]");
                                    DS1Service.updateMergeFile mf = mDS1Service.getUpdateMergeFile();
                                    if (0 != mf.getVersion().compareTo(mDS1Service.mVersion.ui))
                                    {
                                        // same version
                                        currentVersion.setTextColor(Color.RED);
                                    }
                                    else
                                    {
                                        currentVersion.setTextColor(Color.GREEN);
                                    }
                                }
                            });
                        }
                    }
                    else if (!versionRequested)
                    {
                        versionRequested = true;
                        mDS1Service.requestVersions();
                    }

                    DS1Service.updaterThread ut = mDS1Service.getUpdateThread();
                    if (ut != null)
                    {
                        DS1UpdateTest.this.runOnUiThread(new Runnable() {

                            public void run() {
                                if (!(mDS1Service.updating) && lastupdateEnable)
                                {
                                    // we finished an update, recheck version
                                    versionRequested = false;
                                }
                                if (mDS1Service.updating)
                                {
                                    int time = mDS1Service.ut.getTimeRemaining();
                                    int min = time / 60;
                                    int s = time % 60;
                                    updateTimeRemaining.setText(String.valueOf(min) + "m " + String.valueOf(s) + "s estimated remaining");
                                }
                                else
                                {
                                    updateTimeRemaining.setText("");
                                }
                                lastupdateEnable = mDS1Service.updating;
                                updateButton.setEnabled(!mDS1Service.updating);
                                updateProgress.setProgress(ut.getProgressPercent());
                                updateProgressText.setText(ut.getProgressText());
                            }
                        });
                    }

                    DS1Service.updateMergeFile mf = mDS1Service.getUpdateMergeFile();
                    if ((!latestPosted) &&  (mf != null)) {
                        DS1UpdateTest.this.runOnUiThread(new Runnable(){

                            public void run() {
                                latestPosted = true;
                                String gps_db_ver = String.valueOf(mf.gps_db.version).substring(0,4) + "." + String.valueOf(mf.gps_db.version).substring(4,6) + "." + String.valueOf(mf.gps_db.version).substring(6);
                                updateVersion.setText("Update available: " + mf.getVersion() + " [ gps_db : " +  gps_db_ver + " ]");


                            }
                        });
                    }


                }
            }
        };

        updateTimer.schedule(t, 0, 50);

    }

    @Override
    public void onPause()
    {
        super.onPause();

        // cancel update if possible

        if (mDS1Service != null)
        {
            if (mDS1Service.ut != null)
            {
                if (mDS1Service.updating)
                    mDS1Service.ut.cancelUpdate();
            }
        }

        updateTimer.cancel();

    }



}