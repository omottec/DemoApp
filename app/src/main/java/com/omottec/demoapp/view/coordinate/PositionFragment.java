package com.omottec.demoapp.view.coordinate;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 04/07/2018.
 */

public class PositionFragment extends Fragment {
    public static final String TAG = "PositionFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_position, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated view:" + view);
        logPosition(view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Logger.d(TAG, "onGlobalLayout view:" + view);
                logPosition(view);
            }
        });
    }

    private void logPosition(View view) {
        if (view instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) view;
            int childCount = ll.getChildCount();
            Logger.d(TAG, "ll.getChildCount:" + childCount
                    + ", ll.getLeft:" + ll.getLeft()
                    + ", ll.getTop:" + ll.getTop()
                    + ", ll.getRight:" + ll.getRight()
                    + ", ll.getBottom:" + ll.getBottom()
                    + ", ll.getPaddingLeft:"+ ll.getPaddingLeft()
                    + ", ll.getPaddingTop:" + ll.getPaddingTop()
                    + ", ll.getPaddingRight:" + ll.getPaddingRight()
                    + ", ll.getPaddingBottom:" + ll.getPaddingBottom());
            View v = null;
            LinearLayout.LayoutParams lp = null;
            for (int i = 0; i < childCount; i++) {
                v = ll.getChildAt(i);
                lp = (LinearLayout.LayoutParams) v.getLayoutParams();
                Logger.d(TAG, "child " + i
                        + ", v.getLeft:" + v.getLeft()
                        + ", v.getTop:" + v.getTop()
                        + ", v.getRight:" + v.getRight()
                        + ", v.getBottom:" + v.getBottom()
                        + ", v.leftMargin:" + lp.leftMargin
                        + ", v.topMargin:" + lp.topMargin
                        + ", v.rightMargin:" + lp.rightMargin
                        + ", v.bottomMargin:" + lp.bottomMargin);
            }
        }
    }
}
