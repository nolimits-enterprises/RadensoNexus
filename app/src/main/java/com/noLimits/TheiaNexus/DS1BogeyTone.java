package com.noLimits.TheiaNexus;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.io.FileDescriptor;

public class DS1BogeyTone extends DS1ServiceActionACtivity {

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
        mDS1Service.setBogeyTone(t - 1);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            if (!checked)
                return;



            switch (view.getId()) {
                default:
                    setTone(1);
                    break;
                case R.id.radio_tone_2:
                    setTone(2);
                    break;
                case R.id.radio_tone_3:
                    setTone(3);
                    break;
                case R.id.radio_tone_4:
                    setTone(4);
                    break;
                case R.id.radio_tone_5:
                    setTone(5);
                    break;

            }

        }
    };

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Bogey Tone");

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


        rb_6.setVisibility(View.INVISIBLE);
        rb_7.setVisibility(View.INVISIBLE);
        rb_8.setVisibility(View.INVISIBLE);
        rb_9.setVisibility(View.INVISIBLE);
        rb_10.setVisibility(View.INVISIBLE);

        rb_1.setOnClickListener(clickListener);
        rb_2.setOnClickListener(clickListener);
        rb_3.setOnClickListener(clickListener);
        rb_4.setOnClickListener(clickListener);
        rb_5.setOnClickListener(clickListener);

    }


    int getTone()
    {
        return mDS1Service.getmSetting().bogey_tone;
    }

    @Override
    void onGotResult()
    {
        if (mDS1Service == null)
            return;

        int tone = getTone();

        switch(tone)
        {
            case 0:
                rb_1.setChecked(true);
                break;
            case 1:
                rb_2.setChecked(true);
                break;
            case 2:
                rb_3.setChecked(true);
                break;
            case 3:
                rb_4.setChecked(true);
                break;
            case 4:
                rb_5.setChecked(true);
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
