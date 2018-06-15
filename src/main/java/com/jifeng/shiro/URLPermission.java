package com.jifeng.shiro;

import java.io.Serializable;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;

@SuppressWarnings("serial")
public class URLPermission implements Permission, Serializable {

	protected PatternMatcher pathMatcher;
	private String url;

	public URLPermission(String url) {
		pathMatcher = new AntPathMatcher();
		this.url = url;
	}

	

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}


	@Override
	public boolean implies(Permission p) {
			System.out.println("----------------------implies");
			URLPermission otherPerm = (URLPermission) p;
			return pathMatcher.matches(getUrl(), otherPerm.getUrl());
	}
}
