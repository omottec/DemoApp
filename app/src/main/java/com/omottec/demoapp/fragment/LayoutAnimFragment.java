package com.omottec.demoapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 9/22/16.
 */
public class LayoutAnimFragment extends Fragment {
    private Activity mActivity;
    private ListView mLv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLv = new ListView(mActivity);
        String[] array = new String[50];
        for (int i = 0; i < 50; i++)
            array[i] = "item " + i;
        mLv.setAdapter(new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, array));
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.list_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mLv.setLayoutAnimation(controller);
        return mLv;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
