package com.hysm.wecat.wxdo;

import java.util.Date;
import java.util.List;


//消息推送的封装类
public class pushmessage {
	//推送文本消息
	public String pushmess(String to,String from ,String content){
		StringBuffer sb = new StringBuffer();  
		  
		  sb.append("<xml><ToUserName><![CDATA[");  
	        sb.append(to);  
	        sb.append("]]></ToUserName><FromUserName><![CDATA[");  
	        sb.append(from);  
	        sb.append("]]></FromUserName><CreateTime>");  
	        sb.append(System.currentTimeMillis()/1000);  
	        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");  
	        sb.append(content);  
	        sb.append("]]></Content></xml>");  
	        return sb.toString();  
		
		
	}
	//推送单图文消息
	public String  pushImageandTest(String to,String from,List<massage> mass){
		StringBuffer sb = new StringBuffer();  
		 sb.append("<xml><ToUserName><![CDATA[");  
	     sb.append(to);  
	     sb.append("]]></ToUserName><FromUserName><![CDATA[");  
	     sb.append(from);  
	     sb.append("]]></FromUserName><CreateTime>");  
	     sb.append(System.currentTimeMillis()/1000);
	     sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
	     sb.append("<ArticleCount>"+mass.size()+"</ArticleCount><Articles>");
	     for(massage ma:mass){
	    	sb.append("<item><Title><![CDATA[");
	    	 sb.append(ma.getTitle());
	    	 sb.append("]]></Title><Description><![CDATA[");
	    	 sb.append(ma.getDescription());
	    	 sb.append("]]></Description><PicUrl><![CDATA[");
	    	  sb.append(ma.getPicurl());
	    	 sb.append("]]></PicUrl><Url><![CDATA[");
	    	 sb.append(ma.getUrl());
	    	 sb.append("]]></Url></item>");
	  
	     }
	     sb.append("</Articles>");
	     sb.append("</xml>");
		
		return sb.toString();
	}
	
private String pushhead(String to,String from){
	StringBuffer sb = new StringBuffer();  
	 sb.append("<xml><ToUserName><![CDATA[");  
     sb.append(to);  
     sb.append("]]></ToUserName><FromUserName><![CDATA[");  
     sb.append(from);  
     sb.append("]]></FromUserName><CreateTime>");  
     sb.append(System.currentTimeMillis()/1000);
     sb.append("</CreateTime>");
	
     return null;
}
}
