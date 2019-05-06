package com.netease.nim.demo.task.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.task.adapter.AddActivityListAdapter;
import com.netease.nim.demo.task.adapter.MemberAdapter;
import com.netease.nim.demo.task.api.RxTaskInfoService;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.demo.task.helper.AlterDialogHelper;
import com.netease.nim.demo.task.helper.DateHelper;
import com.netease.nim.demo.task.helper.TaskHepler;

import com.netease.nim.demo.task.api.OnAlterDialogSelected;
import com.netease.nim.demo.task.api.OnStepClick;
import com.netease.nim.demo.task.view.TaskStep;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddActivityActivity extends UI {
    private static final String TAG = "AddActivityActivity";
    private int CODE_SEND=0;
    private int CODE_RESULT=1;
    private Date sDate;  //最早时间
    private Date eDate; //最晚时间
    private int tid;
    private static int index=0;
    private TaskBean task;
    private  Button btn_save;
    private Button btn_add;
    @BindView(R.id.listView)
     ListView listView;
    @BindView(R.id.tv_task_name)
   TextView tv_task_name;
    private View footer;

    private AddActivityListAdapter addActivityListAdapter;
    private TextView tv_start;
    private EditText et_result;
    private EditText et_content;
    private EditText et_name;
    private TextView tv_end;
    private ImageButton btn_end;
    private ImageButton btn_start;
    private Context context=AddActivityActivity.this;;
    private SimpleDateFormat sdf;
    private  List<ActivityBean> activityBeanList;
    private Button btn_save_back;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> selectedAccount;
    private MemberAdapter memberAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_activity);
        setTitle(R.string.task_add_activity);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId =R.string.task_add_activity;
        setToolBar(R.id.toolbar, options);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //从其他Activity获取task信息
        getFromActivity();
        init();



    }

    private void getFromActivity()  {
        Intent intent=getIntent();
        if(intent.getSerializableExtra("task")!=null)
        {
            task= (TaskBean) intent.getSerializableExtra("task");
            try {
                sDate= TaskHepler.getTime(task.getTstartDate());
                eDate=TaskHepler.getTime(task.getTendDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        activityBeanList=AddTaskAcitivity.activityBeanList;
//        if(intent.getSerializableExtra("activities")!=null)
//            activityBeanList= (List<ActivityBean>) intent.getSerializableExtra("activities");
//        if(activityBeanList==null)
//            activityBeanList=new ArrayList<>();
    }

    private void init() {
        tv_task_name.setText(task.getTname());
        //footer布局初始化

        footer=LayoutInflater.from(context).inflate(R.layout.footer_add_activity,listView,false);
        et_name = footer.findViewById(R.id.et_activity_name);
        et_content = footer.findViewById(R.id.et_activity_content);
        et_result = footer.findViewById(R.id.et_activity_result);
        tv_start = footer.findViewById(R.id.tv_start);
        tv_end = footer.findViewById(R.id.tv_end);
        btn_save=footer.findViewById(R.id.btn_save);
        btn_start = footer.findViewById(R.id.btn_startDate);
        btn_end = footer.findViewById(R.id.btn_endDate);
        btn_save_back = footer.findViewById(R.id.btn_save_back);
        recyclerView = footer.findViewById(R.id.recyclerview_member);



        //设置footer布局的点击事件
        btn_save.setOnClickListener(onClickListener_save);
        btn_start.setOnClickListener(onClickListener_start);
        btn_end.setOnClickListener(onClickListener_end);
        btn_save_back.setOnClickListener(onClickListener_save_back);


        //设置成员member
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        memberAdapter = new MemberAdapter(context,new ArrayList<UserBean>());
        recyclerView.setAdapter(memberAdapter);

        //设置listview
        addActivityListAdapter = new AddActivityListAdapter(this, activityBeanList, new AddActivityListAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(int index) {
                activityBeanList.remove(index);
                addActivityListAdapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(addActivityListAdapter);
        listView.addFooterView(footer);





    }

    /**
     * 返回按键点击
     */
    @Override
    public void onBackPressed() {
      AlterDialogHelper.initSelectDialog("确认保存并返回上一页", AddActivityActivity.this, new OnAlterDialogSelected() {
          @Override
          public void onComfirm() {
//                Intent intent=new Intent(AddActivityActivity.this,AddTaskAcitivity.class);
//                EventBus.getDefault().post(activityBeanList);
//              AddTaskAcitivity.activityBeanList.addAll(activityBeanList);
//                startActivity(intent);
                finish();
          }

          @Override
          public void onCancel() {

          }
      });

    }

    /***************************处理点击事件********************************/
    /**
     * 选择开始日期
     */
    private View.OnClickListener onClickListener_start=new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            TaskHepler.showTimePickerView(context, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date) {
                    if (date.after(sDate)&&date.before(eDate)){
                        tv_start.setText(sdf.format(date));
                    }else
                    {
                        AlterDialogHelper.initErrorDialog("日期范围:"+sdf.format(sDate)+"-"+sdf.format(eDate),context);
                    }
                }
            });
        }
    };

    /**
     * 选择结束日期
     */
    private View.OnClickListener onClickListener_end=new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            TaskHepler.showTimePickerView(context, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date) {
                    if (tv_start.getText()==null||tv_start.getText().toString().equals("")){
                        AlterDialogHelper.initErrorDialog("请先选择开始日期",context);
                        return;
                    }
                    try {
                        if (date.after(sdf.parse(tv_start.getText().toString()))&&date.before(eDate)){
                            tv_end.setText(sdf.format(date));
                        }else
                        {
                            AlterDialogHelper.initErrorDialog("日期范围:"+tv_start.getText().toString()+"-"+sdf.format(eDate),context);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    /**
     * 保存添加的活动到list
     */
    private View.OnClickListener onClickListener_save=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //构造任务
            ActivityBean activityBean=new ActivityBean();
            Boolean flag=true;
            activityBean.setAname(et_name.getText().toString());
            activityBean.setAstate(0);
            activityBean.setContent(et_content.getText().toString());
            activityBean.setAid_tid(task.getTid());
            activityBean.setAresult(et_result.getText().toString());
            activityBean.setStartDate(tv_start.getText().toString());
            activityBean.setEndDate(tv_end.getText().toString());
            //设置任务成员
            activityBean.setSelectedMembers(selectedAccount);
            activityBeanList.add(activityBean);
            addActivityListAdapter.notifyDataSetChanged();

            //更新开始时间范围
            try {
                sDate=sdf.parse(activityBean.getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            et_name.setText("");
            et_content.setText("");
            et_result.setText("");
            tv_start.setText("");
            tv_end.setText("");
            memberAdapter.list.clear();
            memberAdapter.notifyDataSetChanged();

        }
    };

    /**
     * 保存新增关键活动信息并且保存退出到添加任务界面
     */
    private View.OnClickListener onClickListener_save_back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            EventBus.getDefault().postSticky(activityBeanList);
            AddTaskAcitivity.activityBeanList.addAll(activityBeanList);
            finish();
        }
    };
    /**
     * 活动之间的传递数据
     * @param requestCode
     * @param resultCode
     * @param data  被选中的用户accid 账号
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== MemberAdapter.CODE_ADD_USER&&resultCode== ContactSelectActivity.RESULT_OK)
        {
            selectedAccount = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
//            addActivityListAdapter.notifyDataSetChanged(
            ActivityHelper.getRxService()
                    .getUsers(selectedAccount)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<UserBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: "+e.getMessage());
                        }

                        @Override
                        public void onNext(List<UserBean> userBeans) {
                            memberAdapter.list=userBeans;
                            memberAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

}
