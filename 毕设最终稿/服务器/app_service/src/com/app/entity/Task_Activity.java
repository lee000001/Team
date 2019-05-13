package com.app.entity;

import java.util.List;

import com.app.entity.ActivityBean;
import com.app.entity.UserInfo;

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
