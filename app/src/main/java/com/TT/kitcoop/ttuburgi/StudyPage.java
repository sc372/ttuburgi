package com.TT.kitcoop.ttuburgi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.TT.kitcoop.ttuburgi.Comment.Comment;
import com.TT.kitcoop.ttuburgi.Data.StudyDataAdapter;
import com.TT.kitcoop.ttuburgi.Data.UserStudyDataAdapter;
import com.TT.kitcoop.ttuburgi.Dialog.CustomDialog;

/**
 * Created by kitcoop on 2017-04-20.
 */

public class StudyPage extends AppCompatActivity {

    private CustomDialog customDialog;

    private Intent intent;
    private StudyDataAdapter studyDataAdapter;
    private UserStudyDataAdapter userStudyDataAdapter;

    private String selectSub;

    private String studyname;
    private String content;
    private String exam;
    private String q1;
    private String a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_page);

        TextView title = (TextView)findViewById(R.id.studyTitle_tv1);
        TextView subTitle = (TextView)findViewById(R.id.studyTitle_tv2);

        TextView code1 = (TextView)findViewById(R.id.code_tv1);
        TextView txt1 = (TextView)findViewById(R.id.txt_tv1);

        final LinearLayout quizBox = (LinearLayout)findViewById(R.id.quiz_box);
        final Button quizBt = (Button)findViewById(R.id.quiz_btn);
        final TextView quizTxt = (TextView)findViewById(R.id.quiz_tv);
        final Button quizCheckBt = (Button)findViewById(R.id.quiz_btn_check);
        final EditText quizEt = (EditText)findViewById(R.id.quiz_et);

        intent = getIntent();
        selectSub = intent.getStringExtra("selectSub");
        System.out.println(selectSub);

        // 데이터베이스 열기
        studyDataAdapter = new StudyDataAdapter(StudyPage.this);
        studyDataAdapter.createStudyDataBase();
        studyDataAdapter.openStudyDatabase();

        userStudyDataAdapter = new UserStudyDataAdapter(StudyPage.this);
        userStudyDataAdapter.createUserDatabase();
        userStudyDataAdapter.openUserDatabase();

        // 랜덤 학습 불러와서 변수에 저장
        try {
            Cursor randomStudy = studyDataAdapter.getRandomList(selectSub);

            studyname = randomStudy.getString(2);
            content = randomStudy.getString(3);
            exam = randomStudy.getString(4);
            q1 = randomStudy.getString(5);
            a1 = randomStudy.getString(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        title.setText(selectSub);
        subTitle.setText(studyname);
        code1.setText(exam);
        txt1.setText(content);

        quizTxt.setText(q1);
        quizBox.setVisibility(View.GONE);

        //퀴즈버튼 누르면...
        quizBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizBox.setVisibility(v.VISIBLE);
                quizBt.setVisibility(v.GONE);
            }
        });


        //퀴즈정답 확인 버튼 누르면...
        quizCheckBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 입력받은 값 check에 저장.
                String check = quizEt.getText().toString();
                String answer = a1;

                // 정답 aaaa 와 check를 비교해서 맞으면 다이알로그 띄우고 학습창으로 넘어가기. 아니면 틀렸다고 토스트 띄우기
                if (answer.equals(check)){
                    onClickView(v);
                } else {
                    Toast.makeText(StudyPage.this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //북마크, 코멘트 부분
        final ToggleButton tgBookmark = (ToggleButton)findViewById(R.id.toggleBook_btn);
        final ToggleButton tgComment = (ToggleButton)findViewById(R.id.toggleComm_btn);

        //북마크에 들어갈 이미지 셋팅
        final Drawable iconBookmark_off = getResources().getDrawable(R.drawable.icon_bookmark_off);
        final Drawable iconBookmark_on = getResources().getDrawable(R.drawable.icon_bookmark_on);
        //iconBookmark_off.setBounds(100,50,130,80);

        // 북마크 토글 버튼 on, off 시 이벤트 주기
        tgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tgBookmark.isChecked()){
                    // 북마크 on 일때
                    tgBookmark.setCompoundDrawablesWithIntrinsicBounds(iconBookmark_on, null, null, null);
                } else {
                    // 북마크 off 일때
                    tgBookmark.setCompoundDrawablesWithIntrinsicBounds(iconBookmark_off, null, null, null);
                }
            }
        });

        // 코멘트 버튼 on, off 일때 이벤트 => 현재 코멘트 토글은 필요 없음.
        tgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudyPage.this, Comment.class));

                if (tgComment.isChecked()){
                    // 코멘트 버튼 on일때

                }else {
                    // 코멘트 버튼 off일때
                }
            }
        });

        //데이터베이스 닫기
        studyDataAdapter.closeStudy();
        userStudyDataAdapter.closeUserStudy();


    }

    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.quiz_btn_check:
                customDialog = new CustomDialog(this, "정답입니다.", "학습리스트로 돌아갑니다.", rightListener);
                customDialog.show();
                break;
        }
    }

    private View.OnClickListener rightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "버튼 클릭", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();

            StudyPage.this.finish();
            //startActivity(new Intent(StudyPage.this, NavigationActivity.class));
        }
    };

}