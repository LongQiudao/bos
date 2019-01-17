package com.catfish.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.catfish.bos.dao.IRegionDao;
import com.catfish.bos.dao.base.impl.BaseDaoImpl;
import com.catfish.bos.domain.Region;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	/**
	 * 根据页面输入信息进行模糊查询
	 * */
	public List<Region> findListByq(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? OR r.citycode LIKE ? OR r.city LIKE ?  OR r.district LIKE ? OR r.province LIKE ?";
		List<Region> list =(List<Region>) this.getHibernateTemplate().find(hql,"%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return list;
	}

}
