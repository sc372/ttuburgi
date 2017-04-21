package com.TT.kitcoop.ttuburgi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LangSelect extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private String java;
    private String javascript;
    private String spring;
    private String jsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_select);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        ArrayList<Select_Item> selectItems = new ArrayList<>();

        Select_Item item1 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA", "선택하지 않음");
        Select_Item item2 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JAVA Script", "레벨3, 레벨2 선택");
        Select_Item item3 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"Spring", "레벨1 선택");
        Select_Item item4 = new Select_Item(R.drawable.common_google_signin_btn_icon_dark,"JSP", "선택하지 않음");

        selectItems.add(item1);
        selectItems.add(item2);
        selectItems.add(item3);
        selectItems.add(item4);


        final LangAdapter adapter = new LangAdapter(this, selectItems);
        final ListView listView = (ListView)findViewById(R.id.langS_lv);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);

            }
        });


        Button btn = (Button) findViewById(R.id.langS_btn1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String userId = null;
                if (user != null) {
                    userId = user.getUid();
                }

                DatabaseReference databaseReference = null;
                if (userId != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("language");
                }

                int checkN = checkedItems.size();
                int count = adapter.getCount();

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        System.out.println("레벨 :" + i + " 이 선택되었습니다.");

                        if(i == 0){
                            java = "java";
                            System.out.println(java);
                            if (databaseReference != null) {
                                databaseReference.child("subject_1").setValue(java);
                            }
                        }else if(i == 1) {
                            javascript = "javascript";
                            System.out.println(javascript);
                            if (databaseReference != null) {
                                databaseReference.child("subject_2").setValue(javascript);
                            }
                        }else if(i == 2) {
                            spring = "spring";
                            System.out.println(spring);
                            if (databaseReference != null) {
                                databaseReference.child("subject_3").setValue(spring);
                            }
                        }else if(i == 3) {
                            jsp = "jsp";
                            System.out.println(jsp);
                            if (databaseReference != null) {
                                databaseReference.child("subject_4").setValue(jsp);
                            }
                        }

                        startActivity(new Intent(LangSelect.this, TimeSelectActivity.class));
                    }
                }
            }
        });
    }
}