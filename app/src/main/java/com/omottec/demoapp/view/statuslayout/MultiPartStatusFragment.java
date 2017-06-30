package com.omottec.demoapp.view.statuslayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.BaseFragment;
import com.omottec.demoapp.net.Api;
import com.omottec.demoapp.net.IGeoCoding;
import com.omottec.demoapp.utils.Logger;

import java.net.URL;
import java.net.URLEncoder;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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
        mRedTv = (TextView) view.findViewById(R.id.tv_red);
        mBlueTv = (TextView) view.findViewById(R.id.tv_blue);
        mRedSl.init(new StatusConfiguration.Builder(getActivity()).contentView(R.layout.l_status_content_red).build());
        mBlueSl.init(new StatusConfiguration.Builder(getActivity()).contentView(R.layout.l_status_content_blue).build());
        mRedSl.showLoading();
        Api.getInstance()
                .get(IGeoCoding.class)
                .getGeoCoding(URLEncoder.encode("北京"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GeoCoding>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "onCompleted red");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "onError red");
                    }

                    @Override
                    public void onNext(GeoCoding geoCoding) {
                        Logger.d(TAG, "onNext red");
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
                .subscribe(new Subscriber<GeoCoding>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "onCompleted blue");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "onError blue");
                    }

                    @Override
                    public void onNext(GeoCoding geoCoding) {
                        Logger.d(TAG, "onNext blue");
                        mBlueTv.setText("lat:" + geoCoding.lat
                                + ", lon:" + geoCoding.lon);
                        mBlueSl.showContent();
                    }
                });
    }
}
