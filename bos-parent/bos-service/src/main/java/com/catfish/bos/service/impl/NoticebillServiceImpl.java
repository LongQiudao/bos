package com.catfish.bos.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catfish.bos.dao.IDecidedzoneDao;
import com.catfish.bos.dao.INoticebillDao;
import com.catfish.bos.dao.IWorkbillDao;
import com.catfish.bos.domain.Decidedzone;
import com.catfish.bos.domain.Noticebill;
import com.catfish.bos.domain.Staff;
import com.catfish.bos.domain.Subarea;
import com.catfish.bos.domain.User;
import com.catfish.bos.domain.Workbill;
import com.catfish.bos.service.INoticebillService;
import com.catfish.bos.utils.BosUtils;
import com.catfish.bos.utils.PageBean;
import com.catfish.crm.service.ICustomerService;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	/**
	 * 添加业务通知单，尝试自动分单
	 * */
	public void save(Noticebill model) {
		User user = BosUtils.getLoginUser();
		model.setUser(user);//设置本次操作的管理员
		noticebillDao.save(model);
		//获取客户的取件地址
		String address = model.getPickaddress();
		//远程调用crm服务根据取件地址，查询客户所在定区
		String dicidedzoneId = customerService.findDecidedzoneIdByAddress(address);
		System.out.println(dicidedzoneId);
		if(dicidedzoneId!=null) {
			Decidedzone decidedzone = decidedzoneDao.findById(dicidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//给业务通知单关联取派员
			//设置分单类型为自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员产生工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workbill.setNoticebill(model);
			workbill.setPickstate(Workbill.PICKSTATE_NO);
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			workbill.setType(Workbill.TYPE_1);
			workbillDao.save(workbill);
			//调用短信平台发短信
		}else {
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
	}
	/**
	 * 业务通知单分页查询
	 * */
	public void pageQuery(PageBean pageBean) {
		noticebillDao.pageQuery(pageBean);
		
	}
	/**
	 * 查询人工分单业务通知单
	 * */
	public List<Noticebill> findNoticebillByOrdertype() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Noticebill.class);
		detachedCriteria.add(Restrictions.eq("ordertype","人工分单"));
		return noticebillDao.findByCriteria(detachedCriteria);
	}
	/**
	 * 人工调度
	 * */
	public void diaodu(Noticebill model) {
		Noticebill noticebill = noticebillDao.findById(model.getId());
		noticebill.setStaff(model.getStaff());
		Workbill workbill = new Workbill();
		workbill.setAttachbilltimes(0);
		workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
		workbill.setNoticebill(model);
		workbill.setPickstate(Workbill.PICKSTATE_NO);
		workbill.setRemark(model.getRemark());
		workbill.setStaff(model.getStaff());
		workbill.setType(Workbill.TYPE_1);
		workbillDao.save(workbill);
		noticebillDao.update(noticebill);
	}

}
