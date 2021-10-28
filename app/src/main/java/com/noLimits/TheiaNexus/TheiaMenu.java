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

public class TheiaMenu extends TheiaServiceActionActivity {
    private ListView mActionList;
    String devAddr;
    int index = 0;

    ArrayList<String> arrayMenu = new ArrayList<String>();
    ArrayList intentMenu = new ArrayList<TheiaMenu.activityConfiguration>();
    ArrayList headerList = new ArrayList<Integer>();


    private final BroadcastReceiver mTheiaReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action == TheiaService.THEIA_CONNECTED)
            {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
            }
            else if (action == TheiaService.THEIA_DISCONNECTED)
            {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
        }
    };

    class activityConfiguration
    {
        public String type;
        public String title;
        public String name;

        public boolean isGeneric;
        public Class<?> cls;

        activityConfiguration(Class<?> c)
        {
            isGeneric = false;
            cls = c;
        }

        activityConfiguration(String argTitle, String argName, TheiaServiceGenericActionActivity.SettingType argType)
        {
            cls = TheiaServiceGenericActionActivity.class;
            title = argTitle;
            name = argName;
            isGeneric = true;


            TheiaServiceGenericActionActivity.SettingType[] settingValues = TheiaServiceGenericActionActivity.SettingType.values();
            for (int i = 0; i < settingValues.length; i++)
            {
                if (argType == settingValues[i])
                {
                    type = String.valueOf(i);
                }
            }
        }

        activityConfiguration(Class<?> c, String argTitle, String argName, TheiaServiceGenericActionActivity.SettingType argType)
        {
            cls = c;
            title = argTitle;
            name = argName;
            isGeneric = true;
            type = "n/a";

        }

    }

    class MenuAdapter extends ArrayAdapter<String>
    {

        public MenuAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        private Set headerSet = new TreeSet<Integer>();

        public void addHeader(int n)
        {
            headerSet.add(n);
        }


        @Override
        public View getView(int p, View v, ViewGroup g)
        {

            TextView item = (TextView) super.getView(p,v,g);

            if (!(headerSet.contains(p))) {
                item.setTypeface(item.getTypeface(), Typeface.NORMAL);
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);

                Log.i("GET_VIEW", "gotView " + String.valueOf(p) + " - nobold");
                return item;

            }

            Log.i("GET_VIEW", "gotBold " + String.valueOf(p) + " - bold -" + item.getText().toString());


            item.setTypeface(item.getTypeface(), Typeface.BOLD);
            item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            //item.setBackground(null);

            return item;

        }
    }


    protected void addMenuItems()
    {
        arrayMenu.add("Tones");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);
        arrayMenu.add("    K Band");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / K Band", "tone/radar_k", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    Ka Band");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / Ka Band", "tone/radar_ka", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    X Band");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / X Band", "tone/radar_x", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    Laser");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / Laser", "tone/laser", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    Unregistered");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / Unregistered", "tone/unregistered", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    BSM");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / BSM", "tone/bsm", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));
        arrayMenu.add("    Opener");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Tone / Opener", "tone/opener", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TONE));

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        devAddr = getIntent().getStringExtra("DEV_ADDR");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        mConnectedText = (TextView)findViewById(R.id.textMenuConnect);
        mActionList = (ListView)findViewById(R.id.Menu_View);

        if (mTheiaService != null) {
            if (mTheiaService.isConnected()) {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
            } else {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
        }
        else
        {
            mConnectedText.setText("Disconnected");
            mConnectedText.setTextColor(Color.RED);
        }




        SpannableString bold = new SpannableString("Testing");
        bold.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        addMenuItems();

        ArrayAdapter adapter = new TheiaMenu.MenuAdapter(this, R.layout.menu_list, arrayMenu);

        for (int i = 0; i < headerList.size(); i++)
        {
            ((TheiaMenu.MenuAdapter)adapter).addHeader((Integer)(headerList.get(i)));
        }


        mActionList.setAdapter(adapter);
        mActionList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TheiaMenu.activityConfiguration conf = (TheiaMenu.activityConfiguration)intentMenu.get(position);
                Class<?> nextActivity = conf.cls;

                if (nextActivity == null)
                    return;

                Intent intent = new Intent(TheiaMenu.this, nextActivity);

                if (conf.isGeneric)
                {
                    intent.putExtra("TYPE", conf.type);
                    intent.putExtra("TITLE", conf.title);
                    intent.putExtra("NAME", conf.name);
                }


                //based on item add info to intent
                intent.putExtra("DEV_ADDR", devAddr);
                startActivity(intent);

            }

        });
        //mActionList.
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mTheiaService != null) {
            if (mTheiaService.isConnected()) {
                mConnectedText.setText("Connected");
                mConnectedText.setTextColor(Color.GREEN);
            } else {
                mConnectedText.setText("Disconnected");
                mConnectedText.setTextColor(Color.RED);
            }
        }
        else
        {
            mConnectedText.setText("Disconnected");
            mConnectedText.setTextColor(Color.RED);
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction(TheiaService.THEIA_CONNECTED);
        filter.addAction(TheiaService.THEIA_DISCONNECTED);
        registerReceiver(mTheiaReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTheiaReceiver);
    }
}
