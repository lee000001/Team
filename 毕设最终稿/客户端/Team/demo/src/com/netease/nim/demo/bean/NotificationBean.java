package com.netease.nim.demo.bean;

public class NotificationBean {
    private  String content;
    private String sender;
    private TaskBean task;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "NotificationBean{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", task=" + task +
                '}';
    }
}
