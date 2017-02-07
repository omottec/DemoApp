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
    private int mCount;

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
        /*switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d(Tag.TOUCH, "mCount:" + mCount);
                return mCount++ % 2 == 0;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                return false;
        }*/
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onTouchEvent "
                + TouchUtils.getTouchEventAction(event));
        return true;
//        return super.onTouchEvent(event);
    }
}
