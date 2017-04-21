package com.TT.kitcoop.ttuburgi;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.TT.kitcoop.ttuburgi.Data.StudyDataAdapter;
import com.TT.kitcoop.ttuburgi.Data.UserStudyDataAdapter;
import com.TT.kitcoop.ttuburgi.TO.SubjectTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.Subject;

/**
 * Created by yunjeong on 2017-04-09.
 */

public class FragmentMenu1 extends Fragment {

    private StudyDataAdapter studyDataAdapter;
    private UserStudyDataAdapter userStudyDataAdapter;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String sub1Data;
    private String sub2Data;
    private String sub3Data;
    private String sub4Data;

    private String java;
    private String javascript;
    private String spring;
    private String jsp;

    ArrayList<Select_Item> selectItems;
    m1ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_m1lang_listview, container, false);

        studyDataAdapter = new StudyDataAdapter(getActivity());
        studyDataAdapter.createStudyDataBase();
        studyDataAdapter.openStudyDatabase();

        userStudyDataAdapter = new UserStudyDataAdapter(getActivity());
        userStudyDataAdapter.createUserDatabase();
        userStudyDataAdapter.openUserDatabase();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String userId = null;
        if (user != null) {
            userId = user.getUid();
        }

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("language");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SubjectTO subjectTO = dataSnapshot.getValue(SubjectTO.class);
                FragmentMenu1.this.sub1Data = subjectTO.getSubject_1();
                FragmentMenu1.this.sub2Data = subjectTO.getSubject_2();
                FragmentMenu1.this.sub3Data = subjectTO.getSubject_3();
                FragmentMenu1.this.sub4Data = subjectTO.getSubject_4();

                listData();

            }

            private void listData() {
                if (sub1Data != null) {
                    if (sub1Data.equals("java")) {
                        java = sub1Data;
                        //System.out.println(java);
                        selectItems.add(new Select_Item(R.drawable.common_google_signin_btn_icon_dark, java , ""));
                    }
                }

                if (sub2Data != null ) {
                    if (sub2Data.equals("javascript")){
                        javascript = sub2Data;
                        selectItems.add(new Select_Item(R.drawable.common_google_signin_btn_icon_dark, javascript , ""));
                    }
                }

                if (sub3Data != null ) {
                    if (sub3Data.equals("spring")) {
                        spring = sub3Data;
                        selectItems.add(new Select_Item(R.drawable.common_google_signin_btn_icon_dark, spring , ""));
                    }
                }

                if (sub4Data != null ) {
                    if (sub4Data.equals("jsp")) {
                        jsp = sub4Data;
                        selectItems.add(new Select_Item(R.drawable.common_google_signin_btn_icon_dark, jsp , ""));
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        selectItems = new ArrayList<>();


        adapter = new m1ListViewAdapter(getActivity().getApplicationContext(), selectItems);
        ListView lv = (ListView)view.findViewById(R.id.m1lang_listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if ( position == 0 ) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), StudyPage.class);
                    intent.putExtra("selectSub", sub1Data);
                    System.out.println(sub1Data);
                    startActivity(intent);
                }
                if ( position == 1 ) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), StudyPage.class);
                    intent.putExtra("selectSub", sub2Data);
                    System.out.println(sub2Data);
                    startActivity(intent);
                }
                if ( position == 2 ) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), StudyPage.class);
                    intent.putExtra("selectSub", sub3Data);
                    System.out.println(sub3Data);
                    startActivity(intent);
                }
                if ( position == 3 ) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), StudyPage.class);
                    intent.putExtra("selectSub", sub4Data);
                    System.out.println(sub4Data);
                    startActivity(intent);
                }
            }
        });

        studyDataAdapter.closeStudy();
        userStudyDataAdapter.closeUserStudy();

        return view;
    }
}