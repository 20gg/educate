/**
 * 
 */
package com.hysm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年5月26日
 */

public class CommonFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		
		//((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin","*");
		
		
		arg2.doFilter(arg0, response);   
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
