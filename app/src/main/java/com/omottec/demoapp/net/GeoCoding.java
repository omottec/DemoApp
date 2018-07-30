package com.omottec.demoapp.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public interface GeoCoding {
    @GET("geocoding")
    Observable<com.omottec.demoapp.view.statuslayout.GeoCoding> getGeoCoding(@Query("a") String a);
}
