package com.omottec.demoapp.utils;

import android.view.MotionEvent;

/**
 * Created by qinbingbing on 8/26/16.
 */
public final class TouchUtils {
    public static String getTouchEventAction(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "MotionEvent.ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "MotionEvent.ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "MotionEvent.ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "MotionEvent.ACTION_CANCEL";
            default:
                return "MotionEvent.OTHER";
        }
    }

}
