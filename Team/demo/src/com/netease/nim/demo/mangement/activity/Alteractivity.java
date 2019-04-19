package com.netease.nim.demo.mangement.activity;

import android.os.Bundle;

import com.netease.nim.demo.R;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;

public class Alteractivity  extends UI {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_activity);
        setTitle(R.string.activity_detail);

        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.activity_detail;
        setToolBar(R.id.toolbar, options);

    }

}
