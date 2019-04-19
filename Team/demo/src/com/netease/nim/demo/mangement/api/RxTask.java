package com.netease.nim.demo.mangement.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RxTask {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头

    /**
     * 添加新任务（关键活动等)
     */
    @POST("task/addTask")   //提交接口0.......-  ··
    Call<ResponseBody> addTask(@Body RequestBody info);

    @POST("task/deleteTask")   //提交接口0.......-  ··
    Call<ResponseBody> deleteTask(@Body RequestBody info);

    @POST("task/updateTask")   //提交接口0.......-  ··
    Call<ResponseBody> updateTask(@Body RequestBody info);


}
