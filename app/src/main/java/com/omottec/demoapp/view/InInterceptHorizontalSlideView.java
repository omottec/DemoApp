package com.omottec.demoapp.view;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 8/30/16.
 */
public class InInterceptHorizontalSlideView extends ViewGroup {
    private int mChildWidth;
    private float mLastX;
    private float mLastY;
    private float mLastXIntercept;
    private float mLastYIntercept;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private int mChildIndex;

    public InInterceptHorizontalSlideView(Context context) {
        super(context);
        init();
    }

    public InInterceptHorizontalSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InInterceptHorizontalSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideView.onMeasure uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.d(Tag.MEASURE, "widthMeasureSpec:" + UiUtils.getMeasureSpecModeAndSize(widthMeasureSpec));
        Log.d(Tag.MEASURE, "heightMeasureSpec:" + UiUtils.getMeasureSpecModeAndSize(heightMeasureSpec));
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, getChildAt(0).getMeasuredHeight());
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getChildAt(0).getMeasuredWidth() * childCount, heightSpecSize);
        } else {
            setMeasuredDimension(getChildAt(0).getMeasuredWidth() * childCount,
                    getChildAt(0).getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideView.onLayout uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
        int leftOffset = 0;
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            int measuredWidth = child.getMeasuredWidth();
            mChildWidth = measuredWidth;
            child.layout(leftOffset, 0, leftOffset + measuredWidth, child.getMeasuredHeight());
            leftOffset += measuredWidth;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "InInterceptHorizontalSlideView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.dispatchTouchEvent(ev);
        Log.d(Tag.SLIDE, "InInterceptHorizontalSlideView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "InInterceptHorizontalSlideView.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    return true;
                }
                return false;
            default:
                return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.SLIDE, "InInterceptHorizontalSlideView.onTouchEvent "
                + TouchUtils.getTouchEventAction(event));
        mTracker.addMovement(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy((int) (mLastX - x), 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
//                int mChildIndex = scrollX / mChildWidth;
                mTracker.computeCurrentVelocity(1000);
                float xVelocity = mTracker.getXVelocity();
                Log.d(Tag.SLIDE, "scrollX:" + scrollX
                        + ", mChildWidth:" + mChildWidth
                        + ", xVelocity:" + xVelocity);
                if (Math.abs(xVelocity) > 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(getChildCount() - 1, mChildIndex));
                Log.d(Tag.SLIDE, "mChildIndex:" + mChildIndex);
                smoothScrollBy(mChildIndex * mChildWidth - scrollX, 500);
                mTracker.clear();
                break;
        }
//        Log.d(Tag.SLIDE, "x:" + x
//                + ", y:" + y
//                + ", mLastX:" + mLastX
//                + ", mLastY:" + mLastY
//                + ", mLastXIntercept:" + mLastXIntercept
//                + ", mLastYIntercept:" + mLastYIntercept);
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int duration) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTracker.recycle();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideView.onWindowFocusChanged hasWindowFocus:" + hasWindowFocus);
        Log.d(Tag.MEASURE, "Measured: " + getMeasuredWidth() + "*" + getMeasuredHeight());
        Log.d(Tag.MEASURE, getWidth() + "*" + getHeight());
    }
}
