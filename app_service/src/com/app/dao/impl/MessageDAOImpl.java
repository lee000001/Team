package com.app.dao.impl;

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
		tx.commit();
		session.close();
		return list;
	}
}
