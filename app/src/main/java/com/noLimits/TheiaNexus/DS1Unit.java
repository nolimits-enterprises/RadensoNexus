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

public class DS1Unit extends DS1ServiceActionACtivity {

    RadioButton rb_mph;
    RadioButton rb_kmh;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Unit selection");

        setContentView(R.layout.activity_ds1unit);

        rb_mph = (RadioButton)findViewById(R.id.radio_mph);
        rb_kmh = (RadioButton)findViewById(R.id.radio_kph);


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_mph:
                mDS1Service.setStandard(false);
                break;
            case R.id.radio_kph:
                mDS1Service.setStandard(true);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Unit.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;


                boolean standard = mDS1Service.getmSetting().unit_standard;

                if (standard)
                    rb_kmh.setChecked(true);
                else
                    rb_mph.setChecked(true);

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
