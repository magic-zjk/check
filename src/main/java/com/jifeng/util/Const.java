package com.jifeng.util;

import org.springframework.context.ApplicationContext;

/**
 * 静态属性配置
 * @author sushengli
 * 2015年5月21日下午4:01:13
 */
public class Const {
	public static final String SALT = "JIFENG_KEY";
	public static final String CURR_USER = "currUser";
	public static final String CURR_ROLES = "currRoles";
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	public static final String END_TIME = "2015-09-17";
}
