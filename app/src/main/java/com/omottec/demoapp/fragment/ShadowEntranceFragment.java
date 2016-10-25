package com.omottec.demoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.ShadowActivity;

/**
 * Created by qinbingbing on 10/25/16.
 */

public class ShadowEntranceFragment extends Fragment implements View.OnClickListener {
    public static final String SHADOW_KEY = "shadow_key";
    public static final int MAP_SHADOW_FLOAT = 1;
    public static final int MAP_SHADOW_INSERT = 2;
    public static final int NORMAL_SHADOW_FLOAT = 3;
    public static final int NORMAL_SHADOW_INSERT = 4;
    private View mRootView;
    private TextView mMapShadowFloatTv;
    private TextView mMapShadowInsertTv;
    private TextView mNormalShadowFloatTv;
    private TextView mNormalShadowInsertTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_shadow_entrance, null);
        mMapShadowFloatTv = (TextView) mRootView.findViewById(R.id.map_shadow_float_tv);
        mMapShadowInsertTv = (TextView) mRootView.findViewById(R.id.map_shadow_insert_tv);
        mNormalShadowFloatTv = (TextView) mRootView.findViewById(R.id.normal_shadow_float_tv);
        mNormalShadowInsertTv = (TextView) mRootView.findViewById(R.id.normal_shadow_insert_tv);
        mMapShadowFloatTv.setOnClickListener(this);
        mMapShadowInsertTv.setOnClickListener(this);
        mNormalShadowFloatTv.setOnClickListener(this);
        mNormalShadowInsertTv.setOnClickListener(this);
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ShadowActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.map_shadow_float_tv:
                bundle.putInt(SHADOW_KEY, MAP_SHADOW_FLOAT);
                break;
            case R.id.map_shadow_insert_tv:
                bundle.putInt(SHADOW_KEY, MAP_SHADOW_INSERT);
                break;
            case R.id.normal_shadow_float_tv:
                bundle.putInt(SHADOW_KEY, NORMAL_SHADOW_FLOAT);
                break;
            case R.id.normal_shadow_insert_tv:
                bundle.putInt(SHADOW_KEY, NORMAL_SHADOW_INSERT);
                break;
        }
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
