package com.omottec.demoapp.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.statuslayout.MockData;

import com.omottec.logger.Logger;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitFragment extends Fragment {
    public static final String TAG = "RetrofitFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv.setText("Click View");
                Api.getInstance()
                        .get(MockService.class)
                        .getMockData("zhangsan|a~")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<MockData>() {
                            @Override
                            public void onCompleted() {
                                Logger.d(TAG, "onCompleted blue");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Logger.d(TAG, "onError blue");
                                mTv.setText(e.getMessage());
                            }

                            @Override
                            public void onNext(MockData mockData) {
                                Logger.d(TAG, "onNext blue:" + mockData);
                                mTv.setText(mockData.toString());
                            }
                        });
            }
        });
    }
}
