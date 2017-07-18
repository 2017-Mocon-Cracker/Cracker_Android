package com.example.choi.cracker.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.choi.cracker.Data.RideData;
import com.example.choi.cracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 7. 18..
 */

public class UsedListAdapter extends RecyclerView.Adapter<UsedListAdapter.ViewHolder> {
    List<RideData> items = new ArrayList<>();
    Context context;
    int itemLayout;

    public UsedListAdapter(List<RideData> items, Context context, int itemLayout) {
        this.items = items;
        this.context = context;
        this.itemLayout = itemLayout;
    }

    @Override
    public UsedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsedListAdapter.ViewHolder holder, int position) {
        if(position%2==0){
            holder.whereRide.setText("[버스]" + items.get(position).getWhereRide() + "번 승차");
        }else{
            holder.whereRide.setText("[버스]" + items.get(position).getWhereRide() + "번 하차");
        }
        holder.usedMoney.setText("720\\");
        holder.money.setText("잔액" + items.get(position).getMoney() + "원");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView money,usedMoney,whereRide;

        public ViewHolder(View itemView) {
            super(itemView);
            money = (TextView) itemView.findViewById(R.id.money);
            usedMoney = (TextView) itemView.findViewById(R.id.used_money);
            whereRide = (TextView) itemView.findViewById(R.id.ride);
        }
    }
}
