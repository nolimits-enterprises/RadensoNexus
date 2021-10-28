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

public class TheiaMenuUnit extends TheiaMenu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("Unit");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    Unit Type");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / Unit", "unit/unit", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_UNIT));
        arrayMenu.add("    Language");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / Language", "unit/language", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_LANGUAGE));
        arrayMenu.add("    24hr Time");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / 24hr Time", "unit/time24", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Auto Mute");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / Auto Mute", "unit/auto_mute", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Auto Mute Start");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / Auto Mute Start", "unit/auto_mute", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_AUTO_MUTE_START));
        arrayMenu.add("    Auto Mute Volume");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Unit / Auto Mute Vol", "unit/auto_mute_vol", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_PERCENT));

    }
}
