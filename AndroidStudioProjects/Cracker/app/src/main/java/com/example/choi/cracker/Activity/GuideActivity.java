package com.example.choi.cracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.choi.cracker.Adapter.Pager_Adapter;
import com.example.choi.cracker.R;

import org.w3c.dom.Text;

/**
 * Created by choi on 2017. 7. 13..
 */

public class GuideActivity extends AppCompatActivity {
    FloatingActionButton next;
    TextView next_text;
    int tab_num=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.guide_pager);
        next = (FloatingActionButton) findViewById(R.id.tutorial_next_btn);
        next_text = (TextView) findViewById(R.id.next_text);

        Pager_Adapter adapter = new Pager_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        next_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab_num = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++tab_num);
                if(tab_num==3) {
                    next.setVisibility(View.GONE);
                    next_text.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}