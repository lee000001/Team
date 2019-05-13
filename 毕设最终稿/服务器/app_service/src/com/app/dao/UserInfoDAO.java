package com.app.dao;

import java.util.List;

import com.app.entity.UserInfo;

public interface UserInfoDAO {
	public void register(UserInfo cond);
//	public void findById(String accid);
	 /**
	  * 通过id数组获取用户数组
	  * @return
	  */
	 public List<UserInfo> getAcitvityMember(List<String> selectedUids);
}
