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

public class TheiaMenuAlert extends TheiaMenu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("Alerts");                  // 4
        intentMenu.add(new TheiaMenu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    Radar");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Alert / Radar", "alert/radar", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Laser");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Alert / Laser", "alert/laser", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));

        arrayMenu.add("    Unregistered");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Alert / Unregistered", "alert/unregistered", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    BSM");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Alert / BSM", "alert/bsm", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
        arrayMenu.add("    Opener");           // 5
        intentMenu.add(new TheiaMenu.activityConfiguration("Alert / Opener", "alert/opener", TheiaServiceGenericActionActivity.SettingType.SETTING_TYPE_BINARY));
    }
}

