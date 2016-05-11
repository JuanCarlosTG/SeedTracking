package com.kreativeco.sysbioscience.farmers;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.kreativeco.sysbioscience.farmer.assigns.AssignsFragment;
import com.kreativeco.sysbioscience.farmer.properties.PropertyFragment;
/**
 * Created by omar on 5/8/16.
 */
public class HomeAdapterFarmer extends FragmentPagerAdapter{

    int mNumOfTabs;

    public HomeAdapterFarmer(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AssignsFragment();
            case 1:
                return new PropertyFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount(){return mNumOfTabs;
    }
}

