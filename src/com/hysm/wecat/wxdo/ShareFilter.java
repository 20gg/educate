package com.hysm.wecat.wxdo;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hysm.wecat.GetJsticket;



public class ShareFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		Date date = new java.util.Date();
		long time = date.getTime();

		// 时间戳只有10位 要做处理
		String dateline = time + "";
		dateline = dateline.substring(0, 10);

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String timeStamp = dateline;
		System.out.println(timeStamp);
		String url = request.getScheme() + "://" + request.getServerName()+":8080"
				+ request.getRequestURI();
		if (request.getQueryString() != null) {
			url = url + "?" + request.getQueryString();
		}
		System.out.println("url:" + url);
		// 获取前台的时间戳
		
		String jsticket = GetJsticket.getJSApiTicket();
		// String
		// ticket="sM4AOVdWfPE4DxkXGEs8VOzZLwOOuvTVRsa4UeyjiEQ3A8-f_VLl3GaJEj8z738rLEP_EzWMYBClwfIhB7I1Og";

		String noncestr = UUID.randomUUID().toString();

		// 注意这里参数名必须全部小写，且必须有序
		String[] paramArr = new String[] { "jsapi_ticket=" + jsticket,
				"timestamp=" + timeStamp, "noncestr=" + noncestr, "url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&" + paramArr[1])
				.concat("&" + paramArr[2]).concat("&" + paramArr[3]);
		/*
		 * String
		 * content="jsapi_ticket="+jsticket+"&noncestr="+noncestr+"&timestamp="
		 * +timeStamp+"&url="+url;
		 */
		MessageDigest crypt;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			byte[] digest = crypt.digest(content.toString().getBytes());
			Formatter formatter = new Formatter();
			for (byte b : digest) {
				formatter.format("%02x", b);
			}
			String signature = formatter.toString();
			formatter.close();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("welcome.html").forward(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
