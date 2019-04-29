package com.app.service;

import java.util.List;

import com.app.entity.MessageBean;
import com.app.entity.UserInfo;

public interface MessageService {

	/**
	 * 获取通知消息
	 * @param accid
	 * @return
	 */
	public List<MessageBean> getMsg(String accid);
}
