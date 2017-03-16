package com.omottec.demoapp.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 16/03/2017.
 */

public class TabPagerActivity extends AppCompatActivity {
    private View mRootView;
    private TabLayout mTl;
    private ViewPager mVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_tab_pager);
        initView();

    }

    private void initView() {
        mRootView = findViewById(R.id.ll_root);
        mTl = (TabLayout) findViewById(R.id.tl);
        mVp = (ViewPager) findViewById(R.id.vp);
        List<String> fragNames = new ArrayList<>();
        int count = 2;
        for (int i = 0; i < count; i++)
            fragNames.add("frag " + i);
        mVp.setAdapter(new SimpleFragPagerAdapter(getSupportFragmentManager(), fragNames));
        mTl.setupWithViewPager(mVp);
        mTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*mRootView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag.FRAME_TAB_PAGER, "view post");
                resizeTabLayout();
            }
        });*/

        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(Tag.FRAME_TAB_PAGER, "onGlobalLayout");
                resizeTabLayout();
            }
        });
    }

    private void resizeTabLayout() {
        int totalWidth = 0, childWidth = 0;
        View rootChild = mTl.getChildAt(0);
        if (rootChild instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) rootChild;
            int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; i++) {
                childWidth = vg.getChildAt(i).getWidth();
                totalWidth += childWidth;
                Log.d(Tag.FRAME_TAB_PAGER, vg.getChildAt(i) + " " + childWidth);
            }
            int screenWidth = UiUtils.getScreenSize(this, true);
            Log.d(Tag.FRAME_TAB_PAGER, "screenWidth:" + screenWidth
                    + ", totalWidth:" + totalWidth);
            if (totalWidth < screenWidth) mTl.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootView.removeCallbacks(null);
    }
}
