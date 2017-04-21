package com.TT.kitcoop.ttuburgi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

public class LevelSelect extends AppCompatActivity {
    private View levelSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        ArrayList<Select_Item> selectItems = new ArrayList<>();

        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        System.out.println("포지션 넘어오니?? :" + position);


        if (position == 0) {

            Select_Item item1 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA Level1", "설명설명설명");
            Select_Item item2 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA Level2", "설명설명설명");
            Select_Item item3 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA Level3", "설명설명설명");
            Select_Item item4 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA Level4", "설명설명설명");

            selectItems.add(item1);
            selectItems.add(item2);
            selectItems.add(item3);
            selectItems.add(item4);

        } else if (position == 1){

            Select_Item item1 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JS Level1", "설명설명설명");
            Select_Item item2 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JS Level2", "설명설명설명");
            Select_Item item3 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JS Level3", "설명설명설명");

            selectItems.add(item1);
            selectItems.add(item2);
            selectItems.add(item3);

        } else if (position == 2) {
            Select_Item item1 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"Spring Level1", "설명설명설명");
            Select_Item item2 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"Spring Level2", "설명설명설명");

            selectItems.add(item1);
            selectItems.add(item2);
        } else if (position == 3) {
            Select_Item item1 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JSP Level1", "설명설명설명");
            Select_Item item2 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JSP Level2", "설명설명설명");

            selectItems.add(item1);
            selectItems.add(item2);
        }



        final LevelAdapter adapter = new LevelAdapter(this, selectItems);
        final ListView listView = (ListView)findViewById(R.id.levelS_lv);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                levelSelect = view;

                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);
            }
        });


        findViewById(R.id.level_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent outintent = new Intent(LevelSelect.this, LangSelect.class);

                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                System.out.println("checkedItems : " + checkedItems);

                int checkN = checkedItems.size();
                System.out.println("몇개인지 출력되나? : " + checkN);

                int count = adapter.getCount();

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        System.out.println("레벨 :" + i + " 이 선택되었습니다.");
                        System.out.println("checkedItems.get(i) : " + checkedItems.get(i));
                        System.out.println("listView.isItemChecked(i) : " + listView.isItemChecked(i));

                    }
                }

                outintent.putExtra("checkNum", checkN);
                outintent.putExtra("pos", position);
                setResult(RESULT_OK, outintent);
                finish();
            }
        });
    }

}
