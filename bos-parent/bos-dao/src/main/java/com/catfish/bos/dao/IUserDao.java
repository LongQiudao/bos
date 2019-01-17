package com.catfish.bos.dao;

import com.catfish.bos.dao.base.IBaseDao;
import com.catfish.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

}
