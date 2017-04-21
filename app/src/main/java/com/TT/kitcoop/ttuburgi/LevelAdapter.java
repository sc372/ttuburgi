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
 * Created by Bitna on 2017-04-15.
 */

public class LevelAdapter extends BaseAdapter {
    private ArrayList<Select_Item> listViewItemList;
    private Context context;
    private LayoutInflater layoutInflater;


    public LevelAdapter(Context context, ArrayList<Select_Item> listViewItemList){
        this.context = context;
        this.listViewItemList = listViewItemList;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final int pos =position;
        context = parent.getContext();

        if(convertView == null) {
            //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.level_listitem, parent, false);
        }

        //화면에 표시될 View로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView)convertView.findViewById(R.id.select_imgView);
        TextView titleTextView = (TextView)convertView.findViewById(R.id.select_textView1);
        TextView txtTextView = (TextView)convertView.findViewById(R.id.select_textView2);


        // Select_Item에서 position에 위치한 데이터 참조 획득
        Select_Item selectItem = listViewItemList.get(pos);

        //아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageResource(selectItem.getIcon());
        titleTextView.setText(selectItem.getTitle());
        txtTextView.setText(selectItem.getTxt());


        //ImageView checkImg = (ImageView)convertView.findViewById(R.id.checkImageView);

        //CheckBox cb = (CheckBox)convertView.findViewById(R.id.checkBox1);


        return convertView;
    }
}