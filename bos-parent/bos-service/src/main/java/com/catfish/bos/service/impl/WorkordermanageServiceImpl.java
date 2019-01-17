package com.catfish.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IWorkordermanageDao;
import com.catfish.bos.domain.Workordermanage;
import com.catfish.bos.service.IWorkordermanageService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	//工作单录入
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}
	//工作单分页查询
	public void pageQuery(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);	
	}
	//批量导入工作单
	public void saceBatch(List<Workordermanage> list) {
		for (Workordermanage workordermanage : list) {
			workordermanageDao.saveOrUpdate(workordermanage);
		}
		
	}
	//查询所有工作单
	public List<Workordermanage> findAll() {
		
		return workordermanageDao.findAll();
	}

}
