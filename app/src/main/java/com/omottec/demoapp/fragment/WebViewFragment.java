package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;

import java.lang.annotation.Annotation;

/**
 * Created by qinbingbing on 24/06/2017.
 */

public class WebViewFragment extends Fragment implements JavascriptInterface {
    private View mRootView;
    private WebView mWebView;
    private TextView mTv;
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_web_view, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWebView = (WebView) mRootView.findViewById(R.id.web_view);
        mTv = (TextView) mRootView.findViewById(R.id.tv);
        mBtn = (Button) mRootView.findViewById(R.id.btn);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
//        mWebView.loadUrl("file:///android_asset/wx.html");
//        mWebView.loadUrl("http://www.meituan.com");
        mWebView.loadUrl("http://consumer.mall.test.sankuai.com/fe/c/delivery.html");
        mWebView.addJavascriptInterface(this, "wx");

        mTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        mBtn.setOnClickListener(v -> {
            mWebView.loadUrl("javascript:actionFromNative()");
            mWebView.loadUrl("javascript:actionFromNativeWithParam(" + "'come from native'" + ")");
        });
    }

    @JavascriptInterface
    public void actionFromJs() {
        getActivity().runOnUiThread(() -> {
            String text = "Js Call Native";
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            mTv.setText(mTv.getText() + "\n" + text);
        });
    }

    @JavascriptInterface
    public void actionFromJsWithParam(String str) {
        getActivity().runOnUiThread(() -> {
            String text = "Js Call Native with arg: " + str;
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            mTv.setText(mTv.getText() + "\n" + text);
        });
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
