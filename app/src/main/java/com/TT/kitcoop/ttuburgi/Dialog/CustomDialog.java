package com.TT.kitcoop.ttuburgi.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.TT.kitcoop.ttuburgi.R;

/**
 * Created by kitcoop on 2017-04-20.
 */

public class CustomDialog extends Dialog {
    private String dTitle;
    private String dContent;
    private TextView titleTv;
    private TextView txtTv;
    private Button rightBt;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public CustomDialog(Context context, String title, String content, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.dTitle = title;
        this.dContent = content;
        this.mRightClickListener = singleListener;
    }

    public CustomDialog(Context context, String title, String content, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.dTitle = title;
        this.dContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_custom_dialog);

        titleTv = (TextView)findViewById(R.id.Dialogtitle);
        txtTv = (TextView)findViewById(R.id.Dialogtxt);
        rightBt = (Button)findViewById(R.id.Dialogbtn1);

        //제목과 내용을 생성자에 셋팅
        titleTv.setText(dTitle);
        txtTv.setText(dContent);

        //클릭 이벤트 셋팅
        rightBt.setOnClickListener(mRightClickListener);

    }
}
