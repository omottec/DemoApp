package com.omottec.demoapp.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 07/12/2018.
 */

public class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
    public MySimpleOnGestureListener() {
        super();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onSingleTapUp " + TouchUtils.getTouchEventAction(e));
        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onLongPress " + TouchUtils.getTouchEventAction(e));
        super.onLongPress(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Logger.d(Tag.GESTURE, "onScroll distanceX:" + distanceX + " distanceY:" +distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Logger.d(Tag.GESTURE, "onFling velocityX:" + velocityX + " velocityY:" + velocityY);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onShowPress " + TouchUtils.getTouchEventAction(e));
        super.onShowPress(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onDown " + TouchUtils.getTouchEventAction(e));
        return super.onDown(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onDoubleTap " + TouchUtils.getTouchEventAction(e));
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onDoubleTapEvent " + TouchUtils.getTouchEventAction(e));
        return super.onDoubleTapEvent(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onSingleTapConfirmed " + TouchUtils.getTouchEventAction(e));
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        Logger.d(Tag.GESTURE, "onContextClick " + TouchUtils.getTouchEventAction(e));
        return super.onContextClick(e);
    }
}
