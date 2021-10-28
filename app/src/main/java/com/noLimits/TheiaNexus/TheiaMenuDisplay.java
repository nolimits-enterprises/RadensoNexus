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

public class TheiaMenuDisplay extends TheiaMenu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("Display");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        
        arrayMenu.add("    Brightness");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Brightness", "display/brightness", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BRIGHTNESS));
        arrayMenu.add("    Mode");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Mode", "display/mode", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_DISPLAY_MODE));
        arrayMenu.add("    Color");          // 6
        intentMenu.add(new TheiaMenu.activityConfiguration(TheiaSettingColor.class));
        arrayMenu.add("    Radar Color");          // 6
        intentMenu.add(new TheiaMenu.activityConfiguration(TheiaSettingColor.class, "Display / Radar Color", "display/alert/radar", null));
        arrayMenu.add("    Laser Color");          // 6
        intentMenu.add(new TheiaMenu.activityConfiguration(TheiaSettingColor.class, "Display / Laser Color", "display/alert/laser", null));
        arrayMenu.add("    Upside Down");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Upside Down", "display/upside_down", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Speed");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Speed", "display/sub/speed", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Compass");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Compass", "display/sub/compass", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Altitude");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Display / Altitude", "display/sub/altitude", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
    }
}
