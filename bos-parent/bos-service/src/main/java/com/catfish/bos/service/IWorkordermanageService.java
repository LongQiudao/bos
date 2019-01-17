package com.catfish.bos.service;

import java.util.List;

import com.catfish.bos.domain.Workordermanage;
import com.catfish.bos.utils.PageBean;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public void pageQuery(PageBean pageBean);

	public void saceBatch(List<Workordermanage> list);

	public List<Workordermanage> findAll();

}
