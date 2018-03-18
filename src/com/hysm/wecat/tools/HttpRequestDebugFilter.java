package com.hysm.wecat.tools;

//import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpRequestDebugFilter  implements Filter
{

	public void destroy()
	{
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		if(doDebug)
		{
			HttpRequestDebug.debugRequest((HttpServletRequest)request);
		}
		
		chain.doFilter(request, response);
	}
	
	public static boolean doDebug = true;

	public void init(FilterConfig fc) throws ServletException 
	{
		doDebug = Boolean.valueOf(fc.getInitParameter("doDebugRequest"));
	}



}
