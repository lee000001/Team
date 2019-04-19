package com.netease.nim.demo.mangement.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.mangement.ManagementHelper;
import com.netease.nim.demo.mangement.adapter.TaskCalenderAdapter;
import com.netease.nim.demo.mangement.view.CustomCalender.CustomCalendar;
import com.netease.nim.demo.mangement.view.CustomCalender.DayFinish;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.suke.widget.SwitchButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskCalenderActivity extends UI {

    private CustomCalendar cal;
    private ListView listView;
    private List<TaskBean> taskBeanList;
    private TaskCalenderAdapter adapter;
    private View header;

    private static final String TAG = "TaskCalenderActivity";
    private TextView tv_tip;
    private SwitchButton switchButton;
    private Boolean isCreator;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_calender);

        setTitle(R.string.task_calender);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_calender;
        setToolBar(R.id.toolbar, options);
        cal = (CustomCalendar)findViewById(R.id.cal);
        tv_tip = findViewById(R.id.tv_tip_creator);
        switchButton= findViewById(R.id.switch_button);
        switchButton.setChecked(true);
        isCreator = false;
        listView = findViewById(R.id.listView);
        taskBeanList = new ArrayList<>();
        selectedDate = new Date();


//        //TODO 模拟请求当月数据
        final List<DayFinish> list = new ArrayList<>();


        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    tv_tip.setText("我创建");
                    isCreator=true;
                }else {
                    tv_tip.setText("与我有关");
                    isCreator=false;
                }
                getData();
            }

        });

        getData();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月");
        String s=format1.format(new Date().getTime());
//        cal.setRenwu(s, list);
        cal.setMonth(s);

        cal.setOnClickListener(new CustomCalendar.onClickListener() {
            @Override
            public void onLeftRowClick() {
                Toast.makeText(TaskCalenderActivity.this, "点击减箭头", Toast.LENGTH_SHORT).show();
                cal.monthChange(-1);
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                            TaskCalenderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        }catch (Exception e){
                        }
                    }
                }.start();
            }

            /**
             * 下一个月
             */
            @Override
            public void onRightRowClick() {
//                Toast.makeText(TaskCalenderActivity.this, "点击加箭头", Toast.LENGTH_SHORT).show();
                cal.monthChange(1);
                new Thread(){
                    @Override
                    public void run() {
                        try{
//                            Thread.sleep(1000);
                            TaskCalenderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        }catch (Exception e){
                        }
                    }
                }.start();
            }

            /**
             * 标题点击事件
             * @param monthStr
             * @param month
             */
            @Override
            public void onTitleClick(String monthStr, Date month) {
//                Toast.makeText(TaskCalenderActivity.this, "点击了标题："+monthStr, Toast.LENGTH_SHORT).show();
            }

            /**
             * 星期被点击
             * @param weekIndex
             * @param weekStr
             */
            @Override
            public void onWeekClick(int weekIndex, String weekStr) {
//                Toast.makeText(TaskCalenderActivity.this, "点击了星期："+weekStr, Toast.LENGTH_SHORT).show();
            }

            /**
             * 日期被点击
             * @param day
             * @param dayStr
             * @param finish
             */
            @Override
            public void onDayClick(int day, String dayStr, DayFinish finish) {
//                Toast.makeText(TaskCalenderActivity.this, "点击了日期："+dayStr, Toast.LENGTH_SHORT).show();
//                Log.w("", "点击了日期:"+dayStr);

                //显示点击日期的任务
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd");

                try {
                    selectedDate=sdf.parse(dayStr);
                    getData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void getData() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ManagementHelper.getRxService()
                .getByDate(Preferences.getUserAccount(),isCreator, sdf.format(selectedDate))
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
                        if(adapter==null){

                            taskBeanList.addAll(taskBeans);
                            initAdapter();
                        }else {
                            taskBeanList.clear();
                            taskBeanList.addAll(taskBeans);
//                            adapter.notifyDataSetChanged();
                            initAdapter();
                        }
                        Toast.makeText(TaskCalenderActivity.this, "当前日期有"+taskBeans.size()+"个任务", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initAdapter() {
        adapter = new TaskCalenderAdapter(this,taskBeanList);
        listView.setAdapter(adapter);
    }

}
