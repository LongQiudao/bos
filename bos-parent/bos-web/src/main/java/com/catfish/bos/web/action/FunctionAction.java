package com.catfish.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Function;
import com.catfish.bos.service.IFunctionService;
import com.catfish.bos.utils.PageBean;
import com.catfish.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private IFunctionService functionService;
	/**
	 * 查询所有权限，返回json数据
	 * */
	public String listajax() {
		List<Function> list =functionService.findAll();
		this.javatojson(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	/**
	 * 添加权限
	 * */
	public String add() {
		
		functionService.save(model);
		return LIST;
	}
	/**
	 * 权限数据分页查询
	 * */
	public String pageQuery() {
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * 根据当前对应的用户查询菜单数据 并返回json数据
	 * */
	public String findMenu() {
		List<Function> list = functionService.findMenu();
		this.javatojson(list, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
}
