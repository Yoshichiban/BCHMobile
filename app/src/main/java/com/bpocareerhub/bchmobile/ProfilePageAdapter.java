package com.bpocareerhub.bchmobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by BCH_OJT on 7/4/2016.
 * https://www.javacodegeeks.com/2013/04/android-tutorial-using-the-viewpager.html
 */
class ProfilePageAdapter extends FragmentStatePagerAdapter {
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

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

}

