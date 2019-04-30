package com.netease.nim.demo.task.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.netease.nim.demo.R;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

/**
 * 任务变更小心页面
 */
public class TaskMessageActivity extends UI {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_message);
        setTitle(R.string.task_message);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.task_message;
        setToolBar(R.id.toolbar, options);

        listView = findViewById(R.id.listview_message);


    }
}
