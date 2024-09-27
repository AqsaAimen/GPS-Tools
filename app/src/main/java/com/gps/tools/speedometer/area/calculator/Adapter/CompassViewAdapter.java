package com.gps.tools.speedometer.area.calculator.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gps.tools.speedometer.area.calculator.Activities.Compass.ARFragment;
import com.gps.tools.speedometer.area.calculator.Activities.Compass.MilitaryFragment;
import com.gps.tools.speedometer.area.calculator.Activities.Compass.SimpleFragment;

public class CompassViewAdapter extends FragmentPagerAdapter {

    public Context myContext;
    int totalTabs;

    public CompassViewAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SimpleFragment simpleFragment = new SimpleFragment();
                return simpleFragment;
            case 1:
                MilitaryFragment militaryFragment  = new MilitaryFragment();
                return militaryFragment;
            case 2:
                 ARFragment arFragment  = new ARFragment();
                return arFragment;
            default:
                return getItem(position);
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
