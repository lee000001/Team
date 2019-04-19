package com.netease.nim.demo.bean;

import java.io.Serializable;

/**
 * 任务实体类
 */
public class TaskBean implements Serializable {

    private int tid;                  //任务编号
    private String tname;                //任务名称
    private String ttype;                //任务类型
    private String tcreator;            //任务创建者
    private String tcreateDate;        //任务创建日期
    private String tstartDate;         //任务开始日期
    private String tendDate;            //任务结束日期
    private String tcontent;            //任务内容
    private String tresult;             //任务关键成果
    private Integer tstate;                //任务状态 0 未开始 ，1正在进行，2完成，3过期
    private  Boolean isEdit=false;

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }

    public Integer getTstate() {
        return tstate;
    }

    public void setTstate(Integer tstate) {
        this.tstate = tstate;
    }


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getTcreator() {
        return tcreator;
    }

    public void setTcreator(String tcreator) {
        this.tcreator = tcreator;
    }

    public String getTcreateDate() {
        return tcreateDate;
    }

    public void setTcreateDate(String tcreateDate) {
        this.tcreateDate = tcreateDate;
    }

    public String getTstartDate() {
        return tstartDate;
    }

    public void setTstartDate(String tstartDate) {
        this.tstartDate = tstartDate;
    }

    public String getTendDate() {
        return tendDate;
    }

    public void setTendDate(String tendDate) {
        this.tendDate = tendDate;
    }

    public String getTcontent() {
        return tcontent;
    }

    public void setTcontent(String tcontent) {
        this.tcontent = tcontent;
    }

    public String getTresult() {
        return tresult;
    }

    public void setTresult(String tresult) {
        this.tresult = tresult;
    }

    public TaskBean() {
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", ttype='" + ttype + '\'' +
                ", tcreator='" + tcreator + '\'' +
                ", tcreateDate='" + tcreateDate + '\'' +
                ", tstartDate='" + tstartDate + '\'' +
                ", tendDate='" + tendDate + '\'' +
                ", tcontent='" + tcontent + '\'' +
                ", tresult='" + tresult + '\'' +
                ", tstate=" + tstate +
                '}';
    }
}
