package com.netease.nim.demo.task.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.netease.nim.demo.Constant;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.task.activity.TaskDetailActivity;
import com.netease.nim.demo.task.adapter.TaskDataAdapter;
import com.netease.nim.demo.task.api.RxTaskInfoService;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 任务Fragment
 * <p/>
 *
 */
public class TasksFragment extends TFragment {


    private static final String TAG = "taskfragment";
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private TaskDataAdapter dataAdapter;
    private View view;
    private Context context;
    private Activity activity;
    private  static  int curPage=0;
    private  static int pageSize=5;
    private List<TaskBean> datas=new ArrayList<>();
    int maxsize;
    private String accid;
    private View headerView;
    private Spinner spinner;
    private static  int tstate=-1;
    private SwitchButton switchButton;
    private TextView tv_tip;
    private  boolean isCreator=true;
    private Button btn_search;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accid=Preferences.getUserAccount();
        context=getContext();
        activity=getActivity();
        initView();
        getData();
    }

    private void initView() {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        listView = view.findViewById(R.id.listView);
        spinner = view.findViewById(R.id.spinner_category);
        tv_tip=view.findViewById(R.id.tv_tip_creator);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                getData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskBean task = (TaskBean) dataAdapter.getItem(i);
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("task", task);
                activity.startActivity(intent);
            }
        });


        initSpinner();

        initSwitch();

    }

    private void initSwitch() {
        switchButton = view.findViewById(R.id.switch_button);

        switchButton.setChecked(true);
        switchButton.isChecked();
        switchButton.toggle();     //switch state
        switchButton.toggle(true);//switch without animation
        switchButton.setShadowEffect(true);//disable shadow effect
        switchButton.setEnabled(true);//disable button
        switchButton.setEnableEffect(true);//disable the switch animation
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                if(isChecked) {
                    tv_tip.setText("我创建");
//                    ToastHelper.showToast(context,"创建者");
                    isCreator=true;

                }
                else {
                    tv_tip.setText("待完成");
//                    ToastHelper.showToast(context,"非创建者");
                    isCreator=false;
                }
                getData();
            }
        });
    }

    private void initSpinner() {
        //声明一个SimpleAdapter独享，设置数据与对应关系
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context, getCategory(), R.layout.task_category_spinner,
                new String[]{"state", "state_name"}, new int[]{
                R.id.img_state, R.id.textview});
        //绑定Adapter到Spinner中
        spinner.setAdapter(simpleAdapter);
        //Spinner被选中事件绑定。
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //parent为一个Map结构的和数据
                Map<String, Object> map = (Map<String, Object>) parent
                        .getItemAtPosition(position);

                if(map.get("state_name").equals("未分类")) tstate=-1;
                if(map.get("state_name").equals("未开始")) tstate=0;
                if(map.get("state_name").equals("进行中")) tstate=1;
                if(map.get("state_name").equals("已完成")) tstate=2;
                if(map.get("state_name").equals("已过期")) tstate=3;
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public List<Map<String, Object>> getCategory() {
        //生成数据源
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("state", R.drawable.iconback_dot_24px);
        map4.put("state_name", "未分类");
        list.add(map4);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", R.drawable.dot_blue_24px);
        map.put("state_name", "未开始");
        list.add(map);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("state", R.drawable.dot_red_24px);
        map2.put("state_name", "进行中");
        list.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("state", R.drawable.dot_green_24px);
        map3.put("state_name", "已完成");
        list.add(map3);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("state", R.drawable.dot_pink_24px);
        map5.put("state_name", "已过期");
        list.add(map5);
        return list;
    }

    private void initAdapter() {
        dataAdapter=new TaskDataAdapter(context,datas);
        listView.setAdapter(dataAdapter);

    }

    private void getData() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.APP_SERVICE_URL)//基础URL 建议以 / 结尾
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build();
        final RxTaskInfoService rxjavaService = retrofit.create(RxTaskInfoService.class);
        rxjavaService.getAll(accid,isCreator,tstate)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Subscriber<List<TaskBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<TaskBean> taskBeans) {

                        if(dataAdapter==null)   //初始化
                        {
                            datas.addAll(taskBeans);
                            Collections.reverse(datas);
                            initAdapter();
                        }
                        else
                        {
                            datas.clear();
                            datas.addAll(taskBeans);
                            Collections.reverse(datas);
                        }
                        refreshLayout.setRefreshing(false);
                        dataAdapter.notifyDataSetChanged();
                    }
                });
    }
    /**
     * listview滑动到顶部
     */
    public void scrollToTop() {
        if (listView != null) {
            listView.scrollTo(0,0);
        }

    }

}


