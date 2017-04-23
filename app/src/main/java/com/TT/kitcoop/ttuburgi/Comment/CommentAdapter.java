package com.TT.kitcoop.ttuburgi.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.TT.kitcoop.ttuburgi.R;
import com.TT.kitcoop.ttuburgi.Select_Item;

import java.util.ArrayList;

/**
 * Created by yunjeong on 2017-04-22.
 */

public class CommentAdapter extends BaseAdapter {
    private ArrayList<Select_Item> listViewItemList;
    private Context context;
    private LayoutInflater layoutInflater;

    public CommentAdapter(Context context, ArrayList<Select_Item> listViewItemList) {
        this.listViewItemList = listViewItemList;
        this.context = context;
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
        context = parent.getContext();

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.comment_listitem, parent, false);
        }

        //화면에 표시될 View로부터 위젯에 대한 참조 획득
        TextView userTv = (TextView)convertView.findViewById(R.id.commTitle_tv);
        TextView txtTv = (TextView)convertView.findViewById(R.id.commTxt_tv);

        // Select_Item에서 position에 위치한 데이터 참조 획득
        Select_Item selectItem = listViewItemList.get(position);

        //아이템 내 각 위젯에 데이터 반영
        userTv.setText(selectItem.getTitle());
        txtTv.setText(selectItem.getTxt());

        return convertView;
    }
}
