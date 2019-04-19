package com.example.testservice;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RxWeatherService {
    @GET("activity/msg")
    Observable<String> getMessage();
}