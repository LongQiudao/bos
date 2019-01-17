package com.catfish.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IFunctionDao;
import com.catfish.bos.domain.Function;
import com.catfish.bos.domain.User;
import com.catfish.bos.service.IFunctionService;
import com.catfish.bos.utils.BosUtils;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	@Autowired
	private IFunctionDao functionDao;
	/**
	 * 查询所有权限
	 * */
	public List<Function> findAll() {
		
		return functionDao.findAll();
	}
	/**
	 * 添加权限
	 * */
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")) {
			model.setParentFunction(null);
		}
		functionDao.save(model);
		
	}
	/**
	 * 权限数据分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
		
	}
	/**
	 * 根据当前用户查询对应的权限返回json数据
	 * */
	
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BosUtils.getLoginUser();
		if(user.getUsername().equals("厂长")) {
			list =functionDao.findAllMenu();
		}else {
			list= functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}


}
