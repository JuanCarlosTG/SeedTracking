package com.kreativeco.sysbioscience.farmer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.kreativeco.sysbioscience.farmer.datafarmer.DataFragment;
import com.kreativeco.sysbioscience.farmer.assigns.AssignsFragment;
import com.kreativeco.sysbioscience.farmer.properties.PropertyFragment;
import com.kreativeco.sysbioscience.farmer.purchases.PurchasesFragment;


/**
 * Created by kreativeco on 01/02/16.
 */
public class FragmentAdapterFarmer extends FragmentPagerAdapter {

    int mNumOfTabs;

    public FragmentAdapterFarmer(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DataFragment();
            case 1:
                return new PurchasesFragment();
            case 2:
                return new AssignsFragment();
            case 3:
                return new PropertyFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
