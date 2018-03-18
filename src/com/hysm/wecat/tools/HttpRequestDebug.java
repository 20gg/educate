package com.hysm.wecat.tools;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDebug 
{
	
	public static void debugRequest(HttpServletRequest request)
	{
		System.out.println("****************debug HttpRequest**************");
		System.out.println("CharacterEncoding:"+request.getCharacterEncoding());
		System.out.println("ContentType:"+request.getContentType());
		System.out.println("ContextPath:"+request.getContextPath());
		System.out.println("ContextPath:"+request.getRequestedSessionId());
		Enumeration<String> enu = request.getParameterNames();
		String param = null;
		Object value = null;
		
		while(enu.hasMoreElements())
	    {
	        	param = enu.nextElement();
	        	value = request.getParameter(param);
	        	if(value instanceof String)
	        	{
	        		System.out.println(param+":"+value);
	        	}
	        	else if(value instanceof String[])
	        	{
	        		System.out.println(param+":");
	        		DebugTool.dumpArray((String[])value);
	        	}
	    }
		
		enu = request.getAttributeNames();
		
		while(enu.hasMoreElements())
	    {
	        	param = enu.nextElement();
	        	value = request.getParameter(param);
	        	if(value instanceof String)
	        	{
	        		System.out.println(param+":"+value);
	        	}
	        	else
	        	{
	        		System.out.println(param+":");
	        		DebugTool.dumpObject(request.getAttribute(param));
	        	}
	    }
		
	}
	

}
