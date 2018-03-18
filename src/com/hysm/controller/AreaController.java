package com.hysm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

 
import com.hysm.db.Area_db;
import com.hysm.db.Article_db;
import com.hysm.tools.DateTool;
import com.hysm.tools.Word_tool;

@Controller
@RequestMapping("area")
public class AreaController extends BaseController {

	@Autowired
	private Area_db area_db;
	
	@Autowired
	private Article_db art_db;
	
	
	
	/**
	 * 查询讨论区
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/query_post_page" }, method = { RequestMethod.POST })
	public void query_post_page(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		System.out.println("------------1---------------");
		
		String openid = request.getParameter("openid");
		
		System.out.println("-------------2--------------"+openid);
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		System.out.println("-------------3-------------"+openid);
		
		Document user = area_db.query_user_byopenid(openid);
		
		int count = area_db.query_post_count(1,0,"");
		
		List<Document> post_list = area_db.query_post_page(1,skip, limit,0,"");
		System.out.println("-------------4-------------"+openid);
		List<String> id_list = new ArrayList<String>(); 
		
		//遍历，替换敏感字
		if(post_list.size() > 0){ 
			List<Document> word_list = area_db.query_all_word(); 
			 
				for(int i=0;i<post_list.size();i++){
					
					if(word_list.size() > 0){
					
						String new_title = Word_tool.replace_check(post_list.get(i).getString("title"), word_list);
						post_list.get(i).put("title", new_title);
						
						String new_context = Word_tool.replace_check(post_list.get(i).getString("context"), word_list); 
						post_list.get(i).put("context", new_context);
					
					}
					
					id_list.add(post_list.get(i).getString("_id"));
					
				}  
			
		}
		
		if(id_list.size() > 0){
			
			List<Document> zan_log = area_db.query_zan_log(openid, id_list, 1); 
			
			System.out.println(zan_log);
			
			if(zan_log.size() > 0){
				
				for(int i=0;i<zan_log.size();i++){
					
					for(int j=0;j<post_list.size();j++){
						
						if(post_list.get(j).getString("_id").equals(zan_log.get(i).getString("pid"))){
							post_list.get(j).put("zan_state", 1);
							 
						}
						
					}
					
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
		
		List<Document> lunbo_list = art_db.query_lunbo();
		
		model.put("lunbo_list", lunbo_list);
		  
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		 
		model.put("user", user);
		model.put("post_list", post_list);
		
		sendjson(model, response);
		
	}
	
	/**
	 * 新增话题    发帖
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/add_post" }, method = { RequestMethod.POST })
	public void add_post(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid");
		
		String title = request.getParameter("title"); 
		String context = request.getParameter("context");
		
		//int is_at=Integer.valueOf(request.getParameter("is_at"));
		String is_at = request.getParameter("is_at");
		
		Document user = area_db.query_user_byopenid(openid);
		
		Document doc = new Document();
		
		
				if(is_at!=null&&!is_at.equals("")){
					doc.put("at_state", 0);
					doc.put("is_at", Integer.valueOf(is_at));
				}
		
		doc.put("name", user.getString("name"));
		doc.put("head", user.getString("head"));
		doc.put("open_id", user.getString("open_id"));
		doc.put("title", title);
		doc.put("context", context);
		doc.put("zan", 0);
		doc.put("state", 1); 
		doc.put("ctime", DateTool.fromDate24H());
		doc.put("cdate", DateTool.fromDateY());
		
		area_db.add_db_info("post", doc);
		
		model.put("back_code", 200); 
		sendjson(model, response);
	}
	
	/**
	 * 讨论区的赞与取消赞
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/zan_post" }, method = { RequestMethod.POST })
	public void zan_post(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("post_id");
		String openid = request.getParameter("openid");
		
		int kind = Integer.valueOf(request.getParameter("kind"));
		
		if(kind == 1){
			area_db.zan_post(_id);
			
			Document doc = new Document();
			doc.put("openid", openid);
			doc.put("cdate", DateTool.fromDateY());
			doc.put("pid", _id);
			doc.put("type", 1);
			
			area_db.add_db_info("zan_log", doc);
			
		}else{
			area_db.no_zan_post(_id);
			
			area_db.delete_zan(_id, openid, 1);
		}
		  
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	/**
	 * 查询话题的评论
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/query_post_log" }, method = { RequestMethod.POST })
	public void query_post_log(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("post_id");
		String openid = request.getParameter("openid");
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		} 
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		Document post = area_db.query_one_post(_id); 
		Document post_user = area_db.query_user_byopenid(post.getString("open_id"));
		
		post.put("role", post_user.getInteger("role"));
		
		//遍历，替换敏感字
		List<Document> word_list = area_db.query_all_word();
		
		if(word_list.size() > 0){
			String n_title = Word_tool.replace_check(post.getString("title"), word_list);
			post.put("title", n_title); 
			String n_context = Word_tool.replace_check(post.getString("context"), word_list); 
			post.put("context", n_context);
		} 
		
		int zan_no = area_db.query_one_zan(openid, _id); 
		if(zan_no > 0){
			post.put("zan_state", 1);
		}else{
			post.put("zan_state", 0);
		}
		
		int count = area_db.query_post_log_count(1,_id); 
		List<Document> post_log = area_db.query_post_log_page(1,_id,skip, limit);
		
		Document ddd=new Document();
		Map<String,Object> mm=new HashMap<String, Object>();
		mm.put("$exists", true);
		ddd.put("teacher",mm);
		ddd.put("post_id", _id);
		
		Document teacher_log=area_db.find_vip_order("post_log", ddd);
		
		model.put("teacher_log", teacher_log);
		List<String> id_list = new ArrayList<String>();
		
		//遍历，替换敏感字
		if(post_log.size() > 0){ 
			  
				
			for(int i=0;i<post_log.size();i++){
				if(word_list.size() > 0){
					
					String new_context = Word_tool.replace_check(post_log.get(i).getString("context"), word_list); 
					post_log.get(i).put("context", new_context);
				
				}
				
				Document p_user = area_db.query_user_byopenid(post_log.get(i).getString("open_id"));
				if(p_user != null){
					post_log.get(i).put("role", p_user.getInteger("role"));
				} 
				
				id_list.add(post_log.get(i).getString("_id"));
			}
			
		}
		
		
		if(id_list.size() > 0){
			
			List<Document> zan_log = area_db.query_zan_log(openid, id_list, 2); 
			
			if(zan_log.size() > 0){
				
				for(int i=0;i<zan_log.size();i++){
					
					for(int j=0;j<post_log.size();j++){
						
						if(post_log.get(j).getString("_id").equals(zan_log.get(i).getString("pid"))){
							post_log.get(j).put("zan_state", 1);
						}
						
					}
					
				}
				
			} 
		}
		
		Document user = area_db.query_user_byopenid(openid);
		
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
		 
		 
		model.put("post_log", post_log);
		
		model.put("post", post);
		model.put("user", user);
		
		sendjson(model, response);
	}
	
	/**
	 * 新增评论    发帖
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/add_post_log" }, method = { RequestMethod.POST })
	public void add_post_log(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String openid = request.getParameter("openid"); 
		String post_id = request.getParameter("post_id"); 
		String context = request.getParameter("context"); 
		
		Document user = area_db.query_user_byopenid(openid);
		
		Document doc = new Document();
		
		doc.put("name", user.getString("name"));
		doc.put("head", user.getString("head"));
		
		doc.put("post_id", post_id);
		doc.put("context", context);
		doc.put("zan", 0);
		doc.put("state", 1); 
		doc.put("ctime", DateTool.fromDate24H());
		doc.put("cdate", DateTool.fromDateY());
		
		area_db.add_db_info("post_log", doc);
		
		model.put("back_code", 200); 
		sendjson(model, response);
	}
	
	/**
	 * 赞与取消赞
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/zan_post_log" }, method = { RequestMethod.POST })
	public void zan_post_log(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("log_id");
		String openid = request.getParameter("openid");
		
		int kind = Integer.valueOf(request.getParameter("kind"));
		
		if(kind == 1){
			area_db.zan_post_log(_id);
			
			Document doc = new Document();
			doc.put("openid", openid);
			doc.put("cdate", DateTool.fromDateY());
			doc.put("pid", _id);
			doc.put("type", 2); 
			area_db.add_db_info("zan_log", doc);
			
		}else{
			area_db.no_zan_post_log(_id);
			
			area_db.delete_zan(_id, openid, 2);
		}
		  
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	/**
	 * 查询讨论区
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/get_post_list" })
	public String get_post_list(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
	 
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int kind = 0; 
		if(request.getParameter("kind") != null){
			kind = Integer.valueOf(request.getParameter("kind"));
		}
		
		String input = "";
		if(request.getParameter("input") != null){
			input = request.getParameter("input");
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		 
		
		int count = area_db.query_post_count(-1,kind,input);
		
		List<Document> post_list = area_db.query_post_page(-1,skip, limit,kind,input);
		 
				 
		
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
		
		model.put("count", count);
		model.put("limit", limit);
		
		model.put("kind", kind);
		model.put("input", input); 
		
		model.put("post_list", post_list);
		
		return "/pt/post/post_list";
		
	}
	
	/**
	 * 查询话题的评论
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/get_post_log" })
	public String get_post_log(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("post_id");
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		} 
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		Document post = area_db.query_one_post(_id);
		
		int count = area_db.query_post_log_count(-1,_id); 
		List<Document> post_log = area_db.query_post_log_page(-1,_id,skip, limit);
		
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
		model.put("post_log", post_log);
		model.put("count", count);
		model.put("limit", limit);
		
		model.put("post", post);
		
		return "/pt/post/post_log";
	}
	
	@RequestMapping(value = { "/change_post" }, method = { RequestMethod.POST })
	public void change_post(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String post_id = request.getParameter("post_id");
		 
		area_db.change_post(post_id);
		  
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/change_post_log" }, method = { RequestMethod.POST })
	public void change_post_log(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String log_id = request.getParameter("log_id");
		 
		area_db.change_post_log(log_id);
		  
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/forbit_list" })
	public String forbit_list(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int kind = 0; 
		if(request.getParameter("kind") != null){
			kind = Integer.valueOf(request.getParameter("kind"));
		}
		
		String input = "";
		if(request.getParameter("input") != null){
			input = request.getParameter("input");
		}
		
		int forbit = 0;
		if(request.getParameter("forbit") != null){
			forbit = Integer.valueOf(request.getParameter("forbit"));
		}
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		} 
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = area_db.get_user_forbit_count(kind, input, forbit);
		
		List<Document> user_list = area_db.get_user_forbit_count(kind, input, forbit, skip, limit);
		
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
		model.put("user_list", user_list);
		model.put("limit", limit);
		model.put("count", count);
		
		model.put("kind", kind);
		model.put("input", input);
		model.put("forbit", forbit);
		
		return "/pt/post/forbit";
	}
	
	@RequestMapping(value = { "/forbit_all" }, method = { RequestMethod.POST })
	public void forbit_all(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String id_str = request.getParameter("id_str");
		
		int forbit = Integer.valueOf(request.getParameter("forbit"));
		 
		String[] arr = id_str.split(","); 
		List<ObjectId> id_list = new ArrayList<ObjectId>();
		
		for(int i=0;i<arr.length;i++){
			id_list.add(new ObjectId(arr[i]));
		}
		
		area_db.forbit_users(id_list, forbit);
		  
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/word_list" })
	public String word_list(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		List<Document> word_list = area_db.query_all_word();  
		model.put("word_list", word_list); 
		
		return "/pt/post/word_list";
	}
	
	@RequestMapping(value = { "/add_word" }, method = { RequestMethod.POST })
	public void add_word(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String word = request.getParameter("word");
		
		int c = area_db.get_word_count(word);
		
		if(c < 1){
			Document doc = new Document(); 
			doc.put("word", word);
			
			area_db.add_db_info("sensitive_word", doc);
			
			model.put("back_code", 200); 
			
		}else{
			model.put("back_code", 100); 
		}
		  
		
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/delete_word" }, method = { RequestMethod.POST })
	public void delete_word(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("word_id");
		
		area_db.delete_word(_id);
		
		model.put("back_code", 200); 
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/set_yg" })
	public String set_yg(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		 Document yg = area_db.query_yg();
		 
		 model.put("yg", yg); 
		 
		
		return "/pt/post/set_yg";
	}
	
	@RequestMapping(value = { "/aaa_yg" })
	public String aaa_yg(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String _id = request.getParameter("id");
		String name = request.getParameter("name");
		String link = request.getParameter("link");
		String time = request.getParameter("time");
		String time_note = request.getParameter("time_note");
		
		Document doc = new Document();
		doc.put("name", name);
		doc.put("link", link);
		doc.put("time", time);
		doc.put("time_note", time_note);
		
		area_db.update_yg(_id, doc);
		
		 Document yg = area_db.query_yg(); 
		 model.put("yg", yg); 
		 
		
		return "/pt/post/set_yg";
	}
}
