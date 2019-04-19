package com.netease.nim.demo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.bigkoo.pickerview.TimePickerView;
import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.Task_Activity;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.bean.TaskBean;

import com.netease.nim.demo.task.helper.AlterDialogHelper;
import com.netease.nim.demo.task.helper.DateHelper;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddTaskAcitivity extends UI implements View.OnClickListener {
    private static final String TAG = "AddTaskAcitivity";
    private CircleImageView btn_start;
    private CircleImageView btn_end;
    private TaskBean task=new TaskBean();
    private EditText et_task_name;
    private EditText et_content,et_result;
    private TextView tv_start,tv_end;
    private List<ActivityBean> activityBeanList=new ArrayList<>();
    private Button btn_add_activity;
    private Button btn_save;
    public static int CODE_SEND=0;
    public static int CODE_RESULT=1;
    private int tid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        setTitle(R.string.task_add_task);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId =R.string.task_add_task;
        setToolBar(R.id.toolbar, options);

        btn_end=findViewById(R.id.btn_endDate);
        btn_start=findViewById(R.id.btn_startDate);
        tv_end=findViewById(R.id.tv_end);
        tv_start=findViewById(R.id.tv_start);
        btn_add_activity=findViewById(R.id.btn_add_activity);
        btn_save=findViewById(R.id.btn_save);
        et_task_name=findViewById(R.id.et_task_name);
        et_content=findViewById(R.id.et_task_content);
        et_result=findViewById(R.id.et_task_result);

        setTaskId(); //设置任务id

        btn_add_activity.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        // 注册订阅者
        EventBus.getDefault().register(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_startDate:
                TaskHepler.showTimePickerView(AddTaskAcitivity.this,onTimeSelectListener_start);
                break;
            case R.id.btn_endDate:
                TaskHepler.showTimePickerView(AddTaskAcitivity.this,onTimeSelectListener_end);
                break;
            case R.id.btn_add_activity:
                generateTask();
                if(!validate())
                {
                    AlterDialogHelper.initErrorDialog("请先填写任务信息",AddTaskAcitivity.this);
                    break;
                }
               //跳转到添加活动界面
                Intent intent=new Intent(AddTaskAcitivity.this,AddActivityActivity.class);
                intent.putExtra("task",task);
                intent.putExtra("activities", (Serializable) activityBeanList);
//                startActivityForResult(intent,CODE_SEND);
                startActivity(intent);
                break;
            case R.id.btn_save:
                save();
                break;
        }
    }





    /**
     * 保存“task”
     */
    private void save() {
        if(activityBeanList==null||activityBeanList.size()==0)
        {
            TaskHepler.initErrorDialog("请添加关键活动",AddTaskAcitivity.this);
            return;
        }
        generateTask();
        if(validate())
        {
            Task_Activity t=new Task_Activity();
            t.setActivities(activityBeanList);
            t.setTask(task);
            TaskHepler.addTask(t,AddTaskAcitivity.this);
        }else {
            TaskHepler.initErrorDialog("请正确填写任务信息",AddTaskAcitivity.this);
        }
    }

//    /**
//     * 从“添加活动界面”传过来的“关键活动信息"
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==CODE_SEND||resultCode==CODE_RESULT)
//        {
//            if(data.getSerializableExtra("activities")!=null)
//                if (activityBeanList==null){
//                    activityBeanList=new ArrayList<>();
//                    activityBeanList.addAll((List<ActivityBean>) data.getSerializableExtra("activities"));
//                    Log.d(TAG, "onActivityResult:关键活动数量= "+activityBeanList.size());
//                }
//
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<ActivityBean> list) {
        //接受从addActivityActivity 中传递的activities
        if(list!=null){
            activityBeanList=list;
        }
    }

    /**
     * 选择开始日期
     */
    private TimePickerView.OnTimeSelectListener onTimeSelectListener_start=new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date) {
            Date curDate=new Date();
            if(date.before(curDate)){   //选中日期早于当前日期
                TaskHepler.initErrorDialog("日期不得早于当前日期",AddTaskAcitivity.this);
            }
            else {
                String start=TaskHepler.getTime(date);
                task.setTcreateDate(TaskHepler.getTime(curDate));
                task.setTstartDate(start);
                tv_start.setText(start);
                if(curDate.before(date))  //开始日期早于当前日 任务状态为未开始
                    task.setTstate(0);
                else
                    task.setTstate(1);  //当前日期为开始日期，任务状态为进行中

            }
        }
    };
    /**
     * 选择结束日期
     */
    private TimePickerView.OnTimeSelectListener onTimeSelectListener_end=new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date) {
            if(task.getTstartDate()==null|| TextUtils.isEmpty(task.getTstartDate())){   //没有选择开始日期
                TaskHepler.initErrorDialog("请先选择开始日期",AddTaskAcitivity.this);
            }
            else {
                try {
                    if(date.before(TaskHepler.getTime(task.getTstartDate()))){   //选中日期早于开始日期
                        TaskHepler.initErrorDialog("结束日期不得早于开始日期",AddTaskAcitivity.this);
                    }
                    else {
                        String end=TaskHepler.getTime(date);
                        task.setTendDate(end);
                        tv_end.setText(end);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 验证”task"信息完整性
     * @return
     */
    public Boolean validate()
    {
        if(TextUtils.isEmpty(task.getTname())) {
            return false;
        }
        if(TextUtils.isEmpty(task.getTstartDate())) {
            return false;
        }
        if(TextUtils.isEmpty(task.getTendDate())) {
            return false;
        }
        if(TextUtils.isEmpty(task.getTcontent())) {
            return false;
        }
        if(TextUtils.isEmpty(task.getTresult())) {
            return false;
        }
        if(TextUtils.isEmpty(task.getTcreator())) {
            return false;
        }
        if(task.getTid()==0)
        {
            return false;
        }
//        if(activityBeanList==null) {
//            return false;
//        }
        return  true;
    }

    /**
     *为"task"对象赋值
     */
    public void generateTask()
    {
        task.setTname(et_task_name.getText().toString());
        task.setTcreator(Preferences.getUserAccount());
        task.setTcontent(et_content.getText().toString());
        task.setTresult(et_result.getText().toString());
        task.setTid(tid);
    }

    /**
     * 设置任务id
     */
    public void setTaskId()
    {
        TaskHepler.getRxService().getTaskId()
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tid =(int)integer;
                        Log.d(TAG, "onNext: 当前任务主键"+integer);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

}
