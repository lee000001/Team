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




//    private void getData() {
//        ManagementHelper.getRxService()
//                .getDataAnalysis(Preferences.getUserAccount(),true)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<DataAnalysis>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<DataAnalysis> dataAnalyses) {
//                        list = dataAnalyses;
//                        setData();
//
//                    }
//                });
//    }
//
//    private void setData() {
//
//
//        int sum=0;
//        for(DataAnalysis d:list)
//        {
//            sum+=d.getValue();
//        }
//
//        entries.clear();
//
//        for(DataAnalysis d:list)
//        {
//            entries.add(new PieEntry(d.getValue()/sum, d.getLabel()));
//        }
//
//
//
//
//        mChart.setUsePercentValues(true); //设置是否显示数据实体(百分比，true:以下属性才有意义)
//
//        mChart.getDescription().setEnabled(false);
//
//        mChart.setExtraOffsets(5, 5, 5, 5);//饼形图上下左右边距
//
//
//
//        mChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]
//
//
//
//        //mChart.setCenterTextTypeface(mTfLight);//设置所有DataSet内数据实体（百分比）的文本字体样式
//
//        mChart.setCenterText("任务状态");//设置PieChart内部圆文字的内容
//
//
//
//        mChart.setDrawHoleEnabled(true);//是否显示PieChart内部圆环(true:下面属性才有意义)
//
//        mChart.setHoleColor(Color.WHITE);//设置PieChart内部圆的颜色
//
//
//
//        mChart.setTransparentCircleColor(Color.WHITE);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
//
//        mChart.setTransparentCircleAlpha(110);//设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
//
//        mChart.setHoleRadius(28f);//设置PieChart内部圆的半径(这里设置28.0f)
//
//        mChart.setTransparentCircleRadius(31f);//设置PieChart内部透明圆的半径(这里设置31.0f)
//
//
//
//        mChart.setDrawCenterText(true);//是否绘制PieChart内部中心文本（true：下面属性才有意义）
//
//
//
//        mChart.setRotationAngle(0);//设置pieChart图表起始角度
//
//        // enable rotation of the chart by touch
//
//        mChart.setRotationEnabled(true);//设置pieChart图表是否可以手动旋转
//
//        mChart.setHighlightPerTapEnabled(true);//设置piecahrt图表点击Item高亮是否可用
//
//
//
//        mChart.animateY(1400, Easing.EaseInOutQuad);
//
//        // mChart.spin(2000, 0, 360);
//
//
//
//        // 获取pieCahrt图列
//
//        Legend l = mChart.getLegend();
//
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//
//        l.setDrawInside(false);
//
//        l.setXEntrySpace(7f); //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
//
//        l.setYEntrySpace(0f); //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
//
//        l.setYOffset(0f);//设置比例块Y轴偏移量
//
//
//
//        // entry label styling
//
//        mChart.setEntryLabelColor(Color.WHITE);//设置pieChart图表文本字体颜色
//
////        mChart.setEntryLabelTypeface(mTfRegular);//设置pieChart图表文本字体样式
//
//        mChart.setEntryLabelTextSize(12f);//设置pieChart图表文本字体大小
//
//
//
////        PieDataSet dataSet = new PieDataSet(entries, "数据说明");
//
//
//        PieDataSet dataSet = new PieDataSet(entries, "任务状态");
//
//        dataSet.setDrawIcons(false);
//
//
//
//        dataSet.setSliceSpace(3f);
//
//        dataSet.setIconsOffset(new MPPointF(0, 40));
//
//        dataSet.setSelectionShift(5f);
//
//
//
//        // add a lot of colors
//
//
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//
//            colors.add(c);
//
//
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//
//            colors.add(c);
//
//
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//
//            colors.add(c);
//
//
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//
//            colors.add(c);
//
//
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//
//            colors.add(c);
//
//
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//
//
//        dataSet.setColors(colors);
//
//        //dataSet.setSelectionShift(0f);
//
//
//
//        PieData data = new PieData(dataSet);
//
//        data.setValueFormatter(new PercentFormatter());
//
//        data.setValueTextSize(11f);
//
//        data.setValueTextColor(Color.WHITE);
//
////        data.setValueTypeface(mTfLight);
//
//        mChart.setData(data);
//
//
//
//        // undo all highlights
//
//        mChart.highlightValues(null);
//
//
//
//        mChart.invalidate();
//
//    }
}
