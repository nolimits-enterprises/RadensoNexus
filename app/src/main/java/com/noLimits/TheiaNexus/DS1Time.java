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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class DS1Time extends DS1ServiceActionACtivity {

    TextView txt;
    SeekBar sb;
    Switch sw_dst;
    Switch sw_24;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Time Configuration");

        setContentView(R.layout.activity_ds1time);
        txt = (TextView)findViewById(R.id.txtGMT);
        sb = (SeekBar)findViewById(R.id.seek_GMT);
        sw_dst = (Switch)findViewById(R.id.sw_DST);
        sw_24 = (Switch)findViewById(R.id.sw_24);

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
                        mDS1Service.setGMT(progress);
                        txt.setText("GMT" + ((progress >= 12) ? ("+" + String.valueOf(progress-12)) : String.valueOf(progress-12)));
                    }
                }
            }
        });


    }


    public void onPlus(View v)
    {
        sb.setProgress(sb.getProgress()+1);
        int progress = sb.getProgress();
        mDS1Service.setGMT(progress);
        txt.setText("GMT" + ((progress >= 12) ? ("+" + String.valueOf(progress-12)) : String.valueOf(progress-12)));
    }

    public void onMinus(View v)
    {
        sb.setProgress(sb.getProgress()-1);
        int progress = sb.getProgress();
        mDS1Service.setGMT(progress);
        txt.setText("GMT" + ((progress >= 12) ? ("+" + String.valueOf(progress-12)) : String.valueOf(progress-12)));
    }



    public void onDST(View v)
    {
        boolean checked = ((Switch) v).isChecked();
        mDS1Service.setDST(checked);
    }

    public void on24(View v)
    {
        boolean checked = ((Switch) v).isChecked();
        mDS1Service.set24(checked);
    }

    @Override
    void onGotResult()
    {
        DS1Time.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                int progress = mDS1Service.getmSetting().gmt;
                sb.setProgress(progress);
                txt.setText("GMT" + ((progress >= 12) ? ("+" + String.valueOf(progress-12)) : String.valueOf(progress-12)));

                sw_dst.setChecked(mDS1Service.getmSetting().dst);
                sw_24.setChecked(mDS1Service.getmSetting().time_24);
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

