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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TheiaSettingColor  extends TheiaServiceActionActivity {

    String settingName = null;
    String title = null;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_color);

        final Intent intent = getIntent();
        if (intent.hasExtra("NAME"))
            settingName = intent.getStringExtra("NAME");

        if (intent.hasExtra("TITLE"))
            title       = intent.getStringExtra("TITLE");

        if (title == null)
            setTitle("Color Select");
        else
            setTitle(title);


    }


    public void onColorClick(View v) {
        int color = -1;
        switch (v.getId()) {
            case R.id.buttonGreen:
                color = 3;
                break;
            case R.id.buttonYellow:
                color = 2;
                break;
            case R.id.buttonPurple:
                color = 5;
                break;
            case R.id.buttonRed:
                color = 1;
                break;
            case R.id.buttonTeal:
                color = 4;
                break;
            case R.id.buttonWhite:
                color = 0;
                break;
        }

        if (color >= 0) {
            if (settingName == null)
                mTheiaService.writeColor(color);
            else
                mTheiaService.writeGeneric(settingName, color);
        }
    }

}
