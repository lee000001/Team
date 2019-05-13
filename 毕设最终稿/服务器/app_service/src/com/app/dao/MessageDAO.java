package com.app.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.ActivityBean;
import com.app.entity.MessageBean;
import com.app.entity.UserInfo;

public interface MessageDAO {
	/**
	 * 根据accid获取消息
	 * @param accid
	 * @return
	 */
	public List<MessageBean> getMsg(String accid);
	/**
	 * 响应滞后消息
	 * @param list
	 */
	public void lateMessage(List<ActivityBean> list);
}
