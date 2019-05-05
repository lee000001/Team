package com.netease.nim.demo.mangement;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netease.nim.demo.Constant;

import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.bean.Task_Activity;
import com.netease.nim.demo.mangement.api.RxActivityPost;
import com.netease.nim.demo.mangement.api.RxManagementService;
import com.netease.nim.demo.mangement.api.RxTask;
import com.netease.nim.demo.task.api.AddTask;
import com.netease.nim.demo.task.helper.AlterDialogHelper;
import com.netease.nim.uikit.common.ToastHelper;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ManagementHelper {
    private static final String TAG = "ManagementHelper";
    public static RxManagementService getRxService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_SERVICE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        final RxManagementService rxjavaService = retrofit.create(RxManagementService.class);
        return rxjavaService;
//        .addmenber(tid,addUids)
//                .subscribeOn(Schedulers.io())//IO线程加载数据
//                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
//                .subscribe();
    }

//    public static void deleteTask(final Context context, TaskBean task){
//        getRxService()
//                .deleteTask(task)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Boolean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        AlterDialogHelper.initErrorDialog("删除失败",context);
//                    }
//
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//
//                    }
//                });
//    }
//
//    public static void updateTask(final Context context, TaskBean task){
//        getRxService()
//                .updateTask(task)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Boolean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        AlterDialogHelper.initErrorDialog("更新失败",context);
//                    }
//
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//
//                    }
//                });
//    }

    public static void deleteTask(TaskBean t, final Context context)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        json=gson.toJson(t,TaskBean.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(Constant.APP_SERVICE_URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        RxTask post=retrofit.create(RxTask.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Log.d(TAG, "解析json:"+json);

        Call<ResponseBody> call=post.deleteTask(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.e(TAG,"成功"+response.code());//这里是用于测试，服务器返回的数据就是提交的数据。
                    ToastHelper.showToast(context,"删除成功");
                }else
                {
                    ToastHelper.showToast(context,"删除失败");
                }
            }
            @Override

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,"onError"+t.getMessage());
                ToastHelper.showToast(context,"删除失败");
            }

        });

    }
    public static void updateTask(TaskBean t, final Context context)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        json=gson.toJson(t,TaskBean.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(Constant.APP_SERVICE_URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        RxTask post=retrofit.create(RxTask.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Log.d(TAG, "解析json:"+json);

        Call<ResponseBody> call=post.updateTask(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.e(TAG,"成功"+response.code());//这里是用于测试，服务器返回的数据就是提交的数据。
                    ToastHelper.showToast(context,"更新成功");
                }else
                {
                    ToastHelper.showToast(context,"更新失败");
                }
            }
            @Override

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,"onError"+t.getMessage());
                ToastHelper.showToast(context,"更新失败");
            }

        });

    }
    public static void updateActivity(ActivityBean a, final Context context)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        json=gson.toJson(a,ActivityBean.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(Constant.APP_SERVICE_URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        RxActivityPost post=retrofit.create(RxActivityPost.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Log.d(TAG, "解析json:"+json);

        Call<ResponseBody> call=post.updateActivity(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.e(TAG,"成功"+response.code());//这里是用于测试，服务器返回的数据就是提交的数据。
                    ToastHelper.showToast(context,"更新成功");
                }else
                {
                    ToastHelper.showToast(context,"更新失败");
                }
            }
            @Override

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,"onError"+t.getMessage());
                ToastHelper.showToast(context,"更新失败");
            }

        });

    }
    public static void deleteActivity(ActivityBean t, final Context context)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        json=gson.toJson(t,ActivityBean.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(Constant.APP_SERVICE_URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        RxActivityPost post=retrofit.create(RxActivityPost.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Log.d(TAG, "解析json:"+json);

        Call<ResponseBody> call=post.deleteActivity(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.e(TAG,"成功"+response.code());//这里是用于测试，服务器返回的数据就是提交的数据。
                    ToastHelper.showToast(context,"删除成功");
                }else
                {
                    ToastHelper.showToast(context,"删除失败");
                }
            }
            @Override

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,"onError"+t.getMessage());
                ToastHelper.showToast(context,"删除失败");
            }

        });

    }
    private static String getJson(Task_Activity t) {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        return json=gson.toJson(t,Task_Activity.class);
    }
}
