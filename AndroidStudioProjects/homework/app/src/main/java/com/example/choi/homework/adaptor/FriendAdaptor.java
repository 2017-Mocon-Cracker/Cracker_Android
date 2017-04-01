package com.example.choi.homework.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.choi.homework.Data;
import com.example.choi.homework.R;

import java.util.List;

/**
 * Created by parktaejun on 2017. 2. 10..
 */

public class FriendAdaptor extends BaseAdapter {

    private Context context;
    private List<Data> items;

    public FriendAdaptor(Context context, List<Data> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        if(position == 0){
            view = LayoutInflater.from(context).inflate(R.layout.item_search, null);
        }else if(position == 1 || position == 3){
            view = LayoutInflater.from(context).inflate(R.layout.item_textview, null);
            TextView text = (TextView)view.findViewById(R.id.item__menu);

            String _text = position == 1 ? "내 프로필" : "친구";
            text.setText(_text);
        }else if(position == 2){
            view = LayoutInflater.from(context).inflate(R.layout.item_profile, null);
            TextView profileText = (TextView) view.findViewById(R.id.profile_name);
            profileText.setText("최동근");
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_friend, null);
            TextView name = (TextView) view.findViewById(R.id.friend_name);
            name.setText(items.get(position).getText());
        }

        return view;
    }
}