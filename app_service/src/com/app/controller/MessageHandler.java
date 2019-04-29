package com.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.entity.MessageBean;
import com.app.entity.Task_Activity;
import com.app.entity.UserInfo;
import com.app.service.MessageService;
import com.app.service.UserInfoService;
import com.app.tool.JsonStringToObject;

@RequestMapping("/message")
@Controller
public class MessageHandler {

	@Autowired
	MessageService messageService;

	
	@RequestMapping("/msg")
	@ResponseBody
	public List<MessageBean> getMsg(@RequestParam(value="accid") String accid){
		return messageService.getMsg(accid);
			
	}


	
}
