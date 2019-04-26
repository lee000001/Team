package com.netease.nim.demo.task.helper;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netease.nim.demo.Constant;
import com.netease.nim.demo.bean.Task_Activity;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.task.activity.ActivityDetailActivity;
import com.netease.nim.demo.task.activity.AddTaskAcitivity;
import com.netease.nim.demo.task.api.AddTask;
import com.netease.nim.demo.task.api.RxTaskInfoService;
import com.netease.nim.uikit.common.ToastHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskHepler {
    private static final String TAG = "TaskHepler";
    public static RxTaskInfoService getRxService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_SERVICE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        final RxTaskInfoService rxjavaService = retrofit.create(RxTaskInfoService.class);
        return rxjavaService;
//        .addmenber(tid,addUids)
//                .subscribeOn(Schedulers.io())//IO线程加载数据
//                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
//                .subscribe();
    }

    public static void showTimePickerView(Context context, TimePickerView.OnTimeSelectListener onTimeSelectListener) {

        TimePickerView pvTime = new TimePickerView(context, TimePickerView.Type.ALL);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date(System.currentTimeMillis()));
        pvTime.setCyclic(false);

        pvTime.setTitle("选择时间");
        pvTime.setCancelable(true);

        //时间选择后回调
        pvTime.setOnTimeSelectListener(onTimeSelectListener);
        pvTime.show();

    }
    public static void initErrorDialog(String msg,Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert = alert;
        alert.setTitle("温馨提示").setMessage(msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create();
        alert.show();
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        return format.format(date);
    }

    public static Date getTime(String times) throws ParseException {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        return format.parse(times);
    }

    public static void addTask(Task_Activity t, final Context context)
    {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String json;
        //将用户信息转换成json
        json=gson.toJson(t,Task_Activity.class);


        Retrofit retrofit=new Retrofit.Builder()

                .baseUrl(Constant.APP_SERVICE_URL)

                .addConverterFactory( GsonConverterFactory.create())

                .build();

        AddTask post=retrofit.create(AddTask.class);

        final RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Log.d(TAG, "解析json:"+json);

        Call<ResponseBody> call=post.addTask(body);

        call.enqueue(new Callback<ResponseBody>() {

            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.e(TAG,"成功"+response.code());//这里是用于测试，服务器返回的数据就是提交的数据。
                    ToastHelper.showToast(context,"添加成功");
                }else
                {
                    ToastHelper.showToast(context,"添加失败");
                }
            }
            @Override

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,"onError"+t.getMessage());
                ToastHelper.showToast(context,"添加失败");
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

    public  static void setfinish(final Context context, int aid) {
        ActivityHelper.getRxService()
                .setFinish(aid,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                        ToastHelper.showToast(context,"修改失败");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean){
                            ToastHelper.showToast(context,"修改成功");
                        }else {
                            ToastHelper.showToast(context,"修改失败");
                        }
                    }
                });
    }

}
