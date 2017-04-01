package com.example.choi.homework.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choi.homework.Data;
import com.example.choi.homework.R;

import java.util.List;

/**
 * Created by choi on 2017. 3. 30..
 */

public class ChattinglistAdaptor extends BaseAdapter {
    private Context context;
    private List<Data> items;

    public ChattinglistAdaptor(Context context, List<Data> items){
        this.context=context;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View view = LayoutInflater.from(context).inflate(R.layout.chat, null);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.container);

        if(position == 0){
            view = LayoutInflater.from(context).inflate(R.layout.item_search, null);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chatlist, null);
            TextView name = (TextView) view.findViewById(R.id.chat_list);
            name.setText(items.get(position).getText());
        }

        return view;
    }
}

