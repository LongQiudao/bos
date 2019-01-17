package com.catfish.bos.dao;

import java.util.List;

import com.catfish.bos.dao.base.IBaseDao;
import com.catfish.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findFunctionListByUserId(String userId);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);

}
