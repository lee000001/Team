package com.netease.nim.demo.bean;

import java.util.List;

/**
 * 用于连接task 和activity实体类 进行post请求的中间临时类
 */
public class Task_Activity {
    private List<ActivityBean> activities;
    private TaskBean task;
    public List<ActivityBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityBean> activities) {
        this.activities = activities;
    }

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Task_Activity{" +
                "activities=" + activities +
                ", task=" + task +
                '}';
    }
}
