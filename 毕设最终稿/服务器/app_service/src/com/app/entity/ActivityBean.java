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
 * “活动”实体类
 * @author lee
 *
 */
@Entity
@Table(name="activity",catalog="test")
public class ActivityBean {
	private  int aid;
	 private String aname="";
	 private String aresult="";
	 private String content="";
	 private int aid_tid;
	 private Date startDate;
	 private Date endDate;
	 private Integer astate;                //任务状态 0 未开始 ，1正在进行，2完成，3过期
	 private List<String> selectedMembers;  //忽视掉该字段隐射 ，，表示当前活动的中成员的id
	 private Boolean isEdit=false;
	 
	 
	 @Transient
	 public Boolean getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Transient
	public List<String> getSelectedMembers() {
		return selectedMembers;
	}
	public void setSelectedMembers(List<String> selectedMembers) {
		this.selectedMembers = selectedMembers;
	}
	
	@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="aid",unique=true,nullable=false)	
		 public int getAid() {
			return aid;
		}
		public void setAid(int tid) {
			this.aid = tid;
		}
		@Column(name="aname",length=256)
		public String getAname() {
			return aname;
		}
		public void setAname(String aname) {
			this.aname = aname;
		}
		@Column(name="aresult",length=512)
		public String getAresult() {
			return aresult;
		}
		public void setAresult(String aresult) {
			this.aresult = aresult;
		}
		@Column(name="aid_tid",length=11,nullable=false)
		public int getAid_tid() {
			return aid_tid;
		}
		public void setAid_tid(int aid_tid) {
			this.aid_tid = aid_tid;
		}
		@Column(name="startDate")
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		@Column(name="endDate")
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		@Column(name="astate")
		public Integer getAstate() {
			return astate;
		}
		public void setAstate(Integer astate) {
			this.astate = astate;
		}	
		@Column(name="acontent",length=512)
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
			return "ActivityBean [aid=" + aid + ", aname=" + aname + ", aresult=" + aresult + ", content=" + content
					+ ", aid_tid=" + aid_tid + ", startDate=" + startDate + ", endDate=" + endDate + ", astate="
					+ astate + ", selectedMembers=" + selectedMembers + "]";
		}
		
		
	

	 

}
