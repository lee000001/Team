package com.netease.nim.demo.login;

import com.netease.nim.demo.login.bean.UserBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostUserInfo {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头

    @POST("api/register")   //提交接口0.......-  ··
    Call<UserBean> postUserInfo(@Body RequestBody userInfo);//传入的参数为RequestBody
}



