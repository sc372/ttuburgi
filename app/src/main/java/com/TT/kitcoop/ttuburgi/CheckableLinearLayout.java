package com.TT.kitcoop.ttuburgi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by yunjeong on 2017-04-12.
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);

        return cb.isChecked();
    }

    public void setChecked(boolean b) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);

        if (cb.isChecked() != b) {
            cb.setChecked(b);
        }
    }

    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);

        setChecked(cb.isChecked() ? false:true);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

}
