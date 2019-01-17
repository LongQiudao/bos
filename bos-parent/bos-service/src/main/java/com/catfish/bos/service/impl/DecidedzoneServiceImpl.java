package com.catfish.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IDecidedzoneDao;
import com.catfish.bos.dao.ISubareaDao;
import com.catfish.bos.domain.Decidedzone;
import com.catfish.bos.domain.Subarea;
import com.catfish.bos.service.IDecidedzoneService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	/**
	 * 定区添加,同时关联分区
	 * */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);
			subarea.setDecidedzone(model);
		}
		
		
	}
	/**
	 * 定区分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
		
	}

}
