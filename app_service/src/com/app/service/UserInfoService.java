package com.app.service;

import java.util.List;

import com.app.entity.UserInfo;

public interface UserInfoService {
	public void register(UserInfo cond);
	 /**
	  * 通过id数组获取用户数组
	  * @return
	  */
	 public List<UserInfo> getAcitvityMember(List<String> selectedUids);
}
