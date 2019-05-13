package com.netease.nim.demo.mangement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.netease.nim.demo.R;
import com.netease.nim.demo.mangement.activity.DataAnalysisActivity;
import com.netease.nim.demo.mangement.activity.TaskLateActivity;
import com.netease.nim.demo.mangement.activity.TaskManagementActivity;
import com.netease.nim.demo.mangement.activity.TaskCalenderActivity;
import com.netease.nim.demo.mangement.activity.TaskSearchActivity;
import com.netease.nim.demo.mangement.api.SnapChatAttachment;

import com.netease.nim.demo.task.activity.AddTaskAcitivity;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理界面Fragment
 * <p/>
 *
 */
public class ManagementFragment extends TFragment implements View.OnClickListener {


    private static final String TAG = "ManagementFragment";
    private  View view;
    private Button btn_custom;
    private Button btn_alter;
    private Button btn_calender;
    private Button btn_search;
    private Button btn_analysis;
    private Button btn_management;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.fragment_mangement, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_custom=view.findViewById(R.id.btn_custom);
        btn_custom.setOnClickListener(this);
        btn_alter = view.findViewById(R.id.btn_alter);
        btn_alter.setOnClickListener(this);
        btn_calender = view.findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);
        btn_search=view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_analysis = view.findViewById(R.id.btn_analysis);
        btn_analysis.setOnClickListener(this);
        btn_management = view.findViewById(R.id.btn_management);
        btn_management.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_custom:
                //添加任务
                startActivity(new Intent(getContext(), AddTaskAcitivity.class));
                break;
            case R.id.btn_alter:
                startActivity(new Intent(getContext(), TaskLateActivity.class));
                break;

            case R.id.btn_calender:
                startActivity(new Intent(getContext(), TaskCalenderActivity.class));
                break;

            case R.id.btn_search:
                startActivity(new Intent(getContext(), TaskSearchActivity.class));
                break;
            case R.id.btn_analysis:
                startActivity(new Intent(getContext(), DataAnalysisActivity.class));
                break;
            case R.id.btn_management:
                startActivity(new Intent(getContext(), TaskManagementActivity.class));
                break;

        }
    }







}


