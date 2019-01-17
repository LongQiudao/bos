package com.catfish.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.catfish.bos.dao.IUserDao;
import com.catfish.bos.dao.base.impl.BaseDaoImpl;
import com.catfish.bos.domain.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

	/**
	 * 根据用户名和密码查询用户
	 * */
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username=? AND u.password=? ";
		List<User> userList=(List<User>) this.getHibernateTemplate().find(hql,username,password);
		if(userList !=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 根据用户名查询用户
	 * */
	public User findUserByUsername(String username) {
		String hql = "FROM User u WHERE u.username=? ";
		List<User> userList=(List<User>) this.getHibernateTemplate().find(hql,username);
		if(userList !=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

}
