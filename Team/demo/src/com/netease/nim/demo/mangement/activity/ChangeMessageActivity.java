package com.netease.nim.demo.mangement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.MessageBean;
import com.netease.nim.demo.mangement.adapter.ChangeMessageAdapter;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

import java.util.List;

/**
 * 任务变更小心页面
 */
public class ChangeMessageActivity extends UI {

    private ListView listView;
    private List<MessageBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_message);
        setTitle(R.string.task_message);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_message;
        setToolBar(R.id.toolbar, options);

        Intent intent=getIntent();
        list = (List<MessageBean>) intent.getSerializableExtra("list");
        listView = findViewById(R.id.listview_message);
        ChangeMessageAdapter adapter=new ChangeMessageAdapter(this,list);
        listView.setAdapter(adapter);



    }
}
