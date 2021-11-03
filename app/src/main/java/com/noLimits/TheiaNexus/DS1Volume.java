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

public class DS1Volume extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;
    TextView txt;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Volume");

        setContentView(R.layout.activity_ds1volume);
        sb = (SeekBar)findViewById(R.id.seek_dsVolume);
        txt = (TextView)findViewById(R.id.txtVolume);

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
                        mDS1Service.setVolume(progress);
                        txt.setText(String.valueOf(progress));
                    }
                }
            }
        });


    }

    public void onPlus(View v)
    {
        sb.setProgress(sb.getProgress()+1);
        int progress = sb.getProgress();
        mDS1Service.setVolume(progress);
        txt.setText(String.valueOf(progress));
    }

    public void onMinus(View v)
    {
        sb.setProgress(sb.getProgress()-1);
        int progress = sb.getProgress();
        mDS1Service.setVolume(progress);
        txt.setText(String.valueOf(progress));
    }

    @Override
    void onGotResult()
    {
        DS1Volume.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                sb.setProgress(mDS1Service.getmSetting().volume);
                txt.setText(String.valueOf(mDS1Service.getmSetting().volume);

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

