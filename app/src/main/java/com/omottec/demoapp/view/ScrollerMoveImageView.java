package com.omottec.demoapp.view;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 8/25/16.
 */
public class ScrollerMoveImageView extends AppCompatImageView {
    Scroller mScroller;

    public ScrollerMoveImageView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public ScrollerMoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public ScrollerMoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "smoothScrollTo scrollX:" + scrollX
                + ", scrollY:" + scrollY
                + ", destX:" + destX
                + ", destY:" + destY);
        mScroller.startScroll(scrollX, scrollY, destX - scrollX, destY - scrollY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "computeScroll");
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            Log.d(Tag.MOVE_VIEW_BY_SCROLLER, "currX:" + currX + ", currY:" + currY);
            scrollTo(currX, currY);
            postInvalidate();
        }
        logParams();

    }

    public void logParams() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        StringBuilder sb = new StringBuilder();
        sb
                .append('\n')
                .append("getLeft():" + getLeft() + "\n")
                .append("getTop():" + getTop() + "\n")
                .append("getRight():" + getRight() + "\n")
                .append("getBottom():" + getBottom() + "\n")
                .append("getWidth():" + getWidth() + "\n")
                .append("getHeight():" + getHeight() + "\n")
                .append("getScrollX():" + getScrollX() + "\n")
                .append("getScrollY():" + getScrollY() + "\n")
                .append("width:" + lp.width + "\n")
                .append("height:" + lp.height);
        Log.d(Tag.MOVE_VIEW_BY_SCROLLER, sb.toString());
    }
}
