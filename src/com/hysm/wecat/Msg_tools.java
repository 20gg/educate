package com.hysm.wecat;

import org.json.JSONException;
import org.json.JSONObject;

public class Msg_tools {

	/**
	 * 
	 * @param openid  接收人的openid
	 * @param msg_id  模板ID
	 * @param msg_url 模板消息点击链接
	 * @param data_json   消息数据
	 * @return  0 发送成功  其他发送失败
	 */ 
	public static int send_wx_msg(String openid,String msg_id,String msg_url,String data_json){
		
		JSONObject send_json = new JSONObject();
		
		int errcode = -1;
		
		try {
			
			send_json.put("touser", openid);
			send_json.put("template_id", msg_id);
			send_json.put("url", msg_url);
			
			JSONObject data = new JSONObject(data_json);
			
			send_json.put("data", data);
			
			String post_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+GetToken.getAccessToken();
			
			String back_str = HttpUtil.getPostUrl(post_url, send_json.toString());
			
			org.json.JSONObject back_json = new JSONObject(back_str);
			
			errcode = back_json.getInt("errcode"); 
			
			//msgid, back_json.getInt("msgid") 返回消息id
			
			
		} catch (JSONException e){ 
			e.printStackTrace();
		}
		
		return errcode;
	}
}
