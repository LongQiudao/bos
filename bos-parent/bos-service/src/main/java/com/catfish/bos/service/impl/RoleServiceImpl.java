package com.catfish.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.catfish.bos.dao.IRoleDao;
import com.catfish.bos.domain.Function;
import com.catfish.bos.domain.Role;
import com.catfish.bos.service.IRoleService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Autowired
	private IRoleDao roleDao;
	/**
	 * 添加角色,并关联权限
	 * */
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		if(StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String id : ids) {
				Function function = new Function(id);
				model.getFunctions().add(function);
			}
		}
	}
	/**
	 * 角色分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
		
	}
	/**
	 * 查询所有角色数据
	 * */
	public List<Role> findAll() {
		
		return roleDao.findAll();
	}

}
