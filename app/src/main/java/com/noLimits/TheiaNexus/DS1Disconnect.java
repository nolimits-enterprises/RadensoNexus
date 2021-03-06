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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.nolimits.ds1library.DS1Service;

import static com.noLimits.TheiaNexus.TheiaService.THEIA_DISCONNECTED;
import static com.nolimits.ds1library.DS1Service.DS1_DISCONNECTED;

public class DS1Disconnect extends DS1ServiceActionACtivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnect);

    }

    public void onClick(View v)
    {
        SharedPreferences p = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putBoolean(getString(R.string.key_autoconnect), false);
        editor.commit();

        mDS1Service.disconnect();
        sendBroadcast(new Intent(DS1_DISCONNECTED));
        finish();
    }
}

