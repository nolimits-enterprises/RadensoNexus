package com.noLimits.TheiaNexus;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.nolimits.ds1library.DS1Service;

public class DS1AlertPriority extends DS1ServiceActionACtivity {

    RadioButton rb_band;
    RadioButton rb_signal;



    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setTitle("Alert Priority");

        setContentView(R.layout.activity_ds1alertpriority);

        rb_band = (RadioButton)findViewById(R.id.rad_alertpri_band);
        rb_signal = (RadioButton)findViewById(R.id.rad_alertpri_signal);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (!checked)
            return;

        switch(view.getId()) {
            case R.id.rad_alertpri_band:
                mDS1Service.setAlertPrioritySignal(false);
                break;
            case R.id.rad_alertpri_signal:
                mDS1Service.setAlertPrioritySignal(true);
                break;
        }
    }

    @Override
    void onGotResult()
    {
        DS1AlertPriority.this.runOnUiThread(new Runnable(){

            public void run() {
                if (mDS1Service == null)
                    return;

                boolean pri = mDS1Service.getmSetting().signal_priority;

                if (pri)
                    rb_signal.setChecked(true);
                else
                    rb_band.setChecked(true);

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
