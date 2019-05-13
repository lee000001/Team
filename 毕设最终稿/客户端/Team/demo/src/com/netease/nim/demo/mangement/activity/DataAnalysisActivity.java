package com.netease.nim.demo.mangement.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.DataAnalysis;
import com.netease.nim.demo.mangement.fragment.DataAnalysisFragment1;
import com.netease.nim.demo.mangement.fragment.DataAnalysisFragment2;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 2018/2/28.
 */

public class DataAnalysisActivity extends UI{

    private PieChart mChart;

    private static final String TAG = "DataAnalysisActivity";
    private ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    private List<DataAnalysis> list;
    private int sum;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private FragmentTransaction transition;
    private DataAnalysisFragment1 fragment1;
    private  Fragment  currentFragment=new Fragment();
    private DataAnalysisFragment2 fragment2;
    private TextView tv_tip;
    private SwitchButton switchButton;
    private Boolean isCreator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_data_analysis_activity);
        setTitle(R.string.task_data_analysis);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_data_analysis;
        setToolBar(R.id.toolbar, options);
        switchButton = findViewById(R.id.switch_button);
        tv_tip = findViewById(R.id.tv_tip_creator);
        isCreator = true;
        switchButton.setChecked(isCreator);
//       mChart = findViewById(R.id.pieChart);
//       getData();
        fragmentManager = getSupportFragmentManager();

        fragments = new ArrayList<>();
        fragment1=new DataAnalysisFragment1();
        fragment2 = new DataAnalysisFragment2();

        switchFragment(fragment1);

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                if(isChecked) {
                    tv_tip.setText("我创建");
//                    ToastHelper.showToast(context,"创建者");
                   switchFragment(fragment1);
                   isCreator=true;

                }
                else {
                    tv_tip.setText("待完成");
//                    ToastHelper.showToast(context,"非创建者");
                    switchFragment(fragment2);
                    isCreator=false;
                }

            }
        });


    }
    //首先需要先实例好n个全局Fragment
    private  void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
//第一次使用switchFragment()时currentFragment为null，所以要判断一下
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.add(R.id.container, targetFragment,targetFragment.getClass().getName());
             } else {
                transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;

        transaction.commit();
    }


}
