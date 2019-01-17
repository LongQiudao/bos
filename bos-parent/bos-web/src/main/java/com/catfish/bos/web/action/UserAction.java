package com.catfish.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.catfish.bos.domain.User;
import com.catfish.bos.service.IUserService;
import com.catfish.bos.utils.BosUtils;
import com.catfish.bos.utils.MD5Utils;
import com.catfish.bos.web.action.base.BaseAction;


@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	/**
	 * 用户登录
	 * */
	//接收页面的验证码
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Autowired
	private IUserService userService;
	/**
	 * 用户登录，使用shiro框架提供的方式认证
	 * */
	public String login(){
		//校验验证码
		String val=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&& checkcode.equals(val)){
		Subject subject = SecurityUtils.getSubject();//获取当前用户对象状态为，未认证
		AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),MD5Utils.md5(model.getPassword()));//创建用户名密码令牌对象
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("用户名或密码错误");
			return LOGIN;
		}
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return HOME;
		}else{
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
		
	}
	public String login_bak(){
		//校验验证码
		String val=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&& checkcode.equals(val)){
			User user = userService.login(model);
			if(user!=null){
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return HOME;
			}else{
				this.addActionError("用户名或密码错误");
				return LOGIN;
			}
		}else{
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
		
	}
	/**
	 * 用户注销
	 * */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	/**
	 * 修改当前用户密码
	 * @throws IOException 
	 * */
	public String editPassword() throws IOException{
		String f = "1";
		User user = BosUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(),model.getPassword());
		} catch (Exception e) {
			f = "0";
			e.printStackTrace();
		}	
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	/**
	 * 用户分页查询
	 * */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String username = model.getUsername();
		String gender = model.getGender();
		String station = model.getStation();
		String telephone = model.getTelephone();
		if(StringUtils.isNotBlank(username)) {
			dc.add(Restrictions.like("username", "%"+username+"%"));
		}
		if(StringUtils.isNotBlank(gender)) {
			dc.add(Restrictions.like("gender", "%"+gender+"%"));
		}
		if(StringUtils.isNotBlank(station)) {
			dc.add(Restrictions.like("station", "%"+station+"%"));
		}
		if(StringUtils.isNotBlank(telephone)) {
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		userService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","noticebills","roles"});
		return NONE;
	}
	private String roleIds;
	/**
	 * 新增用户
	 * */
	public String add() {
		userService.save(model,roleIds);
		return LIST;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 批量删除用户
	 * */
	private String ids;
	public String deleteBatch() {
		userService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 修改客户信息
	 * */
	public String edit() {
		userService.update(model);
		return LIST;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}
