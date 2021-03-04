package com.omottec.demoapp.view.vpFrag;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 16/03/2017.
 */

public class VpTabActivity extends AppCompatActivity implements View.OnClickListener {
    private View mRootView;
    private ViewPager mVp;
    private TextView mTv0;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private VpFragmentStatePagerAdapter mStatePagerAdapter;
    private VpFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_vp_fragment);
        initView();
    }

    private void initView() {
        mRootView = findViewById(R.id.ll_root);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTv0 = (TextView) findViewById(R.id.tv0);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);

//        mVp.setAdapter(new VpFragmentStatePagerAdapter(getSupportFragmentManager()));
        mVp.setAdapter(new VpFragmentPagerAdapter(getSupportFragmentManager()));
        mVp.setOffscreenPageLimit(3);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(Tag.VP_FRAG, "onPageSelected position:" + position);
                switch (position) {
                    case 0:
                        selectTab(0);
                        break;
                    case 1:
                        selectTab(1);
                        break;
                    case 2:
                        selectTab(2);
                        break;
                    case 3:
                        selectTab(3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        mVp.setCurrentItem(0);
        selectTab(0);

        mTv0.setOnClickListener(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv0:
                Log.d(Tag.VP_FRAG, "onClick tv0 setCurrentItem(0)");
                mVp.setCurrentItem(0);
                break;
            case R.id.tv1:
                Log.d(Tag.VP_FRAG, "onClick tv1 setCurrentItem(1)");
                mVp.setCurrentItem(1);
                break;
            case R.id.tv2:
                Log.d(Tag.VP_FRAG, "onClick tv2 setCurrentItem(2)");
                mVp.setCurrentItem(2);
                break;
            case R.id.tv3:
                Log.d(Tag.VP_FRAG, "onClick tv3 setCurrentItem(3)");
                mVp.setCurrentItem(3);
                break;
        }
    }

    private void selectTab(int position) {
        Log.d(Tag.VP_FRAG, "selectTab position:" + position);
        switch (position) {
            case 0:
                mTv0.setBackgroundResource(android.R.color.white);
                mTv1.setBackgroundResource(android.R.color.holo_green_light);
                mTv2.setBackgroundResource(android.R.color.holo_blue_light);
                mTv3.setBackgroundResource(android.R.color.darker_gray);
                break;
            case 1:
                mTv0.setBackgroundResource(android.R.color.holo_red_light);
                mTv1.setBackgroundResource(android.R.color.white);
                mTv2.setBackgroundResource(android.R.color.holo_blue_light);
                mTv3.setBackgroundResource(android.R.color.darker_gray);
                break;
            case 2:
                mTv0.setBackgroundResource(android.R.color.holo_red_light);
                mTv1.setBackgroundResource(android.R.color.holo_green_light);
                mTv2.setBackgroundResource(android.R.color.white);
                mTv3.setBackgroundResource(android.R.color.darker_gray);
                break;
            case 3:
                mTv0.setBackgroundResource(android.R.color.holo_red_light);
                mTv1.setBackgroundResource(android.R.color.holo_green_light);
                mTv2.setBackgroundResource(android.R.color.holo_blue_light);
                mTv3.setBackgroundResource(android.R.color.white);
                break;
        }
    }
}
