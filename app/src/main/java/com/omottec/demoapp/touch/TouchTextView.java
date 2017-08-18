package com.omottec.demoapp.touch;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/26/16.
 */
public class TouchTextView extends AppCompatTextView {

    public TouchTextView(Context context) {
        super(context);
    }

    public TouchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchTextView.dispatchTouchEvent "
                + TouchUtils.getTouchEventAction(event));
        return super.dispatchTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchTextView.onTouchEvent "
                + TouchUtils.getTouchEventAction(event));
        return super.onTouchEvent(event);
//        return true;
//        return false;
    }
}
