package com.catfish.bos.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.catfish.bos.domain.User;



/**
 * bos项目的工具类
 * */
public class BosUtils {
	//获取session对象
	public static HttpSession getSession(){
		
		return ServletActionContext.getRequest().getSession();
	}
	public static User getLoginUser(){
		
		return (User) getSession().getAttribute("user");
	}
	/**
	 * 将时间戳转化我时间类型
	 * */
	public static String stampToDate(String s){
	    String res;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long lt = new Long(s);
	    Date date = new Date(lt);
	    res = simpleDateFormat.format(date);
	    return res;
	}
}


