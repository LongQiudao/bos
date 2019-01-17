package com.catfish.bos.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IWorkbillDao;
import com.catfish.bos.domain.Workbill;
import com.catfish.bos.service.IWorkbillService;
import com.catfish.bos.utils.PageBean;
@Service
@Transactional
public class WorkbillServiceImpl implements IWorkbillService{
	@Autowired
	private IWorkbillDao workbillDao;

	//工单分页查询
	public void pageQuery(PageBean pageBean) {
		workbillDao.pageQuery(pageBean);
	}

	//追单
	public void repeatBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] workbillIds = ids.split(",");
			for (String id : workbillIds) {
				Workbill workbill = workbillDao.findById(id);
				if(workbill.getType().equals(Workbill.TYPE_1)) {
					workbill.setType(Workbill.TYPE_2);
				}
				if(!workbill.getType().equals(Workbill.TYPE_4)) {
					workbill.setAttachbilltimes(workbill.getAttachbilltimes()+1);
				}
				workbillDao.update(workbill);
			}
		}
		
	}

	//消单操作
	public void cancelBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] workbillIds = ids.split(",");
			for (String id : workbillIds) {
				Workbill workbill = workbillDao.findById(id);
				if(!workbill.getType().equals(Workbill.TYPE_4)) {
					workbill.setType(Workbill.TYPE_4);
				}
				workbillDao.update(workbill);
			}
		}
		
		
	}

	//修改工单
	public void update(Workbill model) {
		Workbill workbill = workbillDao.findById(model.getId());
		if(!workbill.getType().equals(Workbill.TYPE_4)) {
			workbill.setType(Workbill.TYPE_3);
			workbill.setStaff(model.getStaff());
			workbillDao.update(workbill);
		}
	}
	
}
