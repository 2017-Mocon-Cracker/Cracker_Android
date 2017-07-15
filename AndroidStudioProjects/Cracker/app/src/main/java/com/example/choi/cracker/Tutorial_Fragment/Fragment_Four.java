package com.example.choi.cracker.Tutorial_Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choi.cracker.R;

/**
 * Created by choi on 2017. 7. 13..
 */

public class Fragment_Four extends Fragment {
    public Fragment_Four() {
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
        TextView textView = (TextView)layout.findViewById(R.id.tutotial_num);
        textView.setText("4");
        return layout;
    }
}
