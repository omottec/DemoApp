package com.omottec.demoapp.net;

import com.omottec.demoapp.view.statuslayout.GeoCoding;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public interface IGeoCoding {
    @GET("/geocoding")
    Observable<GeoCoding> getGeoCoding(@Query("a") String a);
}
