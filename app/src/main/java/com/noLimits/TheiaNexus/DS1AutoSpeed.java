package com.noLimits.TheiaNexus;
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



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class DS1AutoSpeed extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;
    TextView txt;
    boolean metric = false;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("DS1 Auto Speed");

        setContentView(R.layout.activity_ds1slide);
        sb = (SeekBar)findViewById(R.id.sb_slide);
        txt = (TextView)findViewById(R.id.text_ds1slide);

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
                        mDS1Service.setAutoSpeedSetting(progress * (metric ? 10 : 5) + (metric ? 20 : 15));
                        setMRCDText();

                    }
                }
            }
        });


    }

    public void onPlus(View v)
    {
        sb.setProgress(sb.getProgress()+1);
        int progress = sb.getProgress();
        mDS1Service.setAutoSpeedSetting(progress * (metric ? 10 : 5) + (metric ? 20 : 15));
        setMRCDText();
    }

    public void onMinus(View v)
    {
        sb.setProgress(sb.getProgress()-1);
        int progress = sb.getProgress();
        mDS1Service.setAutoSpeedSetting(progress * (metric ? 10 : 5) + (metric ? 20 : 15));
        setMRCDText();
    }

    void setMRCDText()
    {
        /*
        if (sb.getProgress() == 0)
        {
            txt.setText("Off");
            return;
        }
         */

        if (metric)
        {
            int speed = sb.getProgress() *10 + 20;
            txt.setText(String.valueOf(speed) + " kph");
        }
        else
        {
            int speed = sb.getProgress() * 5 + 15;
            txt.setText(String.valueOf(speed) + " mph");
        }
    }

    @Override
    void onGotResult()
    {
        DS1AutoSpeed.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                metric = mDS1Service.getmSetting().unit_standard;
                int speed = mDS1Service.getmSetting().autoSpeed;
                if (speed > 0) {
                    if (metric) {
                        speed -= 20;
                        speed /= 10;
                        sb.setMax(7);

                    }
                    else {
                        speed -= 15;
                        speed /= 5;
                        sb.setMax(9);

                    }
                }

                sb.setProgress(speed);
                setMRCDText();

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
