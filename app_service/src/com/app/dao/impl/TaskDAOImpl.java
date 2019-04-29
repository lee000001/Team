package com.app.dao.impl;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.TaskDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.DataAnalysis;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;
@Repository("taskDAO")
public class TaskDAOImpl implements TaskDAO{

	@Autowired
	SessionFactory sessionFactory;

	
	@Override
	public int getCount_task(String accid,Boolean isCreator) {
		// TODO Auto-generated method stub
		String sqlString = null ;
		Session session = null;
		Query query;
		int count=134;
		session=sessionFactory.openSession();
		
		
		try
		{
			
			if(isCreator)
			{
				sqlString="select count(*) from TaskBean as t  where t.tcreator=' "+accid+"'" ;
				String hql="select count(*) from TaskBean as t where t.tcreator=?";//此处的Product是对象
				
				 query = session.createQuery(hql);
				 query.setParameter(0,accid);

				 count=((Long) query.setCacheable(true).uniqueResult()).intValue();//此处用Long类型进行转换
				
				 
			}
			else if(isCreator==false)
			{
				String hql="select count(*) from user_task where accid='"+accid+"'" ;//此处的product是数据库中的表名 
				query=session.createSQLQuery(hql); 
				List<BigInteger> list=query.list(); //此处胡一定要注意是Bignter类型的，傻了吧唧的试了好多（Long，Integer）都报错 
				count = list.get(0).intValue();
			}
			
		}
		catch (Exception e) {
            
            e.printStackTrace();
        } finally {
        
            session.close();//关闭流，一定要关闭，不然会影响运行速度。
        }
		return count;
		
		
	}
	@Override
	public List<TaskBean> getTasks(int curPage, int pageSize, String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		if(isCreator)  //获取该用户创建的任务信息
		{
			  String hql = "from TaskBean as t where t.tcreator= ? ";
			  Query query = session.createQuery(hql);
			  query.setParameter(0, accid);
			  query.setFirstResult(curPage*pageSize);
			  query.setMaxResults(pageSize);
			  List<TaskBean> list=query.list();
			  session.close();
			  System.out.println(query.getQueryString());
			  return list;
		}
		else 
		{
			String sql = "FROM task AS a WHERE a.tid IN ( SELECT tid FROM user_task AS b WHERE b.accid = '"+accid+"' )";
			  Query query = session.createQuery(sql);			  
			  query.setFirstResult(curPage*pageSize);
			  query.setMaxResults(pageSize);
			  List<TaskBean> list=query.list();
			  session.close();
			  return list;
		}
		 
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
    	ActivityBean a=new ActivityBean();
    	ActivityBean a2=new ActivityBean();
    	a.setAname("activity1");
    	a2.setAname("activity2");
    	a.setAid_tid(8);
    	a2.setAid_tid(8);
    	
    	
    	
    	//再保存多的一段
    	session.save(a);
    	session.save(a2);
    	session.close();
		
	}
	@Override
	public TaskBean findById(int tid) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Transaction tran;
		TaskBean t = null;
		  try {
	            //开启事务
	            tran=session.beginTransaction();
	          
	            //将传递进来的login对象存入数据库
	            t=(TaskBean)session.get(TaskBean.class, tid);
	            
	            //提交事务
	            tran.commit();
	            //提示信息
//	            System.out.println(cond.toString());
	            //关闭Session对象
	            session.close();
	        }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		return t;
	}


	/**
	 * 通过任务id获取任务成员
	 */
	@Override
	public List<UserInfo> getUserInfoByTaskId(int tid) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		String sql =String.format("SELECT * FROM userinfo where accid in(SELECT accid FROM user_task where tid='%d')",tid);
		  Query query = session.createSQLQuery(sql).addEntity(UserInfo.class);			   
		  List<UserInfo> list=query.list();
		  session.close();
		  return list;
	}


	@Override
	public List<TaskBean> getAllTasks(String accid, Boolean isCreator, int tstate) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 String sql="";
		 Query query=null;
			if(isCreator)  //获取该用户创建的任务信息
			{
				  sql=String.format("select * from task where tcreator='%s'", accid);
				  if(tstate!=-1)
					  sql=String.format("select * from task where tcreator='%s' and tstate=%d", accid,tstate);
				 
			}
			else 
			{
				if(tstate!=-1)
					sql =String.format( "select * from  task  a WHERE a.tstate=%d a.tid  IN ( SELECT tid FROM user_task AS b WHERE b.accid = '%s' "
						+ " and b.tstate=%d)",tstate,accid,tstate);
				else
					sql =String.format( "select * from  task  a WHERE a.tid IN ( SELECT tid FROM user_task AS b WHERE b.accid = '%s' "
							+ ")",accid);
				  
			}
			System.out.println(sql);
			query = session.createSQLQuery(sql).addEntity(TaskBean.class);			  
			  
			  List<TaskBean> list=query.list();
			  session.close();
			  return list;
	}
	/**
	 * 任务添加成员
	 */
	@Override
	public boolean addmenber(int tid, List<String> addUids) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		String sql="";
		sql="insert into user_task (tid,accid) values";
		for(int i=0;i<addUids.size();i++)
		{
			sql+=String.format("(%d,'%s')",tid,addUids.get(i));
			if(i!=addUids.size()-1)
			{
				sql+=",";
			}
			else {
				sql+=";";
			}
				
			
		}
		System.out.println(sql);
		Query query=session.createSQLQuery(sql);
		int result=query.executeUpdate();
		
		System.out.println("影响行数："+result+"  待添加的行数："+addUids.size());
		session.close();
		return result==addUids.size()?true:false;
	}
	@Override
	public BigInteger getTaskId() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		String sql="SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='test' AND TABLE_NAME='task';";
		Query query=session.createSQLQuery(sql);
		BigInteger b=(BigInteger) query.list().get(0);
		session.close();
		return b; 
	}
	
	@Override
	public boolean  addTask(TaskBean task, List<ActivityBean> activities) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		session.save(task);
		for(ActivityBean a:activities)
		{
			session.save(a);
		}
		session.close();
		System.out.println("添加完成");
		return true;
		
		
	}
	@Override
	public List<TaskBean> search(String accid, Boolean isCreator, String key) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 String sql="";
		 Query query=null;
			if(isCreator)  //获取该用户创建的任务信息
			{
				  sql="select * from task where tcreator='"+accid+"' and tname like '%"+key+"%'";
				 
				 
			}
			else 
			{

				sql ="select * from  task  a WHERE tname like '%"+key+"%' a.tid  IN ( SELECT tid FROM user_task AS b WHERE b.accid = '"+accid+"' "
						+ ")";
				
			}
			System.out.println(sql);
			query = session.createSQLQuery(sql).addEntity(TaskBean.class);			  
			  
			  List<TaskBean> list=query.list();
			  session.close();
			  return list;
	}
	@Override
	public List<TaskBean> getByDate(String accid, Boolean isCreator, String date) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 String sql="";
		 Query query=null;
			if(isCreator)  //获取该用户创建的任务信息
			{
//				  sql=String.format("select * from task t where tcreator='%s' and t.tstartDate<'%s' and t.tendDate>'%s' ",
//						  accid,date,date);
				
				  sql=String.format("select * from task t where tcreator='%s' and '%s' between t.tstartDate and t.tendDate ",
						  accid,date);
				 
				 
			}
			else 
			{

				sql =String.format( "select * from  task  t WHERE '%s' between t.tstartDate and t.tendDate  and t.tid IN ( SELECT tid FROM user_task AS b WHERE b.accid = '%s'"
						+ ")",date,accid);
				
			}
			System.out.println(sql);
			query = session.createSQLQuery(sql).addEntity(TaskBean.class);			  
			  
			  List<TaskBean> list=query.list();
			  session.close();
			  return list;
	}
	@Override
	public List<DataAnalysis> dataAnalysis(String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		 String sql="";
		 Query query=null;
			if(isCreator) 
			{
				  sql=String.format(" select tstate state,count(*) value from task t  WHERE t.tcreator='%s'  group by tstate ",accid);				 
				 
			}
			else 
			{

				sql =String.format(" select tstate state,count(*) value from task t   WHERE t.tid IN(SELECT tid FROM user_task "
						+ "WHERE accid ='%s') group by tstate ",accid);
				
			}
			System.out.println(sql);
			query = session.createSQLQuery(sql);		  
		 List list = session.createSQLQuery(sql)  
		                    .addScalar("state",StandardBasicTypes.INTEGER).  
		                    addScalar("value", StandardBasicTypes.INTEGER).list();  
		        
		 List<DataAnalysis> l=new ArrayList();
		  for(Iterator iterator = list.iterator();iterator.hasNext();){  
		            //每个集合元素都是一个数组，数组元素师person_id,person_name,person_age三列值  
		            Object[] objects = (Object[]) iterator.next();  
		            DataAnalysis data=new DataAnalysis((int)objects[0],(int)objects[1]);
		            l.add(data);
		        }  
		        
		   session.close();  
		   return l;
	}
	@Override
	public void deleteTask(TaskBean task) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 session.delete(task);
		 tx.commit();
		 session.close();
		
	}
	@Override
	public void updateTask(TaskBean task) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 session.update(task);
		 tx.commit();
		 session.close();
		
	}
	



	

}
