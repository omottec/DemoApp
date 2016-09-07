package com.omottec.demoapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

import java.lang.reflect.TypeVariable;

/**
 * Created by qinbingbing on 9/7/16.
 */
public class CircleView extends View {
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d(Tag.CUSTOM_VIEW," CircleView constructor 2 params");
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(Tag.CUSTOM_VIEW," CircleView constructor 3 params");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics());
        Log.d(Tag.CUSTOM_VIEW, "widthMode:" + widthMode
                + ", widthSize:" + widthSize
                + ", heightMode:" + heightMode
                + ", heightSize:" + heightSize);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(size, size);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(size, heightMeasureSpec);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpec, size);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        Log.d(Tag.CUSTOM_VIEW, "CircleView.onDraw width:" + width
                + ", height:" + height
                + ", paddingLeft:" + paddingLeft
                + ", paddingTop:" + paddingTop
                + ", paddingRight:" + paddingRight
                + ", paddingBottom:" + paddingBottom);
        width -= paddingLeft + paddingRight;
        height -= paddingTop + paddingBottom;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
