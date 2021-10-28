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

public class TheiaMenuGPS extends TheiaMenu {
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("GPS");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    Enabled");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Enabled", "gps/gps", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));

        arrayMenu.add("    Signal Announce");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Sig Announce", "gps/sig_ann", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));

        arrayMenu.add("    Red Light Cam");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / RLC", "gps/rlc", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));

        arrayMenu.add("    Speed Cam");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Speed Cam", "gps/camera", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));

        arrayMenu.add("    Alert Distance");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Alert Distance", "gps/dist", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_GPS_ALERT_DIST));

        arrayMenu.add("    Lockout Distance");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Lockout Distance", "gps/lockout_dist", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_GPS_ALERT_DIST));

        arrayMenu.add("    Lockout Tolerance");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / Lockout Tolerance", "gps/lockout_tolerance", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_TOLERANCE));

        arrayMenu.add("    RLC Auto Mute");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("GPS / RLC Auto Mute", "gps/auto_mute", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_GPS_RLC_AUTO_MUTE));


    }
}
