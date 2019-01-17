package com.catfish.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Role;
import com.catfish.bos.service.IRoleService;
import com.catfish.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	@Autowired
	private IRoleService roleService;
	
	//添加属性驱动用来接收角色信息
	private String functionIds;
	/**
	 * 添加角色
	 * */
	public String add() {
		roleService.save(model,functionIds);
		return LIST;
	}
	/**
	 * 角色分页查询
	 * */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","functions","users"});
		return NONE;
	}
	/**
	 * 查询所有角色数据返回json
	 * */
	public String listajax() {
		List<Role> list =roleService.findAll();
		this.javatojson(list, new String[] {"functions","users"});
		return NONE;
	}
	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
}
