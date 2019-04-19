package com.netease.nim.demo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.adapter.ActivityListAdapter;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 显示gai任务的activity列表
 */
public class ActivityListActivity extends UI {

    private ListView listView;
    private TextView tv_name;
    private TaskBean task;
    private List<ActivityBean> activityBeanList=new ArrayList<>();
    private ActivityListAdapter adapter;
    private static final String TAG = "ActivityListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        setTitle(R.string.activity_list);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_list;
        setToolBar(R.id.toolbar, options);

        Intent intent = getIntent();
        task = (TaskBean) intent.getSerializableExtra("task");
        tv_name = findViewById(R.id.tv_task_name);
        listView = findViewById(R.id.listView_activity);
//        View empty_view= LayoutInflater.from(this).inflate(R.layout.layout_empty,null,false);
        //设置空布局
        listView.setEmptyView(findViewById(R.id.empty));

        tv_name.setText(task.getTname());
        //活动该task的activity;
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
                    public void onNext(List<ActivityBean> activityBeans) {
                        activityBeanList.addAll(activityBeans);
                        initListviewAdapter(activityBeanList);
                        Log.d(TAG, "onNext: "+activityBeans.toString());

                    }
                });
    }


    private void initListviewAdapter(List<ActivityBean> activityBeanList) {
        adapter = new ActivityListAdapter(ActivityListActivity.this, this.activityBeanList,task);

        listView.setAdapter(adapter);
    }
}
