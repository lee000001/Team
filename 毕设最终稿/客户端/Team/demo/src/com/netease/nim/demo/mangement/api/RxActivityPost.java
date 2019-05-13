package com.netease.nim.demo.mangement.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RxActivityPost {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头

    /**
     * 添加新任务（关键活动等)
     */
    @POST("activity/addActivity")   //提交接口0.......-  ··
    Call<ResponseBody> addActivity(@Body RequestBody info);

    @POST("activity/deleteActivity")   //提交接口0.......-  ··
    Call<ResponseBody> deleteActivity(@Body RequestBody info);

    @POST("activity/updateActivity")   //提交接口0.......-  ··
    Call<ResponseBody> updateActivity(@Body RequestBody info);

    @POST("message/lateMessage")   //提交滞后消息
    Call<ResponseBody> sendLateMsg(@Body RequestBody info);

}
