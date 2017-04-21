package com.TT.kitcoop.ttuburgi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Study_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_list);

        Intent intent = getIntent();

        TextView textView = (TextView)findViewById(R.id.tv_studyList);
        textView.setText(intent.getStringExtra("title"));
    }
}
