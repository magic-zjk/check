package com.jifeng.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;

public class URLPermissionsAuthorizationFilter extends AuthorizationFilter
	implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object obj) throws Exception {
		
		Subject subject = getSubject(request, response);
		
		String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		boolean isPermitted = false;
		//URLPermission currtpPermission = new URLPermission(requestURI);
		//isPermitted = subject.isPermitted(currtpPermission);
		requestURI = requestURI.substring(1, requestURI.length()).replaceAll("/", ":");
		isPermitted = subject.isPermitted(requestURI);
		System.out.println(isPermitted+"-------------------------------------"+requestURI);
		return isPermitted;
	}

}
