package com.example.choi.homework.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choi.homework.Fragment.Chattingfragment;
import com.example.choi.homework.Fragment.Friendfrag;
import com.example.choi.homework.Fragment.Optionfragment;

/**
 * Created by choi on 2017. 3. 30..
 */

public class TabPagerAdaptor extends FragmentStatePagerAdapter {
    int tabCount;
    public TabPagerAdaptor(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                Friendfrag friendfragment = new Friendfrag();
                return friendfragment;
            case 1 :
                Chattingfragment chatfragment = new Chattingfragment();
                return  chatfragment;
            case 2 :
                Optionfragment optionfragment = new Optionfragment();
                return optionfragment;
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
