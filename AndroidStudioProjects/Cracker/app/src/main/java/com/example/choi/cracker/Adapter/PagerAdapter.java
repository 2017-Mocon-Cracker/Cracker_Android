package com.example.choi.cracker.Adapter;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choi.cracker.TutorialFragment.FragmentThr;
import com.example.choi.cracker.TutorialFragment.FragmentTwo;
import com.example.choi.cracker.TutorialFragment.FragmentOne;

/**
 * Created by choi on 2017. 7. 13..
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    public PagerAdapter(android.support.v4.app.FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    public PagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThr();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
