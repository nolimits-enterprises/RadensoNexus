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

public class DS1Power extends DS1ServiceActionACtivity {

    RadioButton rb_disabled;
    RadioButton rb_15;
    RadioButton rb_30;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Auto Power Off");

        setContentView(R.layout.activity_autopower);

        rb_disabled = (RadioButton)findViewById(R.id.radio_power_off);
        rb_15 = (RadioButton)findViewById(R.id.radio_power_15);
        rb_30 = (RadioButton)findViewById(R.id.radio_power_30);


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_power_off:
                mDS1Service.setPowerOff(0);
                break;
            case R.id.radio_power_15:
                mDS1Service.setPowerOff(15);
                break;
            case R.id.radio_power_30:
                mDS1Service.setPowerOff(30);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Power.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;


                int power = mDS1Service.getmSetting().power_off;

                switch(power)
                {
                    case 0:
                        rb_disabled.setChecked(true);
                        break;
                    case 15:
                        rb_15.setChecked(true);
                        break;
                    case 30:
                        rb_30.setChecked(true);
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
