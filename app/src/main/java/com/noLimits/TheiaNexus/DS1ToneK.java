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

public class DS1ToneK extends DS1Tone{

    @Override
    public void onCreate (Bundle b) {
        super.onCreate(b);
        setTitle("K Tone");
    }

    @Override
    int getTone()
    {
        return mDS1Service.getmSetting().K_tone+1;
    }

    @Override
    void setTone(int t)
    {
        if(mDS1Service != null)
        {
            mDS1Service.setKTone(t);
        }
    }


}
