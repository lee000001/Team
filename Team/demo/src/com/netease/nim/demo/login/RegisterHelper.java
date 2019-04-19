package com.netease.nim.demo.login;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netease.nim.demo.login.bean.UserBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterHelper {

    private static final String URL="http://192.168.1.113:8080/app_service/";


    /**
     * 将注册信息更新到应用服务器上
     * @param account
     * @param nickName
     * @param password
     * @return
     */
    public static boolean  registerOnAppService(String account, String nickName, String password)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String userInfo_json;

        UserBean userBean=new UserBean();
        userBean.setAccid(account);
        userBean.setName(nickName);
        userBean.setPassword(password);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userBean.setBirth(sdf.format(date));
        //将用户信息转换成json
        userInfo_json=gson.toJson(userBean,UserBean.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        PostUserInfo postUserInfo=retrofit.create(PostUserInfo.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),userInfo_json);
        Log.d("sssss", "解析json:"+userInfo_json);

        Call<UserBean> call=postUserInfo.postUserInfo(body);

        call.enqueue(new Callback<UserBean>() {

            @Override

            public void onResponse(Call<UserBean> call, Response<UserBean> response) {


                if(response.isSuccessful())
                     Log.e("sssss","成功");//这里是用于测试，服务器返回的数据就是提交的数据。
                else
                    Log.e("sssss", "失败" );

            }



            @Override

            public void onFailure(Call<UserBean> call, Throwable t) {

                Log.e("fail",t.getMessage());

            }

        });

        return true;
    }
//    public UserBean initdata()
//    {
//        UserBean userBean=new UserBean();
//        return
//    }
}
