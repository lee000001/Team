package com.app.service;

import java.util.List;

import com.app.entity.MessageBean;
import com.app.entity.UserInfo;

public interface MessageService {

	/**
	 * ��ȡ֪ͨ��Ϣ
	 * @param accid
	 * @return
	 */
	public List<MessageBean> getMsg(String accid);
}
