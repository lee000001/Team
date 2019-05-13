package com.app.controller;

import java.util.ArrayList;
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

import com.app.entity.Task_Activity;
import com.app.entity.UserInfo;
import com.app.service.UserInfoService;
import com.app.tool.JsonStringToObject;

@RequestMapping("/api")
@Controller
public class UserInfoHandler {

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping(value="/register",method = RequestMethod.POST)
	@ResponseBody
	public void register(@RequestBody String json)
	{

		UserInfo ui=JsonStringToObject.jsonToObject(json, UserInfo.class);
		userInfoService.register(ui);
		System.out.println(json);
		return;
		
	}
//	

	@RequestMapping(value="/getAcitvityMember")
	@ResponseBody
	public List<UserInfo> getAcitvityMember(
		@RequestParam(value="selectedUids") List<String> selectedUids) {
		return userInfoService.getAcitvityMember(selectedUids);	
		
	}


	
}
