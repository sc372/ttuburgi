package com.TT.kitcoop.ttuburgi;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yunjeong on 2017-04-09.
 */

public class FragmentMenu2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activitymenu2, container, false);

        final ArrayList<HashMap<String, String>> datas = new ArrayList<>();

        HashMap<String, String> data1 = new HashMap<>();
        HashMap<String, String> data2 = new HashMap<>();
        HashMap<String, String> data3 = new HashMap<>();

        data1.put("c1", "JAVA");
        data1.put("c2", "설명설명설명");

        data2.put("c1", "JAVA Script");
        data2.put("c2", "설명설명설명");

        data3.put("c1", "Spring");
        data3.put("c2", "설명설명설명");


        datas.add(data1);
        datas.add(data2);
        datas.add(data3);

        final SimpleAdapter adapter2 = new SimpleAdapter(getActivity().getApplicationContext(), datas, android.R.layout.simple_list_item_2,
                new String[]{"c1", "c2"},
                new int[]{ android.R.id.text1, android.R.id.text2}
        ){
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);
                TextView tv2 = (TextView)v.findViewById(android.R.id.text2);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(24);

                tv2.setTextColor(Color.GRAY);
                tv2.setTextSize(16);

                return v;
            }
        };

        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.DKGRAY));
        listView.setDividerHeight(1);

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1 , datas){
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(24);

                return v;
            }
        };*/

        listView.setAdapter(adapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = (HashMap)datas.get(position);

                Intent intent = new Intent(getActivity().getApplicationContext(), Study_list.class);
                intent.putExtra("title", data.get("c1"));

                startActivity(intent);

                Toast.makeText(getActivity().getApplicationContext(), data.get("c1"), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
