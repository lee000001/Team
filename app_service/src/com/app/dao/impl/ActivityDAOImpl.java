package com.app.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dao.ActivityDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.TaskBean;
import com.app.entity.UserInfo;
@Repository("activityDAO")
public class ActivityDAOImpl implements ActivityDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<ActivityBean> getActivity(int tid) {
		// TODO Auto-generated method stub
		String hql=String.format("From ActivityBean a where a.aid_tid=%d",tid);
		
		Session session=sessionFactory.openSession();
		Query query=session.createQuery(hql);
		List<ActivityBean> list=query.list();
		session.close();
		System.out.println(hql);
		return (List<ActivityBean>) list;
	}

	@Override
	public List<UserInfo> getMembers(int aid) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.openSession();
		String sql=String.format("SELECT * FROM userinfo WHERE accid in\r\n" + 
				"(SELECT accid FROM activity_user WHERE aid=%d)", aid);
		Query query=session.createSQLQuery(sql).addEntity(UserInfo.class);
		List<UserInfo> list=query.list();
		session.close();
		return list;
	}

	@Override
	public Boolean addMembers(int aid, int tid, List<String> selectedUids) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		String sql="insert IGNORE activity_user values";
		String sql2="insert IGNORE user_task values";
		for(int i=0;i<selectedUids.size();i++)
		{
			
			sql+=String.format("(%d,'%s')",aid,selectedUids.get(i));
			sql2+=String.format("(%d,'%s')",tid,selectedUids.get(i));
			if(i==selectedUids.size()-1)
			{
				sql+=";";
				sql2+=";";
			}else {
				sql+=",";
				sql2+=",";
			}
//			sql+=String.format("insert IGNORE user_task values(%d,'%s');", tid,s);
		}
		System.out.println(sql);
		System.out.println(sql2);
		int count1=session.createSQLQuery(sql).executeUpdate();
		int count2=session.createSQLQuery(sql2).executeUpdate();
		
		session.close();
		
		return count1>0&&count2>0;
	}

	@Override
	public Boolean setFinish(int aid, int state) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		ActivityBean a=session.get(ActivityBean.class, aid);
		a.setAstate(state);
		System.out.println(a.toString());
		session.update(a);
        session.getTransaction().commit(); 
		 session.close();
		return true;
		
	}

	@Override
	public List<UserInfo> getUsers(List<String> Uids) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		List<UserInfo> users=new ArrayList<>();
		for(String accid:Uids) {
			UserInfo u=session.get(UserInfo.class, accid);
			users.add(u);
		}
		tx.commit();
		 session.close();
		return users;
	}

	

	@Override
	public void deleteActivity(ActivityBean activity) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 session.delete(activity);
		 tx.commit();
		 session.close();
	}

	@Override
	public void updateActivity(ActivityBean activity) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 session.update(activity);
		 tx.commit();
		 session.close();
	}

	@Override
	public ActivityBean getActivityById(int aid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 ActivityBean a=session.get(ActivityBean.class, aid);
		 tx.commit();
		 session.close();
		 return a;
	}

	
}
