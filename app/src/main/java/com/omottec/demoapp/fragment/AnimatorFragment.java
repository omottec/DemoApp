package com.omottec.demoapp.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 9/21/16.
 */
public class AnimatorFragment extends Fragment {
    private View mRootView;
    private Button mBtn;
    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_animator, null);
        mBtn = (Button) mRootView.findViewById(R.id.btn);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                transformBtn();
                transformBtn1();
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "click btn", Toast.LENGTH_SHORT).show();
            }
        });
        return mRootView;
    }

    private void transformBtn() {
        int width = mBtn.getWidth();
        Log.d(Tag.ANIMATOR, "btn init width:" + width);
        ValueAnimator animator = ValueAnimator.ofInt(width, width * 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                Log.d(Tag.ANIMATOR, "onAnimationUpdate value:" + value + ", fraction:" + fraction);
                mBtn.getLayoutParams().width = value;
                mBtn.requestLayout();
            }
        });
        animator.setDuration(3000).start();
    }

    private void transformBtn1() {
        Log.d(Tag.ANIMATOR, "transformBtn1");
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.tranform_btn);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        mBtn.startAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
