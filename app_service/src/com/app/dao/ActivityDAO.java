package com.app.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.ActivityBean;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;

public interface ActivityDAO {
	/**
	 * ͨ������id ��ȡ�����Ļ
	 * @param tid
	 * @return
	 */
	public List<ActivityBean> getActivity(int tid);
	
	/**
	 * ͨ���id ��ȡ��Ӧ��Ա
	 * @param aid
	 * @return
	 */
	public List<UserInfo> getMembers(int aid);
	
	/**
	 * ���ؼ����������Ա
	 * @param aid
	 * @param tid
	 * @param selectedUids
	 * @return
	 */
	public Boolean addMembers(int aid, int tid, List<String> selectedUids);

	/**
	 * �ı�activity��״̬
	 * @param aid
	 * @param state
	 * @return
	 */
	public Boolean setFinish(int aid,int state);
	/**
	 * ͨ���û�accid�����ȡ�û�����
	 * @param Uids
	 * @return
	 */
	public List<UserInfo> getUsers(List<String> Uids);
	
	 /**
	  * ɾ���
	  * @param task
	  */
	 public void deleteActivity(ActivityBean activity);
	 /**
	  * ��������
	  * @param Activity
	  */
	 public void updateActivity(ActivityBean activity);
	
	 /**
	  * ͨ��tid �������Ϣ
	  * @param tid
	  * @return
	  */
	 public ActivityBean getActivityById(int aid);

	 /**
	  * ��ȡ�ͺ�Ļ��Ϣ
	  * @param accid
	  * @return
	  */
	 public List<ActivityBean> getLateActivity(String accid);

}
