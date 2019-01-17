package com.catfish.bos.dao;

import java.util.List;

import com.catfish.bos.dao.base.IBaseDao;
import com.catfish.bos.domain.Workbill;

public interface IWorkbillDao extends IBaseDao<Workbill>{

	public List<Workbill> findNewWorkbills();

}
