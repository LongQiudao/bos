package com.catfish.bos.service;

import java.util.List;

import com.catfish.bos.domain.Region;
import com.catfish.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> regionList);

	public void pageQuery(PageBean pageBean);

	

	public void add(Region model);

	public void deleteBatch(String ids);

	public Region findById(String id);

	public void update(Region model);

	public List<Region> findAll();

	public List<Region> findListByq(String q);

}
