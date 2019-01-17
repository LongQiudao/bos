package com.catfish.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.catfish.bos.dao.IWorkbillDao;
import com.catfish.bos.dao.base.impl.BaseDaoImpl;
import com.catfish.bos.domain.Function;
import com.catfish.bos.domain.Workbill;
@Repository
public class WorkbillDaoImpl extends BaseDaoImpl<Workbill> implements IWorkbillDao{

	@Override
	public List<Workbill> findNewWorkbills() {
		String hql = "FROM Workbill w WHERE w.type='新单'";
		List<Workbill> list =  (List<Workbill>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
