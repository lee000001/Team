package com.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.app.entity.UserInfo;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * “消息”实体类
 * @author lee
 *
 */
@Entity
@Table(name="message",catalog="test")
public class MessageBean {
	private  int mid;
	 private String receiver="";
	 private String msg="";
	 private int mid_tid;
	 private int mid_aid;
	 private Integer mtype;              //0表示任务信息，1表示
	 private Integer isRead=0;            //0表示未读，1表示已读
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mid",unique=true,nullable=false)	
	 public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public MessageBean() {
		super();
	}
	@Column(name="receiver",length=32)	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column(name="msg",length=512)	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Column(name="mid_tid")	
	public int getMid_tid() {
		return mid_tid;
	}
	public void setMid_tid(int mid_tid) {
		this.mid_tid = mid_tid;
	}
	@Column(name="mid_aid")	
	public int getMid_aid() {
		return mid_aid;
	}
	public void setMid_aid(int mid_aid) {
		this.mid_aid = mid_aid;
	}
	@Column(name="mtype")	
	public Integer getMtype() {
		return mtype;
	}
	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}
	@Column(name="isRead")	
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
