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

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

public class DS1GPS extends DS1ServiceActionACtivity {
    Switch sw_gps;
    Switch sw_sig;
    Switch sw_auto;
    Switch sw_rlc;
    Switch sw_speed;
    Switch sw_rlc_speed;

    RadioButton rb_auto_nl;
    RadioButton rb_auto_50;
    RadioButton rb_auto_100;
    RadioButton rb_auto_150;
    RadioButton rb_auto_200;

    RadioButton rb_alert_auto;
    RadioButton rb_alert_short;
    RadioButton rb_alert_medium;
    RadioButton rb_alert_long;

    SeekBar sb_rlc;

    TextView txt_rlc;

    boolean metric = false;

    public void onSwitchClicked(View view)
    {
        boolean checked = ((Switch) view).isChecked();

        switch(view.getId())
        {
            case R.id.sw_GPS:
                mDS1Service.setGPSEnable(checked);
                break;
            case R.id.swi_gps_sig:
                mDS1Service.setGPSSigAnnounceEnable(checked);
                break;
            case R.id.sw_gps_lockout:
                mDS1Service.setGPSAutoLockoutEnable(checked);
                break;
            case R.id.sw_gps_rlc:
                mDS1Service.setGPSRLCEnable(checked);
                break;
            case R.id.sw_gps_speed:
                mDS1Service.setGPSSpeedEnable(checked);
                break;
            case R.id.sw_gps_rlc_speed:
                mDS1Service.setGPSRLCSpeedEnable(checked);
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_gps_auto_nl:
                mDS1Service.setGPSAutoLockout(0);
                break;
            case R.id.radio_gps_auto_50:
                mDS1Service.setGPSAutoLockout(50);
                break;
            case R.id.radio_gps_auto_100:
                mDS1Service.setGPSAutoLockout(100);
                break;
            case R.id.radio_gps_auto_150:
                mDS1Service.setGPSAutoLockout(150);
                break;
            case R.id.radio_gps_auto_200:
                mDS1Service.setGPSAutoLockout(200);
                break;

            case R.id.radio_gps_dist_auto:
                mDS1Service.setGPSAlertDist(DS1Service.Alert_Distance.ALERT_DISTANCE_AUTO);
                break;
            case R.id.radio_gps_dist_short:
                mDS1Service.setGPSAlertDist(DS1Service.Alert_Distance.ALERT_DISTANCE_SHORT);
                break;
            case R.id.radio_gps_dist_med:
                mDS1Service.setGPSAlertDist(DS1Service.Alert_Distance.ALERT_DISTANCE_MEDIUM);
                break;
            case R.id.radio_gps_dist_long:
                mDS1Service.setGPSAlertDist(DS1Service.Alert_Distance.ALERT_DISTANCE_LONG);
                break;

        }
    }

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("GPS Configuration");

        setContentView(R.layout.activity_gps);
        sw_gps = (Switch) findViewById(R.id.sw_GPS);
        sw_sig = (Switch) findViewById(R.id.swi_gps_sig);
        sw_auto = (Switch) findViewById(R.id.sw_gps_lockout);
        sw_rlc = (Switch) findViewById(R.id.sw_gps_rlc);
        sw_speed = (Switch) findViewById(R.id.sw_gps_speed);
        sw_rlc_speed = (Switch) findViewById(R.id.sw_gps_rlc_speed);

        rb_auto_nl = (RadioButton) findViewById(R.id.radio_gps_auto_nl);
        rb_auto_50 = (RadioButton) findViewById(R.id.radio_gps_auto_50);
        rb_auto_100 = (RadioButton) findViewById(R.id.radio_gps_auto_100);
        rb_auto_150 = (RadioButton) findViewById(R.id.radio_gps_auto_150);
        rb_auto_200 = (RadioButton) findViewById(R.id.radio_gps_auto_200);

        rb_alert_auto = (RadioButton) findViewById(R.id.radio_gps_dist_auto);
        rb_alert_short = (RadioButton) findViewById(R.id.radio_gps_dist_short);
        rb_alert_medium = (RadioButton) findViewById(R.id.radio_gps_dist_med);
        rb_alert_long = (RadioButton) findViewById(R.id.radio_gps_dist_long);

        sb_rlc = (SeekBar) findViewById(R.id.seek_gps_rlc);
        txt_rlc = (TextView) findViewById(R.id.txt_gps_rlc_speed);

        sb_rlc.setMax(4);

        sb_rlc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing for now
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing for now
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser)
                {
                    if (mDS1Service != null)
                    {
                        if (progress == 0)
                        {
                            mDS1Service.setGPSRLCAutoMute(progress);
                        }
                        else
                        {
                            if (metric)
                            {
                                mDS1Service.setGPSRLCAutoMute(progress * 10 + 80);
                            }
                            else {
                                mDS1Service.setGPSRLCAutoMute(progress * 5 + 50);
                            }
                        }

                        update_mute_text();

                    }
                }
            }
        });

    }

    void update_mute_text()
    {
        if (sb_rlc.getProgress() == 0)
           txt_rlc.setText("Off");
        else {
            if (metric)
            {
                txt_rlc.setText(String.valueOf(sb_rlc.getProgress() * 10 + 80) + " mph");
            }
            else {
                txt_rlc.setText(String.valueOf(sb_rlc.getProgress() * 5 + 50) + " mph");
            }
        }
    }

    @Override
    void onGotResult()
    {
        DS1GPS.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;

                sw_gps.setChecked(mDS1Service.getmSetting().gps);
                sw_sig.setChecked(mDS1Service.getmSetting().gps_announce);
                sw_auto.setChecked(mDS1Service.getmSetting().auto_lockout);
                sw_rlc.setChecked(mDS1Service.getmSetting().red_light_cam);
                sw_speed.setChecked(mDS1Service.getmSetting().speed_cam);
                sw_rlc_speed.setChecked(mDS1Service.getmSetting().red_light_and_speed_cam);

                DS1Service.Alert_Distance dist = mDS1Service.getmSetting().alert_distance;
                switch(dist)
                {
                    case ALERT_DISTANCE_AUTO:
                        rb_alert_auto.setChecked(true);
                        break;
                    case ALERT_DISTANCE_LONG:
                        rb_alert_long.setChecked(true);
                        break;
                    case ALERT_DISTANCE_MEDIUM:
                        rb_alert_medium.setChecked(true);
                        break;
                    case ALERT_DISTANCE_SHORT:
                        rb_alert_short.setChecked(true);
                        break;
                }

                int lockout_limit = mDS1Service.getmSetting().lockout_limit;
                switch(lockout_limit)
                {
                    case 0:
                        rb_auto_nl.setChecked(true);
                        break;
                    case 50:
                        rb_auto_50.setChecked(true);
                        break;
                    case 100:
                        rb_auto_100.setChecked(true);
                        break;
                    case 150:
                        rb_auto_150.setChecked(true);
                        break;
                    case 200:
                        rb_auto_200.setChecked(true);
                        break;
                }

                int auto_mute = mDS1Service.getmSetting().rlc_auto_mute;
                metric = (mDS1Service.getmSetting().unit_standard);

                if(metric)
                {
                    sb_rlc.setMax(3);
                }
                else
                {
                    sb_rlc.setMax(4);
                }

                if (auto_mute == 0) {
                    sb_rlc.setProgress(auto_mute);
                }
                else
                {
                    if (metric)
                    {
                        sb_rlc.setProgress((auto_mute - 80)/10);
                    }
                    else {
                        sb_rlc.setProgress((auto_mute - 50) / 5);
                    }
                }
                update_mute_text();
            }
        });
    }
}


