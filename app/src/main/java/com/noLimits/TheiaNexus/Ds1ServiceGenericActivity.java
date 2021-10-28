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
 */


package com.noLimits.TheiaNexus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
public class Ds1ServiceGenericActionActivity extends DS1ServiceActionACtivity {

    public enum SettingType
    {
        SETTING_TYPE_USER_MODE,
        SETTING_TYPE_BINARY,
        SETTING_TYPE_USER_GUN_ANNOUNCE,
        SETTING_TYPE_OVER_SPEED,
        SETTING_TYPE_PERCENT,
        SETTING_TYPE_BRIGHTNESS,
        SETTING_TYPE_COLOR,
        SETTING_TYPE_DISPLAY_MODE,
        SETTING_TYPE_TONE,
        SETTING_TYPE_GPS_ALERT_DIST,
        SETTING_TYPE_TOLERANCE,
        SETTING_TYPE_GPS_RLC_AUTO_MUTE,
        SETTING_TYPE_TIME,
        SETTING_TYPE_DST,
        SETTING_TYPE_TIME24,
        SETTING_TYPE_UNIT,
        SETTING_TYPE_AUTO_OFF,
        SETTING_TYPE_AUTO_MUTE_START,
        SETTING_TYPE_LANGUAGE,
        SETTING_TYPE_TIMEZONE,

    };



    SeekBar sb;
    protected String settingName;
    protected String displayName;
    protected String title;
    int selectedValue;
    SettingType settingType = SettingType.SETTING_TYPE_BINARY;
    List<String> optionsList;
    List<View> viewList;
    Switch sw;
    TextView tv;

    protected void createOptionsList()
    {
        optionsList = new ArrayList<String>();

        switch(settingType)
        {
            case SETTING_TYPE_USER_MODE:
                optionsList.add("Radar+Laser");
                optionsList.add("Track");
                break;

            case SETTING_TYPE_BINARY:
                optionsList.add("Off");
                optionsList.add("On");
                break;

            case SETTING_TYPE_USER_GUN_ANNOUNCE:
                optionsList.add("Display");
                optionsList.add("Display+Voice");
                optionsList.add("None");
                break;

            case SETTING_TYPE_DISPLAY_MODE:
                optionsList.add("Clock");
                optionsList.add("Scan");
                optionsList.add("Smart Dark");
                optionsList.add("Dark");
                break;

            case SETTING_TYPE_UNIT:
                optionsList.add("English");
                optionsList.add("Metric");
                break;

            case SETTING_TYPE_LANGUAGE:
                optionsList.add("English");
                optionsList.add("Spanish");
                optionsList.add("French");
                optionsList.add("Chinese");
                break;

            case SETTING_TYPE_TONE:
                for (int i = 0; i < 13; i++)
                {
                    optionsList.add(String.valueOf(i+1));
                }
                break;

            case SETTING_TYPE_GPS_ALERT_DIST:
                optionsList.add("Auto");
                optionsList.add("Short");
                optionsList.add("Medium");
                optionsList.add("Long");
                break;

            case SETTING_TYPE_AUTO_MUTE_START:
                optionsList.add("1 sec");
                optionsList.add("2 sec");
                optionsList.add("3 sec");
                break;

            default:
                optionsList.add("None");
                break;
        }

    }

    public void writeValue()
    {
        mDS1Service.writeGeneric(settingName, selectedValue);
    }

    @Override
    public void onCreate (Bundle b) {

        // should read the value here and selected it

        super.onCreate(b);

        final Intent intent = getIntent();
        String strType = intent.getStringExtra("TYPE");
        int intType = Integer.decode(strType);
        settingType = SettingType.values()[intType];

        settingName = intent.getStringExtra("NAME");
        title       = intent.getStringExtra("TITLE");

        setTitle(title);

        createOptionsList();
        setContentView(R.layout.activity_generic);
        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.genericLayout);

        if (settingType == SettingType.SETTING_TYPE_PERCENT)
        {
            sb = new SeekBar(TheiaServiceGenericActionActivity.this);
            sb.setMax(100);

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    sendSelected(seekBar.getProgress());

                }
            });

            parentLayout.addView(sb);

            return;
        }

        if (settingType == SettingType.SETTING_TYPE_TOLERANCE)
        {
            sb = new SeekBar(TheiaServiceGenericActionActivity.this);
            sb.setMax(19);

            tv = new TextView(TheiaServiceGenericActionActivity.this);
            tv.setText("1");

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tv.setText("+/- ".concat(String.valueOf(progress+1).concat(" MHz")));
                }


                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }


                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    sendSelected(seekBar.getProgress() + 1);

                }


            });

            parentLayout.addView(sb);
            parentLayout.addView(tv);



            return;
        }

        if (settingType == SettingType.SETTING_TYPE_BRIGHTNESS)
        {
            sb = new SeekBar(TheiaServiceGenericActionActivity.this);
            sb.setMax(100);

            sw = new Switch(TheiaServiceGenericActionActivity.this);
            sw.setChecked(false);

            tv = new TextView(TheiaServiceGenericActionActivity.this);
            tv.setText("Auto brightness enabled:");

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }


                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    sendSelected(seekBar.getProgress());

                }
            });

            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        sb.setEnabled(false);
                        sendSelected(-1);
                    }
                    else
                    {
                        sb.setEnabled(true);
                        sendSelected(sb.getProgress());
                    }

                }
            });

            parentLayout.addView(sb);
            parentLayout.addView(tv);
            parentLayout.addView(sw);

            return;
        }


        RadioGroup rg = new RadioGroup(TheiaServiceGenericActionActivity.this);

        viewList = new ArrayList<View>();

        for (int i = 0; i < optionsList.size(); i++)
        {
            RadioButton rb = new RadioButton(TheiaServiceGenericActionActivity.this);
            rb.setText(optionsList.get(i));

            final int thisNum = i;
            rb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    sendSelected(thisNum);
                }
            });
            rg.addView(rb);
            viewList.add(rb);
        }

        parentLayout.addView(rg);

    }

    protected void sendSelected(int s)
    {
        Log.i("GenericSetting", "Sending".concat(String.valueOf(s)));

        mDS1Service.writeGeneric(settingName, s);
    }

    @Override
    protected void onConnectedTask()
    {
        if (mDS1Service != null)
        {

            mDS1Service.readValue(settingName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(TheiaService.THEIA_GOT_RESULT);
        registerReceiver(mTheiaReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTheiaReceiver);
    }

    private final BroadcastReceiver mTheiaReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == TheiaService.THEIA_GOT_RESULT)
            {
                // select this one
                int result = intent.getIntExtra("RESULT", -1);

                if (settingType == SettingType.SETTING_TYPE_PERCENT)
                {
                    sb.setProgress(result);
                    return;
                }

                if (settingType == SettingType.SETTING_TYPE_BRIGHTNESS)
                {
                    if (result == -1)
                    {
                        sw.setChecked(true);
                        sb.setEnabled(false);
                    }
                    else
                    {
                        sb.setProgress(result);
                        sw.setChecked(false);
                        sb.setEnabled(true);
                    }
                    return;
                }

                if (settingType == SettingType.SETTING_TYPE_TOLERANCE)
                {
                    sb.setProgress(result-1);
                    return;
                }

                if (result >= viewList.size())
                    return;

                if (result <= 0)
                    result = 0;
                RadioButton rb = (RadioButton) viewList.get(result);
                rb.setSelected(true);
                rb.setChecked(true);
            }
        }
    };



}
 */