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
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class DS1Channels extends DS1ServiceActionACtivity {

    Switch sw_laser;
    Switch sw_mrcd;
    Switch sw_X;
    Switch sw_K;
    Switch sw_Ka;
    Switch sw_pop_K;
    Switch sw_pop_Ka;
    SeekBar sb_X;
    SeekBar sb_K;
    SeekBar sb_Ka;
    TextView text_X;
    TextView text_K;
    TextView text_Ka;

    boolean disableCheck = false;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Channel and Sensitivity Selection");

        setContentView(R.layout.activity_dssens);
        sw_laser = (Switch)findViewById(R.id.sw_ds1laser);
        sw_mrcd = (Switch)findViewById(R.id.sw_ds1mrcd);
        sw_X = (Switch)findViewById(R.id.sw_ds1x);
        sw_K = (Switch)findViewById(R.id.sw_ds1K);
        sw_Ka = (Switch)findViewById(R.id.sw_ds1Ka);
        sw_pop_K = (Switch)findViewById(R.id.sw_pop_K);
        sw_pop_Ka = (Switch)findViewById(R.id.sw_pop_Ka);
        sb_X = (SeekBar)findViewById(R.id.prog_sens_X);
        sb_K = (SeekBar)findViewById(R.id.prog_sense_K);
        sb_Ka = (SeekBar)findViewById(R.id.prog_sense_ka);
        text_X = (TextView)findViewById(R.id.text_sense_X);
        text_K = (TextView)findViewById(R.id.btnKPlus);
        text_Ka = (TextView)findViewById(R.id.text_sense_Ka);



        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (disableCheck)
                    return;

                switch(buttonView.getId())
                {
                    case R.id.sw_ds1laser:
                        mDS1Service.setLaserEnable(isChecked);
                        break;
                    case R.id.sw_ds1mrcd:
                        mDS1Service.setMRCDEnable(isChecked);
                        break;
                    case R.id.sw_ds1x:
                        mDS1Service.setXEnable(isChecked);
                        break;
                    case R.id.sw_ds1K:
                        mDS1Service.setKEnable(isChecked);
                        break;
                    case R.id.sw_ds1Ka:
                        mDS1Service.setKaEnable(isChecked);
                        break;
                    case R.id.sw_pop_K:
                        mDS1Service.setPopKEnable(isChecked);
                         break;
                    case R.id.sw_pop_Ka:
                        mDS1Service.setPopKaEnable(isChecked);
                        break;
                }


            }

        };

        sw_laser.setOnCheckedChangeListener(checkListener);
        sw_mrcd.setOnCheckedChangeListener(checkListener);
        sw_X.setOnCheckedChangeListener(checkListener);
        sw_K.setOnCheckedChangeListener(checkListener);
        sw_Ka.setOnCheckedChangeListener(checkListener);
        sw_pop_K.setOnCheckedChangeListener(checkListener);
        sw_pop_Ka.setOnCheckedChangeListener(checkListener);

        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {

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
                    switch (seekBar.getId())
                    {
                        case R.id.prog_sens_X:
                            mDS1Service.setXSensitivity(progress);
                            text_X.setText(String.valueOf(progress * 10 + 50) + "%");
                            break;
                        case R.id.prog_sense_K:
                            mDS1Service.setKSensitivity(progress);
                            text_K.setText(String.valueOf(progress * 10 + 50) + "%");
                            break;
                        case R.id.prog_sense_ka:
                            mDS1Service.setKaSensitivity(progress);
                            text_Ka.setText(String.valueOf(progress * 10 + 50) + "%");
                            break;
                    }
                }
            }
        };





        sb_X.setOnSeekBarChangeListener(seekListener);
        sb_K.setOnSeekBarChangeListener(seekListener);
        sb_Ka.setOnSeekBarChangeListener(seekListener);


    }

    public void onClick(View v)
    {
        int progress;

        switch(v.getId())
        {
            case R.id.btnKaNeg:
                sb_Ka.setProgress(sb_Ka.getProgress() - 1);
                progress = sb_Ka.getProgress();
                mDS1Service.setKaSensitivity(progress);
                text_Ka.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
            case R.id.btnKaPlus:
                sb_Ka.setProgress(sb_Ka.getProgress() + 1);
                progress = sb_Ka.getProgress();
                mDS1Service.setKaSensitivity(progress);
                text_Ka.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
            case R.id.btnKNeg:
                sb_K.setProgress(sb_K.getProgress() - 1);
                progress = sb_K.getProgress();
                mDS1Service.setKSensitivity(progress);
                text_K.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
            case R.id.btnKPlus:
                sb_K.setProgress(sb_K.getProgress() + 1);
                progress = sb_K.getProgress();
                mDS1Service.setKSensitivity(progress);
                text_K.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
            case R.id.btnXNeg:
                sb_X.setProgress(sb_X.getProgress() - 1);
                progress = sb_X.getProgress();
                mDS1Service.setXSensitivity(progress);
                text_X.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
            case R.id.btnXPlus:
                sb_X.setProgress(sb_X.getProgress() + 1);
                progress = sb_X.getProgress();
                mDS1Service.setXSensitivity(progress);
                text_X.setText(String.valueOf(progress * 10 + 50) + "%");
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1Channels.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;

                disableCheck = true;

                sw_laser.setChecked(mDS1Service.getmSetting().laser);
                sw_mrcd.setChecked(mDS1Service.getmSetting().MRCD);
                sw_X.setChecked(mDS1Service.getmSetting().X);
                sw_K.setChecked(mDS1Service.getmSetting().K);
                sw_Ka.setChecked(mDS1Service.getmSetting().Ka);
                sw_pop_K.setChecked(mDS1Service.getmSetting().pop_K);
                sw_pop_Ka.setChecked(mDS1Service.getmSetting().pop_Ka);
                int x = mDS1Service.getmSetting().custom_X;
                sb_X.setProgress(x);
                text_X.setText(String.valueOf(sb_X.getProgress() * 10 + 50) + "%");
                sb_K.setProgress(mDS1Service.getmSetting().custom_K);
                text_K.setText(String.valueOf(sb_K.getProgress() * 10 + 50) + "%");
                sb_Ka.setProgress(mDS1Service.getmSetting().custom_Ka);
                text_Ka.setText(String.valueOf(sb_Ka.getProgress() * 10 + 50) + "%");

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

