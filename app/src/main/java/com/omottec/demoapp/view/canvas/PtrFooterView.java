package com.omottec.demoapp.view.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrFooterView extends View {
    public static final String TAG = "PtrFooterView";
    private RectF mRectF;
    private int mRectBorder = UiUtils.dip2px(getContext(), 1);
    private int mRectSize = UiUtils.dip2px(getContext(), 24);
    private Paint mAcrPaint = new Paint();
    private Paint mTextPaint = new Paint();

    public PtrFooterView(Context context) {
        super(context);
    }

    public PtrFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "init width:" + width + ", height:" + height);
        int rectLeftOffset = (width - mRectSize) / 2;
        mRectF = new RectF(rectLeftOffset + mRectBorder,
                mRectBorder,
                rectLeftOffset + mRectSize - mRectBorder,
                mRectSize - mRectBorder);
        mAcrPaint.setAntiAlias(true);
        mAcrPaint.setStyle(Paint.Style.STROKE);
        mAcrPaint.setStrokeWidth(mRectBorder);
        mAcrPaint.setColor(Color.GREEN);

//        int textLeftOffset =
        mTextPaint.setTextSize(15);
        mTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.drawArc(mRectF, 0, 270, false, mAcrPaint);
        String text = "相信生活美好";
        canvas.drawText(text, 0, mRectSize, mTextPaint);
    }
}
