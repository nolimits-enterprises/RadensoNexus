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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

public class DS1Subs extends DS1ServiceActionACtivity {


    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Subdisplay selection");

        setContentView(R.layout.activity_ds1sub);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_sub_off:
                    mDS1Service.setSubDisplay(DS1Service.Sub_Display.SUB_OFF);
                    break;
            case R.id.radio_sub_speed:
                mDS1Service.setSubDisplay(DS1Service.Sub_Display.SUB_SPEED);
                    break;
            case R.id.radio_sub_compass:
                mDS1Service.setSubDisplay(DS1Service.Sub_Display.SUB_COMPASS);
                break;
            case R.id.radio_sub_speed_compass:
                mDS1Service.setSubDisplay(DS1Service.Sub_Display.SUB_SPEED_COMPASS);
                break;
            case R.id.radio_sub_altitude:
                mDS1Service.setSubDisplay(DS1Service.Sub_Display.SUB_ALTITUDE);
                break;

        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        //mDS1Service.requestSettings();

    }

    @Override
    void onGotResult() {
        DS1Subs.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;


                DS1Service.Sub_Display sub = mDS1Service.getmSetting().sub_display;

                switch (sub) {
                    case SUB_ALTITUDE:
                        ((RadioButton) findViewById(R.id.radio_sub_altitude)).setChecked(true);
                        break;
                    case SUB_COMPASS:
                        ((RadioButton) findViewById(R.id.radio_sub_compass)).setChecked(true);
                        break;
                    case SUB_OFF:
                        ((RadioButton) findViewById(R.id.radio_sub_off)).setChecked(true);
                        break;
                    case SUB_SPEED:
                        ((RadioButton) findViewById(R.id.radio_sub_speed)).setChecked(true);
                        break;
                    case SUB_SPEED_COMPASS:
                        ((RadioButton) findViewById(R.id.radio_sub_speed_compass)).setChecked(true);
                        break;
                }

            }
        });
    }

}