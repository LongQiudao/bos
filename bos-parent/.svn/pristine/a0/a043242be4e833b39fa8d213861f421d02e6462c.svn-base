package com.catfish.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.catfish.bos.dao.base.IBaseDao;
import com.catfish.bos.utils.PageBean;


/**
 * 持久层通用实现
 * */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T>{

	//代表待查询的某个实体类的类型
	private Class<T> entityClass;
	//在父类（BaseDaoImpl）的构造方法中来获得entityClass
	public BaseDaoImpl() {
		ParameterizedType superclass=(ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments=superclass.getActualTypeArguments();
		entityClass=(Class<T>) actualTypeArguments[0];
	}
	//根据类型注入spring会话工厂对象sessionFactory
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory ){
		super.setSessionFactory(sessionFactory);
	}
	//增加
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);	
	}
	//删除
	public void delete(T entity) {	
		this.getHibernateTemplate().delete(entity);
	}
	//修改
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}
	//按主键查询
	public T findById(Serializable id) {		
		return this.getHibernateTemplate().get(entityClass, id);
	}
	//查询所有
	public List<T> findAll() {
		String hql ="FROM "+entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}
	//通用修改方法
	public void excuteUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i=0;
		for(Object object:objects){
			query.setParameter(i++, object);
		}
		query.executeUpdate();
		
	}
	/**
	 * 通用分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//查询total---总数据量
		detachedCriteria.setProjection(Projections.rowCount());//指定hibernate框架发出sql的形式----》select count(*) from bc_staff;
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		
		//查询rows---当前页需要展示的数据集合
		detachedCriteria.setProjection(null);//指定hibernate框架发出sql的形式----》select * from bc_staff;
		//指定hibernate框架封装对象的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}
	//保存或更新方法
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}
	/**
	 * 通用的查询方法
	 * */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
