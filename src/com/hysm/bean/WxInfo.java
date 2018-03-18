package com.hysm.bean;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.bson.Document;

import com.hysm.db.Login_db;
import com.hysm.tools.CookieDo;
import com.hysm.wecat.GetToken;
import com.hysm.wecat.HttpUtil;
import com.hysm.wecat.wxdo.EmojiFilter;

public class WxInfo implements Runnable {

	private String openid;
	private Login_db userdb;
	public WxInfo(String openid, Login_db userdb){
		this.openid=openid;
		this.userdb=userdb;
	}
	
	
	
	
	
	
	
	@Override
	public void run() {
		Document user = userdb.query_userByopenid(openid);

		if (user == null) {
			// 将登录时间录入

			
			
			String access_token = GetToken.getAccessToken(); 
			
			String  url2="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
	          
	        String str=HttpUtil.getUrl(url2);
	        
	        try {
				str = new String(str.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	        System.out.println(str);
			 
			JSONObject js=JSONObject.fromObject (str);
			 
			 Document doc=new Document();
			 
			String nickname=js.getString("nickname"); 
			String open_id =js.getString("openid");
			 
			doc.append("sex",js.getInt("sex"));
			doc.append("open_id",openid);
			doc.append("province",js.getString("province"));
			doc.append("city",js.getString("city")); 
			doc.append("country",js.getString("country"));
				 
			doc.append("head",js.getString("headimgurl"));
			
			 nickname=EmojiFilter.filterEmoji(nickname);
	         doc.append("name",nickname); 
	         doc.append("state",0); 
	        
	         userdb.check_user(open_id, doc);
			
	       
	         
		}

	}

}
