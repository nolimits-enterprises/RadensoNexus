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
import com.nolimits.ds1library.DS1Service;

public class DS1ColorLaser extends DS1Color{

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);
        setTitle("Laser Color");
    }

    @Override
    void setColor(DS1Service.Colors c)
    {
        mDS1Service.setColor(c, DS1Service.SETTING_NUM_LASER_COLOR);
    }

    @Override
    DS1Service.Colors getColor()
    {
        return mDS1Service.getmSetting().laser_color;
    }

}
