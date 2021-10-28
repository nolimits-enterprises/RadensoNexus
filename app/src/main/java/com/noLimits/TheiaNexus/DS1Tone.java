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

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

public class DS1Tone extends DS1ServiceActionACtivity {

    RadioButton rb_1;
    RadioButton rb_2;
    RadioButton rb_3;
    RadioButton rb_4;
    RadioButton rb_5;
    RadioButton rb_6;
    RadioButton rb_7;
    RadioButton rb_8;
    RadioButton rb_9;
    RadioButton rb_10;

    boolean disableCheck = false;

    void setTone(int t)
    {

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            if (!checked)
                return;

            AssetFileDescriptor afd;



            switch (view.getId()) {
                default:
                    setTone(1);
                     afd = getResources().openRawResourceFd(R.raw.s1);
                    break;
                case R.id.radio_tone_2:
                    setTone(2);
                    afd = getResources().openRawResourceFd(R.raw.s2);
                    break;
                case R.id.radio_tone_3:
                    afd = getResources().openRawResourceFd(R.raw.s3);
                    setTone(3);
                    break;
                case R.id.radio_tone_4:
                    afd = getResources().openRawResourceFd(R.raw.s4);
                    setTone(4);
                    break;
                case R.id.radio_tone_5:
                    afd = getResources().openRawResourceFd(R.raw.s5);
                    setTone(5);
                    break;
                case R.id.radio_tone_6:
                    afd = getResources().openRawResourceFd(R.raw.s6);
                    setTone(6);
                    break;
                case R.id.radio_tone_7:
                    afd = getResources().openRawResourceFd(R.raw.s7);
                    setTone(7);
                    break;
                case R.id.radio_tone_8:
                    afd = getResources().openRawResourceFd(R.raw.s8);
                    setTone(8);
                    break;
                case R.id.radio_tone_9:
                    afd = getResources().openRawResourceFd(R.raw.s9);
                    setTone(9);
                    break;
                case R.id.radio_tone_10:
                    afd = getResources().openRawResourceFd(R.raw.s10);
                    setTone(10);
                    break;

            }

            FileDescriptor fd = afd.getFileDescriptor();
            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(fd, afd.getStartOffset(),
                        afd.getLength());
                player.setLooping(false);
                player.prepare();
                player.start();
            } catch (Exception e) {
            }
        }
    };

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Tone");

        setContentView(R.layout.activity_tone);

        rb_1 = (RadioButton)findViewById(R.id.radio_tone_1);
        rb_2 = (RadioButton)findViewById(R.id.radio_tone_2);
        rb_3 = (RadioButton)findViewById(R.id.radio_tone_3);
        rb_4 = (RadioButton)findViewById(R.id.radio_tone_4);
        rb_5 = (RadioButton)findViewById(R.id.radio_tone_5);
        rb_6 = (RadioButton)findViewById(R.id.radio_tone_6);
        rb_7 = (RadioButton)findViewById(R.id.radio_tone_7);
        rb_8 = (RadioButton)findViewById(R.id.radio_tone_8);
        rb_9 = (RadioButton)findViewById(R.id.radio_tone_9);
        rb_10 = (RadioButton)findViewById(R.id.radio_tone_10);

        rb_1.setOnClickListener(clickListener);
        rb_2.setOnClickListener(clickListener);
        rb_3.setOnClickListener(clickListener);
        rb_4.setOnClickListener(clickListener);
        rb_5.setOnClickListener(clickListener);
        rb_6.setOnClickListener(clickListener);
        rb_7.setOnClickListener(clickListener);
        rb_8.setOnClickListener(clickListener);
        rb_9.setOnClickListener(clickListener);
        rb_10.setOnClickListener(clickListener);

    }


    int getTone()
    {
       return 1;
    }

    @Override
    void onGotResult()
    {
        if (mDS1Service == null)
            return;

        int tone = getTone();

        switch(tone)
        {
            case 1:
                rb_1.setChecked(true);
                break;
            case 2:
                rb_2.setChecked(true);
                break;
            case 3:
                rb_3.setChecked(true);
                break;
            case 4:
                rb_4.setChecked(true);
                break;
            case 5:
                rb_5.setChecked(true);
                break;
            case 6:
                rb_6.setChecked(true);
                break;
            case 7:
                rb_7.setChecked(true);
                break;
            case 8:
                rb_8.setChecked(true);
                break;
            case 9:
                rb_9.setChecked(true);
                break;
            case 10:
                rb_10.setChecked(true);
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mDS1Service != null)
            mDS1Service.requestSettings();

    }

}
