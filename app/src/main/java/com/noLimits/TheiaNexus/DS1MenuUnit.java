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

public class DS1MenuUnit extends DS1Menu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("Unit");                  // 4
        intentMenu.add(new DS1Menu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    Region");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Region.class));
        arrayMenu.add("    Time");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Time.class));
        arrayMenu.add("    Unit");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Unit.class));
        arrayMenu.add("    Auto Power Off");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1Power.class));
        arrayMenu.add("    Auto Mute");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1AutoMute.class));
    }
}