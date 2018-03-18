package com.hysm.wecat;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hysm.bean.MessageType;
 
import net.sf.json.JSONObject;

//获取token的
public class GetToken
{
   
    
    public static String TOKEN="xJ4EPvdb7Pk8bm85y8JN";

    public static String gettoken()
    {

        JSONObject js = null;
        String access_token = null;
        String json = null;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + MessageType.appid + "&secret=" + MessageType.appsecret;

        json = HttpUtil.getUrl(url);
        
        System.out.println(json);

        try
        {
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");

        }
        catch (UnsupportedEncodingException e)
        {

            e.printStackTrace();
        }

        js = JSONObject.fromObject(json);

        access_token = js.getString("access_token");

        System.out.println("access_token==============" + access_token);

        return access_token;

    }

    //
    public static String getAccessToken()
    {
    	  WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
       ServletContext servletContext = webApplicationContext.getServletContext();
   	 //ServletContext servletContext = ServletActionContext.getServletContext();
        WxtokenAndTicket wat=(WxtokenAndTicket)servletContext.getAttribute(TOKEN);
        String access_token=null;
        long create_time=0;
        if(wat!=null){
        	 
             access_token=wat.getToken();
             create_time=wat.getToken_ctime();
        }else{
        	 wat=new WxtokenAndTicket();
        }
  
        if (access_token == null)
        {
            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
          
            wat.setToken(access_token);
            wat.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, wat);
            return access_token;
        }
        // 判断是否有效
        if ((System.currentTimeMillis() - create_time)/1000 < 3500)
        {

            return access_token;

        }
        else
        {

            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
          
            wat.setToken(access_token);
            wat.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, wat);
            return access_token;

        }

    }
    
    public static void main(String[] args) {
		
    	System.out.println( gettoken());
	}
}
