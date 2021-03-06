package com.omottec.demoapp.net;

import android.support.v4.util.LruCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public final class Api {
    private Retrofit mAliyunRetrofit;

    private Map<Class<?>, Retrofit> mApiRetrofitMap = new ConcurrentHashMap<>();

    private LruCache<Class<?>, Object> mApiCache = new LruCache<>(5);

    public <T> T get(Class<T> clazz) {
        T api = (T) mApiCache.get(clazz);
        if (api != null) return api;
        synchronized (mApiCache) {
            api = (T) mApiCache.get(clazz);
            if (api == null) {
                api = mApiRetrofitMap.get(clazz).create(clazz);
                mApiCache.put(clazz, api);
            }
        }
        return api;
    }

    private Api() {
        mAliyunRetrofit = new Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
//                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiRetrofitMap.put(IGeoCoding.class, mAliyunRetrofit);
    }

    private static final class ApiHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return ApiHolder.INSTANCE;
    }

}
