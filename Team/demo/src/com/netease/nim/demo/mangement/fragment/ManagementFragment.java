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
import com.netease.nim.demo.mangement.activity.DataManagementActivity;
import com.netease.nim.demo.mangement.activity.TaskCalenderActivity;
import com.netease.nim.demo.mangement.activity.TaskSearchActivity;
import com.netease.nim.demo.mangement.api.SnapChatAttachment;
import com.netease.nim.demo.mangement.activity.MultiselectActivity;
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
    private EditText et_msg;
    private List<String> memberUids;
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

//        et_msg = view.findViewById(R.id.et_msg);
        memberUids = new ArrayList<>();
        memberUids.add("10001");
        memberUids.add("10004");

    }

    private void sendCustomMsg() {
        SnapChatAttachment attachment=new SnapChatAttachment();

        IMMessage message = MessageBuilder.createCustomMessage(
                "10001", SessionTypeEnum.P2P,"下次", attachment
        );
        NIMClient.getService(MsgService.class).sendMessage(message, false);
    }

    private void SendMessage(String msg, List<String> membersUids) {
        // 该帐号为示例，请先注册
        String account = "10001";
        for(String accid:membersUids)
        {
            // 以单聊类型为例
            SessionTypeEnum sessionType = SessionTypeEnum.P2P;
            String text = "（任务提示消息）"+msg;
            // 创建一个文本消息
            IMMessage textMessage = MessageBuilder.createTextMessage(accid, sessionType, text);
            // 发送给对方
            NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
        }
    }

    private void send() throws JSONException {
        // 构造自定义通知，指定接收者
        CustomNotification notification = new CustomNotification();
        String receiverId="10001";
        SessionTypeEnum sessionType=SessionTypeEnum.P2P;
        notification.setSessionId(receiverId);
        notification.setSessionType(sessionType);

// 构建通知的具体内容。为了可扩展性，这里采用 json 格式，以 "id" 作为类型区分。
        JSONObject json = new JSONObject();
        json.put("id", "2");
        JSONObject data = new JSONObject();
        data.put("body", "the_content_for_display");
        data.put("url", "url_of_the_game_or_anything_else");
        json.put("data", data);
        notification.setContent(json.toString());

// 设置该消息需要保证送达
        notification.setSendToOnlineUserOnly(false);

// 设置 APNS 的推送文本
        notification.setApnsText("the_content_for_apns");

// 自定义推送属性
        Map<String,Object> pushPayload = new HashMap<>();
        pushPayload.put("key1", "payload 1");
        pushPayload.put("key2", 2015);
        notification.setPushPayload(pushPayload);
        CustomNotificationConfig config = new CustomNotificationConfig();
        config.enablePush = true;
        config.enableUnreadCount = true;
        notification.setConfig(config);

// 发送自定义通知
        NIMClient.getService(MsgService.class).sendCustomNotification(notification);

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
                startActivity(new Intent(getContext(), MultiselectActivity.class));
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
                startActivity(new Intent(getContext(), DataManagementActivity.class));
                break;

        }
    }







}


