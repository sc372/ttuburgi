package com.TT.kitcoop.ttuburgi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.TT.kitcoop.ttuburgi.Alarm.MyService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TimeSelectActivity extends AppCompatActivity {
    private Button time;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    String time1;
    String alarm;

    private EditText select_time;
    private static final int Date_id = 0;
    private static final int Time_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        select_time = (EditText) findViewById(R.id.selectTime);
        time = (Button) findViewById(R.id.selecttime);
        time.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String userId = null;
        if (user != null) {
            userId = user.getUid();
        }
        if (userId == null) {
            Log.v("users:::::::", mAuth.getCurrentUser().getUid());

            startActivity(new Intent(TimeSelectActivity.this, MainActivity.class));

            finish();
        }

        select_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });
    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(TimeSelectActivity.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(TimeSelectActivity.this, TimePickerDialog.THEME_HOLO_DARK, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            //set_date.setText(date1);
        }
    };

    // Time picker dialog
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        // 셋팅 페이지에서 이 코드를 써도 됨
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            user = mAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
                select_time.setText(time1);
                if (time1 != null )
                    select_time.clearFocus();
                    time.setVisibility(View.VISIBLE);

                    // Firebase userdata 데이터 추가
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    databaseReference.child("user_settime").setValue(time1);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            alarm = (String) dataSnapshot.child("user_settime").getValue();
                            //Toast.makeText(TimeSelectActivity.this, alarm, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(TimeSelectActivity.this, MyService.class);
                            intent.putExtra("alarm", alarm);

                            startService(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TimeSelectActivity.this, NavigationActivity.class));
                }
            });
        }
    };
}