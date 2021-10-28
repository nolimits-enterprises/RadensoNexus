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
import com.nolimits.ds1library.DS1Service;

public class DS1Idle extends DS1ServiceActionACtivity {

    RadioButton rb_scan;
    RadioButton rb_time;
    RadioButton rb_smart;
    RadioButton rb_dark;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Idle selection");

        setContentView(R.layout.activity_ds1idle);

        rb_scan = (RadioButton)findViewById(R.id.radio_idle_scan);
        rb_time = (RadioButton)findViewById(R.id.radio_idle_time);
        rb_smart = (RadioButton)findViewById(R.id.radio_idle_smart);
        rb_dark = (RadioButton)findViewById(R.id.radio_idle_dark);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_idle_scan:
                mDS1Service.setIdle(DS1Service.Idle_Mode.IDLE_SCAN);
                break;
            case R.id.radio_idle_time:
                mDS1Service.setIdle(DS1Service.Idle_Mode.IDLE_TIME);
                break;
            case R.id.radio_idle_smart:
                mDS1Service.setIdle(DS1Service.Idle_Mode.IDLE_SMART);
                break;
            case R.id.radio_idle_dark:
                mDS1Service.setIdle(DS1Service.Idle_Mode.IDLE_DARK);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Idle.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                DS1Service.Idle_Mode idle;
                idle = mDS1Service.getmSetting().idle_mode;

                switch (idle)
                {
                    case IDLE_SCAN:

                        rb_scan.setChecked(true);
                        rb_dark.setChecked(false);
                        rb_smart.setChecked(false);
                        rb_time.setChecked(false);
                        break;
                    case IDLE_TIME:
                        rb_scan.setChecked(false);
                        rb_dark.setChecked(false);
                        rb_smart.setChecked(false);
                        rb_time.setChecked(true);
                        break;
                    case IDLE_SMART:
                        rb_scan.setChecked(false);
                        rb_dark.setChecked(false);
                        rb_smart.setChecked(true);
                        rb_time.setChecked(false);
                        break;
                    case IDLE_DARK:
                        rb_scan.setChecked(false);
                        rb_dark.setChecked(true);
                        rb_smart.setChecked(false);
                        rb_time.setChecked(false);
                        break;
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