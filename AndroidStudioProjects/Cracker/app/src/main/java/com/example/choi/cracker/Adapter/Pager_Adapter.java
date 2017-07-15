package com.example.choi.cracker.Adapter;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choi.cracker.Tutorial_Fragment.Fragment_Five;
import com.example.choi.cracker.Tutorial_Fragment.Fragment_Four;
import com.example.choi.cracker.Tutorial_Fragment.Fragment_Thr;
import com.example.choi.cracker.Tutorial_Fragment.Fragment_Two;
import com.example.choi.cracker.Tutorial_Fragment.Fragment_One;

/**
 * Created by choi on 2017. 7. 13..
 */

public class Pager_Adapter extends FragmentStatePagerAdapter {
    int tabCount;
    public Pager_Adapter(android.support.v4.app.FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    public Pager_Adapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_One();
            case 1:
                return new Fragment_Two();
            case 2:
                return new Fragment_Thr();
            case 3:
                return new Fragment_Four();
            case 4:
                return new Fragment_Five();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
