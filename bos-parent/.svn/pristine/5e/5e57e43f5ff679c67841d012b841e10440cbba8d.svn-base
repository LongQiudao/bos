package com.catfish.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.catfish.bos.domain.Region;
import com.catfish.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 表现层通用实现
 * */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria =null;
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
		
	private static final long serialVersionUID = 1L;
	public static final String HOME = "home";
	public static final String LIST = "list";
	//创建模型对象
	protected T model;
	//在构造方法中动态获得实体类型，通过反射创建实体对象model
	public BaseAction() {
		ParameterizedType superclass=(ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments=superclass.getActualTypeArguments();
		Class<T> entityClass=(Class<T>) actualTypeArguments[0];
		detachedCriteria=detachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model=entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public void javatojson(Object object,String [] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(object,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void javatojson(List object,String [] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONArray.fromObject(object,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public T getModel() {		
		return model;
	}

}
