package com.netease.nim.demo.mangement.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.mangement.ManagementHelper;
import com.netease.nim.demo.mangement.adapter.TaskManagementAdapter;
import com.netease.nim.demo.task.api.OnAlterDialogSelected;
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

public class TaskManagementActivity extends UI {
    private  ListView listView;
    private Context context;
    private List<TaskBean> taskBeanList;
    private TaskManagementAdapter adapter;
    private static final String TAG = "DataManagementActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_management_activity);
        setTitle(R.string.task_management);

        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_management;
        setToolBar(R.id.toolbar, options);

        context = TaskManagementActivity.this;
        taskBeanList = new ArrayList<>();
        listView=findViewById(R.id.listView);
        getData();

    }

    private void getData() {
        TaskHepler.getRxService()
                .getAll(Preferences.getUserAccount(),true,-1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TaskBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<TaskBean> taskBeans) {
                        taskBeanList.addAll(taskBeans);
                        if(adapter==null){

                            initAdapter();
                        }else{
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    private void initAdapter() {
        adapter = new TaskManagementAdapter(context, taskBeanList, new TaskManagementAdapter.OnItemClick() {
            @Override
            public void onModify(int index, TaskBean task) {
                // 更新任务
                ManagementHelper.updateTask(task,context);
            }

            @Override
            public void onDelete(final int index, final TaskBean task) {

                //删除任务
                AlterDialogHelper.initSelectDialog("是否删除", context, new OnAlterDialogSelected() {
                    @Override
                    public void onComfirm() {
                        ManagementHelper.deleteTask(task,context);
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
