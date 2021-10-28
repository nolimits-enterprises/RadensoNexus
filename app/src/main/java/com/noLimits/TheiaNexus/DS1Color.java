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
import android.widget.RadioButton;
import android.widget.TextView;
import com.nolimits.ds1library.DS1Service;

public class DS1Color extends DS1ServiceActionACtivity {

    TextView colorText;
    RadioButton rb_ds_red;
    RadioButton rb_ds_blue;
    RadioButton rb_ds_white;
    RadioButton rb_ds_gray;
    RadioButton rb_ds_pink;
    RadioButton rb_ds_purple;
    RadioButton rb_ds_green;
    RadioButton rb_ds_amber;

    boolean disableCheck = false;

    void setColor(DS1Service.Colors c)
    {
        mDS1Service.setColor(c);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            if (!checked)
                return;

            switch (view.getId()) {
                case R.id.radio_ds_red:
                    setColor(DS1Service.Colors.COLOR_RED);
                    break;
                case R.id.radio_ds_blue:
                    setColor(DS1Service.Colors.COLOR_BLUE);
                    break;
                case R.id.radio_ds_white:
                    setColor(DS1Service.Colors.COLOR_WHITE);
                    break;
                case R.id.radio_ds_gray:
                    setColor(DS1Service.Colors.COLOR_GRAY);
                    break;
                case R.id.radio_ds_pink:
                    setColor(DS1Service.Colors.COLOR_PINK);
                    break;
                case R.id.radio_ds_purple:
                    setColor(DS1Service.Colors.COLOR_PURPLE);
                    break;
                case R.id.radio_ds_green:
                    setColor(DS1Service.Colors.COLOR_GREEN);
                    break;
                case R.id.radio_ds_amber:
                    setColor(DS1Service.Colors.COLOR_AMBER);
                    break;

            }
        }
    };

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Color");

        setContentView(R.layout.activity_ds1color);

        rb_ds_red = (RadioButton)findViewById(R.id.radio_ds_red);
        rb_ds_blue = (RadioButton)findViewById(R.id.radio_ds_blue);
        rb_ds_white = (RadioButton)findViewById(R.id.radio_ds_white);
        rb_ds_gray = (RadioButton)findViewById(R.id.radio_ds_gray);
        rb_ds_pink = (RadioButton)findViewById(R.id.radio_ds_pink);
        rb_ds_purple = (RadioButton)findViewById(R.id.radio_ds_purple);
        rb_ds_green = (RadioButton)findViewById(R.id.radio_ds_green);
        rb_ds_amber = (RadioButton)findViewById(R.id.radio_ds_amber);


        rb_ds_red.setOnClickListener(clickListener);
        rb_ds_blue.setOnClickListener(clickListener);
        rb_ds_white.setOnClickListener(clickListener);
        rb_ds_gray.setOnClickListener(clickListener);
        rb_ds_pink.setOnClickListener(clickListener);
        rb_ds_purple.setOnClickListener(clickListener);
        rb_ds_green.setOnClickListener(clickListener);
        rb_ds_amber.setOnClickListener(clickListener);

    }

    public void onColorPress(View v)
    {
        mDS1Service.setColor(DS1Service.Colors.COLOR_GRAY);

    }

    DS1Service.Colors getColor()
    {
        if (mDS1Service != null)
            return mDS1Service.getmSetting().display_color;

        else
            return null;
    }

    @Override
    void onGotResult()
    {
        if (mDS1Service == null)
            return;

        DS1Service.Colors c = getColor();

        switch(c)
        {
            case COLOR_RED:
                rb_ds_red.setChecked(true);
                break;
            case COLOR_AMBER:
                rb_ds_amber.setChecked(true);
                break;
            case COLOR_BLUE:
                rb_ds_blue.setChecked(true);
                break;
            case COLOR_GRAY:
                rb_ds_gray.setChecked(true);
                break;
            case COLOR_GREEN:
                rb_ds_green.setChecked(true);
                break;
            case COLOR_PINK:
                rb_ds_pink.setChecked(true);
                break;
            case COLOR_PURPLE:
                rb_ds_purple.setChecked(true);
                break;
            case COLOR_WHITE:
                rb_ds_white.setChecked(true);
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
