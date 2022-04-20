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
    Switch sw_unlearn_lockout;


    RadioButton rb_auto_nl;
    RadioButton rb_auto_50;
    RadioButton rb_auto_100;
    RadioButton rb_auto_150;
    RadioButton rb_auto_200;

    RadioButton rb_adv_10;
    RadioButton rb_adv_15;
    RadioButton rb_adv_20;


    RadioButton rb_alert_auto;
    RadioButton rb_alert_short;
    RadioButton rb_alert_medium;
    RadioButton rb_alert_long;

    RadioButton radio_ds1_gps_lockout_KX;
    RadioButton radio_ds1_gps_lockout_all;


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
            case R.id.sw_gps_lockout_unlearn:
                mDS1Service.setUnlearnAutoLockout(checked);
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

            case R.id.radio_ds1_gps_lockout_KX:
                mDS1Service.setLockoutBands(DS1Service.Lockout_Bands.LOCKOUT_BANDS_KX);
                break;
            case R.id.radio_ds1_gps_lockout_all:
                mDS1Service.setLockoutBands(DS1Service.Lockout_Bands.LOCKOUT_BANDS_ALL);
                break;

            case R.id.rad_advanced_10:
                mDS1Service.setAdvancedLockout(0);
                break;

            case R.id.rad_advanced_15:
                mDS1Service.setAdvancedLockout(1);
                break;

            case R.id.rad_advanced_20:
                mDS1Service.setAdvancedLockout(2);
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
        sw_unlearn_lockout = (Switch) findViewById(R.id.sw_gps_lockout_unlearn);

        rb_auto_nl = (RadioButton) findViewById(R.id.radio_gps_auto_nl);
        rb_auto_50 = (RadioButton) findViewById(R.id.radio_gps_auto_50);
        rb_auto_100 = (RadioButton) findViewById(R.id.radio_gps_auto_100);
        rb_auto_150 = (RadioButton) findViewById(R.id.radio_gps_auto_150);
        rb_auto_200 = (RadioButton) findViewById(R.id.radio_gps_auto_200);

        rb_alert_auto = (RadioButton) findViewById(R.id.radio_gps_dist_auto);
        rb_alert_short = (RadioButton) findViewById(R.id.radio_gps_dist_short);
        rb_alert_medium = (RadioButton) findViewById(R.id.radio_gps_dist_med);
        rb_alert_long = (RadioButton) findViewById(R.id.radio_gps_dist_long);

        radio_ds1_gps_lockout_KX = (RadioButton) findViewById(R.id.radio_ds1_gps_lockout_KX);
        radio_ds1_gps_lockout_all = (RadioButton) findViewById(R.id.radio_ds1_gps_lockout_all);

        rb_adv_10 = (RadioButton) findViewById(R.id.rad_advanced_10);
        rb_adv_15 = (RadioButton) findViewById(R.id.rad_advanced_15);
        rb_adv_20 = (RadioButton) findViewById(R.id.rad_advanced_20);
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
                sw_unlearn_lockout.setChecked(mDS1Service.getmSetting().autoLearnLockout);

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

                int advancedLock = mDS1Service.getmSetting().advancedLockout;
                if (advancedLock == 0) {
                    rb_adv_10.setChecked(true);
                }
                else if (advancedLock == 1) {
                    rb_adv_15.setChecked(true);
                }
                else
                {
                    rb_adv_20.setChecked(true);
                }

                DS1Service.Lockout_Bands bands = mDS1Service.getmSetting().lockout_bands;
                switch(bands)
                {
                    case LOCKOUT_BANDS_ALL:
                        radio_ds1_gps_lockout_all.setChecked(true);
                        break;
                    default:
                        radio_ds1_gps_lockout_KX.setChecked(true);
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
            }
        });
    }
}


