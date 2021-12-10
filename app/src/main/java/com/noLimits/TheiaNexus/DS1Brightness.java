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
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

public class DS1Brightness extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;
    Button pos;
    Button neg;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Brightness");

        setContentView(R.layout.activity_ds1brightness);
        sb = (SeekBar)findViewById(R.id.seek_dsBright);
        sw = (Switch)findViewById(R.id.sw_ds1brightness);

        pos = (Button)findViewById(R.id.btnBrightPos);
        neg = (Button)findViewById(R.id.btnBrightNeg);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sb.setVisibility(View.GONE);
                    pos.setVisibility(View.GONE);
                    neg.setVisibility(View.GONE);
                    if (mDS1Service != null)
                    {
                        mDS1Service.setBrightness(mDS1Service.brightnessFromInt(0));
                    }
                    // disable the bar
                }
                else {
                    sb.setVisibility(View.VISIBLE);
                    pos.setVisibility(View.VISIBLE);
                    neg.setVisibility(View.VISIBLE);
                    if (mDS1Service != null)
                    {
                        mDS1Service.setBrightness(mDS1Service.brightnessFromInt(3 - sb.getProgress()));
                    }
                }
            }

        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                        mDS1Service.setBrightness(mDS1Service.brightnessFromInt(3 - progress));
                    }
                }
            }
        });


    }

    public void onBPlus(View v)
    {
        sb.setProgress(sb.getProgress()+1);
        mDS1Service.setBrightness(mDS1Service.brightnessFromInt(3 - sb.getProgress()));
    }

    public void onBMinus(View v)
    {
        sb.setProgress(sb.getProgress()-1);
        mDS1Service.setBrightness(mDS1Service.brightnessFromInt(3 - sb.getProgress()));
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (mDS1Service != null)
            mDS1Service.requestSettings();

    }

    @Override
    void onGotResult()
    {
        DS1Brightness.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                DS1Service.Brightness_Type b = mDS1Service.getmSetting().brightness;

                switch (b)
                {
                    case BRIGHTNESS_DIMMER:
                        sw.setChecked(false);
                        sb.setProgress(0);
                        pos.setVisibility(View.VISIBLE);
                        neg.setVisibility(View.VISIBLE);
                        break;
                    case BRIGHTNESS_DIM:
                        sw.setChecked(false);
                        sb.setProgress(1);
                        pos.setVisibility(View.VISIBLE);
                        neg.setVisibility(View.VISIBLE);
                        break;
                    case BRIGHTNESS_BRIGHT:
                        sw.setChecked(false);
                        sb.setProgress(2);
                        pos.setVisibility(View.VISIBLE);
                        neg.setVisibility(View.VISIBLE);
                        break;
                    case BRIGHTNESS_AUTO:
                        sb.setVisibility(View.GONE);
                        pos.setVisibility(View.GONE);
                        neg.setVisibility(View.GONE);
                        sw.setChecked(true);
                        break;
                }

            }
        });
    }

}

