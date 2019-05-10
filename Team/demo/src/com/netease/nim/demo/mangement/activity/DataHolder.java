package com.netease.nim.demo.mangement.activity;


import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;

public class DataHolder {


    public ActivityBean activity;

    public boolean checked;

    public DataHolder(ActivityBean activity, boolean checked) {
        this.activity = activity;
        this.checked = checked;
    }
}
