package com.hysm.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hysm.db.Area_db;
import com.hysm.db.Article_db;
import com.hysm.db.Exercise_db;
import com.hysm.db.Msg_db;
import com.hysm.tools.DateTool;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseController {
	
	@Autowired
	private Area_db area_db;
	
	@Autowired
	private Article_db art_db;
	
	@Autowired
	private Exercise_db exercise_db;
	
	@Autowired
	private Msg_db msg_db;
	
	@RequestMapping(value = { "/get_art_list" })
	public String get_art_list(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int sort = 1; 
		if(request.getParameter("sort") != null){
			sort = Integer.valueOf(request.getParameter("sort"));
		}
		
		String input =""; 
		if(request.getParameter("input") != null){
			input = request.getParameter("input");
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = art_db.query_art_count(-1, sort,input); 
		List<Document> art_list = art_db.query_art_page(-1, sort, skip, limit,input); 
		
		Document recommend = art_db.query_recommend_art(sort);
		
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
		model.put("art_list", art_list);
		model.put("limit", limit); 
		model.put("count", count);
		
		model.put("sort", sort);
		
		model.put("input", input);
		
		model.put("recommend", recommend);
		
		return "/pt/art/art_list";
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/upload_artimg" }, method = { RequestMethod.POST })
	public void uploadCutImg(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = String.valueOf(System.currentTimeMillis());

		int res = 0;
		int is_img = 0;
		String path = request.getRealPath("");
		
		String p_path = path + "/img/art";
		
		String ext = null, file_path = null;
		String fileName = "";

		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());

			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				// size = size + file.getSize();
				fileName = file.getOriginalFilename();// 文件全名
				System.out.println(fileName);
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				
				//System.out.println(fileName);
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()) {
					System.out.println("//不存在");
					dir.mkdir();
				}

				if(ext.equals(".jpg")) {
					is_img = 1;
				}else if (ext.equals(".jpeg")) {
					is_img = 1;
				}else if (ext.equals(".JPG")) {
					is_img = 1;
				}else if (ext.equals(".JPEG")) {
					is_img = 1;
				}else if (ext.equals(".png")) {
					is_img = 1;
				}else if (ext.equals(".PNG")) {
					is_img = 1;
				}
				
				
				File localFile = new File(path+"/img/art" + "/", file_code + ext);// 指定文件名称后缀名  存原图
				file_path = p_path + "/" + file_code + ext;
				
				String f = file_code + ext;
				
				try {
					file.transferTo(localFile); // 保存图片到指定文件夹目录中
					  
					
				} catch (Exception e) {

					e.printStackTrace();
				}  
				res = 1;
 
				
			}
		} 
		if (res == 1) {
			sendMassage("img/art/"+ file_code + ext, response);
		} else {
			sendMassage("error", response);
		}
 
	}
	
	@RequestMapping(value = { "/add_art" }, method = { RequestMethod.POST })
	public void add_art(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int sort = Integer.valueOf(request.getParameter("sort"));
		
		int recommend = Integer.valueOf(request.getParameter("recommend"));
		
		String img = request.getParameter("img");
		
		String title = request.getParameter("title");
		String context = request.getParameter("context");
		
		//其他文章取消热荐
		if(recommend == 1){ 
			Document reco = art_db.query_recommend_art(sort); 
			
			if(reco != null){ 
				String _id = reco.get("_id").toString(); 
				reco.put("recommend", 0); 
				art_db.update_art(_id, reco);
			}
		}
		
		Document doc = new Document();
		
		doc.put("state", 1);
		doc.put("sort", sort);
		doc.put("recommend", recommend);
		
		doc.put("img", img);
		doc.put("title", title);
		doc.put("context", context);
		
		doc.put("ctime", DateTool.fromDate24H());
		doc.put("cdate", new Date());
		
		doc.put("read", 0);
		
		area_db.add_db_info("article", doc);
		
		model.put("back_code", 200); 
		sendjson(model, response);
		
	}
	
	
	@RequestMapping(value = { "/edit_art_show"})
	public String eidt_art_show(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String art_id = request.getParameter("art_id");
		
		Document art = art_db.query_one_art(art_id);
		
		model.put("art", art); 
		
		return "/pt/art/edit_art";
	}
	
	@RequestMapping(value = { "/edit_art" }, method = { RequestMethod.POST })
	public void edit_art(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int sort = Integer.valueOf(request.getParameter("sort")); 
		int recommend = Integer.valueOf(request.getParameter("recommend")); 
		String img = request.getParameter("img"); 
		String art_id = request.getParameter("art_id"); 
		String title = request.getParameter("title");
		String context = request.getParameter("context");
		
		//其他文章取消热荐
		if(recommend == 1){ 
			Document reco = art_db.query_recommend_art(sort); 
			
			if(reco != null){ 
				String _id = reco.get("_id").toString(); 
				reco.put("recommend", 0); 
				art_db.update_art(_id, reco);
			}
		}
		
		Document doc = art_db.query_one_art(art_id); 
		 
		doc.put("sort", sort);
		doc.put("recommend", recommend);
		
		doc.put("img", img);
		doc.put("title", title);
		doc.put("context", context);
		 
		  
		art_db.update_art(art_id, doc);
		
		model.put("back_code", 200); 
		sendjson(model, response);
		
	}
	
	@RequestMapping(value = { "/recommend_one" }, method = { RequestMethod.POST })
	public void recommend_one(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String art_id = request.getParameter("art_id");  
		int kind = Integer.valueOf(request.getParameter("kind"));
		
		if(kind == 0){
			//取消 
			Document doc = art_db.query_one_art(art_id); 
			
			String _id = doc.get("_id").toString(); 
			doc.put("recommend", 0); 
			art_db.update_art(_id, doc);
		}else{
			
			Document doc = art_db.query_one_art(art_id); 
			
			int sort = doc.getInteger("sort");
			
			//取消原来的
			Document reco = art_db.query_recommend_art(sort); 
			if(reco != null){ 
				String _id = reco.get("_id").toString(); 
				reco.put("recommend", 0); 
				art_db.update_art(_id, reco);
			} 
			 
			doc.put("recommend", 1); 
			art_db.update_art(art_id, doc);
		}
		
		model.put("back_code", 200); 
		sendjson(model, response);
		
	}
	
	@RequestMapping(value = { "/state_art" }, method = { RequestMethod.POST })
	public void state_art(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String art_id = request.getParameter("art_id"); 
		
		Document doc = art_db.query_one_art(art_id); 
		
		int state = 1 - doc.getInteger("state");
		
		doc.put("state", state); 
		art_db.update_art(art_id, doc);
		
		model.put("back_code", 200); 
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/art_list"}, method = { RequestMethod.POST })
	public void art_list(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int sort = 1; 
		if(request.getParameter("sort") != null){
			sort = Integer.valueOf(request.getParameter("sort"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = art_db.query_art_count(1, sort,""); 
		List<Document> art_list = art_db.query_art_page(1, sort, skip, limit,""); 
		
		Document recommend = art_db.query_recommend_art(sort);
		if(recommend != null){
			String _id = recommend.get("_id").toString();
			recommend.put("_id", _id);
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
		  
		model.put("page_num", page_num);
		model.put("page_count", page_count); 
		model.put("art_list", art_list);
		model.put("limit", limit);
		
		model.put("sort", sort); 
		model.put("recommend", recommend);
		 
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/read_art"}, method = { RequestMethod.POST })
	public void read_art(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String art_id = request.getParameter("art_id"); 
		Document doc = art_db.query_one_art(art_id); 
		
		int read = doc.getInteger("read")+1;
		
		doc.put("read", read);
		
		art_db.update_art(art_id, doc);
		
		model.put("article", doc);
		 
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/query_exercise"})
	public String query_exercise(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String c_no = request.getParameter("c_no");
		
		List<Document> exercise_list = exercise_db.query_exercise_list(c_no);
		
		model.put("exercise_list", exercise_list); 
		
		model.put("c_no", c_no); 
		
		return "/pt/art/exercise";
	}
	
	@RequestMapping(value = { "/commit_exercise"}, method = { RequestMethod.POST })
	public void commit_exercise(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String c_no = request.getParameter("c_no");
		
		String problem = request.getParameter("problem");
		String answer_a = request.getParameter("answer_a");
		String answer_b = request.getParameter("answer_b");
		String answer_c = request.getParameter("answer_c");
		String answer_d = request.getParameter("answer_d");
		String answer  = request.getParameter("answer");
		 
		String exercise_id = request.getParameter("exercise_id");
		
		if(exercise_id.equals("-1")){
			
			Document doc = new Document();
			
			doc.put("problem", problem);
			doc.put("A", answer_a);
			doc.put("B", answer_b);
			doc.put("C", answer_c);
			doc.put("D", answer_d); 
			doc.put("answer", answer); 
			doc.put("c_no", c_no);
			
			area_db.add_db_info("exercise", doc);
			
		}else{
			
			Document doc = exercise_db.query_one_exercise(exercise_id);
			
			doc.put("problem", problem);
			doc.put("A", answer_a);
			doc.put("B", answer_b);
			doc.put("C", answer_c);
			doc.put("D", answer_d); 
			doc.put("answer", answer);
			
			exercise_db.update_exercise(exercise_id, doc);
		}
		
		model.put("back_code", 100);
		 
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/delete_exercise"}, method = { RequestMethod.POST })
	public void delete_exercise(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String exercise_id = request.getParameter("exercise_id");
		
		exercise_db.delete_exercise(exercise_id);
		
		model.put("back_code", 100);
		 
		sendjson(model, response);
		
	}
	
	
	
	@RequestMapping(value = { "/query_discuss"}, method = { RequestMethod.POST })
	public void query_discuss(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String c_no = request.getParameter("c_no"); 
		String openid = request.getParameter("openid");
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		Document user = area_db.query_user_byopenid(openid);
		
		int count = exercise_db.query_discuss_count(c_no);
		
		List<Document> discuss_list = exercise_db.query_discuss_page(c_no, skip, limit);
		
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
		 
		model.put("user", user);
		model.put("discuss_list", discuss_list);
		
		sendjson(model, response);
		
	}
	
	@RequestMapping(value = { "/add_discuss"}, method = { RequestMethod.POST })
	public void add_discuss(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String c_no = request.getParameter("c_no"); 
		String openid = request.getParameter("openid");
		
		String val = request.getParameter("val");
		
		int is_at = Integer.valueOf(request.getParameter("is_at"));
		
		Document user = area_db.query_user_byopenid(openid);
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		doc.put("name", user.getString("name"));
		doc.put("head", user.getString("head"));
		doc.put("role", user.getInteger("role"));
		doc.put("val", val);
		doc.put("is_at", is_at);
		doc.put("at_state", 0); 
		doc.put("date", DateTool.fromDate24H());
		
		area_db.add_db_info("discuss", doc);
		
		model.put("back_code", 100);
		 
		sendjson(model, response); 
	}
	
	@RequestMapping(value = { "/query_at_discuss"})
	public String query_at_discuss(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		int page = 1; 
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		int count = exercise_db.query_discuss_at_count();
		
		int post_count = exercise_db.query_discuss_at_post();
		
		List<Document> discuss_list = exercise_db.query_discuss_at_page(skip, limit);
		
		List<Document> post_list = exercise_db.query_post_at_page(skip, limit);
		
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
		
		model.put("post_list", post_list);
		model.put("count", count);
		model.put("post_count", post_count);
		model.put("discuss_list", discuss_list);
		
		return "/pt/art/discuss"; 
	}
	
	@RequestMapping(value = { "/query_one_course"}, method = { RequestMethod.POST })
	public void query_one_course(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String c_no = request.getParameter("c_no"); 
		
		Document course = exercise_db.query_one_course(c_no);
		
		model.put("course", course);
		 
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/answer_discuss"}, method = { RequestMethod.POST })
	public void answer_discuss(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String discuss_id = request.getParameter("discuss_id"); 
		String answer_man = request.getParameter("answer_man"); 
		String answer_info = request.getParameter("answer_info"); 
		
		Document doc = exercise_db.query_one_discuss(discuss_id); 
		doc.put("at_state", 1);
		
		doc.put("answer_man", answer_man);
		doc.put("answer_info", answer_info);
		doc.put("answer_time",  DateTool.fromDate24H());
		
		exercise_db.update_discuss(discuss_id, doc);
		
		//添加导师回复模板消息
		
		/*Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("msg_type", 4);
		map.put("openid", doc.getString("open_id"));
		
		map.put("send_name", doc.getString("name"));
		map.put("send_text", doc.getString("val"));
		
		map.put("back_name", answer_man);
		map.put("back_text", answer_info);
		
		msg_db.add_msg(map); */

		model.put("back_code", 100);
		 
		sendjson(model, response); 
		
	}
	
	
	@RequestMapping(value = { "/answer_post"}, method = { RequestMethod.POST })
	public void answer_post(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String discuss_id = request.getParameter("discuss_id"); 
		String answer_man = request.getParameter("answer_man"); 
		String answer_info = request.getParameter("answer_info"); 
		
		Document doc = exercise_db.query_one_post(discuss_id); 
		doc.put("at_state", 1);
		doc.put("is_at", 1);
		doc.put("answer_man", answer_man);
		doc.put("answer_info", answer_info);
		doc.put("answer_time",  DateTool.fromDate24H());
		
		exercise_db.update_post(discuss_id, doc);
		
		
		Document ddcc=new Document();
		ddcc.put("post_id", discuss_id);
		ddcc.put("name", answer_man);
		ddcc.put("head", doc.getString("head"));
		ddcc.put("context", answer_info);
		ddcc.put("state", 1);
		ddcc.put("zan", 1);
		ddcc.put("ctime", DateTool.fromDate24H());
		ddcc.put("cdate", DateTool.fromDateY());
		ddcc.put("teacher", answer_man);
		
		
		area_db.add_db_info("post_log", ddcc);
		
		
		//添加导师回复模板消息
		
	/*	Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("msg_type", 4);
		map.put("openid", doc.getString("open_id"));
		
		map.put("send_name", doc.getString("name"));
		map.put("send_text", doc.getString("context"));
		
		map.put("back_name", answer_man);
		map.put("back_text", answer_info);
		
		msg_db.add_msg(map); 
*/
		model.put("back_code", 100);
		 
		sendjson(model, response); 
		
	}
	
	@RequestMapping(value = { "/query_lunbo"})
	public String query_lunbo(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		List<Document> lunbo_list = art_db.query_lunbo();
		
		model.put("lunbo_list", lunbo_list);
		
		return "/pt/art/lunbo";
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "/upload_lunbo" }, method = { RequestMethod.POST })
	public void upload_lunbo(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		
		String l_name = request.getParameter("l_name"); 
		String l_link = request.getParameter("l_link"); 
		String l_id = request.getParameter("l_id"); 
		
		int has_img =0;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = String.valueOf(System.currentTimeMillis());

		int res = 0;
		int is_img = 0;
		String path = request.getRealPath(""); 
		String p_path = path + "/img/art"; 
		String ext = null, file_path = null;
		String fileName = "";

		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());

			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {
				 has_img =1;
				// size = size + file.getSize();
				fileName = file.getOriginalFilename();// 文件全名
				System.out.println(fileName);
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				
				//System.out.println(fileName);
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()) {
					System.out.println("//不存在");
					dir.mkdir();
				}

				if(ext.equals(".jpg")) {
					is_img = 1;
				}else if (ext.equals(".jpeg")) {
					is_img = 1;
				}else if (ext.equals(".JPG")) {
					is_img = 1;
				}else if (ext.equals(".JPEG")) {
					is_img = 1;
				}else if (ext.equals(".png")) {
					is_img = 1;
				}else if (ext.equals(".PNG")) {
					is_img = 1;
				}
				
				
				File localFile = new File(path+"/img/art" + "/", file_code + ext);// 指定文件名称后缀名  存原图
				file_path = p_path + "/" + file_code + ext;
				
				String f = file_code + ext;
				
				try {
					file.transferTo(localFile); // 保存图片到指定文件夹目录中
					  
					
				} catch (Exception e) {

					e.printStackTrace();
				}  
				res = 1; 
				
			}
		}
		
		String back_code ="";
		
		if(l_id.equals("-1")){
			//新增 
			if( has_img ==1 && res == 1 ){
				
				Document doc = new Document();
				doc.put("name", l_name);
				doc.put("img", "img/art/"+ file_code + ext);
				doc.put("link", l_link);
				doc.put("cdate", DateTool.fromDateY());
				
				area_db.add_db_info("lunbo", doc);
				
				back_code ="success";
			}else{
				back_code ="error";
			}
		}else{
			
			Document doc = art_db.query_one_lunbo(l_id);
			
			doc.put("name", l_name);
			if( has_img ==1 && res == 1 ){
				doc.put("img", "img/art/"+ file_code + ext);
			} 
			doc.put("link", l_link);
			doc.put("cdate", DateTool.fromDateY());
			
			art_db.update_lunbo(l_id, doc);
			
			back_code ="success";
		}
		 
		sendMassage(back_code, response);
		 
	}
	
	@RequestMapping(value = { "/query_host"}, method = { RequestMethod.POST })
	public void query_host(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		List<Document> c_list = art_db.query_host_course();
		
		List<Document> s_list = art_db.query_host_special();
		
		model.put("c_list", c_list);
		model.put("s_list", s_list);
		 
		sendjson(model, response); 
		
	}
	 
	
	@RequestMapping(value = { "/edit_404"})
	public String edit_page(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String html = ""; 
		
		int has_info = 0;
		
		if(request.getParameter("html") != null){
			html = request.getParameter("html");
			
			has_info = 1;
		}
		   
		Document set_page = art_db.query_set_page(404);
		
		if(has_info == 1){
			set_page.put("html", html);
			
			String _id = set_page.get("_id").toString();
			
			art_db.update_set_page(_id, set_page);
		}
		 
		 
		
		model.put("set_page", set_page);
		
		return "/pt/shop/edit_404";
	}
	
	@RequestMapping(value = { "/edit_the"})
	public String edit_the(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		String html = ""; 
		
		int has_info = 0;
		
		if(request.getParameter("html") != null){
			html = request.getParameter("html");
			
			has_info = 1;
		}
		   
		Document set_page = art_db.query_set_page(1);
		
		if(has_info == 1){
			set_page.put("html", html);
			
			String _id = set_page.get("_id").toString();
			
			art_db.update_set_page(_id, set_page);
		}
		 
		 
		
		model.put("set_page", set_page);
		
		return "/pt/shop/edit_the";
	}
	
	
	@RequestMapping(value = { "/get_404"})
	public void get_404(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		Document set_page = art_db.query_set_page(404);
		
		model.put("set_page", set_page);
		
		sendjson(model, response);
	}
	
	@RequestMapping(value = { "/get_the"})
	public void get_the(HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model){
		
		Document set_page = art_db.query_set_page(1);
		
		model.put("set_page", set_page);
		
		sendjson(model, response);
	}
	
	
	
}
