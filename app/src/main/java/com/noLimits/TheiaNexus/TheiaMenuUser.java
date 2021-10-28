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

public class TheiaMenuUser extends TheiaMenu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("User");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    Menu Sounds");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Menu Sounds", "user/menu_beeps", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Voice");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Voice", "user/menu_voice", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Gun Announce");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Gun Announce", "user/gun_announce", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_USER_GUN_ANNOUNCE));
        arrayMenu.add("    Rx Sensitivity");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Sensitivity", "user/sensitivity", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_PERCENT));
        arrayMenu.add("    Band Announce");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Band Announce", "user/band_announce", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Freq Display");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Freq Display", "user/freq", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Reverse Scroll");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Reverse Scroll", "user/reverse", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Cig Heartbeat");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Cig Heartbeat", "user/cig_heart", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Cig Alert");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("User / Cig Alert", "user/cig_alert", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));


    }
}
