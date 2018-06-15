package com.jifeng.util.webUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

// Referenced classes of package com.weeon.core.web:
//			CookieUtils
@SuppressWarnings({"rawtypes","unchecked"})
public class RequestUtils
{

	private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

	public RequestUtils()
	{
	}

	public static String getQueryParam(HttpServletRequest request, String name)
	{
		if (StringUtils.isBlank(name))
			return null;
		if (request.getMethod().equalsIgnoreCase("POST"))
			return request.getParameter(name);
		String s = request.getQueryString();
		if (StringUtils.isBlank(s))
			return null;
		try
		{
			s = URLDecoder.decode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("encoding UTF-8 not support?", e);
		}
		String values[] = (String[])parseQueryString(s).get(name);
		if (values != null && values.length > 0)
			return values[values.length - 1];
		else
			return null;
	}

	public static Map getQueryParams(HttpServletRequest request)
	{
		Map map;
		if (request.getMethod().equalsIgnoreCase("POST"))
		{
			map = request.getParameterMap();
		} else
		{
			String s = request.getQueryString();
			if (StringUtils.isBlank(s))
				return new HashMap();
			try
			{
				s = URLDecoder.decode(s, "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				log.error("encoding UTF-8 not support?", e);
			}
			map = parseQueryString(s);
		}
		Map params = new HashMap(map.size());
		Iterator i$ = map.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			int len = ((String[])entry.getValue()).length;
			if (len == 1)
				params.put(entry.getKey(), ((String[])entry.getValue())[0]);
			else
			if (len > 1)
				params.put(entry.getKey(), entry.getValue());
		} while (true);
		return params;
	}

	public static Map parseQueryString(String s)
	{
		String valArray[] = null;
		if (s == null)
			throw new IllegalArgumentException();
		Map ht = new HashMap();
		StringTokenizer st = new StringTokenizer(s, "&");
		do
		{
			if (!st.hasMoreTokens())
				break;
			String pair = st.nextToken();
			int pos = pair.indexOf('=');
			if (pos != -1)
			{
				String key = pair.substring(0, pos);
				String val = pair.substring(pos + 1, pair.length());
				if (ht.containsKey(key))
				{
					String oldVals[] = (String[])(String[])ht.get(key);
					valArray = new String[oldVals.length + 1];
					for (int i = 0; i < oldVals.length; i++)
						valArray[i] = oldVals[i];

					valArray[oldVals.length] = val;
				} else
				{
					valArray = new String[1];
					valArray[0] = val;
				}
				ht.put(key, valArray);
			}
		} while (true);
		return ht;
	}

	public static Map getRequestMap(HttpServletRequest request, String prefix)
	{
		return getRequestMap(request, prefix, false);
	}

	public static Map getRequestMapWithPrefix(HttpServletRequest request, String prefix)
	{
		return getRequestMap(request, prefix, true);
	}

	private static Map getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
	{
		Map map = new HashMap();
		Enumeration names = request.getParameterNames();
		do
		{
			if (!names.hasMoreElements())
				break;
			String name = (String)names.nextElement();
			if (name.startsWith(prefix))
			{
				String key = nameWithPrefix ? name : name.substring(prefix.length());
				String value = StringUtils.join(request.getParameterValues(name), ',');
				map.put(key, value);
			}
		} while (true);
		return map;
	}

	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
			return ip;
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip))
		{
			int index = ip.indexOf(',');
			if (index != -1)
				return ip.substring(0, index);
			else
				return ip;
		} else
		{
			return getRequestBaseURL(request);
		}
	}

	public static String getRequestBaseURL(HttpServletRequest request)
	{
		StringBuilder buf = new StringBuilder();
		int port = request.getServerPort();
		if (port < 0)
			port = 80;
		String scheme = request.getScheme();
		buf.append(scheme).append("://").append(request.getServerName());
		if ("http".equals(scheme) && port != 80 || "https".equals(scheme) && port != 443)
			buf.append(":").append(port);
		buf.append(request.getContextPath());
		return buf.toString();
	}

	public static String getLocation(HttpServletRequest request)
	{
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null)
			buff.append("?").append(queryString);
		return buff.toString();
	}

	public static String getRequestedSessionId(HttpServletRequest request)
	{
		String sid = request.getRequestedSessionId();
		String ctx = request.getContextPath();
		if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx))
			return sid;
		Cookie cookie = CookieUtils.getCookie(request, "JSESSIONID");
		if (cookie != null)
			return cookie.getValue();
		else
			return null;
	}

	public static void main(String args1[])
	{
	}

}
