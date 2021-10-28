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

public class DS1Region extends DS1ServiceActionACtivity {

    RadioButton rb_us;
    RadioButton rb_ca;
    RadioButton rb_aus;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Region");

        setContentView(R.layout.activity_ds1region);

        rb_us = (RadioButton)findViewById(R.id.radio_reg_US);
        rb_ca = (RadioButton)findViewById(R.id.radio_reg_CA);
        rb_aus = (RadioButton)findViewById(R.id.radio_reg_Aus);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_reg_US:
                mDS1Service.setRegion(DS1Service.Region.REGION_US);
                break;
            case R.id.radio_reg_Aus:
                mDS1Service.setRegion(DS1Service.Region.REGION_AUS);
                break;
            case R.id.radio_reg_CA:
                mDS1Service.setRegion(DS1Service.Region.REGION_CANADA);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Region.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                DS1Service.Region r = mDS1Service.getmSetting().region;

                switch (r)
                {
                    case REGION_AUS:
                        rb_aus.setChecked(true);
                        break;
                    case REGION_CANADA:
                        rb_ca.setChecked(true);
                        break;
                    case REGION_US:
                        rb_us.setChecked(true);
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