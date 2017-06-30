package com.omottec.demoapp.net;

import android.support.v4.util.LruCache;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public final class Api {
    private Api() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static final class ApiHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return ApiHolder.INSTANCE;
    }

    private LruCache<Class<?>, Retrofit> mRetrofitLruCache = new LruCache<>(5);

    private Retrofit mRetrofit;

    public <T> T get(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
