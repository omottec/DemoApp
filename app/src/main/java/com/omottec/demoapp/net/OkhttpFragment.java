package com.omottec.demoapp.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public class OkhttpFragment extends Fragment {
    public static final String TAG = "OkhttpFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setOnClickListener(v -> {
            accessNet();
        });
    }

    private void accessNet() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .eventListener(new NetEventListener())
                /*.proxySelector(new ProxySelector() {
                    @Override
                    public List<Proxy> select(URI uri) {
                        Logger.d(TAG, "ProxySelector select uri:" + uri);
                        return Collections.singletonList(Proxy.NO_PROXY);
                    }

                    @Override
                    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                        Logger.d(TAG, "ProxySelector connectFailed uri:" + uri
                                + ", SocketAddress:" + sa
                                + ", IOException:" + ioe);
                    }
                })*/
                .build();
        Request request = new Request.Builder().url("http://www.mocky.io/v2/5b5ecd102e0000020a69466d").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(TAG, "onFailure", e);
                String netInfo = NetUtils.getNetInfo(getContext());
                String msg = e == null ? netInfo : e.getMessage() + ", " + netInfo;
                Logger.d(TAG, msg);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.d(TAG, "onResponse " + Logger.getThreadInfo());
                String resp = response.toString();
                String body = response.body().string();
                String result = new StringBuilder(resp).append('\n').append(body).toString();
                getActivity().runOnUiThread(() -> {
                    Logger.d(TAG, "runOnUiThread " + Logger.getThreadInfo());
                    Logger.d(TAG, result);
                    mTv.setText(result);
                });
            }
        });
    }
}
