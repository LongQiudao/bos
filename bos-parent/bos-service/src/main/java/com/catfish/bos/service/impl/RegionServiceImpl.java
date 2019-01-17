package com.catfish.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IRegionDao;
import com.catfish.bos.dao.impl.StaffDaoImpl;
import com.catfish.bos.domain.Region;
import com.catfish.bos.service.IRegionService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Autowired
	private IRegionDao regionDao;
	/*
	 * 区域数据的批量保存
	 * */
	public void saveBatch(List<Region> regionList) {
		for (Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
		
	}
	/*
	 * 分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
		
	}
	/*
	 * 增加区域
	 * */
	public void add(Region model) {
		regionDao.save(model);
		
	}
	/**
	 * 批量删除
	 * */
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				Region r = regionDao.findById(id);
				regionDao.delete(r);		
				
			}
		}
		
	}
	/**
	 * 按主键查找
	 * */
	public Region findById(String id) {
		
		return regionDao.findById(id);
	}
	/**
	 * 修改区域
	 * */
	public void update(Region model) {
		regionDao.update(model);
		
	}
	/**
	 * 查询所有分区
	 * */
	public List<Region> findAll() {
		return regionDao.findAll();
	}
	/**
	 * 根据页面输入模糊查询
	 * */
	public List<Region> findListByq(String q) {
		// TODO Auto-generated method stub
		return regionDao.findListByq(q);
	}

}
