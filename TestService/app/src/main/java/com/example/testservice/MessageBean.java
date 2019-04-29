package com.example.testservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * “消息”实体类
 * @author lee
 *
 */

public class MessageBean {
	private  int mid;
	private String receiver="";
	private String msg="";
	private int mid_tid;
	private int mid_aid;
	private Integer mtype;              //0表示任务信息，1表示
	private Integer isRead=0;            //0表示未读，1表示已读

	public MessageBean() {
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMid_tid() {
		return mid_tid;
	}

	public void setMid_tid(int mid_tid) {
		this.mid_tid = mid_tid;
	}

	public int getMid_aid() {
		return mid_aid;
	}

	public void setMid_aid(int mid_aid) {
		this.mid_aid = mid_aid;
	}

	public Integer getMtype() {
		return mtype;
	}

	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "MessageBean{" +
				"mid=" + mid +
				", receiver='" + receiver + '\'' +
				", msg='" + msg + '\'' +
				", mid_tid=" + mid_tid +
				", mid_aid=" + mid_aid +
				", mtype=" + mtype +
				", isRead=" + isRead +
				'}';
	}
}
