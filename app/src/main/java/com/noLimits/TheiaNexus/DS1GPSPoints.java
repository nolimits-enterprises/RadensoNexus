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
import com.nolimits.ds1library.DS1Service;

public class DS1GPSPoints extends DS1ServiceActionACtivity {
    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_ds1gpspoints);
        setTitle("GPS Points");
    }

    public void onHomeClicked(View view) {
        mDS1Service.setGPSHome();
    }

    public void onDelUserClicked(View v)
    {
        mDS1Service.delGPSUser();
    }

    public void onLockoutsClicked(View v)
    {
        mDS1Service.delGPSLockout();
    }
}