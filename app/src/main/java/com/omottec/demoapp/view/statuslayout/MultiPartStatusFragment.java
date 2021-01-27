package com.omottec.demoapp.view.statuslayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.BaseFragment;
import com.omottec.demoapp.net.Api;
import com.omottec.demoapp.net.MockService;
import com.omottec.demoapp.net.NetEventListener;
import com.omottec.logger.Logger;
import com.trello.rxlifecycle.FragmentEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public class MultiPartStatusFragment extends BaseFragment {
    public static final String TAG = "MultiPartStatusFragment";
    private StatusLayout mRedSl;
    private StatusLayout mBlueSl;
    private TextView mRedTv;
    private TextView mBlueTv;

    @Override
    protected View createContentView() {
        return View.inflate(mActivity, R.layout.f_multi_part_status, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRedSl = (StatusLayout) view.findViewById(R.id.sl_red);
        mBlueSl = (StatusLayout) view.findViewById(R.id.sl_blue);
        StatusConfiguration redConfiguration = new StatusConfiguration.Builder(getActivity())
                .contentView(R.layout.l_status_content_red)
                .onRetryListener(v -> {
                    accessNetForRedSl();
                })
                .build();
        mRedSl.init(redConfiguration);
        StatusConfiguration blueConfiguration = new StatusConfiguration.Builder(getActivity())
                .contentView(R.layout.l_status_content_blue)
                .onRetryListener(v -> {
                    accessNetForBlueSl();
                })
                .build();
        mBlueSl.init(blueConfiguration);
        mRedTv = (TextView) view.findViewById(R.id.tv_red);
        mBlueTv = (TextView) view.findViewById(R.id.tv_blue);

        accessNetForRedSl();

        accessNetForBlueSl();

        accessNetForRedSlByOkhttp();
    }

    private void accessNetForRedSlByOkhttp() {
        mRedSl.showLoading();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .eventListener(new NetEventListener())
                .build();
        Request request = new Request.Builder().url("http://gc.ditu.aliyun.com/geocoding?a=%E5%8C%97%E4%BA%AC").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.d(TAG, "onResponse");
                getActivity().runOnUiThread(() -> {
                    mRedTv.setText(response.toString());
                    mRedSl.showContent();
                });
            }
        });
    }

    private void accessNetForBlueSl() {
        mBlueSl.showLoading();
        Api.getInstance()
                .get(MockService.class)
                .getMockData()
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
                        mBlueSl.showNetErr();
                    }

                    @Override
                    public void onNext(MockData mockData) {
                        Logger.d(TAG, "onNext blue:" + mockData);
                        mBlueTv.setText("onNext blue " + mockData);
                        mBlueSl.showContent();
//                        throw new IllegalStateException();
                    }
                });
    }

    private void accessNetForRedSl() {
        mRedSl.showLoading();
        Api.getInstance()
                .get(MockService.class)
                .getMockData()
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<MockData>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Subscriber<MockData>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "onCompleted red");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "onError red");
                        mRedSl.showNetErr();
                    }

                    @Override
                    public void onNext(MockData mockData) {
                        Logger.d(TAG, "onNext red:" + mockData);
                        mRedTv.setText("onNext red " + mockData);
                        mRedSl.showContent();
                    }
                });
    }
}
