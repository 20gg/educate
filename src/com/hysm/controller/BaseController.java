package com.hysm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/comm")
public class BaseController {

	Logger logger = LoggerFactory.getLogger(BaseController.class);

	private HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return attrs.getRequest();
	}

	protected HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	/**
	 * @param response
	 * @param
	 */
	protected void printerJson(HttpServletResponse response, String str) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(str);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	protected void toPrinterJson(HttpServletResponse response, int res_type)
			throws Exception {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("RES_TYPE", res_type);
			response.setCharacterEncoding("utf-8");
			String json = JSON.toJSONStringWithDateFormat(jsonObj,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			this.printerJson(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void toPrinterJson(HttpServletResponse response, int res_type,
			String res_msg) throws Exception {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("RES_TYPE", res_type);
			jsonObj.accumulate("RES_MSG", res_msg);
			response.setCharacterEncoding("utf-8");
			String json = JSON.toJSONStringWithDateFormat(jsonObj,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			this.printerJson(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Description 向前台发送数据
	 * @author
	 * @return 
	 */
	public void sendMassage(String str, HttpServletResponse response) {
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			out.close();
			out = null;

		}

	}
	
	/**
	 * Description 向前台发送数据
	 * @author
	 * @return 
	 */
	public void sendMessage(String str, HttpServletResponse response) {
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			out.close();
			out = null;

		}

	}
	
	  /**
		 * @discription 向前台发送json数据
		 * @author
		 */
	    protected void sendjson(Object obj, HttpServletResponse response) {
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");

			JSONArray json = JSONArray.fromObject(obj);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(json);
				//System.out.println(json.toString());
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}
}
