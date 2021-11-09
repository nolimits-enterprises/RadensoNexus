package com.noLimits.TheiaNexus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class DS1KaFreqVoice extends DS1ServiceActionACtivity {

    TextView colorText;
    Button colorButton;
    SeekBar sb;
    Switch sw;

    boolean disableCheck = false;

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Ka Frequency Voice");

        setContentView(R.layout.activity_ds1binary);
        sw = (Switch)findViewById(R.id.sw_binary);

        sw.setText("Ka Frequency Voice");

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!disableCheck)
                    mDS1Service.setKaVoiceMode(isChecked);
            }
        });

    }
    @Override
    void onGotResult()
    {
        DS1KaFreqVoice.this.runOnUiThread(new Runnable() {

            public void run() {
                if (mDS1Service == null)
                    return;

                disableCheck = true;

                sw.setChecked(mDS1Service.getmSetting().Ka_voice_mode);

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

