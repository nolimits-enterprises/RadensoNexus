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
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;

public class DS1AutoMute extends DS1ServiceActionACtivity {

    RadioButton rb_1s;
    RadioButton rb_2s;
    RadioButton rb_3s;

    RadioButton rb_0p;
    RadioButton rb_10p;
    RadioButton rb_20p;
    RadioButton rb_30p;
    RadioButton rb_40p;
    RadioButton rb_50p;

    RadioButton rb_auto_xk;
    RadioButton rb_auto_allband;

    Switch sw;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Auto Mute Configuration");

        setContentView(R.layout.activity_ds1automute);

        rb_1s = (RadioButton)findViewById(R.id.radio_mute_1s);
        rb_2s = (RadioButton)findViewById(R.id.radio_mute_2s);
        rb_3s = (RadioButton)findViewById(R.id.radio_mute_3s);

        rb_0p = (RadioButton)findViewById(R.id.radio_mute_0);
        rb_10p = (RadioButton)findViewById(R.id.radio_mute_10);
        rb_20p = (RadioButton)findViewById(R.id.radio_mute_20);
        rb_30p = (RadioButton)findViewById(R.id.radio_mute_30);
        rb_40p = (RadioButton)findViewById(R.id.radio_mute_40);
        rb_50p = (RadioButton)findViewById(R.id.radio_mute_50);

        rb_auto_xk = (RadioButton)findViewById(R.id.rad_auto_xk);
        rb_auto_allband = (RadioButton)findViewById(R.id.rad_auto_allband);


        sw = (Switch)findViewById(R.id.sw_automute);

    }

    public void onAutoMuteClicked(View view)
    {
        boolean checked = ((Switch)view).isChecked();

        mDS1Service.setAutoMute(checked);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_mute_1s:
                mDS1Service.setAutoMuteStart(1);
                break;
            case R.id.radio_mute_2s:
                mDS1Service.setAutoMuteStart(2);
                break;
            case R.id.radio_mute_3s:
                mDS1Service.setAutoMuteStart(3);
                break;

            case R.id.radio_mute_0:
                mDS1Service.setAutoMuteVol(0);
                break;
            case R.id.radio_mute_10:
                mDS1Service.setAutoMuteVol(10);
                break;
            case R.id.radio_mute_20:
                mDS1Service.setAutoMuteVol(20);
                break;
            case R.id.radio_mute_30:
                mDS1Service.setAutoMuteVol(30);
                break;
            case R.id.radio_mute_40:
                mDS1Service.setAutoMuteVol(40);
                break;
            case R.id.radio_mute_50:
                mDS1Service.setAutoMuteVol(50);
                break;

            case R.id.rad_auto_allband:
                mDS1Service.setAutoBandAll(true);
                break;

            case R.id.rad_auto_xk:
                mDS1Service.setAutoBandAll(false);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1AutoMute.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                sw.setChecked(mDS1Service.getmSetting().auto_mute);
                 switch (mDS1Service.getmSetting().auto_mute_start)
                 {
                     case 1:
                         rb_1s.setChecked(true);
                         break;
                     case 2:
                         rb_2s.setChecked(true);
                         break;
                     case 3:
                         rb_3s.setChecked(true);
                         break;
                 }
                 switch(mDS1Service.getmSetting().auto_mute_volume)
                 {
                     case 0:
                         rb_0p.setChecked(true);
                         break;
                     case 10:
                         rb_10p.setChecked(true);
                         break;
                     case 20:
                         rb_20p.setChecked(true);
                         break;
                     case 30:
                         rb_30p.setChecked(true);
                         break;
                     case 40:
                         rb_40p.setChecked(true);
                         break;
                     case 50:
                         rb_50p.setChecked(true);
                         break;
                 }

                 if (mDS1Service.getmSetting().autoAllBand == true) {
                     rb_auto_allband.setChecked(true);
                 }
                 else {
                     rb_auto_xk.setChecked(true);
                 }

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mDS1Service != null)
            mDS1Service.requestSettings();

    }

}