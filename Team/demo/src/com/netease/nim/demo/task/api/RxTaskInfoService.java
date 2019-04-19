package com.netease.nim.demo.task.api;

import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.bean.TaskBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RxTaskInfoService {

    /**
     * 查询全部任务信息
     * @param accid
     * @param isCreator
     * @return
     */
    @GET("task/getAllTasks")
    Observable<List<TaskBean>> getAll(@Query("accid") String accid, @Query("isCreator") Boolean isCreator,@Query("tstate") int tstate);

    /**
     * 分页查询接口
     * @param curPage
     * @param pageSize
     * @param accid
     * @param isCreator
     * @return
     */
    @GET("task/getTasks")
    Observable<List<TaskBean>> getMessage(@Query("curPage") int curPage, @Query("pageSize") int pageSize, @Query("accid") String accid, @Query("isCreator") Boolean isCreator);

    /**
     * 查询任务成员信息
     * @param tid 任务id
     * @return
     */
    @GET("task/getUsersByTid")
    Observable<List<UserBean>> getUsers(@Query("tid") int tid);

    @GET("task/getCount")
    Observable<Integer> getMessage(@Query("accid") String accid, @Query("isCreator") Boolean isCreator);

    /**
     * 给任务添加成员
     * @param tid
     * @param addUids
     * @return
     */
    @GET("task/addmenber")
    Observable<Boolean> addmenber(@Query("tid") int tid, @Query("addUids") List<String> addUids);

    /**
     * 获取当前待插入的“task”将会被分配的id
     * @return
     */
    @GET("task/getTaskId")
    Observable<Integer> getTaskId();








}
