package com.app.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.ActivityBean;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;

public interface ActivityDAO {
	/**
	 * 通过任务id 获取所属的活动
	 * @param tid
	 * @return
	 */
	public List<ActivityBean> getActivity(int tid);
	
	/**
	 * 通过活动id 获取相应成员
	 * @param aid
	 * @return
	 */
	public List<UserInfo> getMembers(int aid);
	
	/**
	 * 给关键活动添加任务成员
	 * @param aid
	 * @param tid
	 * @param selectedUids
	 * @return
	 */
	public Boolean addMembers(int aid, int tid, List<String> selectedUids);

	/**
	 * 改变activity的状态
	 * @param aid
	 * @param state
	 * @return
	 */
	public Boolean setFinish(int aid,int state);
	/**
	 * 通过用户accid数组获取用户数组
	 * @param Uids
	 * @return
	 */
	public List<UserInfo> getUsers(List<String> Uids);
	
	 /**
	  * 删除活动
	  * @param task
	  */
	 public void deleteActivity(ActivityBean activity);
	 /**
	  * 更新任务
	  * @param Activity
	  */
	 public void updateActivity(ActivityBean activity);
	
	 /**
	  * 通过tid 活动任务信息
	  * @param tid
	  * @return
	  */
	 public ActivityBean getActivityById(int aid);

	 /**
	  * 获取滞后的活动信息
	  * @param accid
	  * @return
	  */
	 public List<ActivityBean> getLateActivity(String accid);

}
