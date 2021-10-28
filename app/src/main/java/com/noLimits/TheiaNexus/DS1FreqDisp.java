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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

public class DS1FreqDisp extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;

    boolean disableCheck = false;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Frequency Display");

        setContentView(R.layout.activity_ds1binary);
        sw = (Switch)findViewById(R.id.sw_binary);

        sw.setText("Frequency Display ");

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!disableCheck)
                    mDS1Service.setDispFreq(isChecked);
            }
        });

    }
    @Override
    void onGotResult()
    {
        DS1FreqDisp.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;

                disableCheck = true;

                sw.setChecked(mDS1Service.getmSetting().freq_disp);

                disableCheck = false;
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
