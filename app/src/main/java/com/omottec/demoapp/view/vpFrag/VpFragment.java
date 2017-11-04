package com.omottec.demoapp.view.vpFrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

/**
 * Created by omottec on 04/11/2017.
 */

public class VpFragment extends Fragment {
    public static final String ARG_NAME = "name";
    private String mName;
    private TextView mTv;
    private boolean mViewCreated;

    @Override
    public void onAttach(Context context) {
        Log.d(Tag.VP_FRAG, this + " onAttach");
        mName = getArguments().getString(ARG_NAME);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(Tag.VP_FRAG, this + " onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Tag.VP_FRAG, this + " onCreateView");
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(Tag.VP_FRAG, this + " onViewCreated");
        mViewCreated = true;
        mTv = view.findViewById(R.id.tv);
        mTv.setText(mName);
        lazyLoadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(Tag.VP_FRAG, this + " onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Tag.VP_FRAG, this + " onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        Log.d(Tag.VP_FRAG, this + " onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(Tag.VP_FRAG, this + " onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(Tag.VP_FRAG, this + " onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(Tag.VP_FRAG, this + " onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(Tag.VP_FRAG, this + " onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(Tag.VP_FRAG, this + " onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(Tag.VP_FRAG, this + " onDetach");
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(Tag.VP_FRAG, this + " setUserVisibleHint isVisibleToUser:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoadData();
    }

    @Override
    public String toString() {
        return "[" + mName
                + ", hash:"+ hashCode()
                + ", isHidden:" + isHidden()
                + ", isVisible:" + isVisible()
                + ", getUserVisibleHint:" + getUserVisibleHint()
                + "]";
    }

    public static VpFragment newInstance(String name) {
        VpFragment fragment = new VpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    private void lazyLoadData() {
        if (mViewCreated && getUserVisibleHint())
            Log.d(Tag.VP_FRAG, this + " lazyLoadData");
    }
}
