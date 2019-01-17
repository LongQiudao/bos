package com.catfish.bos.web.action;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Noticebill;
import com.catfish.bos.domain.Staff;
import com.catfish.bos.domain.Workbill;
import com.catfish.bos.service.IWorkbillService;
import com.catfish.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<Workbill>{
	@Autowired
	private IWorkbillService workbillService;
	/**
	 * 分页查询工单
	 * */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		Staff staff = model.getStaff();
		Noticebill noticebill = model.getNoticebill();
		if(staff != null) {
			String name = staff.getName();
			dc.createAlias("staff", "s");
			if(StringUtils.isNotBlank(name)) {
				dc.add(Restrictions.like("s.name", "%"+name+"%"));
			}
		}
		if(noticebill!=null) {
			String telephone = noticebill.getTelephone();
			dc.createAlias("noticebill", "n");
			if(StringUtils.isNotBlank(telephone)) {
				dc.add(Restrictions.like("n.telephone", "%"+telephone+"%"));
			}
		}
		workbillService.pageQuery(pageBean);
		this.javatojson(pageBean,new String[] {"decidedzones","workbills","noticebill","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	private String ids;
	/**
	 * 追单操作
	 * */
	public String repeatBatch() {
		workbillService.repeatBatch(ids);
		return LIST;
	}
	/**
	 * 消单操作
	 * */
	public String cancelBatch() {
		workbillService.cancelBatch(ids);
		return LIST;
	}
	
	/**
	 * 修改工单
	 * */
	public String edit() {
		workbillService.update(model);
		return LIST;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
