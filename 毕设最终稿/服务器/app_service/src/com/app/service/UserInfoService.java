package com.app.service;

import java.util.List;

import com.app.entity.UserInfo;

public interface UserInfoService {
	public void register(UserInfo cond);
	 /**
	  * ͨ��id�����ȡ�û�����
	  * @return
	  */
	 public List<UserInfo> getAcitvityMember(List<String> selectedUids);
}
