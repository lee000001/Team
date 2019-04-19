package com.app.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.TaskDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.DataAnalysis;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;
import com.app.service.TaskService;
import com.app.service.UserInfoService;
@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskDAO taskDAO;


	
	@Override
	public List<TaskBean> getTasks(int curPage, int pageSize, String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		return taskDAO.getTasks(curPage, pageSize, accid, isCreator);
	}


	@Override
	public int getCount_task(String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		return taskDAO.getCount_task(accid, isCreator);
	}
	public void test()
	{
		 taskDAO.test();
	}

	@Override
	public TaskBean findById(int tid) {
		// TODO Auto-generated method stub
		return taskDAO.findById(tid);
	}




	@Override
	public List<UserInfo> getUserInfoByTaskId(int tid) {
		// TODO Auto-generated method stub
		return taskDAO.getUserInfoByTaskId(tid);
	}


	@Override
	public List<TaskBean> getAllTasks(String accid, Boolean isCreator, int tstate) {
		// TODO Auto-generated method stub
		return taskDAO.getAllTasks(accid, isCreator, tstate);
	}


	@Override
	public boolean addmenber(int tid, List<String> addUids) {
		// TODO Auto-generated method stub
		return taskDAO.addmenber(tid, addUids);
	}


	@Override
	public BigInteger getTaskId() {
		// TODO Auto-generated method stub
		return taskDAO.getTaskId();
	}


	@Override
	public boolean  addTask(TaskBean task, List<ActivityBean> activities) {
		// TODO Auto-generated method stub
		return taskDAO.addTask(task, activities);
	}


	@Override
	public List<TaskBean> search(String accid, Boolean isCreator, String key) {
		// TODO Auto-generated method stub
		return taskDAO.search(accid, isCreator, key);
	}


	@Override
	public List<TaskBean> getByDate(String accid, Boolean isCreator, String date) {
		// TODO Auto-generated method stub
		return taskDAO.getByDate(accid, isCreator, date);
	}


	@Override
	public List<DataAnalysis> dataAnalysis(String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		return taskDAO.dataAnalysis(accid, isCreator);
	}


	@Override
	public void deleteTask(TaskBean task) {
		// TODO Auto-generated method stub
		taskDAO.deleteTask(task);
		
	}


	@Override
	public void updateTask(TaskBean task) {
		// TODO Auto-generated method stub
		taskDAO.updateTask(task);
	}

}
