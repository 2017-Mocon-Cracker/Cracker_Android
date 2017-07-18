package com.example.choi.cracker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choi.cracker.Adapter.PagerAdapter;
import com.example.choi.cracker.CustomIndicator;
import com.example.choi.cracker.Data.User;
import com.example.choi.cracker.R;
import com.google.gson.Gson;

/**
 * Created by choi on 2017. 7. 13..
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    CustomIndicator customIndicator;
    TextView skip;
    ImageView next;
    Boolean isLogin = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.guide_pager);
        skip = (TextView) findViewById(R.id.tutorial_skip);
        next = (ImageView) findViewById(R.id.tutorial_next);
        customIndicator = (CustomIndicator) findViewById(R.id.custom_indicator);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        customIndicator.creatIndicator(adapter.getCount(),15);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                customIndicator.select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skip.setOnClickListener(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewPager.getCurrentItem();
                if (a!=2){
                    viewPager.setCurrentItem(++a);
                }else
                    finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tutorial_skip:
                finish();
                break;
        }
    }
    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLogin",isLogin);
        editor.commit();
    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        isLogin = pref.getBoolean("isLogin",false);
    }
}