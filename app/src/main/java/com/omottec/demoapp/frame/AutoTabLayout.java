package com.omottec.demoapp.frame;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by omottec on 16/03/2017.
 * 当Mode为MODE_SCROLLABLE且所有Tab的宽度小于屏宽时
 * Tab自动平分剩余的空间
 */

public class AutoTabLayout extends TabLayout {
    public AutoTabLayout(Context context) {
        super(context);
    }

    public AutoTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                resizeTabWidth();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(Tag.FRAME_TAB_PAGER, "onLayout");
        super.onLayout(changed, l, t, r, b);
        resizeTabWidth();
    }

    private void resizeTabWidth() {
        Log.d(Tag.FRAME_TAB_PAGER, "resizeTabWidth");
        int totalWidth = 0, childWidth;
        View rootChild = getChildAt(0);
        Log.d(Tag.FRAME_TAB_PAGER, "rootChild.getWidth:" + rootChild.getWidth());
        if (rootChild instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) rootChild;
            int childCount = vg.getChildCount();
            if (childCount <= 1) return;
            int screenWidth = UiUtils.getScreenSize(getContext(), true);
            for (int i = 0; i < childCount; i++) {
                childWidth = vg.getChildAt(i).getWidth();
                totalWidth += childWidth;
                Log.d(Tag.FRAME_TAB_PAGER, "child "+ i + " width: " + childWidth + ", totalWidth:" + totalWidth);
                if (totalWidth >= screenWidth) return;
            }
            Log.d(Tag.FRAME_TAB_PAGER, "screenWidth:" + screenWidth + ", totalWidth:" + totalWidth + ", childCount:" + childCount);
            if (totalWidth < screenWidth) {
                int extraWidth = (screenWidth - totalWidth) / childCount;
                if (extraWidth < UiUtils.dip2px(getContext(), 2)) {
                    Log.d(Tag.FRAME_TAB_PAGER, "extraWidth is too small return");
                    return;
                }
                View child;
                for (int i = 0; i < childCount; i++) {
                    child = vg.getChildAt(i);
                    ViewGroup.LayoutParams lp = child.getLayoutParams();
                    lp.width = child.getWidth() + extraWidth;
                    child.setLayoutParams(lp);
                    child.requestLayout();
                    Log.d(Tag.FRAME_TAB_PAGER, "requestLayout");
                }
            }
        }
    }
}
