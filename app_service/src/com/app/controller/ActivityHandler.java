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

import com.app.dao.ActivityDAO;
import com.app.entity.ActivityBean;
import com.app.entity.TaskBean;
import com.app.entity.Task_Activity;
import com.app.entity.UserInfo;
import com.app.service.ActivityService;
import com.app.service.UserInfoService;
import com.app.tool.JsonStringToObject;

@RequestMapping("/activity")
@Controller
public class ActivityHandler {

	@Autowired
	ActivityService activityService;


	@RequestMapping(value="/getActivities")
	@ResponseBody
	public List<ActivityBean> getAcitvities(
		@RequestParam(value="tid") int tid) {
		return activityService.getActivity(tid);
		
	}

	@RequestMapping(value="/getMembers")
	@ResponseBody
	public List<UserInfo> getAcitvityMembers(
		@RequestParam(value="aid") int aid) {
		return activityService.getMembers(aid);
		
	}
	
	@RequestMapping(value="/addMembers")
	@ResponseBody
	public Boolean addMembers(
		@RequestParam(value="aid") int aid,
		@RequestParam(value="tid") int tid,
		@RequestParam(value="selecedUids") List<String> selectedUids) {
		return activityService.addMembers(aid, tid, selectedUids);
		
	}
	@RequestMapping("/setFinish")
	@ResponseBody
	public Boolean setFinish(
			@RequestParam(value="aid")int aid,
			@RequestParam(value="state")int state
			){
		
		return activityService.setFinish(aid,state);
		
	}
	
	
	@RequestMapping("/getUsers")
	@ResponseBody
	public List<UserInfo> getUsers(
			@RequestParam(value="Uids")List<String> Uids
			){
		
		
		return activityService.getUsers(Uids);
		
	}
	
	@RequestMapping("/msg")
	@ResponseBody
	public String msg(){
		
		String s=new Date().toLocaleString()+"пео╒";
		return s;
		
	}

	

	
}
