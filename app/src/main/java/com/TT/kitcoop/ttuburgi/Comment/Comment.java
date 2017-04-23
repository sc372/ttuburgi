package com.TT.kitcoop.ttuburgi.Comment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.TT.kitcoop.ttuburgi.R;
import com.TT.kitcoop.ttuburgi.Select_Item;

import java.util.ArrayList;

public class Comment extends AppCompatActivity {
    InputMethodManager mImm;
    // 사용자 id
    private String user = "user00";
    private String txt = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        mImm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        final ArrayList<Select_Item> selectItems = new ArrayList<>();

        Select_Item item1 = new Select_Item("user1","정말 좋은 앱이에요.");
        Select_Item item2 = new Select_Item("user2","정말 좋은 앱이에요.정말정말");
        Select_Item item3 = new Select_Item("user3","정말 좋은 앱이에요.정말정말정말정말");
        Select_Item item4 = new Select_Item("user4","정말 좋은 앱이에요.정말정말");
        Select_Item item5 = new Select_Item("user5","정말 좋은 앱이에요.\n정말정말정말정말");

        selectItems.add(item1);
        selectItems.add(item2);
        selectItems.add(item3);
        selectItems.add(item4);
        selectItems.add(item5);

        final CommentAdapter adapter = new CommentAdapter(this, selectItems);
        ListView listView = (ListView)findViewById(R.id.commentPage_lv);
        listView.setAdapter(adapter);

        final EditText et = (EditText)findViewById(R.id.commentAdd_et);

        // 등록 버튼 누르면 등록...
        findViewById(R.id.commentAdd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = et.getText().toString();
                Select_Item item = new Select_Item(user, txt);
                selectItems.add(item);

                adapter.notifyDataSetChanged();

                et.setText("");
                Toast.makeText(Comment.this, "코멘트가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                mImm.hideSoftInputFromWindow(et.getWindowToken(), 0);

            }
        });
    }
}
