package com.kreativeco.sysbioscience;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kreativeco.sysbioscience.login.profile.Profile;

/**
 * Created by kreativeco on 01/02/16.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Section2();
            case 1:
                return new Section2();
            case 2:
                return new SalesFragment();
            case 3:
                return new License();
            case 4:
                return new Properties();
            case 5:
                return new Logout();
            case 6:
                return new Contact();
            case 7:
                return new Contact();
            case 8:
                return new Section9();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
