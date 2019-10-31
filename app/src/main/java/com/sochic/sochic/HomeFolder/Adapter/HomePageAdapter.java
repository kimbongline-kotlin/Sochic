package com.sochic.sochic.HomeFolder.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.sochic.sochic.HomeFolder.Fragment.*;

public class HomePageAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    public Fragment mFragment;
    public HomePageAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Home_openFragment openFragment = new Home_openFragment();
                mFragment = openFragment;
                return mFragment;

            case 1:
                Home_newFragment newFragment = new Home_newFragment();
                mFragment = newFragment;
                return mFragment;

            case 2:
                Home_bestFragment bestFragment = new Home_bestFragment();
                mFragment = bestFragment;
                return mFragment;

            case 3:
                Home_followFragment followFragment = new Home_followFragment();
                mFragment = followFragment;
                return mFragment;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "OPEN";
            case 1 :
                return "NEW" ;
            case 2:
                return "BEST";
            case 3 :
                return "FOLLOW";

                default:
                    return "";
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}