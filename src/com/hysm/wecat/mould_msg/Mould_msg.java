package com.hysm.wecat.mould_msg;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hysm.tools.DateTool;
import com.hysm.wecat.GetToken;
import com.hysm.wecat.HttpUtil;

/**
 * 模板消息
 * @author songkai
 *
 */
public class Mould_msg {

	
	/**
	 * 根据消息类型生成不同的消息json数据
	 * @param msg
	 * @return
	 */
	public String create_msg_json(Document msg){
		
		int type = msg.getInteger("type");
		
		if(type == 1){
			//购买课程专题，使用消息模板1
		}
		
		return null;
	}
	
	
	
	public static void main(String[] args){
		
		JSONObject json = new JSONObject();
		
		String json_str = "";
		
		try {
			json.put("touser", "oLir8jrPdUazBqrzw-vOzL7Erda4"); 
			json.put("template_id", "UiX8sXhIzK7wHL4qe23V1huRjHKkv_6nQMUmxr9t11w");
			json.put("url", "http://www.baidu.com");
			
			JSONObject first = new JSONObject();
			first.put("value", "你好，你已成功购买课程");
			first.put("color", "#333333");
			
			JSONObject keyword1 = new JSONObject();
			keyword1.put("value", "微信支付10.00元");
			keyword1.put("color", "#2dc158");
			
			JSONObject keyword2 = new JSONObject();
			keyword2.put("value", "课程名称");
			keyword2.put("color", "#2dc158");
			
			JSONObject keyword3 = new JSONObject();
			keyword3.put("value", "1234567");
			keyword3.put("color", "#2dc158");
			
			JSONObject remark = new JSONObject();
			remark.put("value", DateTool.fromDate24H());
			remark.put("color", "#333333");
			
			JSONObject data = new JSONObject();  
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);
			data.put("keyword3", keyword3);
			data.put("remark", remark);
			
			json.put("data", data);
			 
			json_str = json.toString();
			
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		
		System.out.println(json_str);
		
		
		//http post 
		
		String post_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+GetToken.gettoken();
		
		String back_str = HttpUtil.getPostUrl(post_url, json_str);
		
		System.out.println(back_str);
		
	}
	
}
