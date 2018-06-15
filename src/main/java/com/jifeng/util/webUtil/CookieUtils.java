package com.jifeng.util.webUtil;

import javax.servlet.http.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

public class CookieUtils
{

	public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
	public static final int DEFAULT_SIZE = 20;
	public static final int MAX_SIZE = 200;

	public CookieUtils()
	{
	}

	public static int getPageSize(HttpServletRequest request)
	{
		Assert.notNull(request);
		Cookie cookie = getCookie(request, "_cookie_page_size");
		int count = 0;
		if (cookie != null && NumberUtils.isDigits(cookie.getValue()))
			count = Integer.parseInt(cookie.getValue());
		if (count <= 0)
			count = 20;
		else
		if (count > 200)
			count = 200;
		return count;
	}

	public static Cookie getCookie(HttpServletRequest request, String name)
	{
		Assert.notNull(request);
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length > 0)
		{
			Cookie arr$[] = cookies;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Cookie c = arr$[i$];
				if (c.getName().equals(name))
					return c;
			}

		}
		return null;
	}

	public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain)
	{
		Cookie cookie = new Cookie(name, value);
		if (expiry != null)
			cookie.setMaxAge(expiry.intValue());
		if (StringUtils.isNotBlank(domain))
			cookie.setDomain(domain);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		response.addCookie(cookie);
		return cookie;
	}

	public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain)
	{
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		if (StringUtils.isNotBlank(domain))
			cookie.setDomain(domain);
		response.addCookie(cookie);
	}
}
