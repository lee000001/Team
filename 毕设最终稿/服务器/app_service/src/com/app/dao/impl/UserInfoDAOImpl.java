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

import com.app.dao.UserInfoDAO;
import com.app.entity.UserInfo;
@Repository("UserInfoDAO")
public class UserInfoDAOImpl implements UserInfoDAO{

	@Autowired
	SessionFactory sessionFactory;

	

	@Override
	public void register(UserInfo cond) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Transaction tran;
		  try {
	            //开启事务
	            tran=session.beginTransaction();
	            //将传递进来的login对象存入数据库
	            session.save(cond);
	            //提交事务
	            tran.commit();
	            //提示信息
	            System.out.println(cond.toString());
	            //关闭Session对象
	            session.close();
	        }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }

	}



	@Override
	public List<UserInfo> getAcitvityMember(List<String> selectedUids) {
		// TODO Auto-generated method stub
		
		String hql="From UserInfo u where u.accid in";
		String s="(";
		for(int i=0;i<selectedUids.size();i++){
			s+="'"+selectedUids.get(i)+"'";
			if(i!=selectedUids.size()-1) {
				s+=",";
			}
		}
		s+=")";
		hql+=s;
		Session session=sessionFactory.openSession();
		Query query=session.createQuery(hql);
		List<UserInfo> list=query.list();
		session.close();
		System.out.println(hql);
		return list;
	}
}
