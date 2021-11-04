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

public class DS1ActionACtivity extends DS1Menu {

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

        arrayMenu.add("    User Preferences");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuUserPref.class));

        arrayMenu.add("    Band");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuBand.class));

        arrayMenu.add("    Display");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuDisplay.class));

        arrayMenu.add("   Tone");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuTone.class));


        arrayMenu.add("   GPS");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuGPS.class));

        arrayMenu.add("   Unit");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuUnit.class));


        arrayMenu.add("    Update DS1");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1UpdateTest.class));

        arrayMenu.add("    Disconnect");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Disconnect.class));

        arrayMenu.add("");                          // 3
        intentMenu.add(new DS1Menu.activityConfiguration(null));
    }

}
