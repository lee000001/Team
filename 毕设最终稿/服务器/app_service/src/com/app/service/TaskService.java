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
	 * ��ȡ�û��йص�������Ϣ
	 * @param curPage ��ǰҳ��
	 * @param pageSize ÿҳ�ĸ���
	 * @param accid  �˺�
	 * @param �Ƿ��Ǵ�����
	 * @return 
	 */
	public List<TaskBean> getTasks(int curPage,int pageSize,String accid,Boolean isCreator);
	
	/**
	 * ��ȡ���û�������������û��йص�����
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
	  * �������� 
	  * @param accid
	  * @param isCreator
	  * @param key
	  * @return
	  */
	 public List<TaskBean> search(String accid, Boolean isCreator,String key);
	 /**
	  * ��ȡָ�����ڵ���������
	  * @param accid
	  * @param isCreator
	  * @param date
	  * @return
	  */
	 public List<TaskBean> getByDate(String accid,Boolean isCreator,String date);
	 
	 /**
	  * �������ݷ���
	  * @param accid
	  * @param isCreator
	  * @return
	  */
	 public List<DataAnalysis> dataAnalysis(String accid,Boolean isCreator);
	 /**
	  * ɾ������
	  * @param task
	  */
	 public void deleteTask(TaskBean task);
	 /**
	  * ��������
	  * @param task
	  */
	 public void updateTask(TaskBean task);
	 
	 /**
	  * ͨ��tid �������Ϣ
	  * @param tid
	  * @return
	  */
	 public TaskBean getTaskById(int tid);
}
