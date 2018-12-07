package com.dubhe.broken.dubheweather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dubhe.broken.dubheweather.R;

import java.util.List;

/**
 * 作者：DubheBroken
 * 时间：2018/12/7 13:37:20
 * 邮箱：z1574507001@gmail.com
 * 说明：
 */
public class CityListAdapter extends BaseAdapter {

    private Context context;
    private List<String> list_citys;

    public CityListAdapter(Context context, List<String> list_citys) {
        this.context = context;
        this.list_citys = list_citys;
    }

    @Override
    public int getCount() {
        return list_citys.size();
    }

    @Override
    public Object getItem(int position) {
        return list_citys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.city_item_layout, null);
            viewHolder.textCityItem = convertView.findViewById(R.id.text_city_item);
            viewHolder.textCityItem.setText(list_citys.get(position));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        TextView textCityItem;
        LinearLayout linearCityItem;
    }
}
