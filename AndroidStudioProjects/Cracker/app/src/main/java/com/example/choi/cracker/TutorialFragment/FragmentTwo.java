package com.example.choi.cracker.TutorialFragment;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choi.cracker.R;

/**
 * Created by choi on 2017. 7. 13..
 */

public class FragmentTwo extends Fragment {
    public FragmentTwo() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_tutorial, container, false);
        ImageView img = (ImageView)layout.findViewById(R.id.tutorial_img);
        img.setImageDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_second)));
        return layout;
    }
}
