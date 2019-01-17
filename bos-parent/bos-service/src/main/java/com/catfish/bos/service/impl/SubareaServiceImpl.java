package com.catfish.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.ISubareaDao;
import com.catfish.bos.domain.Region;
import com.catfish.bos.domain.Subarea;
import com.catfish.bos.service.ISubareaService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{

	@Autowired
	private ISubareaDao subareaDao;
	/**
	 *添加分区
	 * */
	public void save(Subarea model) {
		subareaDao.save(model);		
	}
	/**
	 * 分区批量保存
	 * */
	public void saveBatch(List<Subarea> list) {
		for (Subarea subarea : list) {
			subareaDao.saveOrUpdate(subarea);
		}
		
	}
	//分区分页查询 
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
		
	}
	/**
	 * 查询所有分区
	 * */
	public List<Subarea> findAll() {	
		return subareaDao.findAll();
	}
	/**
	 * 批量删除分区
	 * */
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] subareaIds = ids.split(",");
			for (String id : subareaIds) {
				Subarea s = subareaDao.findById(id);
				subareaDao.delete(s);		
				
			}
		}
		
	}
	/**
	 * 修改分区
	 * */
	public void update(Subarea subarea) {
		subareaDao.update(subarea);
		
	}
	/**
	 * 查询所有未关联的分区
	 * */
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}
	/**
	 * 根据定区查询关联的分区
	 * */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone.id",decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria);
		}

}
