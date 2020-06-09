package com.omottec.demoapp.view.log;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by qinbingbing on 05/11/2018.
 */

public class LogTextView extends AppCompatTextView {
    public static final String TAG = "LogTextView";

    public LogTextView(Context context) {
        super(context);
        Log.d(TAG, "LogTextView(Context context)");
    }

    public LogTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LogTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure " + getViewStr(this));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout " + getViewStr(this));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw " + getViewStr(this));
    }

    private String getViewStr(View view) {
        if (view.getTag() != null)
            return view.getTag().toString() + "|" + view.toString();
        return view.toString();
    }
}
