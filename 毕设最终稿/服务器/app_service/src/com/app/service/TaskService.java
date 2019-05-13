package com.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.app.entity.ActivityBean;
import com.app.entity.DataAnalysis;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;

public interface TaskService {
	


	/**
	 * 获取用户有关的任务信息
	 * @param curPage 当前页数
	 * @param pageSize 每页的个数
	 * @param accid  账号
	 * @param 是否是创建者
	 * @return 
	 */
	public List<TaskBean> getTasks(int curPage,int pageSize,String accid,Boolean isCreator);
	
	/**
	 * 获取该用户创建或者与该用户有关的任务
	 * @param accid
	 * @return
	 */
	 public int getCount_task(String accid,Boolean isCreator);
	 
	 public void test();
	 public TaskBean findById(int tid);
	 public List<UserInfo> getUserInfoByTaskId(int tid);
	 public List<TaskBean> getAllTasks(String accid, Boolean isCreator,int tstate);
	 public boolean addmenber(int tid,List<String> addUids);
	 public BigInteger getTaskId();
	 public boolean  addTask(TaskBean task, List<ActivityBean> activities);
	 /**
	  * 搜索任务 
	  * @param accid
	  * @param isCreator
	  * @param key
	  * @return
	  */
	 public List<TaskBean> search(String accid, Boolean isCreator,String key);
	 /**
	  * 获取指定日期的任务数组
	  * @param accid
	  * @param isCreator
	  * @param date
	  * @return
	  */
	 public List<TaskBean> getByDate(String accid,Boolean isCreator,String date);
	 
	 /**
	  * 任务数据分析
	  * @param accid
	  * @param isCreator
	  * @return
	  */
	 public List<DataAnalysis> dataAnalysis(String accid,Boolean isCreator);
	 /**
	  * 删除任务
	  * @param task
	  */
	 public void deleteTask(TaskBean task);
	 /**
	  * 更新任务
	  * @param task
	  */
	 public void updateTask(TaskBean task);
	 
	 /**
	  * 通过tid 活动任务信息
	  * @param tid
	  * @return
	  */
	 public TaskBean getTaskById(int tid);
}
