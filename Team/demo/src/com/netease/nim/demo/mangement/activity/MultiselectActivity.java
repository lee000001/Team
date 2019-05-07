package com.netease.nim.demo.mangement.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.main.fragment.MangementListFragment;
import com.netease.nim.demo.mangement.ManagementHelper;
import com.netease.nim.demo.mangement.view.checkListView.KylinCheckListView;
import com.netease.nim.demo.mangement.view.checkListView.KylinOnCheckListener;
import com.netease.nim.demo.mangement.view.demo.ActivityViewModel;

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

public class MultiselectActivity extends UI implements KylinOnCheckListener {

    private KylinCheckListView checkListView;
    private CheckBox cbAll;
    private TextView tvAll;
    private List<ActivityBean> activityBeanList=new ArrayList<>();
    private static final String TAG = "MultiselectActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muti_check_task_activity);
        initToolbar();

        checkListView = (KylinCheckListView) findViewById(R.id.cv_content);
        cbAll = (CheckBox) findViewById(R.id.cb_all);
        tvAll = (TextView) findViewById(R.id.tv_all);

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListView.multAllCheck(cbAll.isChecked());
            }
        });

        initView();
        setData();

    }

    private void initToolbar() {
        setTitle(R.string.activity_alert);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_alert;
        setToolBar(R.id.toolbar, options);
    }

    private void initView(){
        // 设置Item布局
        checkListView.setItemClass(ActivityViewModel.class);
        // 设置监听
        checkListView.setKylinOnCheckListener(this);
        // 设置多选
        checkListView.setShowType(KylinCheckListView.MULTISELECT);
        // 创建CheckList
        checkListView.create();
    }

    private void setData(){

        ManagementHelper.getRxService()
                .getLateActivity(Preferences.getUserAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ActivityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage(),e );
                    }

                    @Override
                    public void onNext(List<ActivityBean> activityBeans) {
                        //活动滞后信息
                        checkListView.setDataToView(activityBeans);
                    }
                });
    }

    @Override
    public void kylinCheckChange(int position, boolean isChecked) {
        if (checkListView.isMultAll()){
            cbAll.setChecked(true);
        }else {
            cbAll.setChecked(false);
        }
    }

}
