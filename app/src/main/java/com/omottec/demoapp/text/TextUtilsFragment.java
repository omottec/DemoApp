package com.omottec.demoapp.text;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 22/01/2018.
 */

public class TextUtilsFragment extends Fragment {
    public static final String TAG = "TextUtilsFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText("TextUtilsFragment");
        String str = "";
        boolean empty = TextUtils.isEmpty(str);
        boolean digitsOnly = TextUtils.isDigitsOnly(str);
        Log.d(TAG, "empty:" + empty + ", digitsOnly:" + digitsOnly);
    }
}
