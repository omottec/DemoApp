package com.omottec.demoapp.parse;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 04/05/2018.
 */

public class ParseFragment extends Fragment {
    public static final String TAG = "ParseFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated " + view);
        mTv = view.findViewById(R.id.tv);
        mTv.setOnClickListener(v -> {
            String url = "imeituan://www.meituan.com/iretail_order?order_id=111&from_pay=";
            Uri uri = Uri.parse(url);
            String fromPay = uri.getQueryParameter("from_pay");
            Logger.d(TAG, "fromPay:" + fromPay);
            Integer.parseInt(fromPay);
        });
    }
}
