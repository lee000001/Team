package com.netease.nim.demo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.adapter.TaskScheduleAdapter;
import com.netease.nim.demo.task.api.NodeProgressAdapter;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.demo.task.view.LogisticsData;
import com.netease.nim.demo.task.view.NodeProgressView;
import com.netease.nim.demo.task.view.TimelineDecoration;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskScheduleActivity extends UI {
    List<ActivityBean>  activityBeanList=new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskScheduleAdapter adapter;
    private TaskBean task;

    private static final String TAG = "TaskScheduleActivity";
    private TimelineDecoration timelineDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_schedule);
        setTitle(R.string.task_schedule);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_schedule;
        setToolBar(R.id.toolbar, options);

        //获取来自其他活动的传值
        final Intent intent=getIntent();
        task = (TaskBean) intent.getSerializableExtra("task");


        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        initAdapter();

//        timelineDecoration = new TimelineDecoration(getDimen(R.dimen.time_line_width),
//                getDimen(R.dimen.time_line_top),
//                ContextCompat.getDrawable(this, R.drawable.ic_check_circle),
//                getDimen(R.dimen.time_going_size),
//                1);
//
////        设置itemDecoration实现时光轴特效
//        recyclerView.addItemDecoration(
//                timelineDecoration);
        //获取数据
        getData();


    }

    private void getData() {
        ActivityHelper.getRxService().getActivities(task.getTid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ActivityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<ActivityBean> list) {
                        if(adapter==null){
                            activityBeanList.addAll(list);
                            initAdapter();
                        }else
                        {
                            activityBeanList.clear();
                            activityBeanList.addAll(list);
                            initAdapter();
                        }
                    }
                });
    }

    private void initAdapter() {
        adapter = new TaskScheduleAdapter(this, activityBeanList);
        adapter.setOnClickListener(new TaskScheduleAdapter.OnClickListener() {
            @Override
            public void onClick(View view, ActivityBean activityBean) {
                 //点击跳转到任务详情
                Intent intent1=new Intent(TaskScheduleActivity.this, ActivityDetailActivity.class);
                intent1.putExtra("activity",activityBean);
                intent1.putExtra("task",task);
                startActivity(intent1);
            }

        });
        recyclerView.setAdapter(adapter);
    }

    private int getDimen(int dimeRes){
        return (int) getResources().getDimension(dimeRes);
    }
}
