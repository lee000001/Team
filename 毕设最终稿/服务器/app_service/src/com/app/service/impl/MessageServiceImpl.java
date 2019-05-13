package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.MessageDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.MessageBean;
import com.app.entity.UserInfo;
import com.app.service.MessageService;
import com.app.service.UserInfoService;
@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDAO messageDAO;

	@Override
	public List<MessageBean> getMsg(String accid) {
		// TODO Auto-generated method stub
		return messageDAO.getMsg(accid);
	}

	@Override
	public void lateMessage(List<ActivityBean> list) {
		// TODO Auto-generated method stub
		messageDAO.lateMessage(list);
	}

}
