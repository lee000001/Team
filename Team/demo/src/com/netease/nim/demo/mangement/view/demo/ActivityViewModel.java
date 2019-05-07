package com.netease.nim.demo.mangement.view.demo;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.mangement.view.checkListView.CheckViewModel;

/**
 * Created by kylin on 2018/2/28.
 */

public class ActivityViewModel extends CheckViewModel<ActivityBean> {


    private TextView tv_result;
    private TextView tv_content;
    private TextView tv_end;




    public ActivityViewModel(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.vm_test;
    }

    @Override
    protected void initView() {
        tv_result = contentView.findViewById(R.id.tv_title);
        tv_content = contentView.findViewById(R.id.tv_content);
        tv_end=contentView.findViewById(R.id.tv_end);
    }

    @Override
    protected void setDataToView() {
        tv_content.setText(data.getContent());
        tv_result.setText(data.getAresult());
        tv_end.setText(data.getEndDate());
    }

}
