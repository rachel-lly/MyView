package com.example.myview.BottomDragLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myview.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<String> mlist;
    private Context mContext;

    public ListAdapter(List<String> mlist, Context mContext) {
        this.mlist = mlist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CityViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            holder = new CityViewHolder((TextView) convertView);
            convertView.setTag(holder);
        } else {
            holder = (CityViewHolder) convertView.getTag();
        }
        final String name = mlist.get(position);
        holder.citynameTv.setText(name);

        return convertView;
    }


    static class CityViewHolder {
      TextView citynameTv;

        public CityViewHolder(TextView citynameTv) {
            this.citynameTv = citynameTv;
        }
    }
}