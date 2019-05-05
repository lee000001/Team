package com.netease.nim.demo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.util.LogTime;
import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.task.adapter.MemberAdapter;
import com.netease.nim.demo.task.api.RxActivityService;
import com.netease.nim.demo.task.api.RxTaskInfoService;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityDetailActivity extends UI implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView tv_result;
    private TextView tv_content;
    private TextView tv_end;
    private TextView tv_start;
    private TextView tv_name;
    private List<UserBean> userBeanList;
    private MemberAdapter memberAdapter;
    private TextView tv_state;
    private ActivityBean activityBean;
    private String TAG="ActivityDetailActivity";
    private TaskBean task;
    private Button btn_set_state;
    private Boolean isCreator=false;
    private Button btn_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        setTitle(R.string.activity_detail);

        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_detail;
        setToolBar(R.id.toolbar, options);
        Intent intent=getIntent();
        //活动间传值
        activityBean = (ActivityBean) intent.getSerializableExtra("activity");
        task = (TaskBean) intent.getSerializableExtra("task");

        isCreator=task.getTcreator().equals(Preferences.getUserAccount());
        init();

        getMember(activityBean);

    }

    private void init() {
        tv_name = findViewById(R.id.tv_name);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        tv_content = findViewById(R.id.tv_content);
        tv_result = findViewById(R.id.tv_result);
        recyclerView = findViewById(R.id.recyclerview_member);
        tv_state = findViewById(R.id.tv_state);
        btn_set_state = findViewById(R.id.btn_set_state);
        btn_set_state.setOnClickListener(this);
        btn_task = findViewById(R.id.btn_task);
        btn_task.setOnClickListener(this);

        if(task.getTstate()==1){
            btn_set_state.setVisibility(View.VISIBLE);
        }else {
            btn_set_state.setVisibility(View.GONE);
        }

        tv_name.setText("活动名称："+activityBean.getAname());
        tv_start.setText(activityBean.getStartDate());
        tv_end.setText(activityBean.getEndDate());
        tv_content.setText(activityBean.getContent());
        tv_result.setText(activityBean.getAresult());
        tv_content.setText(activityBean.getContent());

        String state;
        //任务状态 0 未开始 ，1正在进行，2完成，3过期
        switch (activityBean.getAstate())
        {
            case 0:
                state="未开始";
                break;
            case 1:
                state="正在进行";
                break;
            case 2:
                state="完成";
                break;
            case 3:
                state="已过期";
                break;
            default:
                state="未知";
                break;
        }
        tv_state.setText(state);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置水平布局
        layoutManager.setOrientation(OrientationHelper. HORIZONTAL);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }

    private void getMember(ActivityBean activityBean) {
        ActivityHelper.getRxService()
                .getMembers(activityBean.getAid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<UserBean> userBeans) {
                        userBeanList = new ArrayList<>();
                        initMemberAdapter(userBeans);
                    }
                });
    }

    private void initMemberAdapter(List<UserBean> userBeans) {
        userBeanList.addAll(userBeans);
        memberAdapter = new MemberAdapter(ActivityDetailActivity.this,userBeanList,isCreator);
        recyclerView.setAdapter(memberAdapter);

    }

    /**
     * 给关键活动添加成员
     * @param requestCode
     * @param resultCode
     * @param data  被选中的用户accid 账号
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== MemberAdapter.CODE_ADD_USER&&resultCode== ContactSelectActivity.RESULT_OK)
        {
            ArrayList<String> selectedAccount=data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            RxActivityService service= ActivityHelper.getRxService();
            service.addMembers(activityBean.getAid(),activityBean.getAid_tid(),selectedAccount)
                    .subscribeOn(Schedulers.io())//IO线程加载数据
                    .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: "+e.getMessage());
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                                //重新获取成员信息
                                getMember(activityBean);

                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_set_state:
                ToastHelper.showToast(ActivityDetailActivity.this,"点击事件");
                setfinish(activityBean.getAid());
                break;
            case R.id.btn_task:
                Intent intent=new Intent(this,TaskDetailActivity.class);
                intent.putExtra("task",task);
                startActivity(intent);
                break;
        }


    }

    private void setfinish(int aid) {
        ActivityHelper.getRxService()
                .setFinish(aid,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                        ToastHelper.showToast(ActivityDetailActivity.this,"修改失败");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean){
                            ToastHelper.showToast(ActivityDetailActivity.this,"修改成功");
                        }else {
                            ToastHelper.showToast(ActivityDetailActivity.this,"修改失败");
                        }
                    }
                });
    }
}
