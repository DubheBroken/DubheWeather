package com.dubhe.broken.dubheweather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dubhe.broken.dubheweather.R;
import com.dubhe.broken.dubheweather.entity.HotCity;

import java.util.List;

/**
 * Created by Developer on 2017/7/3.
 */

public class HotCityAdapter extends BaseAdapter {

    private Context context;
    private List<HotCity> list;

    public HotCityAdapter(Context context, List<HotCity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_layout, parent, false);
    }

    static class ViewHolder {
        View view;
        TextView textCityItem;
        LinearLayout linearCityItem;

        ViewHolder(View view) {
            this.view = view;
            this.textCityItem = view.findViewById(R.id.text_city_item);
            this.linearCityItem = view.findViewById(R.id.linear_city_item);
        }
    }
}


