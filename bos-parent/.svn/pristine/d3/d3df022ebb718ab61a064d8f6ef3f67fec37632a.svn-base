package com.catfish.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.objenesis.ObjenesisException;

import com.catfish.bos.utils.PageBean;

/**
 * 持久层通用接口
 * */
public interface IBaseDao<T> {
	//增加
	public void save(T entity);
	//保存或更新
	public void saveOrUpdate(T entity);
	//删除
	public void delete(T entity);
	//修改
	public void update(T entity);
	//按主键查询
	public T findById(Serializable id);
	//查询所有
	public List<T> findAll();
	//通用更新
	public void excuteUpdate(String queryName,Object...objects);
	//通用分页查询方法
	public void pageQuery(PageBean pageBean);
	//通用查询方法
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
