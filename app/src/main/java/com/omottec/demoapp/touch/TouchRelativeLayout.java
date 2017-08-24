package com.omottec.demoapp.touch;

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
    private int mCount = 1;

    public TouchRelativeLayout(Context context) {
        super(context);
//        requestDisallowInterceptTouchEvent(true);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        requestDisallowInterceptTouchEvent(true);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(Tag.TOUCH, "TouchRelativeLayout.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev));
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onInterceptTouchEvent "
                + TouchUtils.getTouchEventAction(ev)
                + " " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onTouchEvent "
                + TouchUtils.getTouchEventAction(event)
                + ", isEnabled:" + isEnabled()
                + ", isClickable:" + isClickable()
                + ", isLongClickable:"  + isLongClickable());
        boolean b = super.onTouchEvent(event);
        Log.d(Tag.TOUCH, "TouchRelativeLayout.onTouchEvent "
                + TouchUtils.getTouchEventAction(event)
                + " " + b);
        return b;
    }
}
