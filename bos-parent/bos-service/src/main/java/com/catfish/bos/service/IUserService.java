package com.catfish.bos.service;

import com.catfish.bos.domain.User;
import com.catfish.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	public void pageQuery(PageBean pageBean);

	

	public void deleteBatch(String ids);

	public void update(User model);

	public void save(User model, String roleIds);

}
