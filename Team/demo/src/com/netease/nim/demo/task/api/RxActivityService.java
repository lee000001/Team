package com.netease.nim.demo.task.api;

import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.login.bean.UserBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit activity服务的相关接口
 */
public interface RxActivityService {


    @GET("activity/getActivities")  //通过任务tid 来获取与之相关的活动信息
    Observable<List<ActivityBean>> getActivities(@Query("tid") int tid);

    @GET("activity/getMembers")  //通过任务aid 来获取与之相关成员
    Observable<List<UserBean>> getMembers(@Query("aid") int aid);


    @GET("activity/addMembers")  //通过任务aid 来获取与之相关成员
    Observable<Boolean> addMembers(@Query("aid") int aid,@Query("tid") int tid,@Query("selecedUids") List<String> selectedUids);

    @GET("activity/setFinish")  //通过活动id来设置任务已经完成
    Observable<Boolean> setFinish(@Query("aid") int aid,@Query("state") int state);

    @GET("activity/getUsers")  //通过accid 数组来获取用户信息
    Observable<List<UserBean>> getUsers(@Query("Uids") List<String> Uids);
    /**
     * 通过tid获取任务详细信息
     * @return
     */
    @GET("activity/getActivityById")
    Observable<ActivityBean> getActivityById(@Query("aid") int aid);
}
