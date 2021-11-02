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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nolimits.ds1library.DS1Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.ViewHolder>
{
    private static final String TAG = "ALERT_ADAPTER";
    List<AlertEntry> alertList = new ArrayList<AlertEntry>();

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textFreq;
        public TextView textType;
        public TextView textBand;
        public TextView textDir;
        public TextView textThreat;
        public ProgressBar progressStrength;

        public ViewHolder(View v)
        {
            super(v);

            textFreq = (TextView) v.findViewById(R.id.textFreq);
            textType = (TextView) v.findViewById(R.id.textType);
            textBand = (TextView) v.findViewById(R.id.textBand);
            textDir  = (TextView) v.findViewById(R.id.textDir);
            textThreat = (TextView) v.findViewById(R.id.textThreat);
            progressStrength = (ProgressBar) v.findViewById(R.id.progressStrength);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup vg, int vt)
    {
         View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.alerts_item, vg, false);
         return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int pos) {
        vh.textThreat.setText("Threat " + String.valueOf(pos));

        String band = "";
        switch (alertList.get(pos).band) {
            case 0:
                band = "X";
                break;
            case 1:
                band = "K";
                break;
            case 2:
                band = "Ka";
                break;
            case 3:
                band = "Pop";
                break;
            case 4:
                band = "MRCD";
                break;
            case 5:
                band = "MRCT";
                break;
            case 6:
                band = "GT3";
                break;
            case 7:
                band = "GT4";
                break;
        }


        String alert_class = "";
        vh.textBand.setText("");
        switch (alertList.get(pos).alert_class) {
            case 0:
                alert_class = "Radar";
                vh.textBand.setText(band);
                break;
            case 1:
                alert_class = "Laser";
                break;
            case 2:
                alert_class = "Speed Cam";
                break;
            case 3:
                alert_class = "Red Light Cam";
                break;
            case 4:
                alert_class = "User mark";
                break;
            case 5:
                alert_class = "Lockout";
                break;
        }

        vh.textType.setText(alert_class);
        vh.progressStrength.setMax(100);

        if (alertList.get(pos).alert_class == 0) {
            vh.progressStrength.setProgress((int) alertList.get(pos).intensity);
            vh.textFreq.setText("30.150 GHz");
        } else {
            vh.progressStrength.setProgress(100);
            vh.textFreq.setText("");
        }

    }

    @Override
    public int getItemCount()
    {
        if (alertList == null)
            return 0;
        return alertList.size();
    }

    public void setAlertList(List<AlertEntry> entries)
    {
        alertList.clear();
        for (int i = 0; i < entries.size(); i++)
        {
            alertList.add(entries.get(i));
        }
        //alertList = entries;
    }

    AlertEntry toEntry(DS1Service.RD_Alert a)
    {
        AlertEntry e = new AlertEntry();

        switch(a.alert_dir)
        {
            case ALERT_DIR_FRONT:
                e.dir = 0;
                break;
            case ALERT_DIR_SIDE:
                e.dir = 1;
                break;
            default:
                e.dir = 2;
                break;
        }

        e.intensity = (a.rssi + 2) * 10;

        if (a.type.compareTo("X") == 0)
        {
            e.alert_class = 0;
            e.band = 0;
        }
        else if (0 == a.type.compareTo("K"))
        {
            e.alert_class = 0;
            e.band = 1;

        }
        else if (0 == a.type.compareTo("Ka"))
        {
            e.alert_class = 0;
            e.band = 2;
        }
        else if (0 == a.type.compareTo("Laser"))
        {
            e.alert_class = 1;
        }
        else if (0 == a.type.compareTo("POP"))
        {
            e.alert_class = 0;
            e.band = 3;
        }
        else if (0 == a.type.compareTo("MRCD"))
        {
            e.alert_class = 0;
            e.band = 4;

        }
        else if (0 == a.type.compareTo("MRCT"))
        {
            e.alert_class = 0;
            e.band = 5;
        }
        else if (0 == a.type.compareTo("GT3"))
        {
            e.alert_class = 0;
            e.band = 6;
        }
        else if (0 == a.type.compareTo("GT4"))
        {
            e.alert_class = 0;
            e.band = 7;
        }

        return e;
    }

    public void setAlertList( Vector<DS1Service.RD_Alert> alerts)
    {

        alertList.clear();
        for (int i= 0 ; i < alerts.size(); i++)
        {
            if(alerts.get(i).detected)
                alertList.add(toEntry(alerts.get(i)));
        }
    }
}
