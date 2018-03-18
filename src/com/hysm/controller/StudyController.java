package com.hysm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;

import com.hysm.bean.Storage_data;
import com.hysm.db.Area_db;
import com.hysm.db.CbgDB;
import com.hysm.db.Study_db;
import com.hysm.db.Top_db;
import com.hysm.tools.DateTool;
import com.mongodb.client.model.Filters;

@Controller
@RequestMapping("study")
public class StudyController extends BaseController {

	@Autowired
	private Area_db area_db;
	
	@Autowired
	private Study_db study_db;
	@Autowired
	private CbgDB cgbdb;
	
	@Autowired
	private Top_db top_db;
	
	
	@RequestMapping(value = { "/add_study" }, method = { RequestMethod.POST })
	public void set_top(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid"); 
		String c_no = request.getParameter("c_no");
		
		String cid =""; //专题的视频id 
		if(request.getParameter("cid") != null){
			cid = request.getParameter("cid");
		}
		
		int see_time = Integer.valueOf(request.getParameter("see_time")); 
		int is_end = Integer.valueOf(request.getParameter("is_end"));
		int is_buy = Integer.valueOf(request.getParameter("is_buy"));
		int type = Integer.valueOf(request.getParameter("type"));//1是课程 2是专题
		
		System.out.println("-----------------"+type);
		
		Document course = null; 
		
		if(type != 2){
			 course = study_db.query_one_course(c_no);
		}else{
			 course = study_db.quert_special(c_no);
		}
		
		
		if(see_time < 5){ 
			see_time = 5;
		}
		
		//查询是否有未看完的课程
		//Document s = study_db.query_state_study(openid, c_no, 1);
		
		//查询课程记录
		Document s = study_db.query_the_study(openid, c_no);
		
		//视频看完加学分
		if(is_end==1){
			
			Document dd=new Document();
				dd.put("open_id", openid); 
			Document config_s=cgbdb.query_config();	
			
			Document user =cgbdb.findwxfamilyinfo("user", dd);
		
			user.put("score", user.getInteger("score")+config_s.getInteger("sign_score"));
			cgbdb.replaceOne("user", Filters.eq("open_id", user.getString("open_id")), user);	
				 
			Document da=new Document();
			da.put("open_id", openid);
			da.put("date", DateTool.fromDateY());
			da.put("score", config_s.getInteger("video_score"));
			da.put("name", user.getString("name"));
			da.put("type", 1);
			cgbdb.insertOne("sign_log", da);
				
		}
			
			
		
		if(s == null){
			
			//新增 
			Document doc = new Document();
			doc.put("openid", openid);
			doc.put("c_no", c_no);
			doc.put("see_time", see_time);
			doc.put("is_buy", is_buy);
			doc.put("c_date", DateTool.fromDateY());
			
			if(type != 2){
				//doc.put("type", course.getInteger("type"));
			//	doc.put("c_name", course.getString("c_name"));
			}else{
				doc.put("type", 2);
				doc.put("c_name", course.getString("special_name"));
				
				 
				
				//专题，每个视频的播放记录
				List<Map<String,Object>> look_log = new ArrayList<Map<String,Object>>();
				
				Map<String,Object> mmm = new HashMap<String, Object>();
				mmm.put("cid", cid);
				mmm.put("see_time", see_time);
				mmm.put("is_end", is_end);
				
				look_log.add(mmm);
				
				doc.put("look_log", look_log);
			}
			 
			doc.put("img", course.getString("img"));
			
			if(is_end == 1){
				doc.put("state", 2);
				 
			}else{
				doc.put("state", 1);
			}
			
			if(type == 2){
				doc.put("state", 1);
			}
			
			area_db.add_db_info("study_log", doc);
			
		}else{
			//修改
			
			s.put("see_time", see_time);
			s.put("is_buy", is_buy);
			s.put("c_date", DateTool.fromDateY());
			
			if(type != 2){
				s.put("type", course.getInteger("type"));
				s.put("c_name", course.getString("c_name"));
			}else{
				s.put("type", 2);
				s.put("c_name", course.getString("special_name"));
				
				 
				
				//专题，每个视频的播放记录
				
				List<Map<String,Object>> look_log = null;
				
				if(s.get("look_log") != null){
					look_log = (List<Map<String,Object>>)s.get("look_log");
				}else{
					look_log = new ArrayList<Map<String,Object>>();
				}
				
				int has_look = 0; 
				for(int i=0;i<look_log.size();i++){
					
					if((look_log.get(i).get("cid").toString()).equals(cid)){
						
						has_look = 1; //已有观看记录 
						look_log.get(i).put("see_time", see_time);
						look_log.get(i).put("is_end", is_end);
					}
				}
				
				if(has_look == 0){
					Map<String,Object> mmm = new HashMap<String, Object>();
					mmm.put("cid", cid);
					mmm.put("see_time", see_time);
					mmm.put("is_end", is_end);
					
					look_log.add(mmm);
				}
				
				s.put("look_log", look_log);
				
			}
			 
			s.put("img", course.getString("img"));
			
			if(is_end == 1){
				s.put("state", 2);
			}else{
				s.put("state", 1);
			}
			
			String _id = s.get("_id").toString();
			
			study_db.update_study(_id, s);
		}
		
		model.put("back_code", 100); 
		sendjson(model, response);
	}
	
	
	@RequestMapping(value = { "/query_study" }, method = { RequestMethod.POST })
	public void query_study(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid"); 
		
		Document user = area_db.query_user_byopenid(openid);
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = study_db.query_study_count(openid);
		
		List<Document> list = study_db.query_study_page(openid, skip, limit);
		
		List<Document> order_list = top_db.query_order_all(openid);
		
		//判断
		
		for(int i=0;i<list.size();i++){
			
			for(int j=0;j<order_list.size();j++){
				
				if(list.get(i).getString("c_no").equals(order_list.get(j).getString("c_no"))){
					list.get(i).put("is_buy", 1);
				}
				
			} 
		}
		 
		
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
		
		Document yg = area_db.query_yg();
		model.put("yg", yg);
		
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		 
		model.put("user", user);
		model.put("list", list);
		
		sendjson(model, response);
	}
	
	 
	@RequestMapping(value = { "/query_score" }, method = { RequestMethod.POST })
	public void query_score(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid"); 
		
		Document user = area_db.query_user_byopenid(openid);
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int score = user.getInteger("score"); 
		int more = study_db.query_more_count(score); 
		int eq = study_db.query_dy_count(score);
		
		int count = study_db.query_user_count();
		
		List<Document> list = study_db.query_s_page(skip, limit);
		
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
		model.put("list", list);
		
		model.put("more", more);
		model.put("eq", eq);
		
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/get_free_music" }, method = { RequestMethod.POST })
	public void get_free_music(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
	
		
			List list = this.study_db.get_free_music();

		    List free_video = this.study_db.get_free_video();

		    Document yg = this.area_db.query_yg();

		    model.put("list", list);
		    model.put("free_video", free_video);

		    model.put("yg", yg);

		    sendjson(model, response);
		
		
		
	}
	
}
