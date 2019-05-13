package com.netease.nim.demo.task.helper;

import com.netease.nim.demo.Constant;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.api.RxActivityService;
import com.netease.nim.demo.task.api.RxTaskInfoService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivityHelper {
    public static RxActivityService getRxService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_SERVICE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        final RxActivityService rxjavaService = retrofit.create(RxActivityService.class);
        return rxjavaService;
//        .addmenber(tid,addUids)
//                .subscribeOn(Schedulers.io())//IO线程加载数据
//                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
//                .subscribe();
    }

    /**
     * 通过任务id来获取任务的相关活动
     * @param tid
     * @param list
     */
    public static void getActivities(int tid, final List<ActivityBean> list)
    {
        getRxService().getActivities(tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ActivityBean>>() {
                    @Override
                    public void call(List<ActivityBean> activityBeans) {
                        list.addAll(activityBeans);
                    }
                });

    }


}
