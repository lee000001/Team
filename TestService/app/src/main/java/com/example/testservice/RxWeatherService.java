package com.example.testservice;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RxWeatherService {
    @GET("message/msg")
    Observable<List<MessageBean>> getMessage(@Query("accid") String accid);
}