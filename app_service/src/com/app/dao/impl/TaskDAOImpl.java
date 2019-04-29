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
				String hql="select count(*) from TaskBean as t where t.tcreator=?";//�˴���Product�Ƕ���
				
				 query = session.createQuery(hql);
				 query.setParameter(0,accid);

				 count=((Long) query.setCacheable(true).uniqueResult()).intValue();//�˴���Long���ͽ���ת��
				
				 
			}
			else if(isCreator==false)
			{
				String hql="select count(*) from user_task where accid='"+accid+"'" ;//�˴���product�����ݿ��еı��� 
				query=session.createSQLQuery(hql); 
				List<BigInteger> list=query.list(); //�˴���һ��Ҫע����Bignter���͵ģ�ɵ�˰�������˺öࣨLong��Integer�������� 
				count = list.get(0).intValue();
			}
			
		}
		catch (Exception e) {
            
            e.printStackTrace();
        } finally {
        
            session.close();//�ر�����һ��Ҫ�رգ���Ȼ��Ӱ�������ٶȡ�
        }
		return count;
		
		
	}
	@Override
	public List<TaskBean> getTasks(int curPage, int pageSize, String accid, Boolean isCreator) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		if(isCreator)  //��ȡ���û�������������Ϣ
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
    	
    	
    	
    	//�ٱ�����һ��
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
	            //��������
	            tran=session.beginTransaction();
	          
	            //�����ݽ�����login����������ݿ�
	            t=(TaskBean)session.get(TaskBean.class, tid);
	            
	            //�ύ����
	            tran.commit();
	            //��ʾ��Ϣ
//	            System.out.println(cond.toString());
	            //�ر�Session����
	            session.close();
	        }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		return t;
	}


	/**
	 * ͨ������id��ȡ�����Ա
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
			if(isCreator)  //��ȡ���û�������������Ϣ
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
	 * ������ӳ�Ա
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
		
		System.out.println("Ӱ��������"+result+"  ����ӵ�������"+addUids.size());
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
		System.out.println("������");
		return true;
		
		
	}
	@Override
	public List<TaskBean> search(String accid, Boolean isCreator, String key) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 String sql="";
		 Query query=null;
			if(isCreator)  //��ȡ���û�������������Ϣ
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
			if(isCreator)  //��ȡ���û�������������Ϣ
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
		            //ÿ������Ԫ�ض���һ�����飬����Ԫ��ʦperson_id,person_name,person_age����ֵ  
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
