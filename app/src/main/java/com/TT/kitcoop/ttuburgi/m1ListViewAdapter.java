package com.TT.kitcoop.ttuburgi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kitcoop on 2017-04-20.
 */

public class m1ListViewAdapter extends BaseAdapter {
    private ArrayList<Select_Item> listViewItemList;
    private Context context;
    private LayoutInflater layoutInflater;

    public m1ListViewAdapter(Context context, ArrayList<Select_Item> listViewItemList) {
        this.listViewItemList = listViewItemList;
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.m1_lang_listitem, parent, false);
        }

        ImageView iconImageView = (ImageView)convertView.findViewById(R.id.m1lang_iv);
        TextView titleTextView = (TextView)convertView.findViewById(R.id.m1lang_tv1);
        TextView txtTextView = (TextView)convertView.findViewById(R.id.m1lang_tv2);

        Select_Item selectItem = listViewItemList.get(position);

        iconImageView.setImageResource(selectItem.getIcon());
        titleTextView.setText(selectItem.getTitle());
        txtTextView.setText(selectItem.getTxt());

        return convertView;
    }
}
