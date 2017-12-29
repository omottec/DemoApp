package com.omottec.demoapp.touch;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/26/16.
 */
public class TouchView extends View {

    public TouchView(Context context) {
        super(context);
        setEnabled(false);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEnabled(false);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(event));
        boolean b = super.dispatchTouchEvent(event);
        Log.d(Tag.TOUCH, "TouchView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(event)
                + " " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchView.onTouchEvent "
                + TouchUtils.getTouchEventAction(event)
                + ", isEnabled:" + isEnabled()
                + ", isClickable:" + isClickable()
                + ", isLongClickable:"  + isLongClickable());
        boolean b = super.onTouchEvent(event);
        Log.d(Tag.TOUCH, "TouchView.onTouchEvent "
                + TouchUtils.getTouchEventAction(event)
                + " " + b);
        return b;
    }
}
