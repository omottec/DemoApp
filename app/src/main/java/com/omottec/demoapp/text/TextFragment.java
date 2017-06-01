package com.omottec.demoapp.text;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 01/06/2017.
 */

public class TextFragment extends Fragment {
    private View mRootView;
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.full_screen_text, null);
        mTv = (TextView) mRootView.findViewById(R.id.tv);
        String str = getContext().getString(R.string.money_format, 200f, "份");
        int i = str.indexOf("/");
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new AbsoluteSizeSpan(UiUtils.sp2px(MyApplication.getContext(), 20)), 0, i,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(UiUtils.sp2px(MyApplication.getContext(), 10)), i, str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv.setText(spannableString);
        return mRootView;
    }
}
