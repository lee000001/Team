package com.netease.nim.demo.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * “活动”实体类
 * @author lee
 *
 */
public class ActivityBean implements Serializable {
    private  int aid;
    private String aname="";
    private String aresult="";
    private int aid_tid;
    private String content="";


    private String startDate;
    private String endDate;
    private int astate;                //任务状态 0 未开始 ，1正在进行，2完成，3过期

    private List<String> selectedMembers;  //忽视掉该字段隐射 ，，表示当前活动的中成员的id



    public List<String> getSelectedMembers() {
        return selectedMembers;
    }
    public void setSelectedMembers(List<String> selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAresult() {
        return aresult;
    }

    public void setAresult(String aresult) {
        this.aresult = aresult;
    }

    public int getAid_tid() {
        return aid_tid;
    }

    public void setAid_tid(int aid_tid) {
        this.aid_tid = aid_tid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAstate() {
        return astate;
    }

    public void setAstate(int astate) {
        this.astate = astate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public ActivityBean() {
        super();
    }
    @Override
    public String toString() {
        return "ActivityBean [aid=" + aid + ", aname=" + aname + ", aresult=" + aresult + ", aid_tid=" + aid_tid
                + ", startDate=" + startDate + ", endDate=" + endDate + ", astate=" + astate + "]";
    }

}
