package com.example.choi.homework.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choi.homework.R;

/**
 * Created by choi on 2017. 3. 30..
 */

public class Optionfragment extends Fragment {
    public Optionfragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_option, container, false);

        return view;
    }

}
