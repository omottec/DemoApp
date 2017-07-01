package com.omottec.demoapp.view.statuslayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.BaseFragment;
import com.omottec.demoapp.net.Api;
import com.omottec.demoapp.net.IGeoCoding;
import com.omottec.demoapp.utils.Logger;
import com.trello.rxlifecycle.FragmentEvent;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

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
        mRedSl.init(new StatusConfiguration.Builder(getActivity()).contentView(R.layout.l_status_content_red).build());
        mBlueSl.init(new StatusConfiguration.Builder(getActivity()).contentView(R.layout.l_status_content_blue).build());
        mRedTv = (TextView) view.findViewById(R.id.tv_red);
        mBlueTv = (TextView) view.findViewById(R.id.tv_blue);

        mRedSl.showLoading();
        Api.getInstance()
                .get(IGeoCoding.class)
                .getGeoCoding("北京")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<GeoCoding>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Subscriber<GeoCoding>() {
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
                    public void onNext(GeoCoding geoCoding) {
                        Logger.d(TAG, "onNext red:" + geoCoding);
                        mRedTv.setText("lat:" + geoCoding.lat
                                + ", lon:" + geoCoding.lon);
                        mRedSl.showContent();
                    }
                });

        mBlueSl.showLoading();
        Api.getInstance()
                .get(IGeoCoding.class)
                .getGeoCoding("上海")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GeoCoding>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "onCompleted blue");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "onError blue");
                        mRedSl.showNetErr();
                    }

                    @Override
                    public void onNext(GeoCoding geoCoding) {
                        Logger.d(TAG, "onNext blue:" + geoCoding);
                        mBlueTv.setText("lat:" + geoCoding.lat
                                + ", lon:" + geoCoding.lon);
                        mBlueSl.showContent();
                    }
                });

        /*mRedSl.showLoading();
        OkHttpClient okHttpClient = new OkHttpClient();
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
        });*/
    }
}
