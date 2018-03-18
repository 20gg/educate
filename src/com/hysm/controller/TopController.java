package com.hysm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hysm.db.Area_db;
import com.hysm.db.CbgDB;
import com.hysm.db.Msg_db;
import com.hysm.db.Top_db;
import com.hysm.tools.DateTool;
import com.hysm.wecat.Msg_tools;
import com.mongodb.client.model.Filters;

@Controller
@RequestMapping("top")
public class TopController extends BaseController {

	@Autowired
	private Area_db area_db;
	
	@Autowired
	private Top_db top_db;
	
	@Autowired
	private Msg_db msg_db;
	
	@Autowired
	private CbgDB cgbdb;
	
	/**
	 * 设置上级
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/set_top" }, method = { RequestMethod.POST })
	public void set_top(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid");
		
		String top_openid = request.getParameter("top_openid"); //通过分享传的上级openid
		
		//String share_ul = request.getParameter("share_ul");
		
		Document user = area_db.query_user_byopenid(openid); 
		
		Map<String,Object> top_map = top_db.query_my_top(openid);
		
		int back_code =0;
		
		Document top_user = area_db.query_user_byopenid(top_openid); 
		
			
			Document add_score=new Document();
			add_score.put("share_openid", openid);
			add_score.put("open_id", top_openid);
		//	add_score.put("share_ul", share_ul);
			//add_score.put("date", DateTool.fromDateY());
		 	int share_log_count=cgbdb.count_course_order("share_log", add_score);
		 	
		 		
		 
		 		if(share_log_count==0 && !top_openid.equals(openid)){
		 			
		 			Document config_s=cgbdb.query_config();
		 			add_score.put("is_add", 1);//代表加分成功
		 			add_score.put("score", config_s.getInteger("share_score"));
		 			add_score.put("scholarship", config_s.getInteger("share_scholarship"));
		 			cgbdb.insertOne("share_log", add_score);
		 			
		 			
		 			
		 			
		 			top_user.put("scholarship",top_user.getInteger("scholarship")+config_s.getInteger("share_scholarship"));
		 			top_user.put("score",top_user.getInteger("score")+config_s.getInteger("share_score"));
		 			
		 			cgbdb.replaceOne("user",  Filters.eq("open_id", top_openid), top_user);
		 			
		 		}else{
		 			
		 			 
		 		}
		 		
		 		
		
		if(Integer.valueOf(top_map.get("safe").toString()) <= 0
				&& !openid.equals(top_openid)  && !openid.equals(top_user.getString("top_openid"))){
			//上级没有 或 已经失效 
			//设置用户的上级 
			
			//不是本人      我的上级的上级   == 不是我
			
			Document config = top_db.query_config_s();
			 
			
			user.put("top_openid", top_openid);
			
			String safe_date = DateTool.addDate(DateTool.fromDateY(), config.getInteger("safe_date"));
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("open_id", top_openid);
			map.put("head", top_user.getString("head"));
			map.put("name", top_user.getString("name"));
			map.put("safe_date", safe_date);
			
			user.put("top_member", map);
			user.put("p1_date", safe_date);
			
			String _id = user.get("_id").toString();
			
			top_db.update_user(_id, user);
			
			back_code =100;
			
			
			//给上级发送消息提醒
			
			Map<String,Object> message_map = new HashMap<String, Object>();
				
			message_map.put("msg_type", 8);
			message_map.put("title", "哇，您太有号召力了！有一位新学员与您关联！");
			message_map.put("name",  user.getString("name"));	
			message_map.put("time", DateTool.fromDateY());					 	
			message_map.put("note",  "全年畅听百节大课，丰富的课后练习\n各行业实战经验分享，大咖干货囤积\n 专家在线问答，告别难题困惑\n讨论区无限发帖，尽享趣味学习\n"
					+"立即点击<详情>，邀请"+user.getString("name")+"加入VIP学员，即可获得150元奖学金，关联有效期为60天！");	
			message_map.put("touser", top_openid);
			 
				
			msg_db.add_msg8(message_map);
					 
			/*List<Document> list= top_db.query_moudle(8);
			
			Document msg =null;
			String _id2="";
			if(list.size()>0){
				 
				for(int x=0;x<list.size();x++){
					  msg = list.get(x);
					  _id2  = msg.get("_id").toString();
					JSONArray json = JSONArray.fromObject(msg.get("data"));
					
					int error=	Msg_tools.send_wx_msg(msg.getString("touser"),
							msg.getString("template_id"), msg.getString("url"),json.getJSONObject(0).toString() );
					if(error==0){
						msg.put("is_send", 1);									
						msg.put("send_time", DateTool.fromDate24H());  
						
						msg_db.update_message(_id2, msg);
						 
					}		
					
				}
				 
			}else{
				msg.put("is_send", -1);
				msg_db.update_message(_id2, msg);
			
			}*/
			
			 
		}else{
			back_code =200;
		}
		
		model.put("user", user);
		model.put("back_code", back_code);
		
		sendjson(model, response);
	}
	
	
	@RequestMapping(value = { "/shcolarship_info" }, method = { RequestMethod.POST })
	public void shcolarship_info(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid");
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		Document user = area_db.query_user_byopenid(openid); 
		
		int pt_num = top_db.query_xia_count(openid, 0); 
		int vip_num = top_db.query_xia_count(openid, 1);
		
		int count = top_db.query_scholship_log_count(openid);
		
		List<Document> log_list = top_db.query_scholship_log(openid, skip, limit);
		
		int page_num = page;//默认第一页 
		int page_count = 1;//默认总页数：1 
		if(count % limit == 0){
			page_count = count/limit;
		}else{
			page_count = (count/limit)+1;
		} 
		if(page_count == 0){
			page_count = 1;
		}
		  
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		
		model.put("user", user);
		model.put("pt_num", pt_num);
		model.put("vip_num", vip_num);
		
		model.put("log_list", log_list);
		
		sendjson(model, response);
		
	}
	
	
	@RequestMapping(value = { "/shcolarship_sort" }, method = { RequestMethod.POST })
	public void shcolarship_sort(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = top_db.query_user_count();
		
		List<Document> list = top_db.query_user_page(skip, limit);
		
		int page_num = page;//默认第一页 
		int page_count = 1;//默认总页数：1 
		if(count % limit == 0){
			page_count = count/limit;
		}else{
			page_count = (count/limit)+1;
		} 
		if(page_count == 0){
			page_count = 1;
		}
		  
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		
		model.put("list", list);
		
		sendjson(model, response);
		
	}
	

	@RequestMapping(value = { "/query_order" }, method = { RequestMethod.POST })
	public void query_order(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid");
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = top_db.query_order_count(openid);
		List<Document> list = top_db.query_order_page(openid, skip, limit);
		
		int page_num = page;//默认第一页 
		int page_count = 1;//默认总页数：1 
		if(count % limit == 0){
			page_count = count/limit;
		}else{
			page_count = (count/limit)+1;
		} 
		if(page_count == 0){
			page_count = 1;
		}
		
		Document user = area_db.query_user_byopenid(openid); 
		
		if(user.getInteger("role")==1){
			Document doc =new Document();
			
			doc.put("state", 1);
			doc.put("is_show", 1);
			 
			List <Document> special=cgbdb.find_study_list("special", doc);
			 
	 	 
			model.put("list", special);
			 
		}else{
			model.put("list", list);
		}
		
		
		  
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		
		
		
		model.put("user", user);
		 
		sendjson(model, response);
	}
	
	
	@RequestMapping(value = { "/delete_collection" }, method = { RequestMethod.POST })
	public void delete_collection(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid");
		String c_no = request.getParameter("c_no");
		
		top_db.delete_collection(openid, c_no);
		
		model.put("back_code", 100);
		
		sendjson(model, response);
	}
	
	 
}
