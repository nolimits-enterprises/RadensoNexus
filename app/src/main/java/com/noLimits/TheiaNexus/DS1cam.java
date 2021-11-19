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

public class DS1cam extends DS1ServiceActionACtivity {

    Switch sw_rlc;
    Switch sw_speed;
    Switch sw_rlc_speed;

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

            case R.id.radio_ds1_gps_lockout_KX:
                mDS1Service.setLockoutBands(DS1Service.Lockout_Bands.LOCKOUT_BANDS_KX);
                break;
            case R.id.radio_ds1_gps_lockout_all:
                mDS1Service.setLockoutBands(DS1Service.Lockout_Bands.LOCKOUT_BANDS_ALL);
                break;

        }
    }

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Cam Configuration");

        setContentView(R.layout.activity_ds1cam);
        sw_rlc = (Switch) findViewById(R.id.sw_gps_rlc);
        sw_speed = (Switch) findViewById(R.id.sw_gps_speed);
        sw_rlc_speed = (Switch) findViewById(R.id.sw_gps_rlc_speed);

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
        DS1cam.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;


                sw_rlc.setChecked(mDS1Service.getmSetting().red_light_cam);
                sw_speed.setChecked(mDS1Service.getmSetting().speed_cam);
                sw_rlc_speed.setChecked(mDS1Service.getmSetting().red_light_and_speed_cam);



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


