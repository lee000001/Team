package com.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
@Entity
@Table(name="userinfo",catalog="test")
public class UserInfo {
	 private  String accid="";
	 private String upassword="";
	 private String nickname="";
	 private String icon="";
	 private String email="";
	 private String sign="";
	 private Date birth;
	 private String mobile="";
	 private String gender="";
	 private String ex="";
	 @JsonIgnore
	 private Set<TaskBean> tasks=new HashSet<TaskBean>();//用户持有任务的集合
	 
	 @ManyToMany(cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	 @JoinTable(                                
	            name="user_task",                    //中间表的名字
	            joinColumns= {@JoinColumn(name="accid")},        //外键的字段
	            inverseJoinColumns= {@JoinColumn(name="tid")})    //反转控制字段的名字
	public Set<TaskBean> getTasks() {
		return tasks;
	}
	public void setTasks(Set<TaskBean> tasks) {
		this.tasks = tasks;
	}
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="accid",unique=true,nullable=false,length=32)	
	 public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	@Column(name="upassword",length=64)
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	@Column(name="nickname",length=64)
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column(name="icon",length=256)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name="email",length=64)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="sign",length=256)
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Column(name="birth")
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Column(name="mobile",length=11)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name="gender",length=1)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name="ex",length=1024)
	public String getEx() {
		return ex;
	}
	public void setEx(String ex) {
		this.ex = ex;
	}
	public UserInfo() {
		super();
	}
	public UserInfo(int i, String string, String string2) {
		this.accid=String.valueOf(i);
		this.nickname=string;
		this.upassword=string2;
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserInfo [accid=" + accid + ", upassword=" + upassword + ", nickname=" + nickname + ", icon=" + icon
				+ ", email=" + email + ", sign=" + sign + ", birth=" + birth + ", mobile=" + mobile + ", gender="
				+ gender + ", ex=" + ex + ", tasks=" + tasks + "]";
	}
	
	
	 
	
}
