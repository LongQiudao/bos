package com.catfish.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.catfish.bos.dao.IFunctionDao;
import com.catfish.bos.dao.IUserDao;
import com.catfish.bos.domain.Function;
import com.catfish.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
	@Autowired 
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("厂长")) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			list = functionDao.findByCriteria(detachedCriteria );
		}else {
			list = functionDao.findFunctionListByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken passwordToken=(UsernamePasswordToken) arg0;
		String username = passwordToken.getUsername();
		User user  = userDao.findUserByUsername(username);
		if(user==null) {
			//页面输入的用户不存在
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		return info;
	}

}
