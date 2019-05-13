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

import com.app.dao.MessageDAO;
import com.app.dao.UserInfoDAO;
import com.app.entity.ActivityBean;
import com.app.entity.MessageBean;
import com.app.entity.UserInfo;
@Repository("MessageDAO")
public class MessageDAOImpl implements MessageDAO{

	@Autowired
	SessionFactory sessionFactory; 

	@Override
	public List<MessageBean> getMsg(String accid) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		String hql="from MessageBean as m where m.isRead=0 and m.receiver='"+accid+"'";
		Query query=session.createQuery(hql);
		List<MessageBean> list=query.list();
		Transaction tx=session.beginTransaction();
		for(MessageBean m:list)  //查询到的消息全部设置为已读
		{
			m.setIsRead(1);
			session.save(m);
			
		}
		System.out.println("消息到达");
		tx.commit();
		session.close();
		return list;
	}

	@Override
	public void lateMessage(List<ActivityBean> list) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		MessageBean msg;
		Transaction tx=session.beginTransaction();
		for(ActivityBean a:list)  //查询到的消息全部设置为已读
		{
			String sql=String.format("SELECT * FROM userinfo WHERE accid in\r\n" + 
					"(SELECT accid FROM activity_user WHERE aid=%d)", a.getAid());
			Query query=session.createSQLQuery(sql).addEntity(UserInfo.class);
			List<UserInfo> users=query.list();
			for(UserInfo u:users) {
				msg=new MessageBean();
				msg.setIsRead(0);
//				msg.setMid(a.getAid());
				msg.setMid_aid(a.getAid());
				msg.setMid_tid(a.getAid_tid());
				msg.setMsg("您的任务将要结束请尽快完成");
				msg.setReceiver(u.getAccid());
				msg.setMtype(0);
				session.save(msg);
				System.out.println(msg.toString());
			}
			
			
		}
		
		tx.commit();
		session.close();
		
	}
}
