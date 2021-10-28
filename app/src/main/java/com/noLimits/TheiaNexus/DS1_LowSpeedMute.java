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
import com.nolimits.ds1library.DS1Service;

public class DS1_LowSpeedMute extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;
    TextView tv;
    Button plus;
    Button minus;

    boolean metric = false;


    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Low Speed Mute");

        setContentView(R.layout.activity_ds1slide);
        sb = (SeekBar)findViewById(R.id.sb_slide);
        tv = (TextView)findViewById(R.id.text_ds1slide);
        plus = (Button)findViewById(R.id.btnSlidePlus);
        minus = (Button)findViewById(R.id.btnSlideMinus);

        sb.setMax(9);


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

                        if (progress == 0)
                        {
                            // disabled
                            mDS1Service.setLowSpeedMute(progress);
                            tv.setText("Off");
                        }
                        else {
                            if(metric)
                            {
                                mDS1Service.setLowSpeedMute(progress * 10);
                                tv.setText(String.valueOf(progress * 10) + "kph");
                            }
                            else {
                                mDS1Service.setLowSpeedMute(progress * 5);
                                tv.setText(String.valueOf(progress * 5) + "mph");
                            }
                        }

                    }
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sb.setProgress(sb.getProgress() + 1);
                int progress = sb.getProgress();
                if (mDS1Service != null)
                {

                    if (progress == 0)
                    {
                        // disabled
                        mDS1Service.setLowSpeedMute(progress);
                        tv.setText("Off");
                    }
                    else {
                        if(metric)
                        {
                            mDS1Service.setLowSpeedMute(progress * 10);
                            tv.setText(String.valueOf(progress * 10) + "kph");
                        }
                        else {
                            mDS1Service.setLowSpeedMute(progress * 5);
                            tv.setText(String.valueOf(progress * 5) + "mph");
                        }
                    }

                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sb.setProgress(sb.getProgress() - 1);
                int progress = sb.getProgress();
                if (mDS1Service != null)
                {

                    if (progress == 0)
                    {
                        // disabled
                        mDS1Service.setLowSpeedMute(progress);
                        tv.setText("Off");
                    }
                    else {
                        if(metric)
                        {
                            tv.setText(String.valueOf(progress * 10) + "kph");
                            mDS1Service.setLowSpeedMute(progress * 10);
                        }
                        else {
                            tv.setText(String.valueOf(progress * 5) + "mph");
                            mDS1Service.setLowSpeedMute(progress * 5);
                        }
                    }

                }
            }
        });


    }

    @Override
    void onGotResult()
    {
        DS1_LowSpeedMute.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;


                int m = (mDS1Service.getmSetting().low_speed_mute);
                metric = (mDS1Service.getmSetting().unit_standard);

                if (metric)
                    sb.setMax(10);
                else
                    sb.setMax(9);

                if (m == 0)
                {
                    tv.setText("Off");
                    sb.setProgress(mDS1Service.getmSetting().low_speed_mute);
                }
                else
                {
                    if(metric)
                    {
                        sb.setProgress(m / 10);
                        tv.setText(String.valueOf(m) + "kph");

                    }
                    else {
                        sb.setProgress(m / 5);
                        tv.setText(String.valueOf(sb.getProgress() * 5) + "mph");
                    }
                }


            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mDS1Service != null) {
            mDS1Service.requestSettings();

        }

    }

}

