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

/**
 * “任务”实体类
 * @author lee
 *
 */
@Entity
@Table(name="task",catalog="test")
public class TaskBean {
	private  int tid;
	 private String tname="";
	 private String ttype="";
	 private String tcreator="";
	 private Date tcreateDate;
	 private Date tstartDate;
	 private Date tendDate;
	 private String tcontent;
	 private String tresult;
	 private Integer tstate;  //任务状态 0 未开始 ，1正在进行，2完成，3过期
	 private Boolean isEdit=false;
	 
	 
	 @Transient
	 public Boolean getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}


	@JsonIgnore
	 private Set<UserInfo> users=new HashSet<UserInfo>();//任务持有用户的集合
//	 @JsonIgnore
//	 private Set<ActivityBean> activities=new HashSet<>(); //"任务"持有关键活动集合

	 @ManyToMany(mappedBy="tasks",fetch=FetchType.EAGER)//把教师交给学生控制
	 public Set<UserInfo> getUsers() {
			return users;
		}
	 	public void setUsers(Set<UserInfo> users) {
			this.users = users;
		}
//	@OneToMany(mappedBy="task",cascade= {CascadeType.REMOVE})
//	public Set<ActivityBean> getActivities() {
//			return activities;
//		}
//		public void setActivities(Set<ActivityBean> activities) {
//			this.activities = activities;
//		}
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="tid",unique=true,nullable=false)	
		 public int getTid() {
			return tid;
		}
		public void setTid(int tid) {
			this.tid = tid;
		}		
	   
	
	

	@Column(name="tname",length=64)
	 public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	@Column(name="ttype",length=16)
	public String getTtype() {
		return ttype;
	}
	public void setTtype(String ttype) {
		this.ttype = ttype;
	}
	@Column(name="tcreator",length=32)
	public String getTcreator() {
		return tcreator;
	}
	public void setTcreator(String tcreator) {
		this.tcreator = tcreator;
	}
	
	@Column(name="tcreateDate")
	public Date getTcreateDate() {
		return tcreateDate;
	}
	public void setTcreateDate(Date tcreateDate) {
		this.tcreateDate = tcreateDate;
	}
	
	@Column(name="tstartDate")
	public Date getTstartDate() {
		return tstartDate;
	}
	public void setTstartDate(Date tstartDate) {
		this.tstartDate = tstartDate;
	}
	
	@Column(name="tendDate")
	public Date getTendDate() {
		return tendDate;
	}
	public void setTendDate(Date tendDate) {
		this.tendDate = tendDate;
	}
	@Column(name="tcontent",length=1024)
	public String getTcontent() {
		return tcontent;
	}
	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}
	@Column(name="tresult",length=512)
	public String getTresult() {
		return tresult;
	}
	public void setTresult(String tresult) {
		this.tresult = tresult;
	}
	@Column(name="tstate")
	 public Integer getTstate() {
	        return tstate;
	    }

	    public void setTstate(Integer tstate) {
	        this.tstate = tstate;
	    }

	
	@Override
	public String toString() {
		return "TaskBean [tid=" + tid + ", tname=" + tname + ", ttype=" + ttype + ", tcreator=" + tcreator
				+ ", tcreateDate=" + tcreateDate + ", tstartDate=" + tstartDate + ", tendDate=" + tendDate
				+ ", tcontent=" + tcontent + ", tresult=" + tresult + "]";
	}
	

	 

}
