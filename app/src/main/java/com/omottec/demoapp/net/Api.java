package com.omottec.demoapp.net;

import android.support.v4.util.LruCache;

import com.omottec.demoapp.app.MyApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public final class Api {
    private GsonConverterFactory mGsonConverterFactory;
    private RxJavaCallAdapterFactory mRxJavaCallAdapterFactory;
    private OkHttpClient mOkHttpClient;
    private Retrofit mAliyunRetrofit;
    private Retrofit mMockRetrofit;

    private Map<Class<?>, Retrofit> mApiRetrofitMap = new ConcurrentHashMap<>();

    private LruCache<Class<?>, Object> mApiCache = new LruCache<>(5);

    public <T> T get(Class<T> clazz) {
        Retrofit retrofit = mApiRetrofitMap.get(clazz);
        if (retrofit == null)
            throw new IllegalStateException("must register class int Api constructor before get");
        return retrofit.create(clazz);
    }

    private Api() {
        mGsonConverterFactory = GsonConverterFactory.create();
        mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
        mOkHttpClient = new OkHttpClient.Builder()
                .eventListener(new NetEventListener())
                .cache(new Cache(MyApplication.getContext().getExternalCacheDir(), 20 * 1024 * 1024))
                .build();

        mAliyunRetrofit = new Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
                .client(mOkHttpClient)
                .addConverterFactory(mGsonConverterFactory)
                .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                .build();

        mMockRetrofit = new Retrofit.Builder()
                .baseUrl("https://www.mocky.io/")
                .client(mOkHttpClient)
                .addConverterFactory(mGsonConverterFactory)
                .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                .build();

        mApiRetrofitMap.put(GeoCoding.class, mAliyunRetrofit);
        mApiRetrofitMap.put(MockService.class, mMockRetrofit);
    }

    private static final class ApiHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return ApiHolder.INSTANCE;
    }

}
