package com.hysm.task;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hysm.db.CbgDB;
import com.hysm.db.Msg_db;
import com.hysm.db.Top_db;
import com.hysm.db.Video_db;
import com.hysm.tools.DateTool;
import com.hysm.tools.Http_tool;
import com.hysm.tools.random4;
import com.hysm.wecat.GetToken;
import com.hysm.wecat.HttpUtil;
import com.hysm.wecat.Msg_tools;
import com.mongodb.client.model.Filters;

@Component("myvideo")
public class My_video {
	
	@Autowired
	private Top_db top_db;
	
	@Autowired
	private Video_db video_db;
	
	@Autowired
	private Msg_db msg_db;
	
	@Autowired
	private CbgDB cgbdb;

	@Scheduled(cron = "0 0/10 * * * ?")
	public void video_data(){
		
		Document qq_code = video_db.query_qq_code();
		
		System.out.println("------获取腾讯云中的视频信息---课程-----");
		
		List<Document>  c_list =  video_db.query_no_course(); 
		if(c_list.size() > 0){
			
			for(int i=0;i < c_list.size();i++){
				
				String _id = c_list.get(i).get("_id").toString(); 
				String fileid = c_list.get(i).getString("url");
				
				Map<String,Object> back = Http_tool.http_video_info(qq_code, fileid);
				
				if(Integer.valueOf(back.get("code").toString()) == 0){
					//获取信息成功  保存信息
					c_list.get(i).put("is_show", 1);
					
					c_list.get(i).put("time", Integer.valueOf(back.get("time_long").toString()));
					c_list.get(i).put("v_name", back.get("v_name").toString());
					c_list.get(i).put("v_size", Integer.valueOf(back.get("v_size").toString()));
					c_list.get(i).put("status", back.get("status").toString());
					c_list.get(i).put("img2", back.get("img").toString());
					c_list.get(i).put("v_type", back.get("v_type").toString());
					c_list.get(i).put("sourceurl", back.get("sourceurl").toString());
					
					c_list.get(i).put("trans_list", back.get("trans_list"));
					
					video_db.update_course(_id, c_list.get(i));
				}
				
			}
		}
		
		 
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void little_special(){
		
System.out.println("------获取腾讯云中的视频信息---小课题-----*********************************");
	Document qq_code = video_db.query_qq_code();		

		List<Document>  s_list =  video_db.query_no_special();
		
		if(s_list.size() > 0){
			
			for(int i=0;i < s_list.size();i++){
				
				String _id = s_list.get(i).get("_id").toString();
				
				List<String> url_list = (List<String> )s_list.get(i).get("url");
				
				//Map<String,Object> time_list=new HashMap<String, Object>();
				
				List<Map<String,Object>> time_list=new ArrayList<Map<String,Object>>();
				
				int check_num = 0;
				
				String f_img = "";
				
				for(int j=0;j<url_list.size();j++){
					
				  
						/*String fileid =  url_list.get(j).get("vid").toString();
						
						//生成专题的视频cid  4随机数
						if(url_list.get(j).get("cid") == null){
							url_list.get(j).put("cid",""+random4.getRandomNum4());
						}*/
						String fileid =  url_list.get(j).toString();
					
						Map<String,Object> back = Http_tool.http_video_info(qq_code, fileid);
						
						if(Integer.valueOf(back.get("code").toString()) == 0){
							
							f_img = back.get("img").toString();
							
							Map<String,Object> mmp =new HashMap<String, Object>();
							  
							mmp.put("time", Integer.valueOf(back.get("time_long").toString()));
							mmp.put("v_name", back.get("v_name").toString());
							mmp.put("v_size", Integer.valueOf(back.get("v_size").toString()));
							mmp.put("status", back.get("status").toString());
							mmp.put("img", back.get("img").toString());
							mmp.put("v_type", back.get("v_type").toString());
							mmp.put("sourceurl", back.get("sourceurl").toString()); 
							mmp.put("trans_list", back.get("trans_list"));
							
							mmp.put("code", 0);
							
							
							time_list.add(mmp);	
							
							 
						}else{
							check_num ++;
						}
					 
					 
				}
				
				if(check_num == 0){
					s_list.get(i).put("is_show", 1);
				}else{
					s_list.get(i).put("is_show", 0);
				}
				
				s_list.get(i).put("url", url_list);
				s_list.get(i).put("time_list", time_list);
				//s_list.get(i).put("img", f_img);
				
				video_db.update_special(_id, s_list.get(i));
			}
		} 
		
	}
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void check_top(){
		
		String yesterday = DateTool.get_yesterday(); 
		
		
		System.out.println("------校验上级-----p1--");
		
		List<Document> p1_list = top_db.query_p1_list(yesterday);
		
		if(p1_list.size() > 0){
			
			 for(int i=0;i<p1_list.size();i++){
				 
				 if(p1_list.get(i).getString("p2_date") == null){
					//不存在p2 
					 String _id = p1_list.get(i).get("_id").toString();
					 
					 p1_list.get(i).remove("top_openid");
					 p1_list.get(i).remove("top_member");
					 p1_list.get(i).remove("p1_date");
					 
					 //修改 
					 top_db.update_user(_id,  p1_list.get(i));
				 } 
			 }
		} 
		
		System.out.println("------校验上级-----p2--");
		
		List<Document> p2_list = top_db.query_p2_list(yesterday);
		
		if(p2_list.size() > 0){
			 for(int i=0;i<p2_list.size();i++){
				 
				 String _id = p2_list.get(i).get("_id").toString();
				 
				 p2_list.get(i).remove("top_openid");
				 p2_list.get(i).remove("top_member");
				 p2_list.get(i).remove("p1_date");
				 p2_list.get(i).remove("p2_date");
				 
				 //修改 
				 top_db.update_user(_id,  p2_list.get(i));
			 }
		}
		
	}
	
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void check_course(){
		
		String yesterday = DateTool.get_yesterday();  
		System.out.println("------免费课程过期------"+yesterday); 
		List<Document> list = video_db.query_safe_course(yesterday);
		
		if(list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				
				String _id = list.get(i).get("_id").toString();
				
				list.get(i).put("is_free", "1");
				
				video_db.update_course(_id, list.get(i));
			} 
		}
		
		System.out.println("------免费课程过期------"); 
	}
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void check_order(){
		
		String yesterday = DateTool.get_yesterday();  
		System.out.println("------订单过期------"+yesterday); 
		List<Document> list = video_db.query_safe_order(yesterday);
		
		if(list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				
				String _id = list.get(i).get("_id").toString();
				
				list.get(i).put("is_over", -1);
				
				video_db.update_order(_id, list.get(i));
			} 
		}
		
		System.out.println("------订单过期------"); 
	}	
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void check_vip(){
		
		String yesterday = DateTool.get_yesterday();  
		System.out.println("------vip过期------"+yesterday); 
		List<Document> list = video_db.query_safe_vip(yesterday);
		
		if(list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				
				String _id = list.get(i).get("_id").toString();
				
				String open_id = list.get(i).getString("open_id");
				
				Document user = video_db.query_user(open_id); 
				String u_id = user.get("_id").toString(); 
				user.put("role", 0);
				
				top_db.update_user(u_id, user);
				
				
				list.get(i).put("is_over", -1);
				
				video_db.update_vip(_id, list.get(i));
			} 
		}
		
		System.out.println("------vip过期------"); 
	}	
	
	/**
	 * 发送模板消息
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void send_msg(){
		
		System.out.println("------发送模板消息 ----");
		
		List<Document> list = msg_db.query_no_send();
		
		if(list.size() > 0){ 
			
			for(int i=0;i<list.size();i++){
				
				Document msg = list.get(i); 
				String _id = msg.get("_id").toString();
				
				Document send =new Document();
				send = msg;
				 
				send.remove("_id");
				send.remove("is_send");
				send.remove("c_time");
				
				JSONArray json = JSONArray.fromObject(send); 
				
				String post_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+GetToken.getAccessToken();
				
				String back_str = HttpUtil.getPostUrl(post_url, json.getJSONObject(0).toString());
				
				try {
					org.json.JSONObject back_json = new JSONObject(back_str);
					
					int errcode = back_json.getInt("errcode");
					
					if(errcode == 0){
						//发送成功 
						msg.put("is_send", 1);
						msg.put("send_time", DateTool.fromDate24H());  
						msg.put("msgid", back_json.getInt("msgid")); 
						
						msg_db.update_message(_id, msg);
					}else{
						
						msg.put("errcode", errcode);
						msg_db.update_message(_id, msg);
					}
					
				} catch (JSONException e) { 
					e.printStackTrace();
				}
				
				
			}
		}
		
		System.out.println("------发送模板消息 ----");
	}
	
	/**
	 *  查询专题订阅数
	 */
	
//	@Scheduled(cron = "0 0/1 * * * ?")
	
		public  void select_sc(){
		System.out.println("------查询专题订阅 ----");
		
		List<Document>  special_list=cgbdb.find_special();
			
		Document doc =new Document();
			doc.put("type", 2);
			doc.put("is_order", 1);
		
		List<Document> course_order=cgbdb.find_course_order22("course_order",doc);//查询所有课程订单	
		
			 
			for (int j=0;j<special_list.size();j++){
				
				for (int i=0;i<course_order.size();i++){
					System.out.println(special_list.get(j).getString("special_no").toString()+"....."+course_order.get(i).getString("c_no").toString());
					if(special_list.get(j).getString("special_no").equals(course_order.get(i).getString("c_no"))){//相等则说明被购买
						Document doc2 =new Document();
						doc2.put("c_no", special_list.get(j).getString("special_no"));
						doc2.put("type", 2);
						doc2.put("is_order", 1);
					int count =cgbdb.count_course_order("course_order", doc2);
					
					Document doc3=new Document();
						doc3.put("special_no", special_list.get(j).getString("special_no"));
					
					Document special=cgbdb.findspecial_detail("special", doc3);
					
					System.out.println(count);
					special.put("order_count", count);
					
					cgbdb.replaceOne("special", Filters.eq("special_no", special.getString("special_no")), special);
				}
					
				}
				
				
					
				}
			 		System.out.println("--------任务jieshu--------");		
			}
		
		//@Scheduled(cron = "0 0 0/1 * * ?")
		/*public void send_msg2(){
			
			System.out.println("------发送模板消息-----专题更新 ----");
			
			List<Document> list = msg_db.query_no_send2();
			
			if(list.size() > 0){ 
				
				for(int i=0;i<list.size();i++){
					
					Document msg = list.get(i); 
					String _id = msg.get("_id").toString();
					
					String s_no = msg.getString("special_no");
					
					
					Document special=msg_db.query_s_order2(s_no);
					if(special.getInteger("is_show")==1){
						List<Document> order_list = msg_db.query_s_order(s_no);
						 
						if(order_list.size() > 0){
							
							for(int k=0;k<order_list.size();k++){
								
								if(order_list.get(k).getInteger("is_order")==1&&order_list.get(k).getInteger("is_over")==0){
									//;
									
									
									//JSONObject data = (JSONObject)msg.get("data");
									
									JSONArray json = JSONArray.fromObject(msg.get("data")); 
									
									//String openid="oLir8jsziazLpKT0b6JSSbQ0icR4";//
									int error=	Msg_tools.send_wx_msg(order_list.get(k).getString("open_id"),
											msg.getString("template_id"), msg.getString("url"),json.getJSONObject(0).toString() );
									
									if(error==0){
										msg.put("is_send", 1);									
										msg.put("send_time", DateTool.fromDate24H());  
										
										msg_db.update_message2(_id, msg);
									}
									
								}
								
								
							}
							
							
						}else{
							msg.put("is_send", -1);
							msg_db.update_message2(_id, msg);
						}
						
					}
				 
				}
			} 
			
			System.out.println("------发送模板消息 ----");
		}*/
		
		
		
		//拉取专题中广告视频信息
		@Scheduled(cron = "0 0/10 * * * ?")
		public void special_video(){
			
			Document qq_code = video_db.query_qq_code();
			
			System.out.println("------获取腾讯云中的专题的广告视拼--------");
			
			List<Document>  c_list =  video_db.query_no_special_data(); 
			if(c_list.size() > 0){
				
				for(int i=0;i < c_list.size();i++){
					
					String _id = c_list.get(i).get("_id").toString(); 
					String fileid = c_list.get(i).getString("gg_id");
					
					Map<String,Object> back = Http_tool.http_video_info(qq_code, fileid);
					
					if(Integer.valueOf(back.get("code").toString()) == 0){
						//获取信息成功  保存信息
						c_list.get(i).put("is_gg", 1);
						
						c_list.get(i).put("time", Integer.valueOf(back.get("time_long").toString()));
						c_list.get(i).put("v_name", back.get("v_name").toString());
						c_list.get(i).put("v_size", Integer.valueOf(back.get("v_size").toString()));
						c_list.get(i).put("status", back.get("status").toString());
						c_list.get(i).put("img2", back.get("img").toString());
						c_list.get(i).put("v_type", back.get("v_type").toString());
						c_list.get(i).put("sourceurl", back.get("sourceurl").toString());
						
						c_list.get(i).put("trans_list", back.get("trans_list"));
						
						video_db.update_special(_id, c_list.get(i));
					}
					
				}
			}
			
		
		}	
		
		@Scheduled(cron = "0 0/5 * * * ?")
		public void query_error_message(){ //查询发送失败群发模板消息
			
			Document doc =new Document();
				
				doc.put("state", -1);
			List<Document> list=video_db.query_no_message("error_message", doc);
				
				if(list.size()>0){
					
					for(int i=0;i<list.size();i++){
							
							
							Document message=list.get(i);
							if(message.getInteger("error")!=43004){
								
								int	error_code=	Msg_tools.send_wx_msg(message.getString("openid"), message.getString("template_id"),
										message.getString("url"), message.getString("json"));
								
										if(error_code==0){
										
											message.put("state", 1);
										video_db.update_message(message.get("_id").toString(), message);
									}
								
							}
						
						
					}
					
				}
			
		}
		
		
	//	@Scheduled(cron = "0 0/5 * * * ?")
		public void query_error_course(){ //查询下架的视频并删除学习记录
			
			Document doc =new Document();
				
				doc.put("state", -1);
			List<Document> list=video_db.query_no_message("course", doc);
			List<Document> special=video_db.query_no_message("special", doc);
			
			 
				if(list.size()>0){
					
					for(int i=0;i<list.size();i++){
							
							
							Document message=list.get(i);
							
						video_db.delete_db_one("study_log", message.getString("c_no"));
							 
					}
					
				}
				

				if(special.size()>0){
					
					for(int j=0;j<special.size();j++){
							 
							Document message=list.get(j);
							
						video_db.delete_db_one("study_log", message.getString("special_no"));
							 
					}
					
				}
			
		}
		
			  
}
