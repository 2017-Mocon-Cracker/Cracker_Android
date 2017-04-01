package com.example.choi.homework.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.choi.homework.Data;
import com.example.choi.homework.R;
import com.example.choi.homework.adaptor.ChattinglistAdaptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 3. 30..
 */

public class Chattingfragment extends Fragment {
    public Chattingfragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         EditText search;
         ListView chat_list;
         ChattinglistAdaptor listAdapter;
         List<Data> items = new ArrayList<>();





        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        search = (EditText) view.findViewById(R.id.search);
        chat_list = (ListView) view.findViewById(R.id.chat_list);

        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));
        items.add(new Data("박태준"));
        items.add(new Data("윤영채"));

        listAdapter = new ChattinglistAdaptor(getContext(), items);
        chat_list.setAdapter(listAdapter);


        return view;
    }
}
