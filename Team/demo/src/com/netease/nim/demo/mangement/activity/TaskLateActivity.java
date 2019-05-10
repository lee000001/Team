package com.netease.nim.demo.mangement.activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.main.fragment.MangementListFragment;
import com.netease.nim.demo.mangement.ManagementHelper;


import com.netease.nim.demo.mangement.adapter.TaskLateAdapter;
import com.netease.nim.demo.task.activity.ActivityDetailActivity;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 发送任务滞后提示消息的活动
 */

public class TaskLateActivity extends UI implements View.OnClickListener {

    private static final String TAG = "TaskLateActivity";
    private ListView listView;
    private List<DataHolder> dataList;
    private List<ActivityBean> selectedDatas;
    private Button btn_all_select;
    private Button btn_all_unselect;
    private Button btn_send;
    private TaskLateAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.late_task_activity);
        initToolbar();
        listView = findViewById(R.id.listview_late);
        dataList = new ArrayList<>();
        selectedDatas =new ArrayList<>();
        btn_all_select = findViewById(R.id.btn_all_sel);
        btn_all_unselect = findViewById(R.id.btn_all_unsel);
        btn_send = findViewById(R.id.btn_send);
        btn_all_select.setOnClickListener(this);
        btn_all_unselect.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        //设置列表点击事件

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //记录选中item

                boolean checked = dataList.get(position).checked;

                if (!checked) {

                    dataList.get(position).checked = true;
                    selectedDatas.add(dataList.get(position).activity);

                }else {

                    dataList.get(position).checked = false;
                    selectedDatas.remove(dataList.get(position).activity);

                }

                adapter.notifyDataSetChanged();

            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TaskLateActivity.this, ActivityDetailActivity.class);
                intent.putExtra("activity",dataList.get(position).activity);
                startActivity(intent);
                return true;
            }
        });

        ManagementHelper.getRxService()
                .getLateActivity(Preferences.getUserAccount())
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
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
                        dataList.clear();
                        for(ActivityBean a:activityBeans){
                            dataList.add(new DataHolder(a,false));
                        }
                        if(adapter==null){
                            adapter=new TaskLateAdapter(TaskLateActivity.this,dataList);
                            listView.setAdapter(adapter);
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    private void initToolbar() {
        setTitle(R.string.activity_alert);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_alert;
        setToolBar(R.id.toolbar, options);
    }


    @Override

    public void onClick(View v) {

        switch (v.getId()){

            //设置全部选中

            case R.id.btn_all_sel:

                for (int i = 0; i <dataList.size() ; i++) {

                    dataList.get(i).checked=true;

                }

                adapter.notifyDataSetChanged();

                break;

            //取消全部选中

            case R.id.btn_all_unsel:

                for (int i = 0; i <dataList.size() ; i++) {

                    dataList.get(i).checked=false;

                }

                adapter.notifyDataSetChanged();

                break;
            case R.id.btn_send:
                selectedDatas = new ArrayList<>();
                for(DataHolder d:dataList){
                    if(d.checked){
                        selectedDatas.add(d.activity);
                    }
                }
//                Log.d(TAG, "onClick: "+selectedDatas.toString());
                break;

        }

    }

}
