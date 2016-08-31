package com.omottec.demoapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/31/16.
 */
public class OutInterceptListView extends ListView {
    public OutInterceptListView(Context context) {
        super(context);
    }

    public OutInterceptListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutInterceptListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "OutInterceptListView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.dispatchTouchEvent(ev);
        Log.d(Tag.SLIDE, "OutInterceptListView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "OutInterceptListView.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.onInterceptTouchEvent(ev);
        Log.d(Tag.SLIDE, "OutInterceptListView.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(Tag.SLIDE, "OutInterceptListView.onTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean handled = super.onTouchEvent(ev);
        Log.d(Tag.SLIDE, "OutInterceptListView.onTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + handled);
        return handled;
    }
}
