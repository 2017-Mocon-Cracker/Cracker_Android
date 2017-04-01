package com.example.choi.homework;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.choi.homework.adaptor.TabPagerAdaptor;

public class MainActivity extends AppCompatActivity {
    ViewPager viewpager;
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.click_friend).setTag("person"));
        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.click_chat).setTag("chat"));
        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.click_option).setTag("option"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("친구");

        TabPagerAdaptor pagerAdapter = new TabPagerAdaptor(getSupportFragmentManager(), tablayout.getTabCount());

        viewpager.setAdapter(pagerAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewpager.setCurrentItem(tab.getPosition());
                switch (tablayout.getSelectedTabPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("친구");
                        tab.setIcon(R.mipmap.click_friend);
                        break;
                    case 1:
                        getSupportActionBar().setTitle("채팅");
                        tab.setIcon(R.mipmap.click_chat);
                        break;
                    case 2:
                        getSupportActionBar().setTitle("설정");
                        tab.setIcon(R.mipmap.click_option);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                switch (tablayout.getSelectedTabPosition()) {
                    case 0:
                        tab.setIcon(R.mipmap.click_friend);
                        break;
                    case 1:
                        tab.setIcon(R.mipmap.click_chat);
                        break;
                    case 2:
                        tab.setIcon(R.mipmap.click_option);
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

