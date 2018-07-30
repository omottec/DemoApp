package com.omottec.demoapp.net;

import com.omottec.demoapp.view.statuslayout.MockData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by qinbingbing on 30/07/2018.
 */

public interface MockService {
    @GET("v2/5185415ba171ea3a00704eed")
    Observable<MockData> getMockData();
}
