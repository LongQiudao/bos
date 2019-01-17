package com.catfish.bos.service;

import java.util.List;

import com.catfish.bos.dao.base.IBaseDao;
import com.catfish.bos.domain.Staff;
import com.catfish.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff model);

	public void restoreBatch(String ids);

	public List<Staff> findListNotDelete();

}
