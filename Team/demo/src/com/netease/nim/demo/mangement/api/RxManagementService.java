package com.netease.nim.demo.mangement.api;

import com.netease.nim.demo.bean.DataAnalysis;
import com.netease.nim.demo.bean.TaskBean;

import java.util.Date;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit activity服务的相关接口
 */
public interface RxManagementService {


    /**
     *
     * @param accid
     * @param isCreator
     * @param key
     * @return
     */
    @GET("task/search")
    Observable<List<TaskBean>> search(@Query("accid") String accid, @Query("isCreator") Boolean isCreator,@Query("key") String key);

    @GET("task/getByDate")
    Observable<List<TaskBean>> getByDate(@Query("accid") String accid, @Query("isCreator") Boolean isCreator,@Query("date") String date);

    @GET("task/getDataAnalysis")
    Observable<List<DataAnalysis>> getDataAnalysis(@Query("accid") String accid, @Query("isCreator") Boolean isCreator);

//    //删除任务
//    @GET("task/deleteTask")
//    Observable<Boolean> deleteTask(@Query("task") TaskBean task);
//    //更新任务
//    @GET("task/updateTask")
//    Observable<Boolean> updateTask(@Query("task") TaskBean task);

    @POST("task/deleteTask")
    Observable<Boolean> deleteTask(@Query("task") TaskBean task);
}
