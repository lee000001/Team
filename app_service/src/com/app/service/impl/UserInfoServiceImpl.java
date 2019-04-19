package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserInfoDAO;
import com.app.entity.UserInfo;
import com.app.service.UserInfoService;
@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoDAO userInfoDAO;
	
	
	@Override
	public void register(UserInfo cond) {
		// TODO Auto-generated method stub
		userInfoDAO.register(cond);
		
	}


	@Override
	public List<UserInfo> getAcitvityMember(List<String> selectedUids) {
		// TODO Auto-generated method stub
		return userInfoDAO.getAcitvityMember(selectedUids);
	}

}
