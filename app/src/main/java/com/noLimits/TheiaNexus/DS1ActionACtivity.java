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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DS1ActionACtivity extends DS1Menu {

    @Override
    protected void onGotService() {

        if (mDS1Service == null)
            return;

        if (mDS1Service.lastUpdateFailed)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("It looks like the last update failed.  We highly recommend you do a RECOVERY update.  Go ahead to update page?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener);

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    mDS1Service.lastUpdateFailed = false;

                    Intent intent = new Intent(DS1ActionACtivity.this, DS1UpdateTest.class);
                    intent.putExtra("RECOVERY", "true");
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    mDS1Service.lastUpdateFailed = false;
                    //No button clicked
                    break;
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();

        if (mDS1Service != null)
        {
            if (mDS1Service.lastUpdateFailed)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("It looks like the last update failed.  We highly recommend you do a RECOVERY update.  Go ahead to update page?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener);

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    @Override
    protected void addMenuItems()
    {

        arrayMenu.add("Menu");                    // 0
        intentMenu.add(new DS1Menu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        /*
        arrayMenu.add("    Current Alerts");        // 1
        intentMenu.add(new DS1Menu.activityConfiguration(TheiaStatusAlerts.class));

         */


        arrayMenu.add("    Alerts");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1StatusAlerts.class));

        arrayMenu.add("    Volume");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Volume.class));

        arrayMenu.add("    Settings");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuSettings.class));




        arrayMenu.add("    Update DS1");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1UpdateTest.class));

        arrayMenu.add("    Disconnect");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Disconnect.class));

    }

}
