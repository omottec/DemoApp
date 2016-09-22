package com.omottec.demoapp.fragment;

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
 * Created by qinbingbing on 9/22/16.
 */
public class JniFragment extends Fragment {
    static {
        System.loadLibrary("jni-test");
    }

    private View mRootView;
    private TextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.full_screen_text, null);
        mTv = (TextView) mRootView.findViewById(R.id.tv);
        mTv.setText(get());
        set("Hello from JniFragment");
        return mRootView;
    }

    public static void methodCalledByJni(String msgFromJni) {
        Log.d(Tag.JNI, "methodCalledByJni, msg: " + msgFromJni);
    }

    public native String get();

    public native void set(String str);
}
