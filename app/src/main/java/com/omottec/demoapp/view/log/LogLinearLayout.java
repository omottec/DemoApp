package com.omottec.demoapp.view.log;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class LogLinearLayout extends LinearLayout {
    public static final String TAG = "LogLinearLayout";
    public LogLinearLayout(Context context) {
        super(context);
        Log.d(TAG, "LogLinearLayout(Context context)");
    }

    public LogLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LogLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LogLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure "  + getViewStr(this));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout "  + getViewStr(this));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw "  + getViewStr(this));
    }

    private String getViewStr(View view) {
        if (view.getTag() != null)
            return view.getTag().toString() + "|" + view.toString();
        return view.toString();
    }
}
