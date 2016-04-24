package com.kreativeco.sysbioscience.sales;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by kreativeco on 01/02/16.
 */
public class FragmentAdapterSales extends FragmentPagerAdapter {

    int mNumOfTabs;

    public FragmentAdapterSales(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SalesFragment();
            case 1:
                return new DataFragment();
            case 2:
                return new LicenseFragment();
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
