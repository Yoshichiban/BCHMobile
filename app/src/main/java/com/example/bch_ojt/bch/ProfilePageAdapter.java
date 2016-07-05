package com.example.bch_ojt.bch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by BCH_OJT on 7/4/2016.
 * https://www.javacodegeeks.com/2013/04/android-tutorial-using-the-viewpager.html
 */
class ProfilePageAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;

    public ProfilePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }


    @Override
    public int getCount() {
        return this.fragments.size();
    }
}

