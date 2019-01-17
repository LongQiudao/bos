package com.catfish.bos.service;

import java.util.List;

import com.catfish.bos.domain.Function;
import com.catfish.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu();

}
