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

public class DS1Mode  extends DS1ServiceActionACtivity {

    RadioButton rb_auto;
    RadioButton rb_custom;


    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Mode selection");

        setContentView(R.layout.activity_ds1mode);


        rb_auto = (RadioButton)findViewById(R.id.radio_mode_auto);
        rb_custom = (RadioButton)findViewById(R.id.radio_mode_custom);


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_mode_auto:
                mDS1Service.setMode(DS1Service.Mode.MODE_AUTO_CITY);
                break;
            case R.id.radio_mode_custom:
                mDS1Service.setMode(DS1Service.Mode.MODE_CUSTOM);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Mode.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                DS1Service.Mode m;
                m = mDS1Service.getmSetting().mode;

                switch (m)
                {
                    case MODE_AUTO_CITY:

                        rb_auto.setChecked(true);
                        rb_custom.setChecked(false);
                        break;
                    case MODE_CUSTOM:
                        rb_auto.setChecked(false);
                        rb_custom.setChecked(true);
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
