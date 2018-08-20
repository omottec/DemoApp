package com.omottec.demoapp.net;

import com.omottec.demoapp.view.statuslayout.MockData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by qinbingbing on 30/07/2018.
 */

public interface MockService {
    @GET("v2/5b5ecd102e0000020a69466d")
    Observable<MockData> getMockData();
}
