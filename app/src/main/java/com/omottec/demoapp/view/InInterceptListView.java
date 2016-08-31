package com.omottec.demoapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/31/16.
 */
public class InInterceptListView extends ListView {
    private float mLastX;
    private float mLastY;
    private InInterceptHorizontalSlideView mHorizontalSlideView;

    public InInterceptListView(Context context) {
        super(context);
    }

    public InInterceptListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InInterceptListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInInterceptHorizontalSideView(InInterceptHorizontalSlideView horizontalSideView) {
        mHorizontalSlideView = horizontalSideView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "InInterceptListView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        float x = ev.getX(), y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontalSlideView.requestDisallowInterceptTouchEvent(true);
//                requestDisallowIntercept(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                float dx = x - mLastX;
                Log.d(Tag.SLIDE, "dx:" + dx + ", dy:" + dy);
//                requestDisallowIntercept(Math.abs(dy) > Math.abs(dx));
                mHorizontalSlideView.requestDisallowInterceptTouchEvent(Math.abs(dy) > Math.abs(dx));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        boolean handled = super.dispatchTouchEvent(ev);
        Log.d(Tag.SLIDE, "InInterceptListView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }

    private void requestDisallowIntercept(boolean disallowIntercept) {
        requestDisallowInterceptTouchEvent(disallowIntercept);
        if (!(getParent() instanceof ViewGroup)) return;
        ViewGroup parent = (ViewGroup) getParent();
        while (parent != null) {
            Log.d(Tag.SLIDE, "while..............");
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
            ViewParent viewParent = parent.getParent();
            if (viewParent instanceof ViewGroup) break;
            parent = (ViewGroup) viewParent;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "InInterceptListView.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.onInterceptTouchEvent(ev);
        Log.d(Tag.SLIDE, "InInterceptListView.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "InInterceptListView.onTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.onTouchEvent(ev);
        Log.d(Tag.SLIDE, "InInterceptListView.onTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }
}
