package com.netease.nim.demo.mangement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ListView;

import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.mangement.ManagementHelper;
import com.netease.nim.demo.task.activity.TaskDetailActivity;
import com.netease.nim.demo.task.adapter.TaskDataAdapter;
import com.netease.nim.uikit.common.activity.UI;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TaskSearchActivity extends UI {
    private SearchView searchView;
    private ListView listView;
    private List<TaskBean> taskBeanList;
    private Boolean isCreator;
    private SwitchButton switchButton;
    private TextView tv_tip;

    private static final String TAG = "TaskSearchActivity";
    private TaskDataAdapter adapter;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. 绑定视图
        setContentView(R.layout.task_search);


        // 3. 绑定组件
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.searchListView);
        listView.setEmptyView(findViewById(R.id.search_empty));
        taskBeanList = new ArrayList<>();
        isCreator = true;
        switchButton = findViewById(R.id.switch_button);
        switchButton.setChecked(true);
        tv_tip = findViewById(R.id.tv_tip_creator);
        initAdapter();


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    tv_tip.setText("我创建");
                    isCreator = true;
                } else {
                    tv_tip.setText("待完成");
                    isCreator = false;
                }
                getData(key);
            }
        });
        //设置默认开启搜索框
        searchView.setIconifiedByDefault(false);
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setImeOptions(6);
        //设置默认提示文字
        searchView.setQueryHint("输入您想查找的内容");
        //配置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                getData(query);
                key = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getData(newText);
                key=newText;
                return true;
            }
        });
    }

    private void getData(String key) {
        ManagementHelper.getRxService()
                .search(Preferences.getUserAccount(),isCreator,key)
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
                        if(taskBeans==null){
                            taskBeanList=new ArrayList<>();
                        }else{
                            taskBeanList.clear();
                            taskBeanList.addAll(taskBeans);
                        }
                        if(adapter==null){
                            initAdapter();
                        }else {
                            adapter.notifyDataSetChanged();
                        }

                        Log.d(TAG, "onNext: "+taskBeans.toString());

                    }
                });
    }

    private void initAdapter() {
        adapter = new TaskDataAdapter(this,taskBeanList);
        adapter.setOnClickListener(new TaskDataAdapter.OnClickListener() {
            @Override
            public void onClick(TaskBean task) {
                Intent intent=new Intent(TaskSearchActivity.this, TaskDetailActivity.class);
                intent.putExtra("task",task);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
