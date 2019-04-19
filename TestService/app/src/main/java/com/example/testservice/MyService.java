package com.example.testservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyService extends Service {

    public static final String TAG = "MyService";
    private MyBinder myBinder=new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");

        doRequestByRxRetrofit();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int second=1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+second;
        Intent intent1=new Intent(this,MyService.class);
        PendingIntent pi=PendingIntent.getService(this,0,intent1,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }

    }
    private void doRequestByRxRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/10.200.14.43:8080/app_service/")//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        RxWeatherService rxjavaService = retrofit.create(RxWeatherService .class);
        rxjavaService .getMessage()
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: "+s);
                        EventBus.getDefault().post(s);
//                        Toast.makeText("")
                    }


                });
    }

}