package com.omottec.demoapp.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 1/23/17.
 */

public class ViewFragment extends Fragment {
    public static final String TAG = "ViewFragment";
    private View mRootView;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.full_screen_text, container, false);
        tv = (TextView) mRootView.findViewById(R.id.tv);
        tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange " + hasFocus);
                if (hasFocus)
                    Log.d(TAG, "onFocusChange " + getWidthAndHeight(tv));
            }
        });

        tv.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run " + getWidthAndHeight(tv));
            }
        });
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.d(TAG, "onGlobalLayout " + getWidthAndHeight(tv));
                printViewHierarchy();
            }
        });
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + getWidthAndHeight(tv));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    private String getWidthAndHeight(View view) {
        return view + " " + view.getMeasuredWidth() + " * " + view.getMeasuredHeight();
    }

    private void printViewHierarchy() {
        ViewParent parent = tv.getParent();
        ViewGroup viewGroup;
        while (parent instanceof ViewGroup) {
            viewGroup = (ViewGroup) parent;
            Log.d(TAG, getWidthAndHeight(viewGroup));
            parent = viewGroup.getParent();
        }
    }
}
