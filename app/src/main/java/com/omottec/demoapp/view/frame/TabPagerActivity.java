package com.omottec.demoapp.view.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
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
    private SimpleFragStatePagerAdapter mStatePagerAdapter;
//    private SimpleFragPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.FRAME_TAB_PAGER, "onCreate " + this);
        setContentView(R.layout.a_tab_pager);
        initView();

    }

    private void initView() {
        mRootView = findViewById(R.id.ll_root);
        mTl = (TabLayout) findViewById(R.id.tl);
        mVp = (ViewPager) findViewById(R.id.vp);
        List<String> fragNames = new ArrayList<>();
        int count = 8;
        for (int i = 0; i < count; i++)
            fragNames.add("frag " + i);
        /*fragNames.add("一一");
        fragNames.add("二二");
        fragNames.add("三三三三三三三三");
        fragNames.add("四四四四四四四四四四四四");*/
        mStatePagerAdapter = new SimpleFragStatePagerAdapter(getSupportFragmentManager(), fragNames);
        mVp.setAdapter(mStatePagerAdapter);
//        mVp.setAdapter(new SimpleFragPagerAdapter(getSupportFragmentManager(), fragNames));
//        mVp.setOffscreenPageLimit(3);
        mTl.setupWithViewPager(mVp);
        mTl.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTl.setTabMode(TabLayout.MODE_FIXED);

        /*mRootFl.post(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag.FRAME_TAB_PAGER, "view post");
                resizeTabLayout();
            }
        });*/

        /*mRootFl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(Tag.FRAME_TAB_PAGER, "onGlobalLayout");
                resizeTabLayout();
            }
        });*/

        /*MyApplication.getUiHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> fragNames = new ArrayList<String>();
                fragNames.add("一一");
                fragNames.add("二二");
                fragNames.add("三三三三三三三三");
                fragNames.add("四四四四四四四四四四四四");
                mStatePagerAdapter.setData(fragNames);
            }
        }, 15 * 1000);*/
    }

    private void resizeTabLayout() {
        int totalWidth = 0, childWidth;
        View rootChild = mTl.getChildAt(0);
        Log.d(Tag.FRAME_TAB_PAGER, "rootChild.getWidth:" + rootChild.getWidth());
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
            if (totalWidth < screenWidth) {
                int extraWidth = (screenWidth - totalWidth) / childCount;
                View child;
                for (int i = 0; i < childCount; i++) {
                    child = vg.getChildAt(i);
                    ViewGroup.LayoutParams lp = child.getLayoutParams();
                    lp.width = child.getWidth() + extraWidth;
                    child.setLayoutParams(lp);
                    child.requestLayout();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag.FRAME_TAB_PAGER, "onStart " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag.FRAME_TAB_PAGER, "onResume " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag.FRAME_TAB_PAGER, "onPause " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag.FRAME_TAB_PAGER, "onStop " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.FRAME_TAB_PAGER, "onDestroy " + this);
        mRootView.removeCallbacks(null);
    }
}
