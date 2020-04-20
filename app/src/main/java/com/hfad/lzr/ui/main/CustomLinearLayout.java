package com.hfad.lzr.ui.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.hfad.lzr.R;

public class CustomLinearLayout extends LinearLayout {


    private static final int[] STATE_IN = {R.attr.state_in};
    private static final int[] STATE_OUT = {R.attr.state_out};
    private static final int[] STATE_CHANGE_IN = {R.attr.state_changeIn};
    private static final int[] STATE_CHANGE_OUT = {R.attr.state_changeOut};

    private boolean mIsIn = false;
    private boolean mIsOut = false;
    private boolean mIsChangeIn = false;
    private boolean mIsChangeOut = false;

    public CustomLinearLayout(@NonNull Context context) {
        super(context);
    }

    public CustomLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmIsIn(boolean mIsIn) {
        this.mIsIn = mIsIn;
    }

    public void setmIsOut(boolean mIsOut) {
        this.mIsOut = mIsOut;
    }

    public void setmIsChangeIn(boolean mIsChangeIn) {
        this.mIsChangeIn = mIsChangeIn;
    }

    public void setmIsChangeOut(boolean mIsChangeOut) {
        this.mIsChangeOut = mIsChangeOut;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);
        if (mIsIn)
            mergeDrawableStates(drawableState, STATE_IN);
        if (mIsOut)
            mergeDrawableStates(drawableState, STATE_OUT);
        if (mIsChangeIn)
            mergeDrawableStates(drawableState, STATE_CHANGE_IN);
        if (mIsChangeOut)
            mergeDrawableStates(drawableState, STATE_CHANGE_OUT);
        return drawableState;
    }
}
