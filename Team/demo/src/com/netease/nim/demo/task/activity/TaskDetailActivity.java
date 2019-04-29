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

import com.netease.nim.demo.Constant;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.main.model.Extras;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.demo.task.adapter.MemberAdapter;
import com.netease.nim.demo.task.api.RxTaskInfoService;
import com.netease.nim.demo.task.view.LRTextView;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskDetailActivity extends UI {


    private static final int REQUEST_CODE_ADVANCED =2 ;
    private LRTextView tv_name;
    private LRTextView tv_state;
    private LRTextView tv_creator;
    private TextView tv_content;
    private LRTextView tv_tid;
    private LRTextView tv_startDate;
    private LRTextView tv_endDate;
    private MemberAdapter dataAdapter;
    private RecyclerView recyclerView;
    private TaskBean task;
    private List<UserBean> datas=new ArrayList<>();
    private String state;
    private Button btn_schedule;
    private static final String TAG = "TaskDetailActivity";
    private Button btn_activity;
    private Boolean isEdit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_layout);
        setTitle(R.string.task_detail);

        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_detail;
        setToolBar(R.id.toolbar, options);
        init();
        getData();

    }

    private void init() {
        tv_name=findViewById(R.id.tv_name);
        tv_state=findViewById(R.id.tv_state);
        tv_creator=findViewById(R.id.tv_creator);
        tv_content=findViewById(R.id.tv_content);
        tv_tid=findViewById(R.id.tv_tid);
        recyclerView=findViewById(R.id.recyclerview_task);
        tv_startDate=findViewById(R.id.tv_startDate);
        tv_endDate=findViewById(R.id.tv_endDate);
        btn_schedule=findViewById(R.id.btn_schedule);
        btn_activity = findViewById(R.id.btn_activtiy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置水平布局
        layoutManager.setOrientation(OrientationHelper. HORIZONTAL);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        final Intent intent=getIntent();
        task= (TaskBean) intent.getSerializableExtra("task");

        tv_name.getTv_right().setText(task.getTname());
        switch ( task.getTstate())
        {
            case 0:state="未开始";break;
            case 1:state="进行中";break;
            case 2:state="已完成";break;
            case 3:state="已过期";break;
            default:state="未知";break;
        }
        tv_state.getTv_right().setText(state);
        String name=task.getTcreator().equals(Preferences.getUserAccount())?"我":task.getTcreator();
        tv_creator.getTv_right().setText(name);
        tv_content.setText(task.getTcontent());
        tv_tid.getTv_right().setText(String.valueOf(task.getTid()));
        tv_startDate.getTv_right().setText(task.getTstartDate());
        tv_endDate.getTv_right().setText(task.getTendDate());
        tv_creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看成员信息
                Intent intent1=new Intent(TaskDetailActivity.this,UserProfileActivity.class);
                intent1.putExtra(Extras.EXTRA_ACCOUNT,task.getTcreator());
                startActivity(intent1);
            }
        });
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开任务进度activity
                Intent intent1=new Intent(TaskDetailActivity.this,TaskScheduleActivity.class);
                intent1.putExtra("task",task);
                startActivity(intent1);

            }
        });

        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看关键活动列表
                Intent intent1=new Intent(TaskDetailActivity.this,ActivityListActivity.class);
                intent1.putExtra("task",task);
                startActivity(intent1);
            }
        });

    }
    private void initAdapter() {
        isEdit = Preferences.getUserAccount().equals(task.getTcreator());
//        isEdit=false;  //设置不显示添加功能
        dataAdapter=new MemberAdapter(this,datas,isEdit);
        recyclerView.setAdapter(dataAdapter);
    }

    /**
     * 获取成员信息
     */
    private void getData() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_SERVICE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        final RxTaskInfoService rxjavaService = retrofit.create(RxTaskInfoService.class);
        rxjavaService.getUsers(task.getTid())
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<List<UserBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserBean> taskBeans) {
                        if(dataAdapter==null)
                        {
                            datas.addAll(taskBeans);
                            initAdapter();
                        }
                        else
                        {
                            datas.clear();
                            datas.addAll(taskBeans);
                            dataAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 活动之间的传递数据
     * @param requestCode
     * @param resultCode
     * @param data  被选中的用户accid 账号
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== MemberAdapter.CODE_ADD_USER&&resultCode==ContactSelectActivity.RESULT_OK)
        {
            ArrayList<String> selectedAccount=data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            RxTaskInfoService service=TaskHepler.getRxService();
            service.addmenber(task.getTid(),selectedAccount)
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
                            if(!aBoolean)
                            {
                                ToastHelper.showToast(TaskDetailActivity.this,"添加失败");
                            }
                            else
                            {
                                //重新获取成员信息
                                getData();
                            }
                        }
                    });

        }
    }
}
