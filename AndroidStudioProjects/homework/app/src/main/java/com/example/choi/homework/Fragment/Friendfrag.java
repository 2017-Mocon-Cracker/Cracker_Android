package com.example.choi.homework.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.choi.homework.Data;
import com.example.choi.homework.R;
import com.example.choi.homework.adaptor.FriendAdaptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 3. 30..
 */

public class Friendfrag extends Fragment {



    private EditText friend_edit;
    private ListView friend_list;
    private FriendAdaptor listAdapter;
    private List<Data> items = new ArrayList<>();

    public Friendfrag(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        friend_edit = (EditText) view.findViewById(R.id.search);
        friend_list = (ListView) view.findViewById(R.id.friend_list);

        items.add(new Data("search"));
        items.add(new Data("myProfile"));
        items.add(new Data("최동근"));
        items.add(new Data("myFriend"));
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

        listAdapter = new FriendAdaptor(getContext(), items);
        friend_list.setAdapter(listAdapter);

        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }
}