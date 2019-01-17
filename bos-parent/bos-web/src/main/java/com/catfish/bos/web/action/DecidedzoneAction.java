package com.catfish.bos.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Decidedzone;
import com.catfish.bos.domain.Staff;
import com.catfish.bos.service.IDecidedzoneService;
import com.catfish.bos.web.action.base.BaseAction;
import com.catfish.crm.service.Customer;
import com.catfish.crm.service.ICustomerService;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	@Autowired
	private IDecidedzoneService decidedzoneService;
	//属性驱动接收多个分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	/**
	 * 添加定区
	 * */
	@RequiresPermissions("decidedzone-add")
	public String add() {
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	/**
	 * 定区分页查询方法
	 * */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String id = model.getId();
		Staff staff = model.getStaff();
		
		if(StringUtils.isNotBlank(id)) {
			dc.add(Restrictions.like("id", "%"+id+"%"));
		}
		if(staff != null) {
			String station = staff.getStation();
			dc.createAlias("staff", "s");
			if(StringUtils.isNotBlank(station)) {
				dc.add(Restrictions.like("s.station", "%"+station+"%"));
			}
		}
		decidedzoneService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}
	//注入crm代理对象
	@Autowired
	private ICustomerService customerService;
	/**
	 * 远程调用crm服务获取未关联的客户
	 * */
	public String findListNotAssociation() {
		List<Customer> list = customerService.findListNotAssociation();
		this.javatojson(list, new String[] {});
		return NONE;
	}
	/**
	 * 远程调用crm服务获取已关联的客户
	 * */
	public String findListHasAssociation() {
		String id = model.getId();
		List<Customer> list = customerService.findListHasAssociation(id);
		this.javatojson(list, new String[] {});
		return NONE;
	}
	//获取属性驱动加载 客户id
	private List<Integer> customerIds;
	public List<Integer> getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	/**
	 * 远程调用crm服务,关联客户
	 * */
	@RequiresPermissions("decidedzone-ass")
	public String assigncustomerstodecidedzone() {
		customerService.assigncustomerstodecidedzone(model.getId(),customerIds);
		return LIST;
	}
}
