package com.hysm.tools.wx;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hysm.tools.HttpUtil;



import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class GetJsticket {

	
	 public static String TOKEN="wxtoken";
	//获取jsapi的ticket
	
		private static String getJS_Ticket(){
		
			
			String access_token=GetToken.getAccessToken();
			String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
			String json=HttpUtil.getUrl(url);
			 try {
					json = new String(json.getBytes("ISO-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
				 
				 JSONObject  js=null;
				 String jsTicket=null;
				 try {
					js=JSONObject.fromObject(json);
					jsTicket=js.getString("ticket");
				} catch (JSONException e) {
					
					return null;
				}
			 
			 
			 return jsTicket;
			
		}
		
		
		//判断ticket是否过期，同时将ticket写入数据库
		public static String getJSApiTicket(){
			 WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
	        ServletContext servletContext = webApplicationContext.getServletContext();
//	    	 ServletContext servletContext = ServletActionContext.getServletContext();
		        WxtokenAndTicket wat=(WxtokenAndTicket)servletContext.getAttribute(TOKEN);
		        String jsticket=null;
		        long create_time=0;
		        if(wat!=null){
		        	  jsticket=wat.getJsticket();
				         create_time=wat.getTicket_ctime();	
		        }else{
		        	
		        	wat=new WxtokenAndTicket();
		        }
		       
			if(jsticket==null){
			   
				
				jsticket=getJS_Ticket();
				create_time=System.currentTimeMillis();
				
				wat.setJsticket(jsticket);
				wat.setTicket_ctime(create_time);
				  servletContext.setAttribute(TOKEN, wat);
			
			    return jsticket;
		}
		
		
		
	    if ((System.currentTimeMillis()-create_time)/1000 < 3500) {// 有效
	       
	   	  return jsticket;
	     
	    
	    } else {
	   	    // 无效
	   	  
	    	jsticket=getJS_Ticket();
	    	create_time=System.currentTimeMillis();
			
			wat.setJsticket(jsticket);
			wat.setTicket_ctime(create_time);
			  servletContext.setAttribute(TOKEN, wat);
	          
	        return jsticket; 
	    }
			
		}
}
