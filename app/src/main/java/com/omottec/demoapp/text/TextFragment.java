package com.omottec.demoapp.text;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 01/06/2017.
 */

public class TextFragment extends Fragment {
    public static final String TAG = "TextFragment";
    private View mRootView;
    private TextView mTv;
    private TextView mTv1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_text, null);
        mTv = (TextView) mRootView.findViewById(R.id.tv);
        mTv1 = (TextView) mRootView.findViewById(R.id.tv1);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        setPriceAndUnit();
//        setPriceAndLine();
        String text = "1. 掌鱼生鲜更新，更好更快一一掌鱼生鲜更新，更好更快一一掌鱼生鲜更新，更好更快一一\n" +
                "2. 掌鱼生鲜更新，更好更快二二掌鱼生鲜更新，更好更快二二掌鱼生鲜更新，更好更快二二\n" +
                "3. 掌鱼生鲜更新，更好更快三三掌鱼生鲜更新，更好更快三三掌鱼生鲜更新，更好更快三三\n" +
                "4. 掌鱼生鲜更新，更好更快四四掌鱼生鲜更新，更好更快四四掌鱼生鲜更新，更好更快四四\n" +
                "5. 掌鱼生鲜更新，更好更快五五掌鱼生鲜更新，更好更快五五掌鱼生鲜更新，更好更快五五";
        mTv1.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTv1.setText(text);
        Logger.d(TAG, "onViewCreated");
//        Logger.d(TAG, String.format("aaa %d", "bbb"));

        setTagAndTitle("买一赠一", "红富士苹果红富士苹果红富士苹果红富士苹果红富士苹果红富士苹果红富士苹果红富士苹果");
    }

    private void setPriceAndUnit() {
        String str = getContext().getString(R.string.money_format, 200f, "份");
        int i = str.indexOf("/");
        int j = str.indexOf("&#165;");
        Log.d("TextFragment", "j:" + j);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new AbsoluteSizeSpan(UiUtils.sp2px(MyApplication.getContext(), 30)), 0, i,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), i, str.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(UiUtils.sp2px(MyApplication.getContext(), 10)), i, str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv.setText(spannableString);
    }

    private void setPriceAndLine() {
        String originalPriceText = getContext().getString(R.string.money_format, 200f, "份");
        SpannableString spannableString = new SpannableString(originalPriceText);
        spannableString.setSpan(new StrikethroughSpan(), 0, originalPriceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv.setText(spannableString);
    }
    private void setTagAndTitle(String tag, String title) {
        if (TextUtils.isEmpty(title)) return;
        SpannableString spannableString = null;
        if (TextUtils.isEmpty(tag)) {
            spannableString = new SpannableString(title);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString = new SpannableString(tag + " " + title);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, tag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), tag.length(), tag.length() + title.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mTv.setText(spannableString);
    }
}
