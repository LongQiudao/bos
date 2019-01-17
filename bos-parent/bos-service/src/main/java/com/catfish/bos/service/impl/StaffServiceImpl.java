package com.catfish.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IStaffDao;
import com.catfish.bos.domain.Staff;
import com.catfish.bos.service.IStaffService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	@Autowired
	private IStaffDao staffDao;
	public void save(Staff model) {
		staffDao.save(model);	
	}
	
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
		
	}

	
	public void deleteBatch(String ids) {//1,2,3,4
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.excuteUpdate("staff.delete", id);
			}
		}
	}

	
	public Staff findById(String id) {
		return	staffDao.findById(id);	
	}

	
	public void update(Staff staff) {
		staffDao.update(staff);
		
	}

	
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.excuteUpdate("staff.restore", id);
			}
		}
		
	}

	/**
	 * 查询所有未删除取派员信息
	 * */
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(detachedCriteria);
	}

}
