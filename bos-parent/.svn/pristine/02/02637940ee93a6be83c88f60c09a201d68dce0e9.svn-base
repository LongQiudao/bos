package com.catfish.bos.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IUserDao;
import com.catfish.bos.domain.Role;
import com.catfish.bos.domain.User;
import com.catfish.bos.service.IUserService;
import com.catfish.bos.utils.MD5Utils;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao userDao;
	/**
	 * 用户登录
	 * */
	public User login(User user) {
		String password=MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	/**
	 * 修改当前用户密码
	 * */
	public void editPassword(String id, String password) {
		password=MD5Utils.md5(password);
		userDao.excuteUpdate("user.editpassword",password,id);
		
	}
	/**
	 * 用户分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
		
	}
	/**
	 * 新增客户
	 * */
	public void save(User model, String roleIds) {
		String password = model.getPassword();
		password = MD5Utils.md5(password);
		model.setPassword(password);
		userDao.save(model);
		if(roleIds!=null && roleIds.length()>0) {
			String[] ids = roleIds.split(",");
			for (String id : ids) {
				Role role = new Role(id);
				model.getRoles().add(role);
			}
		}
	}
	/**
	 * 批量删除
	 * */
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] userIds = ids.split(",");
			for (String id : userIds) {
				User user = userDao.findById(id);
				userDao.delete(user);
			}
		}
		
	}
	/**
	 * 修改用户
	 * */
	public void update(User model) {
		User user = userDao.findById(model.getId());
		user.setUsername(model.getUsername());
		user.setSalary(model.getSalary());
		user.setGender(model.getGender());
		user.setStation(model.getStation());
		user.setTelephone(model.getTelephone());
		userDao.update(user);
		
	}
	
}
