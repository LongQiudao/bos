package com.catfish.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.catfish.bos.dao.IFunctionDao;
import com.catfish.bos.dao.base.impl.BaseDaoImpl;
import com.catfish.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{
	public List<Function> findAll() {
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	//根据用户id查询对应的权限
	public List<Function> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	//查询所有菜单权限
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}
	//

	//根据用户id查询对应的权限
	public List<Function> findMenuByUserId(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ? and  f.generatemenu = '1' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, id);
		return list;
	}
}
