package com.omottec.demoapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/25/16.
 */
public class TouchRelativeLayout extends RelativeLayout {

    public TouchRelativeLayout(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
//        return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onTouchEvent "
                + TouchUtils.getTouchEventAction(event));
//        return true;
        return super.onTouchEvent(event);
    }
}
