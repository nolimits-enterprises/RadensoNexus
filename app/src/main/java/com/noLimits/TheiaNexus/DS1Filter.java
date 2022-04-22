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
import android.widget.RadioButton;
import android.widget.Switch;
import com.nolimits.ds1library.DS1Service;

public class DS1Filter extends DS1ServiceActionACtivity {

    boolean disableCheck = false;

    Switch sw_TSR;
    Switch sw_k;
    Switch sw_Ka;

    RadioButton rad_K_wide;
    RadioButton rad_K_narrow;
    RadioButton rad_K_NZ;

    RadioButton rad_Ka_wide;
    RadioButton rad_Ka_narrow;
    RadioButton rad_Ka_seg;

    RadioButton rad_ds1_knotch_off;
    RadioButton rad_ds1_knotch_weak;
    RadioButton rad_ds1_knotch_all;
    RadioButton rad_ds1_knotch_mute;

    Switch sw_1;
    Switch sw_2;
    Switch sw_3;
    Switch sw_4;
    Switch sw_5;
    Switch sw_6;
    Switch sw_7;
    Switch sw_8;
    Switch sw_9;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Filters");

        setContentView(R.layout.activity_ds1filter);

        rad_K_wide = (RadioButton)findViewById(R.id.radio_K_wide);
        rad_K_narrow = (RadioButton)findViewById(R.id.radio_K_narrow);
        rad_K_NZ = (RadioButton)findViewById(R.id.radio_K_NZ);

        rad_Ka_wide = (RadioButton)findViewById(R.id.radio_ka_wide);
        rad_Ka_narrow = (RadioButton)findViewById(R.id.radio_ka_narrow);
        rad_Ka_seg = (RadioButton)findViewById(R.id.radio_ka_seg);

        rad_ds1_knotch_off = (RadioButton)findViewById(R.id.rad_ds1_knotch_off);
        rad_ds1_knotch_weak = (RadioButton)findViewById(R.id.rad_ds1_knotch_weak);
        rad_ds1_knotch_all =  (RadioButton)findViewById(R.id.rad_ds1_knotch_all);
        rad_ds1_knotch_mute = (RadioButton)findViewById(R.id.rad_ds1_knotch_block_mute);

        sw_TSR = (Switch)findViewById(R.id.sw_TSR);
        sw_k = (Switch)findViewById(R.id.sw_K_filter);
        sw_Ka = (Switch)findViewById(R.id.sw_Ka_Filter);

        sw_1 = (Switch)findViewById(R.id.sw_seg1);
        sw_2 = (Switch)findViewById(R.id.sw_seg2);
        sw_3 = (Switch)findViewById(R.id.sw_seg3);
        sw_4 = (Switch)findViewById(R.id.sw_seg4);
        sw_5 = (Switch)findViewById(R.id.sw_seg5);
        sw_6 = (Switch)findViewById(R.id.sw_seg6);
        sw_7 = (Switch)findViewById(R.id.sw_seg7);
        sw_8 = (Switch)findViewById(R.id.sw_seg8);
        sw_9 = (Switch)findViewById(R.id.sw_seg9);
    }

    public void onSwitchClicked(View view)
    {
        boolean checked = ((Switch) view).isChecked();

        switch(view.getId())
        {
            case R.id.sw_TSR:
                mDS1Service.setTSRFilter(checked);
                break;
            case R.id.sw_K_filter:
                mDS1Service.setKFilter(checked);
                enabled_k_radio(checked);
                break;
            case R.id.sw_Ka_Filter:
                mDS1Service.setKaFilter(checked);
                enabled_ka_radio(checked);
                enabled_ka_seg(checked && rad_Ka_seg.isChecked());
                break;
            case R.id.sw_seg1:
                mDS1Service.setSeg(checked, 0);
                break;
            case R.id.sw_seg2:
                mDS1Service.setSeg(checked, 1);
                break;
            case R.id.sw_seg3:
                mDS1Service.setSeg(checked, 2);
                break;
            case R.id.sw_seg4:
                mDS1Service.setSeg(checked, 3);
                break;
            case R.id.sw_seg5:
                mDS1Service.setSeg(checked, 4);
                break;
            case R.id.sw_seg6:
                mDS1Service.setSeg(checked, 5);
                break;
            case R.id.sw_seg7:
                mDS1Service.setSeg(checked, 6);
                break;
            case R.id.sw_seg8:
                mDS1Service.setSeg(checked, 7);
                break;
            case R.id.sw_seg9:
                mDS1Service.setSeg(checked, 8);
                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.radio_K_wide:
                mDS1Service.setKWidth(DS1Service.Width.WIDTH_WIDE);
                break;
            case R.id.radio_K_narrow:
                mDS1Service.setKWidth(DS1Service.Width.WIDTH_NARROW);
                break;
            case R.id.radio_K_NZ:
                mDS1Service.setKWidth(DS1Service.Width.WIDTH_SEG);
                break;
            case R.id.radio_ka_wide:
                mDS1Service.setKaWidth(DS1Service.Width.WIDTH_WIDE);
                enabled_ka_seg(false);
                break;
            case R.id.radio_ka_narrow:
                mDS1Service.setKaWidth(DS1Service.Width.WIDTH_NARROW);
                enabled_ka_seg(false);
                break;
            case R.id.radio_ka_seg:
                mDS1Service.setKaWidth(DS1Service.Width.WIDTH_SEG);
                enabled_ka_seg(true);
                break;
            case R.id.rad_ds1_knotch_all:
                mDS1Service.setKNotch(DS1Service.K_Notch.K_NOTCH_BLOCK_ALL);
                break;
            case R.id.rad_ds1_knotch_off:
                mDS1Service.setKNotch(DS1Service.K_Notch.K_NOTCH_OFF);
                break;
            case R.id.rad_ds1_knotch_weak:
                mDS1Service.setKNotch(DS1Service.K_Notch.K_NOTCH_BLOCK_WEAK);
                break;
            case R.id.rad_ds1_knotch_block_mute:
                mDS1Service.setKNotch(DS1Service.K_Notch.K_NOTCH_MUTE_ALL);
                break;


        }
    }

    void enabled_k_radio(boolean en)
    {

        return;
        /*
        rad_K_narrow.setEnabled(en);
        rad_K_wide.setEnabled(en);
        rad_K_NZ.setEnabled(en);

         */
    }

    void enabled_ka_radio(boolean en)
    {
        return;
        /*
        rad_Ka_narrow.setEnabled(en);
        rad_Ka_wide.setEnabled(en);
        rad_Ka_seg.setEnabled(en);

         */
    }

    void enabled_ka_seg(boolean en)
    {
        return;
        /*
        sw_1.setEnabled(en);
        sw_2.setEnabled(en);
        sw_3.setEnabled(en);
        sw_4.setEnabled(en);
        sw_5.setEnabled(en);
        sw_6.setEnabled(en);
        sw_7.setEnabled(en);
        sw_8.setEnabled(en);
        sw_9.setEnabled(en);

         */
    }

    @Override
    void onGotResult()
    {
        DS1Filter.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                disableCheck = true;

                DS1Service.Width w;
                w = mDS1Service.getmSetting().K_width;

                switch (w)
                {
                    case WIDTH_NARROW:

                        rad_K_narrow.setChecked(true);
                        //rad_K_wide.setChecked(false);
                        //rad_K_NZ.setChecked(false);
                        break;
                    case WIDTH_SEG:
                        //rad_K_narrow.setChecked(false);
                        //rad_K_wide.setChecked(false);
                        rad_K_NZ.setChecked(true);
                        break;
                    case WIDTH_WIDE:
                        //rad_K_narrow.setChecked(false);
                        rad_K_wide.setChecked(true);
                        //rad_K_NZ.setChecked(false);
                        break;
                }

                w = mDS1Service.getmSetting().Ka_width;

                switch (w)
                {
                    case WIDTH_NARROW:

                        rad_Ka_narrow.setChecked(true);
                        //rad_Ka_wide.setChecked(false);
                        //rad_Ka_seg.setChecked(false);
                        break;
                    case WIDTH_SEG:
                        //rad_Ka_narrow.setChecked(false);
                        //rad_Ka_wide.setChecked(false);
                        rad_Ka_seg.setChecked(true);
                        break;
                    case WIDTH_WIDE:
                        //rad_Ka_narrow.setChecked(false);
                        rad_Ka_wide.setChecked(true);
                        //rad_Ka_seg.setChecked(false);
                        break;
                }

                DS1Service.K_Notch k = mDS1Service.getmSetting().k_notch;

                switch (k)
                {
                    case K_NOTCH_BLOCK_ALL:
                        rad_ds1_knotch_all.setChecked(true);
                        break;
                    case K_NOTCH_BLOCK_WEAK:
                        rad_ds1_knotch_weak.setChecked(true);
                        break;
                    case K_NOTCH_MUTE_ALL:
                        rad_ds1_knotch_mute.setChecked(true);
                        break;
                    default:
                        rad_ds1_knotch_off.setChecked(true);
                        break;
                }

                sw_TSR.setChecked(mDS1Service.getmSetting().TSA_filter);
                sw_k.setChecked(mDS1Service.getmSetting().K_filter);
                sw_Ka.setChecked(mDS1Service.getmSetting().Ka_filter);
                sw_1.setChecked(mDS1Service.getmSetting().Ka_Seg[0]);
                sw_2.setChecked(mDS1Service.getmSetting().Ka_Seg[1]);
                sw_3.setChecked(mDS1Service.getmSetting().Ka_Seg[2]);
                sw_4.setChecked(mDS1Service.getmSetting().Ka_Seg[3]);
                sw_5.setChecked(mDS1Service.getmSetting().Ka_Seg[4]);
                sw_6.setChecked(mDS1Service.getmSetting().Ka_Seg[5]);
                sw_7.setChecked(mDS1Service.getmSetting().Ka_Seg[6]);
                sw_8.setChecked(mDS1Service.getmSetting().Ka_Seg[7]);
                sw_9.setChecked(mDS1Service.getmSetting().Ka_Seg[8]);

                enabled_k_radio(sw_k.isChecked());
                enabled_ka_radio(sw_Ka.isChecked());
                enabled_ka_seg(sw_Ka.isChecked() && rad_Ka_seg.isChecked());

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