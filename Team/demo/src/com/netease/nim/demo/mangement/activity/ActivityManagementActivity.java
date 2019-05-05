package com.netease.nim.demo.mangement.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.mangement.ManagementHelper;
import com.netease.nim.demo.mangement.adapter.ActivityManagementAdapter;
import com.netease.nim.demo.mangement.adapter.TaskManagementAdapter;
import com.netease.nim.demo.task.api.OnAlterDialogSelected;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.demo.task.helper.AlterDialogHelper;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityManagementActivity extends UI {
    private  ListView listView;
    private Context context;
    private List<TaskBean> taskBeanList;
    private ActivityManagementAdapter adapter;
    private static final String TAG = "ActivityManagementActiv";
    private TaskBean task;
    private List<ActivityBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_activity);
        setTitle(R.string.activity_management);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_management;
        setToolBar(R.id.toolbar, options);

        context = ActivityManagementActivity.this;
        task = (TaskBean) getIntent().getSerializableExtra("task");
        list = new ArrayList<>();
        listView=findViewById(R.id.listView);
        getData();

    }

    private void getData() {
        ActivityHelper.getRxService()
                .getActivities(task.getTid())
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
                    public void onNext(List<ActivityBean> taskBeans) {
                        list.addAll(taskBeans);
                        if(adapter==null){
                            initAdapter();
                        }else{
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    private void initAdapter() {
        adapter = new ActivityManagementAdapter(context, list,task, new ActivityManagementAdapter.OnItemClick() {
            @Override
            public void onModify(int index, ActivityBean activityBean) {
                // 更新活动
                ManagementHelper.updateActivity(activityBean,context);
            }

            @Override
            public void onDelete(final int index, final ActivityBean activityBean) {
                //删除任务
                AlterDialogHelper.initSelectDialog("是否删除", context, new OnAlterDialogSelected() {
                    @Override
                    public void onComfirm() {
                        ManagementHelper.deleteActivity(activityBean,context);
                        adapter.list.remove(index);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        listView.setAdapter(adapter);
    }

}
