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
import android.content.BroadcastReceiver;
import android.content.Context;
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

public class TheiaActionActivity extends TheiaMenu {

        @Override
        protected void addMenuItems()
        {
            arrayMenu.add("Status");                    // 0
            intentMenu.add(new activityConfiguration(null));
            headerList.add(arrayMenu.size() - 1);

            arrayMenu.add("    Current Alerts");        // 1
            intentMenu.add(new activityConfiguration(TheiaStatusAlerts.class));
            arrayMenu.add("    GPS");                   // 2
            intentMenu.add(new activityConfiguration(null));
            arrayMenu.add("");                          // 3
            intentMenu.add(new activityConfiguration(null));

            arrayMenu.add("Settings");                  // 4
            intentMenu.add(new activityConfiguration(null));
            headerList.add(arrayMenu.size() - 1);
            arrayMenu.add("    Volume");           // 5
            intentMenu.add(new activityConfiguration("Volume", "volume/volume", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_PERCENT));
            arrayMenu.add("    Wifi Update");           // 5
            intentMenu.add(new activityConfiguration(TheiaSettingActivity.class));


            arrayMenu.add("    User");
            intentMenu.add(new activityConfiguration(TheiaMenuUser.class));

            arrayMenu.add("    Alerts");
            intentMenu.add(new activityConfiguration(TheiaMenuAlert.class));

            arrayMenu.add("    Display");
            intentMenu.add(new activityConfiguration(TheiaMenuDisplay.class));

            arrayMenu.add("    Tone");           // 5
            intentMenu.add(new activityConfiguration(TheiaMenu.class));

            arrayMenu.add("    GPS");                   // 2
            intentMenu.add(new activityConfiguration(TheiaMenuGPS.class));

            arrayMenu.add("    Unit");                   // 2
            intentMenu.add(new activityConfiguration(TheiaMenuUnit.class));

            arrayMenu.add("");                          // 7
            intentMenu.add(new activityConfiguration(null));

            arrayMenu.add("Commands");                  // 8
            intentMenu.add(new activityConfiguration(null));
            headerList.add(arrayMenu.size() - 1);
            arrayMenu.add("    Trigger Alert");         // 9
            intentMenu.add(new activityConfiguration(TheiaAlertAction.class));
            arrayMenu.add("");                          // 10
            intentMenu.add(new activityConfiguration(null));

            arrayMenu.add("Connection");                // 11
            intentMenu.add(new activityConfiguration(null));
            headerList.add(arrayMenu.size() - 1);
            arrayMenu.add("    Select Device");         // 12
            intentMenu.add(new activityConfiguration(TheiaScanActivity.class));
            arrayMenu.add("    Disconnect");         // 12
            intentMenu.add(new activityConfiguration(TheiaDisconnect.class));
        }

}
