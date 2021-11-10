package com.noLimits.TheiaNexus;

public class DS1MenuSettings extends DS1Menu{
    @Override
    protected void addMenuItems()
    {
        arrayMenu.add("Settings");                  // 4
        intentMenu.add(new DS1Menu.activityConfiguration(null));
        headerList.add(arrayMenu.size() - 1);

        arrayMenu.add("    User Preferences");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuUserPref.class));

        arrayMenu.add("    Band");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuBand.class));

        arrayMenu.add("    Display");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuDisplay.class));

        arrayMenu.add("   Tone");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuTone.class));


        arrayMenu.add("   GPS");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuGPS.class));

        arrayMenu.add("   Unit");                   // 2
        intentMenu.add(new DS1Menu.activityConfiguration(DS1MenuUnit.class));

    }
}