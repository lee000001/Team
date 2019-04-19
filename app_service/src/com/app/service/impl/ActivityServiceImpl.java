package com.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ActivityDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;
import com.app.service.ActivityService;
import com.app.service.UserInfoService;
@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	ActivityDAO activityDAO;
	
	

	@Override
	public List<ActivityBean> getActivity(int  tid) {
		// TODO Auto-generated method stub
		return activityDAO.getActivity(tid);
	}



	@Override
	public List<UserInfo> getMembers(int aid) {
		// TODO Auto-generated method stub
		return activityDAO.getMembers(aid);
	}



	@Override
	public Boolean addMembers(int aid, int tid, List<String> selectedUids) {
		// TODO Auto-generated method stub
		return activityDAO.addMembers(aid, tid, selectedUids);
	}



	@Override
	public Boolean setFinish(int aid, int state) {
		// TODO Auto-generated method stub
		return activityDAO.setFinish(aid, state);
	}



	@Override
	public List<UserInfo> getUsers(List<String> Uids) {
		// TODO Auto-generated method stub
		return activityDAO.getUsers(Uids);
	}




}
