package com.app.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.app.entity.ActivityBean;
import com.app.entity.DataAnalysis;
import com.app.entity.TaskBean;
import com.app.entity.Task_Activity;

import com.app.entity.UserInfo;
import com.app.service.TaskService;
import com.app.service.UserInfoService;
import com.app.tool.JsonStringToObject;
import com.app.tool.JsonUtil;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;



@RequestMapping("/task")
@Controller
public class TaskHandler {

	@Autowired
	TaskService taskService;

	
	/**
	 * 获取全部任务信息
	 * @return
	 */
	@RequestMapping("/getAllTasks")
	@ResponseBody
	public List<TaskBean> getAllTasks(
			@RequestParam(value="accid") String accid,
			@RequestParam(value="isCreator") Boolean isCreator,
			@RequestParam(value="tstate")int tstate){
		
		List<TaskBean> list=taskService.getAllTasks(accid,isCreator,tstate);
		return list;
		
	}
	
	/**
	 * 分页获取
	 * @return
	 */
	@RequestMapping("/getTasks")
	@ResponseBody
	public List<TaskBean> getTasks(
		@RequestParam(value="curPage") int curPage,
		@RequestParam(value="pageSize") int pageSize,
		@RequestParam(value="accid") String accid,
		@RequestParam(value="isCreator") Boolean isCreator)
	{
			List<TaskBean> list=taskService.getTasks(curPage,pageSize,accid,isCreator);
			System.out.println(pageSize);
			return list;
		
	}
	
	
	
	/**
	 * 获取“任务信息”分页数
	 * @return
	 */
	@RequestMapping("/getCount")
	@ResponseBody
	public int getPageTask(
			@RequestParam(value="accid") String accid,
			@RequestParam(value="isCreator") Boolean isCreator){
		
		return taskService.getCount_task(accid, isCreator);
		
	}
	
//	
	@RequestMapping("/test")
	@ResponseBody
	public void test(){
		taskService.test();
		
	}	


	/**
	 * 
	 * @param accid
	 * @param isCreator
	 * @return
	 */
	@RequestMapping("/getUsersByTid")
	@ResponseBody
	public List<UserInfo> getUsersByTid(
			@RequestParam(value="tid") int tid){
		
		return taskService.getUserInfoByTaskId(tid);
		
	}
	/**
	 * 给任务添加任务成员
	 * @param accid
	 * @param addUids
	 * @return
	 */
	@RequestMapping("/addmenber")
	@ResponseBody
	public boolean addMenber(
			@RequestParam(value="tid") int tid,
			@RequestParam(value="addUids") List<String> addUids){
		
		return taskService.addmenber(tid, addUids);
		
	}
	@RequestMapping("/getTaskId")
	@ResponseBody
	public BigInteger getTaskId(){
		
		return taskService.getTaskId();
		
	}
	

	/**
	 * 添加任务和关键活动
	 * @param accid
	 * @param addUids
	 * @return
	 */

	@RequestMapping(value="/addTask",method = RequestMethod.POST)
	@ResponseBody
	public void addTest(@RequestBody Task_Activity t) {	
		taskService.addTask(t.getTask(), t.getActivities());
		System.out.println(t.toString());
		
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public List<TaskBean> search(
			@RequestParam(value="accid") String accid,
			@RequestParam(value="isCreator") Boolean isCreator,
			@RequestParam(value="key")String key){
		
		List<TaskBean> list=taskService.search(accid,isCreator,key);
		return list;
		
	}
	
	@RequestMapping("/getByDate")
	@ResponseBody
	public List<TaskBean> getByDate(
			@RequestParam(value="accid")String accid,
			@RequestParam(value="isCreator")Boolean isCreator,
			@RequestParam(value="date")String date
			){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(date);
		return taskService.getByDate(accid, isCreator,date);
		
		
	}
	@RequestMapping("/getDataAnalysis")
	@ResponseBody
	public List<DataAnalysis> dataAnalysis(
			@RequestParam(value="accid") String accid,
			@RequestParam(value="isCreator") Boolean isCreator){
		return taskService.dataAnalysis(accid, isCreator) ;
	}

	@RequestMapping(value="/deleteTask",method = RequestMethod.POST)
	@ResponseBody
	public void deleteTask(
			@RequestBody TaskBean task){
		System.out.println("达到"+task.toString());
		taskService.deleteTask(task);
		
		
	}
	
	
	@RequestMapping(value="/updateTask",method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateTask(
			@RequestBody TaskBean task){
		taskService.updateTask(task);
		return true;
	}
	
	
	
}
