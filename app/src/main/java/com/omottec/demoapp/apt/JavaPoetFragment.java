package com.omottec.demoapp.apt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.omottec.apt.api.GenCode;
import com.omottec.demoapp.R;

/**
 * app/build/generated/source/apt/debug/com/omottec/demoapp/apt/JavaPoetFragmentGen.java
 */
@GenCode(key = "omottec", value = "code")
public class JavaPoetFragment extends Fragment {
    public static final String TAG = "JavaPoetFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
    }


}
