package com.netease.nim.demo.task.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.task.helper.AlterDialogHelper;
import com.netease.nim.demo.task.helper.DateHelper;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.demo.task.api.OnStepClick;

import java.util.Date;


public class TaskStep extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "Step";

    private ImageButton btn_start;
    private TextView tv_start;
    private TextView tv_end;
    private ImageButton btn_end;
    LinearLayout container;
    private int tid;
    private TextView tv_step;
    int index;
    private EditText et_activity_content;
    private EditText et_activity_name;
    private EditText et_activity_result;
    private ActivityBean activity;
    public Date sDate; //最迟时间和最早时间
    public Date eDate;
    private View btn_del;
    private  OnStepClick onStepClick;
    private  Button btn_save;

    public TaskStep(Context context,Date sDate,Date eDate,int tid,OnStepClick onStepClick) {
        super(context);
        this.index=index;
        this.sDate=sDate;
        this.eDate=eDate;
        this.tid=tid;
        this.onStepClick=onStepClick;
        LayoutInflater.from(context).inflate(R.layout.task_step,this,true);
        init(index);
    }
    public TaskStep(Context context, OnStepClick onStepClick) {
        super(context);

        this.onStepClick=onStepClick;
        LayoutInflater.from(context).inflate(R.layout.task_step,this,true);
        init(index);
    }
    public TaskStep(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.task_step,this,true);
        init(index);
    }
    public TaskStep(Context context,LinearLayout container) {
        super(context);
        this.container=container;

        LayoutInflater.from(context).inflate(R.layout.task_step,this,true);
        init(index);
    }



    private void init(final int index) {
        btn_start=findViewById(R.id.btn_startDate);
        btn_end=findViewById(R.id.btn_endDate);
        btn_save=findViewById(R.id.btn_save);
        tv_end=findViewById(R.id.tv_end);
        tv_start=findViewById(R.id.tv_start);
        et_activity_name=findViewById(R.id.et_activity_name);
        et_activity_content=findViewById(R.id.et_activity_content);
        et_activity_result=findViewById(R.id.et_activity_name);
        btn_del =findViewById(R.id.btn_del);
        container=findViewById(R.id.task_step_container);
        et_activity_name.setHint("活动"+String.valueOf(index+1));

        activity=new ActivityBean();
        activity.setAname("活动"+String.valueOf(index+1));
        activity.setAstate(0);

        btn_del.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_save.setOnClickListener(this);

    }


    /**
     * 构造活动信息
     */
    public void generateTaskStep()
    {
        activity.setAname(et_activity_name.getText().toString());

    }


    public ActivityBean getActivity() {
        return activity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_startDate:
                TaskHepler.showTimePickerView(getContext(),onTimeSelectListener_start);
                break;
            case R.id.btn_endDate:
                TaskHepler.showTimePickerView(getContext(),onTimeSelectListener_end);
                break;
            case R.id.btn_del:
                if(onStepClick!=null)
                    onStepClick.OnDeleteClick(index);
                break;
            case R.id.btn_save:
                if(onStepClick!=null){
                    generateActivity();
                    if(validate())
                    {
                        onStepClick.OnSaveClick(activity);
                    }
                    else {
                        AlterDialogHelper.initErrorDialog("请正确填写活动信息",getContext());
                    }
                }
                break;
        }
    }
    /**
     * 选择开始日期
     */
    private TimePickerView.OnTimeSelectListener onTimeSelectListener_start=new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date) {
            Date curDate=new Date();
            if(date.before(sDate)||date.after(eDate)){   //选中日期早于当前日期
                TaskHepler.initErrorDialog("范围："+TaskHepler.getTime(sDate)+"-"+TaskHepler.getTime(eDate),getContext());
            }
            else  {
                String start=TaskHepler.getTime(date);
                activity.setStartDate(DateHelper.getTime(date));
                sDate=date;
                tv_start.setText(start);
                if(curDate.before(date))  //开始日期早于当前日 任务状态为未开始
                    activity.setAstate(0);
                else
                    activity.setAstate(0);  //当前日期为开始日期，任务状态为进行中

            }
        }
    };
    /**
     * 选择结束日期
     */
    private TimePickerView.OnTimeSelectListener onTimeSelectListener_end=new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date) {
            if(activity.getStartDate()==null){   //没有选择开始日期
                TaskHepler.initErrorDialog("请先选择开始日期",getContext());
            }
            else {
                if(date.before(sDate)||date.after(eDate)){   //选中日期早于当前日期
                    TaskHepler.initErrorDialog("范围："+TaskHepler.getTime(sDate)+"-"+TaskHepler.getTime(eDate),getContext());
                }
                else {
                    String end=TaskHepler.getTime(date);
                    activity.setEndDate(DateHelper.getTime(date));
                    tv_end.setText(end);
                }

            }
        }
    };

    /**
     * 验证”activity"信息完整性
     * @return
     */
    public Boolean validate()
    {
        if(TextUtils.isEmpty(activity.getAname())) {
            return false;
        }
        if(activity.getStartDate()==null) {
            return false;
        }
        if(activity.getEndDate()==null) {
            return false;
        }
        if(activity.getAid_tid()!=tid) {
            return false;
        }
        if(TextUtils.isEmpty(activity.getContent())) {
            return false;
        }
        if(TextUtils.isEmpty(activity.getAresult())) {
            return false;
        }
        return  true;
    }

    /**
     *为"activity"对象赋值
     */
    public void generateActivity()
    {
        activity.setAname(et_activity_name.getText().toString());
        activity.setAid_tid(tid);
        activity.setContent(et_activity_content.getText().toString());
        activity.setAresult(et_activity_result.getText().toString());

    }
}
