package com.example.aircraftinquiry.BasAdapterRepeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aircraftinquiry.AllBean.Row;
import com.example.aircraftinquiry.R;

import java.util.List;

public class TicketsBas extends BaseAdapter {

    List<Row> list;
    Context mcontext;

    public TicketsBas(List<Row> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(mcontext).inflate(R.layout.ticketlistitem,null,false);

        TextView time1 = view.findViewById(R.id.time1);
        TextView time2 = view.findViewById(R.id.time2);
        TextView content = view.findViewById(R.id.content);
        TextView money = view.findViewById(R.id.money);

        time1.setText(list.get(i).getCreateTime());
        time2.setText(list.get(i).getUpdateTime());
        content.setText(list.get(i).getEnd());
        money.setText(String.valueOf(list.get(i).getPrice()*100));

        return view;
    }
}
