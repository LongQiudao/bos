package com.catfish.bos.web.action;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Staff;
import com.catfish.bos.service.IStaffService;
import com.catfish.bos.utils.PageBean;
import com.catfish.bos.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 取派员管理
 * 
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IStaffService staffService;
	
	/**
	 * 添加取派员
	 */
	@RequiresPermissions("staff-add")
	public String add(){
		staffService.save(model);
		return LIST;
	}
	
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name = model.getName();
		String station = model.getStation();
		String standard = model.getStandard();
		if(StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotBlank(standard)) {
			dc.add(Restrictions.like("standard", "%"+standard+"%"));
		}
		if(StringUtils.isNotBlank(station)) {
			dc.add(Restrictions.like("station", "%"+station+"%"));
		}
		staffService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		return NONE;
	}
	private String ids;

	/**
	 * 取派员批量删除
	 */
	@RequiresPermissions("staff-delete")
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 取派员批量还原
	 * 
	 * */
	@RequiresPermissions("staff-restore")
	public String restoreBatch() {
		staffService.restoreBatch(ids);
		return LIST;
	}
	/**
	 * 修改取派员信息
	 * */
	@RequiresPermissions("staff-edit")
	public String edit() {
		Staff staff =staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setHaspda(model.getHaspda());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setStandard(model.getStandard());
		staffService.update(staff);
		return LIST;
	}
	/**
	 * 查询所有未被删除的取派员
	 * */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.javatojson(list, new String[] {"telephone","haspda","deltag","station","standard","decidedzones"});
		return NONE;
	}
	public String getName() {
		String name = model.getName();
		this.javatojson(name, new String[] {});
		return NONE;
	}
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}
}
