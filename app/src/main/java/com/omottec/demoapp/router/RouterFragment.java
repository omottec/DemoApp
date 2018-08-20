package com.omottec.demoapp.router;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 20/08/2018.
 */

public class RouterFragment extends Fragment implements View.OnClickListener {
    private TextView mSendIntentTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_router, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSendIntentTv = view.findViewById(R.id.tv_send_intent);
        mSendIntentTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_intent:
                onClickSendIntent();
                break;
        }
    }

    private void onClickSendIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("iretail://www.retail.com/web?url=https://mall.dianping.com?poiList=11&poi_id=3"));
        startActivity(intent);
    }
}
