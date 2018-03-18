/**
 * 
 */
package com.hysm.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.Document;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hysm.db.Area_db;
import com.hysm.db.Article_db;
import com.hysm.db.CbgDB;
import com.hysm.db.Exercise_db;
import com.hysm.db.Msg_db;
import com.hysm.db.Sj_db;
import com.hysm.db.Study_db;
import com.hysm.db.Top_db;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.hysm.tools.Download_Excel;
import com.hysm.tools.HighImage;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;
import com.hysm.tools.random4;
import com.hysm.wecat.Msg_tools;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;

import net.sf.json.JSONArray;

/**show_log
 * XXXXXXXXI
 * 
 * @author cgb
 * @date 2017年5月6日
 */

@Controller
@RequestMapping("course")
public class CourseController extends BaseController {

	private int ps = 50;

	@Autowired
	private CbgDB cgbdb;

	@Autowired
	private Sj_db sj;

	@Autowired
	private Article_db art_db;

	@Autowired
	private Exercise_db exercise_db;

	@Autowired
	private Study_db study_db;

	@Autowired
	private Top_db top_db;

	@Autowired
	private Top_db topdb;

	@Autowired
	private Msg_db msg_db;

	@Autowired
	private Area_db area_db;

	@RequestMapping("show_course")
	public String show_shop(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String c_name = req.getParameter("name");
		String state2 = req.getParameter("state");

		int type = 5;
		if (state2 == null) {

			type = 3; // 1.视频0语音-1免费课程
		} else {

			type = Integer.valueOf(state2); // 1.视频0语音-1免费课程
		}

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);
		map.append("ps", ps);
		if (c_name != null && !c_name.equals("")) {
			map.append("c_name", c_name);
		}

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("course", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb3("course", map, type);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		model.put("course_list", pb);

		return "/pt/course/course";
	}

	@RequestMapping("update_course")
	public void update_course(HttpServletRequest req,
			HttpServletResponse response) {
		String c_name = req.getParameter("c_name");
		String price = req.getParameter("price");
		String c_no = req.getParameter("c_no");

		String discount_price = req.getParameter("discount_price=");
		String teacher = req.getParameter("teacher");
		String is_freer = req.getParameter("is_freer");
		String state = req.getParameter("state");
		String top = req.getParameter("top");

		MongoUtil mu = MongoUtil.getThreadInstance();

		Document bson = mu.findOne("course",
				new Document().append("c_no", c_no));
		bson.put("c_name", c_name);
		bson.put("price", price);

		bson.put("discount_price", Integer.valueOf(discount_price));
		bson.put("teacher", teacher);
		bson.put("is_freer", is_freer);
		bson.put("state", Integer.valueOf(state));
		bson.put("top", Integer.valueOf(top));

		mu.replaceOne("course", Filters.eq("c_no", c_no), bson);
		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "success");

		sendjson(map, response);
	}

	@RequestMapping("update_special")
	public void update_special(HttpServletRequest req,
			HttpServletResponse response) {
		String c_name = req.getParameter("c_name");
		String price = req.getParameter("price");
		String c_no = req.getParameter("c_no");
		String discount_price = req.getParameter("discount_price=");
		String teacher = req.getParameter("teacher");
		String is_freer = req.getParameter("is_freer");
		String state = req.getParameter("state");
		String top = req.getParameter("top");

		MongoUtil mu = MongoUtil.getThreadInstance();

		Document bson = mu.findOne("special",
				new Document().append("special_no", c_no));
		bson.put("special_name", c_name);
		bson.put("price", price);
		bson.put("discount_price", Integer.valueOf(discount_price));
		bson.put("teacher", teacher);
		bson.put("is_freer", Integer.valueOf(is_freer));
		bson.put("state", Integer.valueOf(state));
		bson.put("top", Integer.valueOf(top));

		mu.replaceOne("special", Filters.eq("special_no", c_no), bson);
		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "success");

		sendjson(map, response);
	}

	@RequestMapping("delete_c_no")
	public void delete_c_no(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		if (c_no != null && !c_no.equals("")) {
			MongoUtil mu = MongoUtil.getThreadInstance();
			Document doc = new Document();
			doc.put("c_no", c_no);
			mu.deleteMany("course", doc);

			model.put("result", "success");

		} else {
			model.put("result", "error");

		}
		sendjson(model, response);
	}

	@RequestMapping("delete_special_nom")
	public void delete_special_nom(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("special_nom"); // 课程编号
		if (c_no != null && !c_no.equals("")) {
			MongoUtil mu = MongoUtil.getThreadInstance();
			Document doc = new Document();
			doc.put("special_no", c_no);
			mu.deleteMany("special", doc);

			model.put("result", "success");

		} else {
			model.put("result", "error");

		}
		sendjson(model, response);
	}

	@RequestMapping("delete_special_nom2")
	public void delete_special_nom2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("special_nom"); // 课程编号
		if (c_no != null && !c_no.equals("")) {
			MongoUtil mu = MongoUtil.getThreadInstance();
			Document doc = new Document();
			doc.put("special_no", c_no);
			mu.deleteMany("special", doc);

			model.put("result", "success");

		} else {
			model.put("result", "error");

		}
		sendjson(model, response);
	}

	@RequestMapping("courseinfo")
	public String courseinfo(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		Document map = new Document();
		if (c_no != null && !c_no.equals("")) {
			map.append("c_no", c_no);
			Document course = cgbdb.findcourse_detail("course", map);
			if (course != null && !course.equals("")) {

				model.put("course", course);
			}
		}

		return "/pt/course/CourseInfo";
	}

	@RequestMapping("update_cc")
	public String update_cc(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		Document map = new Document();
		if (c_no != null && !c_no.equals("")) {
			map.append("c_no", c_no);
			Document course = cgbdb.findcourse_detail("course", map);
			if (course != null && !course.equals("")) {

				model.put("course", course);
			}
		}

		return "/pt/course/update_course";
	}

	@RequestMapping("specialinfo")
	public String specialinfo(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = req.getParameter("special_no");
		Document map = new Document();
		if (special_no != null && !special_no.equals("")) {
			map.append("special_no", special_no);
			Document course = cgbdb.findc_special_no("special", map);// 查询专题信息
			if (course != null && !course.equals("")) {

				model.put("special", course);
			}
		}

		return "/pt/course/SpecialInfo";
	}

	@RequestMapping("little_specialinfo")
	public String little_specialinfo(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = req.getParameter("special_no");
		Document map = new Document();
		if (special_no != null && !special_no.equals("")) {
			map.append("special_no", special_no);
			Document course = cgbdb.findc_special_no("special", map);// 查询专题信息
			if (course != null && !course.equals("")) {

				model.put("little_special", course);
			}
		}

		return "/pt/course/little_specialinfo";
	}

	@RequestMapping("update_sp")
	public String update_sp(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = req.getParameter("special_no");
		Document map = new Document();
		if (special_no != null && !special_no.equals("")) {
			map.append("special_no", special_no);
			map.append("state", 1);
			Document course = cgbdb.findc_special_no("special", map);// 查询专题信息
			if (course != null && !course.equals("")) {

				List<String> uul = (List<String>) course.get("url");
				
				Map<String, Object> map3 = new HashMap<String, Object>(); 
				map3.put("$in", uul);
				
				Document doc4=new Document();
					doc4.put("c_no", map3);
					doc4.put("state", 1);
					List<Document> doc4_list = cgbdb.show_collect(
							"course", doc4);//查询数组中的所有课程
				/*
				 * List<String> uulstr=new ArrayList<String>(); if(uul!=null){
				 * for(Document d:uul){ uulstr.add(d); } }
				 */

				int type = 0;
				if (uul.size() > 0) {

					for (int i = 0; i < uul.size(); i++) {

						Document dd = new Document();

						dd.put("c_no", uul.get(i));
						dd.put("state", 1);
						model.put("cc_no", uul.get(i));
						Document c_no_course = cgbdb.findcourse_detail(
								"course", dd);

						if (c_no_course != null) {

							type = c_no_course.getInteger("type");

							Document doc = new Document();
							doc.put("is_free", "1");
							doc.put("type", type);
							doc.put("state", 1);
							doc.put("is_show", 1);
							List<Document> course_list = cgbdb.show_collect(
									"course", doc);// 查询所有课程
							model.put("course_list", course_list);
						}

					}

				}
				model.put("doc4", doc4_list);
				model.put("uul", uul);
				model.put("special", course);
			}
		}

		return "/pt/course/uodate_special";
	}

	@RequestMapping("show_uodate_little")
	public String show_uodate_little(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = req.getParameter("special_no");
		Document map = new Document();
		if (special_no != null && !special_no.equals("")) {
			map.append("special_no", special_no);
			Document course = cgbdb.findc_special_no("special", map);// 查询专题信息
			if (course != null && !course.equals("")) {

				List<Document> uul = (List<Document>) course.get("url");

				/*
				 * List<String> uulstr=new ArrayList<String>(); if(uul!=null){
				 * for(Document d:uul){ uulstr.add(d.getString("all_cno")); } }
				 */

				int type = 0;
				if (uul.size() > 0) {

					for (int i = 0; i < uul.size(); i++) {

						Document dd = new Document();

						dd.put("c_no", uul.get(i));

						Document c_no_course = cgbdb.findcourse_detail(
								"course", dd);

						if (c_no_course != null) {

							type = c_no_course.getInteger("type");

							Document doc = new Document();
							doc.put("is_free", "1");
							doc.put("type", type);
							doc.put("state", 1);
							doc.put("is_show", 1);
							List<Document> course_list = cgbdb.show_collect(
									"course", doc);// 查询所有课程
							model.put("course_list", course_list);
						}

					}

				}

				model.put("uul", uul);
				model.put("special", course);
			}
		}

		return "/pt/course/update_little_special";
	}

	@RequestMapping("freeze2")
	public void freeze1(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		String sku_id = req.getParameter("c_no");
		String state = req.getParameter("state");
		Document doc = new Document();

		if (sku_id == null || sku_id.equals("")) {
			model.put("result", "error");
		} else {
			doc.append("sku_id", sku_id);

			Document user = cgbdb.findc_no("course", doc);
			if (user != null) {

				if (state.equals("1")) {
					user.put("state", -1);
				} else {
					user.put("state", 1);
				}

				cgbdb.replaceOne("course",
						Filters.eq("c_no", user.get("c_no")), user);

				model.put("result", "success");
			}

		}
		sendjson(model, response);
	}

	@RequestMapping(value = { "/up_img" }, method = { RequestMethod.POST })
	public void up_sku_img(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = String.valueOf(System.currentTimeMillis()
				+ random4.getRandomNum4());

		int res = 0;
		int is_img = 0;
		String path = request.getRealPath("");
		String p_path = path + "/upload/img/course";

		// String old_path = path + "/upload/img/old";

		String ext = null, file_path = null;
		String fileName = "";

		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());

			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				// size = size + file.getSize();
				fileName = file.getOriginalFilename();// 文件全名
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				System.out.println(fileName);
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()) {
					System.out.println("//不存在");
					dir.mkdir();
				}

				if (ext.equals(".jpg")) {
					is_img = 1;
				} else if (ext.equals(".png")) {
					is_img = 1;
				} else if (ext.equals(".jpeg")) {
					is_img = 1;
				}

				if (is_img == 1) {

					File localFile = new File(path + "/upload/img" + "/",
							file_code + ext);// 指定文件名称后缀名 存原图
					file_path = p_path + "/" + file_code + ext;

					String f = file_code + ext;

					try {
						file.transferTo(localFile); // 保存图片到指定文件夹目录中
						System.out.println(localFile);

						// 压缩图片保存到
						new HighImage().handleImage(path + "/upload/img",
								"course", f, 750, 750);

					} catch (Exception e) {

						e.printStackTrace();
					}
					res = 1;

				} else {

					res = -1;
				}

			}
		}

		if (is_img == 0) {
			sendMassage("not_img", response);
		} else {
			if (res == 1) {
				sendMassage(file_code + ext, response);
			} else {
				sendMassage("error", response);
			}
		}
	}

	@RequestMapping(value = { "/up_view" }, method = { RequestMethod.POST })
	public void up_viewg(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();

		String file_code = String.valueOf(System.currentTimeMillis()
				+ random4.getRandomNum4());

		int res = 0;
		int is_img = 0;
		String path = request.getRealPath("");
		String p_path = path + "/upload/view/course";

		// String old_path = path + "/upload/img/old";

		String ext = null, file_path = null;
		String fileName = "";

		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());

			// 有上传文件的话，容量是大于0的。
			if (file.getSize() > 0) {

				// size = size + file.getSize();
				fileName = file.getOriginalFilename();// 文件全名
				ext = fileName.substring(fileName.lastIndexOf("."));// 后缀
				System.out.println(fileName);
				File dir = new File(p_path);
				// 如果文件夹不存在则创建
				if (!dir.exists() && !dir.isDirectory()) {
					System.out.println("//不存在");
					dir.mkdir();
				}

				is_img = 1;

				if (is_img == 1) {

					File localFile = new File(path + "/upload/view" + "/",
							file_code + ext);// 指定文件名称后缀名 存原图
					file_path = p_path + "/" + file_code + ext;

					try {
						file.transferTo(localFile); // 保存图片到指定文件夹目录中
						System.out.println(localFile);

						// 压缩图片保存到

					} catch (Exception e) {

						e.printStackTrace();
					}
					res = 1;

				} else {

					res = -1;
				}

			}
		}

		if (is_img == 0) {
			sendMassage("not_img", response);
		} else {
			if (res == 1) {
				sendMassage(file_code + ext, response);
			} else {
				sendMassage("error", response);
			}
		}
	}

	@RequestMapping(value = "insertcourse", method = { RequestMethod.POST })
	public void insertcourse(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String c_no = String.valueOf(System.currentTimeMillis());
		String c_name = req.getParameter("c_name");
		String type = req.getParameter("type");
		String img = req.getParameter("img");
		String  c_type=req.getParameter("cs_type");

		String sharee_img = req.getParameter("img2");
		// String img_path="/upload/img/";
		String text = req.getParameter("text");
		String url = req.getParameter("ziyuan");
		String audition = req.getParameter("audition");
		String discount_price2 = req.getParameter("discount_price").trim();
		String price2 = req.getParameter("price").trim();
		String introduce = req.getParameter("introduce");
		String introduce2 = req.getParameter("introduce2");
		String teacher = req.getParameter("teacher");
		String is_free = req.getParameter("is_free");
		String safe_date = req.getParameter("safe_date");// 视频有效期
		String safe_year = req.getParameter("safe_year");// 购买有效期
		String text_text = req.getParameter("text_text");
		String base_watch = req.getParameter("base_watch");//基础点击量
		
		
		float price = Float.valueOf(price2) * 100;
		float discount_price = Float.valueOf(discount_price2) * 100;
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		Map<String, String> map = new HashMap<String, String>();
		MongoUtil mu = MongoUtil.getThreadInstance();

		BasicDBObject bdb2 = new BasicDBObject();

		bdb2.put("c_no", c_no);
		bdb2.put("safe_date", safe_date);
		bdb2.put("safe_year", Integer.valueOf(safe_year));
		bdb2.put("c_name", c_name);
		bdb2.put("type", Integer.valueOf(type));
		bdb2.put("state", 1);
		bdb2.put("img", img);
		bdb2.put("text_text", text_text);
		bdb2.put("sharee_img", sharee_img);
		bdb2.put("text", text);
		bdb2.put("url", url);
		bdb2.put("time", 600);
		bdb2.put("audition", audition);
		bdb2.put("discount_price", (int) discount_price);
		bdb2.put("price", (int) price);
		bdb2.put("introduce", introduce);
		bdb2.put("introduce2", introduce2);
		bdb2.put("teacher", teacher);
		// bdb2.put("time", (int)(Double.valueOf(time)*60));
		bdb2.put("date", DateTool.fromDate24H());
		bdb2.put("new_date", new Date());
		bdb2.put("top", 0);
		bdb2.put("watch", Integer.valueOf(base_watch));
		bdb2.put("is_free", is_free);
		bdb2.put("is_order", 0);
		bdb2.put("is_xin", 1);
		bdb2.put("order_count", 0);
		bdb2.put("c_type", c_type);
		bdb2.put("is_sepecial", 0);
		bdb2.put("base_watch", Integer.valueOf(base_watch));
		bdb2.put("is_show", 0);// 0代表不显示
		mu.insertOne("course", bdb2);
		
		Document  course_tt=new Document();
		
		course_tt.put("type", c_type);
		//course_tt.put("state", Integer.valueOf(type));
			Document doc5=null;
		if(Integer.valueOf(type)==1){
			 doc5 = cgbdb.find_scholarship22("course_type", course_tt);
		}else{
			 doc5 = cgbdb.find_scholarship22("course_type_voice", course_tt);
		}
		
		
			if(doc5==null){
					
				int tt=Integer.valueOf(type);
					if(tt==1){//视频
						mu.insertOne("course_type", course_tt);	
					}else {
						mu.insertOne("course_type_voice", course_tt);							
					}
					 
			}else{
				doc5.put("type", c_type);
				int tt=Integer.valueOf(type);
				if(tt==1){//视频
					
					mu.replaceOne("course_type", Filters.eq("_id", doc5.get("_id")), doc5);
				}else {
					mu.replaceOne("course_type_voice", Filters.eq("_id", doc5.get("_id")), doc5);							
				}
				
				
			}
		
		
		
		map.put("result", "success");
		sendjson(map, response);
	}

	@RequestMapping(value = "upcourse", method = { RequestMethod.POST })
	public void upcourse(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		String c_no = req.getParameter("c_no");
		String c_name = req.getParameter("c_name");
		String type = req.getParameter("type");
		String img = req.getParameter("img");
		int base_watch=Integer.valueOf(req.getParameter("base_watch"));
		String sharee_img = req.getParameter("img2");
		// String img_path="/upload/img/";
		String text = req.getParameter("text");
		String url = req.getParameter("ziyuan");
		String audition = req.getParameter("audition");
		String discount_price2 = req.getParameter("discount_price").trim();
		String price2 = req.getParameter("price").trim();
		String introduce = req.getParameter("introduce");
		String introduce2 = req.getParameter("introduce2");
		String teacher = req.getParameter("teacher");
		String is_free = req.getParameter("is_free");
		String safe_date = req.getParameter("safe_date");// 视频有效期
		String safe_year = req.getParameter("safe_year");// 购买有效期
		// String time = req.getParameter("time");
		String text_text = req.getParameter("text_text");
		
		String c_type=req.getParameter("c_type");
		/*
		 * time.replaceAll(":", ""); System.out.println(time);
		 */
		float price = Float.valueOf(price2) * 100;
		float discount_price = Float.valueOf(discount_price2) * 100;
			
		int cc=0;
			
		Document docc = new Document();
		docc.put("c_no", c_no);

		Map<String, String> map = new HashMap<String, String>();
		MongoUtil mu = MongoUtil.getThreadInstance();

		Document bdb2 = cgbdb.findcourse_detail("course", docc);

		if (url.equals(bdb2.getString("url"))) {
			bdb2.put("url", url);
			if(bdb2.get("sourceurl") != null){
				bdb2.put("is_show", 1);
			}else{
				bdb2.put("is_show", 0);
			}
			
		} else if (!url.equals(bdb2.getString("url"))) {
			bdb2.put("url", url);
			bdb2.put("is_show", 0);// 重新获取信息
		} else {
			model.put("reult", "error");
		}

		bdb2.put("c_no", c_no);
		bdb2.put("c_name", c_name);
		bdb2.put("type", Integer.valueOf(type));
		bdb2.put("state", 1);
		bdb2.put("img", img);
		bdb2.put("sharee_img", sharee_img);
		bdb2.put("text", text);
		bdb2.put("text_text", text_text);
		bdb2.put("safe_date", safe_date);
		bdb2.put("safe_year", Integer.valueOf(safe_year));
		bdb2.put("audition", audition);
		bdb2.put("discount_price", (int) discount_price);
		bdb2.put("price", (int) price);
		bdb2.put("introduce", introduce);
		bdb2.put("introduce2", introduce2);
		bdb2.put("teacher", teacher);
		// bdb2.put("time", (int)(Double.valueOf(time)*60));
		bdb2.put("date", DateTool.fromDate24H());
		bdb2.put("top", 0);
		
		
		if(bdb2.get("base_watch")==null){
			
			cc=0;
		}else{
			
			cc=bdb2.getInteger("base_watch");
		}
		
		bdb2.put("watch", bdb2.getInteger("watch")-cc+base_watch);
		bdb2.put("is_free", is_free);
		bdb2.put("is_order", 0);
		bdb2.put("is_xin", 1);
		bdb2.put("order_count", 0);
		bdb2.put("c_type", c_type);
		bdb2.put("base_watch", base_watch);
		mu.replaceOne("course", Filters.eq("c_no", bdb2.getString("c_no")),
				bdb2);
		map.put("result", "success");
		
			Document  course_tt=new Document();
		
			course_tt.put("type", c_type);
		//course_tt.put("state", Integer.valueOf(type));
			Document doc5=null;
		if(Integer.valueOf(type)==1){
			 doc5 = cgbdb.find_scholarship22("course_type", course_tt);
		}else{
			 doc5 = cgbdb.find_scholarship22("course_type_voice", course_tt);
		}
		
		
			if(doc5==null){
					if(!c_type.equals("") &&  c_type!=null){
						int tt=Integer.valueOf(type);
						if(tt==1){//视频
							mu.insertOne("course_type", course_tt);	
						}else {
							mu.insertOne("course_type_voice", course_tt);							
						}
					}
			 
			}else{
				doc5.put("type", c_type);
				int tt=Integer.valueOf(type);
				if(tt==1){//视频
					
					mu.replaceOne("course_type", Filters.eq("_id", doc5.get("_id")), doc5);
				}else {
					mu.replaceOne("course_type_voice", Filters.eq("_id", doc5.get("_id")), doc5);							
				}
				
				
			}
		
		sendjson(map, response);
	}

	@RequestMapping("insertspecial")
	public void insertspecial(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = String.valueOf(System.currentTimeMillis());
		String special_name = req.getParameter("special_name");
		String img = req.getParameter("img");
		String sharee_img = req.getParameter("img2");
		String buy_img = req.getParameter("img3");
		
		String introduce2 = req.getParameter("introduce2");
		// String img_path="/upload/img/course/";
		String text = req.getParameter("special_textarea1");
		String course_list = req.getParameter("course_list");
		String no_list = req.getParameter("no_list");
		String audition = req.getParameter("special_time");
		String discount_price = req.getParameter("special_sale_price");
		String price2 = req.getParameter("special_price").trim();
		String is_free = req.getParameter("is_free");
		String teacher = req.getParameter("teacher");
		String share_title = req.getParameter("share_title");
		String gg_id = req.getParameter("gg_id");

		String fit_people = req.getParameter("fit_people");
		String order_notice = req.getParameter("order_notice");
		String safe_year = req.getParameter("safe_year");// 购买有效期

		int type = Integer.valueOf(req.getParameter("type"));
		int is_wc = Integer.valueOf(req.getParameter("is_wc"));
		String base_order_count = req.getParameter("base_order_count");

		float price = Float.valueOf(price2) * 100;
		//float discount_price = Float.valueOf(discount_price2) * 100;

		// Map<String ,Object> list=new HashMap<String, Object>();

		String[] m_arr = course_list.split(",");
		
		String [] m_arr2=no_list.split(",");

		
		
		List<Map<String,Object>> list_no=new ArrayList<Map<String,Object>>();
		
		List<String> url_list = new ArrayList<String>();

		for (int i = 0; i < m_arr.length; i++) {
			Map<String,Object> mc=new HashMap<String, Object>();
			Document cc = new Document();

			cc.put("c_no", m_arr[i]);
			
			mc.put("c_no", m_arr[i]);
			mc.put("number", m_arr2[i]);
			
			list_no.add(mc);
			Document course = cgbdb.findcourse_detail("course", cc);

			if (course != null) {

				course.put("is_sepecial", 1);
				course.put("count", i + 1);

				cgbdb.replaceOne("course",
						Filters.eq("c_no", course.getString("c_no")), course);
			}

			url_list.add(m_arr[i]);
		}
			
		
		//int [] m_list=new int[list_no.size()];
		
		
			if(list_no.size()>0){
				
				for(int i=0;i<list_no.size()-1;i++){//外层循环控制排序趟数
					for(int j=0;j<list_no.size()-1-i;j++){//内层循环控制每一趟排序多少次
						
						if(Integer.valueOf(list_no.get(j).get("number").toString())>Integer.valueOf(list_no.get(j+1).get("number").toString())){
							Map<String,Object> temp=list_no.get(j);
							
							list_no.set(j, list_no.get(j+1));
							list_no.set(j+1, temp);
						}
					
					}
				
				}
			}
				
			List<String> url_list2 = new ArrayList<String>();
			if(list_no.size()>0){
				for(int l=0;l<list_no.size();l++){
					//Map<String,Object> mcc=new HashMap<String, Object>();
					//mcc.put("c_no", list_no.get(l).get("c_no"));
					url_list2.add(list_no.get(l).get("c_no").toString());
				}
			}
		Document doc = new Document();
		doc.put("is_wc", is_wc);
		doc.put("special_no", special_no);
		doc.put("special_name", special_name);
		doc.put("img", img);
		doc.put("buy_img", buy_img);
		doc.put("sharee_img", sharee_img);
		doc.put("introduce2", introduce2);
		doc.put("text", text);
		doc.put("url", url_list2);
		doc.put("audition", audition);
		doc.put("discount_price",discount_price);
		doc.put("price", (int) price);
		doc.put("state", 1);
		doc.put("top", 0);
		doc.put("is_free", "1");
		doc.put("watch", 0);
		doc.put("is_gg", 0);
		doc.put("type", type);
		doc.put("share_title", share_title);
		doc.put("gg_id", gg_id);
		doc.put("date", DateTool.fromDate24H());
		doc.put("date_1", DateTool.fromDateY());
		doc.put("kind", 1); // 1专题，2是小专题
		doc.put("new_date", new Date());
		doc.put("safe_date", DateTool.fromDateY());
		doc.put("safe_year", Integer.valueOf(safe_year));
		doc.put("teacher", teacher);
		doc.put("fit_people", fit_people);
		doc.put("order_notice", order_notice);
		doc.put("is_show", 1);// 0代表不显示
		doc.put("base_order_count", Integer.valueOf(base_order_count));
		doc.put("order_count", Integer.valueOf(base_order_count));
		cgbdb.insertOne("special", doc);

		model.put("result", "success");
		sendjson(model, response);
	}

	@RequestMapping("upspecial")
	public void upspecial(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		String special_no = req.getParameter("sepcial_no");
		String special_name = req.getParameter("special_name");
		String img = req.getParameter("img");
		String buy_img = req.getParameter("img3"); 
		String gg_id = req.getParameter("gg_id");
		String sharee_img = req.getParameter("img2");
		String introduce2 = req.getParameter("introduce2");
		String share_title = req.getParameter("share_title");
		String text = req.getParameter("special_textarea1");
		String no_list = req.getParameter("no_list");
		String audition = req.getParameter("special_time");
		String discount_price = req.getParameter("special_sale_price");
		String price2 = req.getParameter("special_price").trim();
		String is_free = req.getParameter("is_free");
		String teacher = req.getParameter("teacher");
		String safe_year = req.getParameter("safe_year");// 购买有效期
		String fit_people = req.getParameter("fit_people");
		String order_notice = req.getParameter("order_notice");
		String course_list = req.getParameter("course_list");
		int is_wc=Integer.valueOf(req.getParameter("is_wc"));
		String base_order_count = req.getParameter("base_order_count");

		float price = Float.valueOf(price2) * 100;
		//float discount_price = Float.valueOf(discount_price2) * 100;

		String[] m_arr = course_list.split(",");
		String [] m_arr2=no_list.split(",");

		List<String> url_list = new ArrayList<String>();
		List<Map<String,Object>> list_no=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < m_arr.length; i++) {
			Map<String,Object> mc=new HashMap<String, Object>();
			Document cc = new Document();

			cc.put("c_no", m_arr[i]);
			mc.put("number", m_arr2[i]);
			mc.put("c_no", m_arr[i]);
			list_no.add(mc);

			Document course = cgbdb.findcourse_detail("course", cc);
			
			
			/*Document  doc2=new Document();
				doc2.put("url", cc);
			
			int if_special=cgbdb.count_course_order("special",doc2);*/
			
			
			if (course != null ) {
				
					
				course.put("is_sepecial", 1);
				
				course.put("count", i + 1);

				cgbdb.replaceOne("course",
						Filters.eq("c_no", course.getString("c_no")), course);
			}
			url_list.add(m_arr[i]);
		}
		
		
		if(list_no.size()>0){
			
			for(int i=0;i<list_no.size()-1;i++){//外层循环控制排序趟数
				for(int j=0;j<list_no.size()-1-i;j++){//内层循环控制每一趟排序多少次
					
					if(Integer.valueOf(list_no.get(j).get("number").toString())>Integer.valueOf(list_no.get(j+1).get("number").toString())){
						Map<String,Object> temp=list_no.get(j);
						
						list_no.set(j, list_no.get(j+1));
						list_no.set(j+1, temp);
					}
				
				}
			
			}
		}
			
		List<String> url_list2 = new ArrayList<String>();
		if(list_no.size()>0){
			for(int l=0;l<list_no.size();l++){
				//Map<String,Object> mcc=new HashMap<String, Object>();
				//mcc.put("c_no", list_no.get(l).get("c_no"));
				url_list2.add(list_no.get(l).get("c_no").toString());
			}
		}
		
		Document docc = new Document();
		docc.put("special_no", special_no);

		Document doc = cgbdb.findc_special_no("special", docc);
		List<String> list2 = (List<String>) doc.get("url");

		if (doc.getString("gg_id") != null
				&& !doc.getString("gg_id").equals(gg_id)) {
			doc.put("is_gg", 0);
		}

		doc.put("special_no", special_no);
		doc.put("special_name", special_name);
		doc.put("img", img);
		doc.put("buy_img", buy_img);
		doc.put("text", text);
		doc.put("sharee_img", sharee_img);
		doc.put("introduce2", introduce2);
		doc.put("is_wc", is_wc);
		int old_count = list2.size();
		int new_count = url_list.size();
		
		int dif_num = 0;
		if (old_count == new_count) {

			for (int i = 0; i < old_count; i++) {

				String old_vid = list2.get(i);
				String new_vid = url_list.get(i);
				
				

				if (!old_vid.equals(new_vid)) {
					dif_num++;
				}
			}
		} else {
			
			
			
			dif_num = 1;
		}

		if (dif_num == 0) {
			// doc.put("url", url_list);
			doc.put("is_show", 1);// 0代表不显示
		} else {
			doc.put("url", url_list);
			doc.put("is_show", 0);// 0代表不显示
		}

		doc.put("audition", audition);
		doc.put("discount_price", discount_price);
		doc.put("price", (int) price);
		doc.put("state", 1);
		doc.put("top", 0);
		doc.put("is_free", is_free);
		doc.put("watch", 0);
		doc.put("url", url_list2);
		doc.put("share_title", share_title);
		doc.put("date", DateTool.fromDate24H());
		doc.put("date_1", DateTool.fromDateY());
		doc.put("teacher", teacher);
		doc.put("gg_id", gg_id);
		doc.put("fit_people", fit_people);
		doc.put("order_notice", order_notice);
		doc.put("safe_year", Integer.valueOf(safe_year));
		
				int cc=0;
			if(doc.get("base_order_count")==null){
			
				cc=0;
			}else{
			
			cc=doc.getInteger("base_order_count");
		}
		
			int  order_count=doc.getInteger("order_count");
			
			doc.put("order_count", order_count-cc+Integer.valueOf(base_order_count)); 
			doc.put("base_order_count", Integer.valueOf(base_order_count));
		cgbdb.replaceOne("special",
				Filters.eq("special_no", doc.getString("special_no")), doc);

		
		
		 
		
		for (int z = 0; z < m_arr.length; z++) {
			Document ccc = new Document();

				ccc.put("c_no", m_arr[z]);
			Document courses = cgbdb.findcourse_detail("course", ccc);
			
			Document  doc3=new Document();
			doc3.put("url", m_arr[z]);
		
		int if_special=cgbdb.count_course_order("special",doc3);
		
		if (courses != null ) {
			
			if(if_special>0){
				courses.put("is_sepecial", 1);
			}else{
				
				courses.put("is_sepecial", 0);
			}

		 
		cgbdb.replaceOne("course",
				Filters.eq("c_no", courses.getString("c_no")), courses);
	}
		}
		

		model.put("result", "success");
		sendjson(model, response);
	}

	@RequestMapping("show_special")
	public String show_special(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String special_name = req.getParameter("name");
		int pn = 0;
		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);
		map.append("ps", ps);
		map.append("kind", 1);
		if (special_name != null && !special_name.equals("")) {
			map.append("special_name", special_name);
		}

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb4("special", map);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		model.put("special_list", pb);

		return "/pt/course/special";
	}

	@RequestMapping("show_little_special")
	public String show_little_special(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String special_name = req.getParameter("name");
		int pn = 0;
		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);
		map.append("ps", ps);
		map.append("kind", 2);
		if (special_name != null && !special_name.equals("")) {
			map.append("special_name", special_name);
		}

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb4("special", map);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		model.put("special_list", pb);

		return "/pt/course/little_special";
	}

	@RequestMapping("insert_little_special")
	public void insert_little_special(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = String.valueOf(System.currentTimeMillis());
		String special_name = req.getParameter("special_name");
		String img = req.getParameter("img");
		String buy_img = req.getParameter("img3");
		String text = req.getParameter("special_textarea1");

		String audition = req.getParameter("special_time");
		String discount_price = req.getParameter("special_sale_price");
		String price2 = req.getParameter("special_price").trim();
		String is_free = req.getParameter("is_free");
		String teacher = req.getParameter("teacher");
		String cont_content = req.getParameter("cont_content");
		String sharee_img = req.getParameter("img2");
		String introduce2 = req.getParameter("introduce2");
		String share_title = req.getParameter("share_title");
		String fit_people = req.getParameter("fit_people");
		String order_notice = req.getParameter("order_notice");
		String safe_year = req.getParameter("safe_year");// 购买有效期

		// int type=Integer.valueOf(req.getParameter("type"));
		String base_order_count = req.getParameter("base_order_count");
		float price = Float.valueOf(price2) * 100;
	//	float discount_price = Float.valueOf(discount_price2) * 100;

		// Map<String ,Object> list=new HashMap<String, Object>();

		String[] m_arr = cont_content.split(",");

		List<String> url_list = new ArrayList<String>();

		for (int i = 0; i < m_arr.length; i++) {

			/*
			 * Document cc=new Document();
			 * 
			 * cc.put("c_no", m_arr[i]);
			 * 
			 * Document course=cgbdb.findcourse_detail("course", cc);
			 */

			/*
			 * if(course!=null){
			 * 
			 * course.put("is_sepecial", 1);
			 * 
			 * course.put("count",i+1);
			 */

			// cgbdb.replaceOne("course", Filters.eq("c_no",
			// course.getString("c_no")), course);
			// }

			url_list.add(m_arr[i]);
		}

		Document doc = new Document();

		doc.put("special_no", special_no);
		doc.put("sharee_img", sharee_img);
		doc.put("introduce2", introduce2);
		doc.put("buy_img", buy_img);
		doc.put("special_name", special_name);
		doc.put("img", img);
		doc.put("text", text);
		doc.put("url", url_list);
		doc.put("audition", audition);
		doc.put("share_title", share_title);
		doc.put("discount_price", discount_price);
		doc.put("price", (int) price);
		doc.put("state", 1);
		doc.put("top", 0);
		doc.put("is_free", is_free);
		doc.put("watch", 0);
		// doc.put("type", type);
		doc.put("date", DateTool.fromDate24H());
		doc.put("date_1", DateTool.fromDateY());
		doc.put("kind", 2);
		doc.put("new_date", new Date());
		doc.put("safe_date", DateTool.fromDateY());
		doc.put("safe_year", safe_year);
		doc.put("teacher", teacher);
		doc.put("fit_people", fit_people);
		doc.put("order_notice", order_notice);
		doc.put("is_show", 0);// 0代表不显示
		doc.put("base_order_count", Integer.valueOf(base_order_count));
		doc.put("order_count", Integer.valueOf(base_order_count));
		cgbdb.insertOne("special", doc);

		model.put("result", "success");
		sendjson(model, response);
	}

	@RequestMapping("update_little_special")
	public void update_little_special(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String special_no = req.getParameter("sepcial_no");
		String special_name = req.getParameter("special_name");
		String img = req.getParameter("img");
		String base_order_count = req.getParameter("base_order_count");
		String sharee_img = req.getParameter("img2");
		String buy_img = req.getParameter("img3");
		 
		String introduce2 = req.getParameter("introduce2");
		// String img_path="/upload/img/course/";
		String text = req.getParameter("special_textarea1");
		String share_title = req.getParameter("share_title");
		String audition = req.getParameter("special_time");
		String discount_price = req.getParameter("special_sale_price");
		String price2 = req.getParameter("special_price").trim();
		String is_free = req.getParameter("is_free");
		String teacher = req.getParameter("teacher");
		String safe_year = req.getParameter("safe_year");// 购买有效期
		String fit_people = req.getParameter("fit_people");
		String order_notice = req.getParameter("order_notice");
		String cont_content = req.getParameter("cont_content");

		float price = Float.valueOf(price2) * 100;
		//float discount_price = Float.valueOf(discount_price2) * 100;

		String[] m_arr = cont_content.split(",");

		List<String> url_list = new ArrayList<String>();

		for (int i = 0; i < m_arr.length; i++) {

			/*
			 * Document cc=new Document();
			 * 
			 * cc.put("c_no", m_arr[i]);
			 * 
			 * Document course=cgbdb.findcourse_detail("course", cc);
			 * 
			 * if(course!=null){
			 * 
			 * course.put("is_sepecial", 1); course.put("special_no",
			 * special_no);
			 * 
			 * cgbdb.replaceOne("course", Filters.eq("c_no",
			 * course.getString("c_no")), course); }
			 */

			url_list.add(m_arr[i]);
		}

		Document docc = new Document();
		docc.put("special_no", special_no);

		Document doc = cgbdb.findc_special_no("special", docc);
		List<String> list2 = (List<String>) doc.get("url");

		doc.put("special_no", special_no);
		doc.put("special_name", special_name);
		doc.put("img", img);
		doc.put("text", text);
		doc.put("sharee_img", sharee_img);
		doc.put("introduce2", introduce2);

		int old_count = list2.size();
		int new_count = url_list.size();

		int dif_num = 0;
		if (old_count == new_count) {

			for (int i = 0; i < old_count; i++) {

				String old_vid = list2.get(i);
				String new_vid = url_list.get(i);

				if (!old_vid.equals(new_vid)) {
					dif_num++;
				}
			}
		} else {
			dif_num = 1;
		}

		if (dif_num == 0) {
			// doc.put("url", url_list);
			doc.put("is_show", 1);// 0代表不显示
		} else {
			doc.put("url", url_list);
			doc.put("is_show", 0);// 0代表不显示
		}

		doc.put("audition", audition);
		doc.put("discount_price", discount_price);
		doc.put("price", (int) price);
		doc.put("state", 1);
		doc.put("top", 0);
		doc.put("is_free", is_free);
		doc.put("share_title", share_title);
		doc.put("watch", 0);
		doc.put("date", DateTool.fromDate24H());
		doc.put("date_1", DateTool.fromDateY());
		doc.put("teacher", teacher);
		doc.put("fit_people", fit_people);
		doc.put("order_notice", order_notice);
		doc.put("safe_year", Integer.valueOf(safe_year));
		doc.put("buy_img", buy_img);
			
		int cc=0;
		if(doc.get("base_order_count")==null){
		
			cc=0;
		}else{
		
		cc=doc.getInteger("base_order_count");
	}
		int  order_count=doc.getInteger("order_count");
		doc.put("order_count", order_count-cc+Integer.valueOf(base_order_count)); 
		doc.put("base_order_count", Integer.valueOf(base_order_count)); 
		cgbdb.replaceOne("special",
				Filters.eq("special_no", doc.getString("special_no")), doc);

		if (new_count > old_count) {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("msg_type", 5);
			map.put("send_name", special_name);
			map.put("send_text", special_name + "更新至" + new_count + "集");
			map.put("back_name", special_name);
			map.put("back_text", special_name + "更新至" + new_count + "集");
			map.put("special_no", special_no);

			msg_db.add_msg2(map);

		}

		model.put("result", "success");
		sendjson(model, response);
	}

	/**
	 * 控制专题上架下架
	 * 
	 * 
	 */
	@RequestMapping("special_freeze")
	public void special_freeze(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String special_no = req.getParameter("special_no");
		String state = req.getParameter("state");
		Document doc = new Document();

		if (special_no == null || special_no.equals("")) {
			model.put("result", "error");
		} else {
			doc.append("special_no", special_no);

			Document user = cgbdb.findc_special_no("special", doc);
			
			 
			if (user != null) {
				
					List<String> arr=(List<String>)user.get("url");
					
					if(arr!=null &&  arr.size()>0){
						
							for(int i=0;i<arr.size();i++){
							
							String c_no=arr.get(i);
							Document  doc2=new Document();
							doc2.put("url", c_no);
						
						int if_special=cgbdb.count_course_order("special",doc2);
						
								if(if_special==1){
									Document  ddd=new Document();
									
										ddd.put("c_no", c_no);
							Document courses = cgbdb.findcourse_detail("course", ddd);
							
									if(courses!=null){
										if (state.equals("1")){
											courses.put("is_sepecial", 0);
										}else{
											courses.put("is_sepecial", 1);
										}
										
										cgbdb.replaceOne("course",
												Filters.eq("c_no", courses.getString("c_no")), courses);
									}
									 
								}else{
									Document  ddd=new Document();
									
									ddd.put("c_no", c_no);
						Document courses = cgbdb.findcourse_detail("course", ddd);
						
								if(courses!=null){
									
									courses.put("is_sepecial", 1);
									cgbdb.replaceOne("course",
											Filters.eq("c_no", courses.getString("c_no")), courses);
								}
								}
							
						}
						
					}
						

				if (state.equals("1")) {
					user.put("state", -1);
					
				} else {
					user.put("state", 1);
				}

				cgbdb.replaceOne("special",
						Filters.eq("special_no", user.get("special_no")), user);

				model.put("result", "success");
			}

		}
		sendjson(model, response);
	}

	@RequestMapping("special_freeze2")
	public void special_freeze2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String special_no = req.getParameter("special_no");
		String state = req.getParameter("state");
		Document doc = new Document();

		if (special_no == null || special_no.equals("")) {
			model.put("result", "error");
		} else {
			doc.append("special_no", special_no);

			Document user = cgbdb.findc_special_no("little_special", doc);
			if (user != null) {

				if (state.equals("1")) {
					user.put("state", -1);
				} else {
					user.put("state", 1);
				}

				cgbdb.replaceOne("little_special",
						Filters.eq("special_no", user.get("special_no")), user);

				model.put("result", "success");
			}

		}
		sendjson(model, response);
	}

	@RequestMapping("show_exercise")
	public void show_exercise(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");

		int pn = 0;
		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);
		map.append("ps", ps);

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("exercise", map);

		Document ddd = new Document();
		ddd.put("c_no", c_no);
		ddd.put("open_id", open_id);
		int count = cgbdb.count_course_order("exercise_log", ddd);
		if (count > 0) {

			Document e_log = cgbdb.find_scholarship22("exercise_log", ddd);

			model.put("e_log", e_log);
		}
		model.put("count", count);
		pb.setRowCount(rc);

		List<Document> list = exercise_db.query_exercise_list2(c_no);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}
		model.put("result", "success");
		model.put("exercise_list", pb);
		Document course = study_db.query_one_course(c_no);
		Document special = study_db.quert_special(c_no);

		model.put("course", course);
		model.put("special", special);

		sendjson(model, response);

	}

	@RequestMapping("checkexercise")
	public void checkexercise(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String answer = req.getParameter("answer");
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");

		List<Document> survey_list = new ArrayList<Document>();
		org.json.JSONArray json_arr = null;

		org.json.JSONObject json;
		try {
			json = new org.json.JSONObject(answer);
			json_arr = json.getJSONArray("answer_arr");

			for (int i = 0; i < json_arr.length(); i++) {
				Document document = new Document();
				document.put("p_id", json_arr.getJSONObject(i)
						.getString("p_id"));
				document.put("val", json_arr.getJSONObject(i).getString("val"));

				survey_list.add(document);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> doc = sj.query_ks_sj(survey_list);

		Document insert = new Document();
		insert.put("open_id", open_id);
		insert.put("c_no", c_no);
		insert.put("record", doc.get("sj_list"));
		insert.put("date", DateTool.fromDate24H());

		cgbdb.insertOne("exercise_log", insert);

		study_db.study_exercise(open_id, c_no);

		model.put("result", "success");
		model.put("count", doc);

		sendjson(model, response);
	}

	@RequestMapping("wx_course_list")
	public void wx_course_list(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		// long begin=System.currentTimeMillis();

		String open_id = req.getParameter("open_id");
		String type = req.getParameter("type");

		int role = 0;
		int state = 0;
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		// String phone = req.getParameter("phone");
		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("course", map1);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		// long user_begin=System.currentTimeMillis();

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		// long user_over=System.currentTimeMillis();

		/*
		 * long course_begin=0; long course_over=0; long course_order_over=0;
		 * long is_buy_over=0;
		 */
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				if (type != null && !type.equals("")) {

					// course_begin=System.currentTimeMillis();
					List<Document> course = cgbdb.find_course_list_limt(
							"course", map1, type);// 查询所有课程
					// course_over=System.currentTimeMillis();

					List<Document> course_order = cgbdb.find_course_order_list(
							open_id, 1);// 查询所有课程订单

					// course_order_over=System.currentTimeMillis();

					for (Document d : course) {
						if (checkIsBugy(course_order, d, 1)) {

							d.append("isBuy", 1);// 1已购买
						}
					}
					pb.setList(course);

					// is_buy_over=System.currentTimeMillis();

					model.put("course_order", course_order);
					model.put("course_list", course);
				}

				/*
				 * List<Document>
				 * special=cgbdb.find_special_lmilt("special",map1);//查询所有专题
				 * 
				 * List<Document>
				 * course_order2=cgbdb.find_course_order_list(open_id
				 * ,2);//查询所有专题订单
				 * 
				 * for(Document d:special){ if(checkIsBugy(course_order2,d,2)){
				 * 
				 * d.append("isBuy", 1);//1已购买
				 * 
				 * 
				 * } }
				 * 
				 * pb.setList(special);
				 */

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {

				if (type != null && !type.equals("")) {

					List<Document> course = cgbdb.find_course_list_limt(
							"course", map1, type);// 查询所有课程

					model.put("course_list", course);
				}

				//List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单

				List<Document> special = cgbdb.find_special_lmilt("special",
						map1);// 查询所有专题
				pb.setList(special);
				model.put("special", pb);
			//	model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				if (type != null && !type.equals("")) {

					List<Document> course = cgbdb.find_course_list_limt(
							"course", map1, type);// 查询所有课程

					List<Document> course_order = cgbdb.find_course_order_list(
							open_id, 1);// 查询所有课程订单

					for (Document d : course) {
						if (checkIsBugy(course_order, d, 1)) {

							d.append("isBuy", 1);// 1已购买
						}
					}
					pb.setList(course);

					model.put("course_order", course_order);
					model.put("course_list", course);
				}

				/*
				 * System.out.println("query_special_begin_time"+System.
				 * currentTimeMillis()); List<Document>
				 * special=cgbdb.find_special_lmilt("special",map1);//查询所有专题
				 * 
				 * System.out.println("query_special_over_time"+System.
				 * currentTimeMillis()); List<Document>
				 * course_order2=cgbdb.find_course_order_list
				 * (open_id,2);//查询所有专题订单
				 * 
				 * System.out.println("query_special_order_over_time"+System.
				 * currentTimeMillis()); for(Document d:special){
				 * if(checkIsBugy(course_order2,d,2)){
				 * 
				 * d.append("isBuy", 1);//1已购买
				 * 
				 * 
				 * } }
				 * 
				 * pb.setList(special);
				 */

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}

		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);

		// long end=System.currentTimeMillis();

		/*
		 * System.out.println("---diff_user-------"+String.valueOf(user_over-
		 * user_begin));
		 * System.out.println("---diff_is_buy-------"+String.valueOf
		 * (is_buy_over-course_order_over));
		 * System.out.println("---diff_course_order-------"
		 * +String.valueOf(course_order_over-course_over));
		 * System.out.println("---diff_course-------"
		 * +String.valueOf(course_over-course_begin));
		 * System.out.println("---diff-------"+String.valueOf(end-begin));
		 */
	}

	@RequestMapping("wx_course_list2")
	public void wx_course_list2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		// long begin=System.currentTimeMillis();

		String open_id = req.getParameter("open_id");
		String type = req.getParameter("type");

		int role = 0;
		int state = 0;
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		// String phone = req.getParameter("phone");
		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		map1.put("is_sepecial", 1);

		Document map2 = new Document();
		map2.put("is_sepecial", 1);
		map2.put("type", Integer.valueOf(type));
		map2.put("state", 1);
		map2.put("is_free", "1");
		int rc = (int) cgbdb.count_course_order("course", map2);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		// long user_begin=System.currentTimeMillis();

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表

		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				if (type != null && !type.equals("")) {

					List<Document> course = cgbdb.find_course_list_limt(
							"special", map1, type);// 查询所有专题

					List<Document> course_llst = cgbdb
							.find_course_list_limt222("course", map1, type);// 查询所有课程
					
					
					
					List<Document> course_order = cgbdb.find_course_order_list(
							open_id, 2);// 查询所有课程订单

					for (Document d : course) {
						if (checkIsBugy(course_order, d, 2)) {

							d.append("isBuy", 1);// 1已购买
						}
					}
					pb.setList(course);

					model.put("course_order", course_order);
					model.put("course_list", course_llst);
				}

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {

				if (type != null && !type.equals("")) {

					List<Document> course = cgbdb.find_course_list_limt(
							"special", map1, type);// 查询所有专题

					List<Document> course_llst = cgbdb
							.find_course_list_limt222("course", map1, type);// 查询所有课程
					List<Document> course_order = cgbdb.find_course_order_list(
							open_id, 2);// 查询所有课程订单

					for (Document d : course) {
						if (checkIsBugy(course_order, d, 2)) {

							d.append("isBuy", 1);// 1已购买
						}
					}
					pb.setList(course);

					model.put("course_order", course_order);
					model.put("course_list", course_llst);
				}

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				if (type != null && !type.equals("")) {

					List<Document> course = cgbdb.find_course_list_limt(
							"special", map1, type);// 查询所有专题

					List<Document> course_llst = cgbdb
							.find_course_list_limt222("course", map1, type);// 查询所有课程
					

					List<Document> course_order = cgbdb.find_course_order_list(
							open_id, 2);// 查询所有课程订单

					for (Document d : course) {
						if (checkIsBugy(course_order, d, 2)) {

							d.append("isBuy", 1);// 1已购买
						}
					}
					pb.setList(course);

					model.put("course_order", course_order);
					model.put("course_list", course_llst);
				}

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}

		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);

	}

	@RequestMapping("wx_course_list233")
	public void wx_course_list233(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		// long begin=System.currentTimeMillis();

		String open_id = req.getParameter("open_id");
		// String type=req.getParameter("type");

		int role = 0;
		int state = 0;
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		// String phone = req.getParameter("phone");
		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);
		map1.append("is_free", "-1");
		Document ll = new Document();
		ll.put("pn", pn);
		ll.put("ps", ps);
		ll.put("is_free", "-1");
		ll.put("state", 1);
		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少
		
		Document ll2 = new Document();		
		ll2.put("is_free", "-1");
		ll2.put("state", 1); 
		int rc = (int) cgbdb.count_course_order("course", ll2);

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表

		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			List<Document> course = cgbdb.show_collect_2("course", ll);// 查询所有课程
			pb.setRowCount(rc);
			
			model.put("course_list", course);
		}

		model.put("user", user);
		model.put("special", pb);

		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);

	}

	@RequestMapping("wx_special")
	public void wx_special(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		int role = 0;
		int state = 0;

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {
				//List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单
				List<Document> special = cgbdb.find_special_lmilt("special",
						map1);// 查询所有专题
				pb.setList(special);
				model.put("special", pb);
				//model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}
		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);
	}

	@RequestMapping("wx_special22")
	public void wx_special22(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		int role = 0;
		int state = 0;

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt2("special",
						map1);// 查询所有专题
				pb.setRowCount(special.size());
				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {
			//	List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单
				List<Document> special = cgbdb.find_special_lmilt2("special",
						map1);// 查询所有专题
				pb.setList(special);
				pb.setRowCount(special.size());
				model.put("special", pb);
			//	model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt2("special",
						map1);// 查询所有专题
				pb.setRowCount(special.size());
				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}
		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);
	}

	@RequestMapping("wx_special123")
	public void wx_special123(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		int role = 0;
		int state = 0;

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt8("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {
		//		List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单
				List<Document> special = cgbdb.find_special_lmilt8("special",
						map1);// 查询所有专题
				pb.setList(special);
				model.put("special", pb);
				//model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt8("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}
		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);
	}

	@RequestMapping("wx_special33")
	public void wx_special33(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		int role = 0;
		int state = 0;

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt4("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {
			//	List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单
				List<Document> special = cgbdb.find_special_lmilt4("special",
						map1);// 查询所有专题
				pb.setList(special);
				model.put("special", pb);
			//	model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt4("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}
		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);
	}

	@RequestMapping("wx_special44")
	public void wx_special44(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		int role = 0;
		int state = 0;

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 8;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		Document map = new Document();
		map.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", map);// 查询用户表
		if (user != null && !user.equals("")) {

			role = user.getInteger("role");
			state = user.getInteger("state");

			if (role == 0 && state == 0) {// role 0 普通学生 1 会员 state 0 正常 -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt5("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			} else if (role == 1 && state == 0) {
			//	List<Document> course_order = cgbdb.find_course_order_list();// 查询所有课程订单
				List<Document> special = cgbdb.find_special_lmilt5("special",
						map1);// 查询所有专题
				pb.setList(special);
				model.put("special", pb);
				//model.put("course_order", course_order);

				model.put("user", user);

			} else if (role == 3 && state == 0) {// role 0 普通学生 1 会员 state 0 正常
													// -1 拉黑

				List<Document> special = cgbdb.find_special_lmilt5("special",
						map1);// 查询所有专题

				List<Document> course_order2 = cgbdb.find_course_order_list(
						open_id, 2);// 查询所有专题订单

				for (Document d : special) {
					if (checkIsBugy(course_order2, d, 2)) {

						d.append("isBuy", 1);// 1已购买

					}
				}

				pb.setList(special);

				model.put("user", user);
				model.put("special", pb);

			}

			else {
				model.put("result", "error");
			}
		}
		List<Document> lunbo_list = art_db.query_lunbo();
		model.put("lunbo_list", lunbo_list);

		sendjson(model, response);
	}

	/**
	 * 判断是否购买
	 * 
	 * @param type
	 * @param map
	 * @return
	 */
	private boolean checkIsBugy(List<Document> course_order, Document d,
			int type) {
		if (type == 1) {
			// 课程
			for (Document dd : course_order) {
				if (dd.getString("c_no").equals(d.getString("c_no"))) {

					if (dd.getInteger("is_order") == 1
							&& dd.getInteger("is_over") == 0) {
						return true;
					}

				}

			}
		} else {

			for (Document dd : course_order) {
				if (dd.getString("c_no").equals(d.getString("special_no"))) {

					if (dd.getInteger("is_order") == 1
							&& dd.getInteger("is_over") == 0) {

						return true;
					}
				}

			}
		}

		return false;

	}

	/**
	 * 判断是否购买
	 * 
	 * @param type
	 * @param map
	 * @return
	 */
	private boolean checkIsBugy3(List<Document> course_order, Document d) {
		if (course_order != null) {

			// 课程
			for (Document dd : course_order) {
				if (dd.getString("c_no").equals(d.getString("c_no"))
						|| dd.getString("c_no").equals(
								d.getString("special_no"))) {

					if (dd.getInteger("is_order") == 1
							&& dd.getInteger("is_over") == 0) {
						return true;
					}

				}

			}
		} else {

		}

		return false;
	}

	private boolean checkIsBugy4(List<Document> course_order, Document d) {
		if (course_order != null) {

			// 课程
			for (Document dd : course_order) {
				if (dd.getString("c_no").equals(d.getString("c_no"))
						|| dd.getString("c_no").equals(
								d.getString("special_no"))) {

					if (dd.getInteger("is_order") == 1
							&& dd.getInteger("is_over") == 0) {
						return true;
					}

				}

			}
		} else {

		}

		return false;
	}

	@RequestMapping("course_detail")
	public void course_detail(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String c_no = req.getParameter("c_no");

		String open_id = req.getParameter("open_id");

		Document doc2 = new Document();
		doc2.put("open_id", open_id);

		Document doc = new Document();
		doc.append("c_no", c_no);

		Document course = cgbdb.findcourse_detail("course", doc);

		Document course_order = cgbdb.findwxfamilyinfo("course_order", doc2);
		Document user = cgbdb.findwxfamilyinfo("user", doc2);

		if (course != null) {

			cgbdb.replaceOne("course",
					Filters.eq("c_no", course.getString("c_no")), course);

			model.put("course", course);
		} else {
			model.put("result", "error");
		}

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		// String phone = req.getParameter("phone");
		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 20;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		map1.put("type", course.getInteger("type"));

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少
		/*
		 * int rc = (int) cgbdb.count_course_order("course", null);
		 * pb.setRowCount(rc);
		 */

		List<Document> course_list = cgbdb.find_course_list(map1);// 查询所有课程

		List<Document> course_order2 = top_db.query_order_all(open_id);// 查询订单表

		// 判断
		for (int n = 0; n < course_list.size(); n++) {

			for (int j = 0; j < course_order2.size(); j++) {

				if (course_list.get(n).getString("c_no")
						.equals(course_order2.get(j).getString("c_no"))) {
					course_list.get(n).put("isBuy", 1);
				}

			}
		}

		List<Document> discuss_list = cgbdb.find_discuss_list();

		int dy = top_db.query_is_buy(open_id, c_no);
		int sc = top_db.query_is_collection(open_id, c_no);

		model.put("dy", dy);

		model.put("sc", sc);

		// Collections.shuffle(course_list);//混乱排序

		pb.setList(course_list);

		model.put("course_list", pb.getList());
		model.put("discuss_list", discuss_list);
		model.put("course_order", course_order);
		model.put("user", user);

		sendjson(model, response);

	}

	@RequestMapping("course_detail_vv")
	public void course_detail_vv(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String c_no = req.getParameter("c_no");

		String open_id = req.getParameter("open_id");

		Document doc2 = new Document();
		doc2.put("open_id", open_id);

		Document doc = new Document();
		doc.append("c_no", c_no);

		Document course = cgbdb.findcourse_detail("course", doc);

		Document course_order = cgbdb.findwxfamilyinfo("course_order", doc2);
		Document user = cgbdb.findwxfamilyinfo("user", doc2);

		if (course != null) {

			cgbdb.replaceOne("course",
					Filters.eq("c_no", course.getString("c_no")), course);

			model.put("course", course);
		} else {
			model.put("result", "error");
		}

		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		// String phone = req.getParameter("phone");
		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 20;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);

		map1.put("type", course.getInteger("type"));

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少
		/*
		 * int rc = (int) cgbdb.count_course_order("course", null);
		 * pb.setRowCount(rc);
		 */

		List<Document> course_list = cgbdb.find_course_list_c(map1);// 查询所有课程

		List<Document> course_order2 = top_db.query_order_all(open_id);// 查询订单表

		// 判断
		for (int n = 0; n < course_list.size(); n++) {

			for (int j = 0; j < course_order2.size(); j++) {

				if (course_list.get(n).getString("c_no")
						.equals(course_order2.get(j).getString("c_no"))) {
					course_list.get(n).put("isBuy", 1);
				}

			}
		}

		List<Document> discuss_list = cgbdb.find_discuss_list();

		int dy = top_db.query_is_buy(open_id, c_no);
		int sc = top_db.query_is_collection(open_id, c_no);

		model.put("dy", dy);

		model.put("sc", sc);

		// Collections.shuffle(course_list);//混乱排序

		pb.setList(course_list);

		model.put("course_list", pb.getList());
		model.put("discuss_list", discuss_list);
		model.put("course_order", course_order);
		model.put("user", user);

		sendjson(model, response);

	}

	@RequestMapping("course_collection")
	public void course_collection(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");

		Document map = new Document();
		map.append("c_no", c_no);

		/*
		 * Document doc33=new Document(); doc33.put("open_id", open_id);
		 * List<Document>
		 * course_order=cgbdb.find_course_order_list2("course_order",doc33);
		 */
		Document course = cgbdb.findcourse_detail("course", map);

		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", open_id);
		doc.put("date", DateTool.fromDate24H());
		doc.put("c_name", course.getString("c_name"));
		doc.put("is_order", course.getInteger("is_order"));
		doc.put("is_free", course.getString("is_free"));
		doc.put("teacher", course.getString("teacher"));
		doc.put("course_date", course.getString("date"));
		doc.put("watch", course.getInteger("watch"));
		doc.put("price", course.getInteger("price"));
		doc.put("img", course.getString("img"));
		doc.put("type", course.getInteger("type"));
		doc.put("state", course.getInteger("state"));
		doc.put("is_xin", course.getInteger("is_xin"));
		cgbdb.insertOne("collection", doc);
		model.put("result", "success");

		sendjson(model, response);

	}

	@RequestMapping("special_collection_log")
	public void special_collection_log(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");

		Document map = new Document();
		map.append("special_no", c_no);

		Document course = cgbdb.findspecial_detail("special", map);

		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", open_id);
		doc.put("date", DateTool.fromDate24H());
		doc.put("c_name", course.getString("special_name"));
		doc.put("is_order", 1);
		doc.put("is_free", 1);
		doc.put("teacher", course.getString("teacher"));
		doc.put("course_date", course.getString("date"));
		doc.put("watch", course.getInteger("watch"));
		doc.put("price", course.getInteger("price"));
		doc.put("img", course.getString("img"));
		doc.put("type", course.getInteger("type"));
		doc.put("state", course.getInteger("state"));
		doc.put("is_xin", course.getInteger("is_xin"));
		cgbdb.insertOne("collection", doc);
		model.put("result", "success");

		sendjson(model, response);

	}

	@RequestMapping("course_discuss")
	public void course_discuss(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");
		String val = req.getParameter("val");
		Document doc = new Document();
		doc.append("open_id", open_id);
		Document user = cgbdb.findwxfamilyinfo("user", doc);

		Document discuss = new Document();
		discuss.put("c_no", c_no);
		discuss.put("open_id", open_id);
		discuss.put("val", val);
		discuss.put("name", user.getString("name"));
		discuss.put("head", user.getString("head"));
		discuss.put("role", user.getInteger("role"));
		discuss.put("date", DateTool.fromDate24H());

		cgbdb.insertOne("discuss", discuss);

		model.put("result", "success");

		sendjson(model, response);
	}

	@RequestMapping("discuss_detail")
	public void discuss_1(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		Document map = new Document();
		List<Document> course_list = cgbdb.find_course_list(map);// 查询所有课程
		List<Document> discuss_list = cgbdb.find_discuss_list();
		model.put("course_list", course_list);
		model.put("discuss_list", discuss_list);

		sendjson(model, response);

	}

	@RequestMapping("special_detail")
	public void special_detail(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String c_no = req.getParameter("c_no");
		String watch = req.getParameter("watch");
		String open_id = req.getParameter("open_id");

		// int i=100;
		Document doc2 = new Document();
		doc2.append("open_id", open_id);
		Document doc = new Document();
		doc.append("special_no", c_no);

		Document special = cgbdb.findspecial_detail("special", doc);// 根据专题号查询专题
		Document user = cgbdb.findwxfamilyinfo("user", doc2);// 查询用户表

		int dy = top_db.query_is_buy(open_id, c_no); // 是否订阅
		int sc = top_db.query_is_collection(open_id, c_no); // 是否收藏

		// 查询这个专题的学习记录

		Document study = study_db.query_the_study(open_id, c_no);

		List<String> url_list = (List<String>) special.get("url");

		model.put("url_list", url_list);

		List<String> list_string = new ArrayList<String>();
		List<Document> ccourse_list = new ArrayList<Document>();
		// 获取专题中课程的视频id

		if (url_list.size() > 0) {
			for (int cc = 0; cc < url_list.size(); cc++) {

				Document zxc = new Document();
				zxc.put("c_no", url_list.get(cc));
				Document one_course = cgbdb.findcourse_detail("course", zxc);
				ccourse_list.add(one_course);
				if (one_course != null) {
					list_string.add(one_course.getString("url"));
				}

			}
			model.put("list_string", list_string);
			model.put("ccourse_list", ccourse_list);
		}

		// 将视频观看时间保存
		List<Map<String, Object>> study_log = new ArrayList<Map<String, Object>>();
		int has_study = 0;
		if (study != null && study.get("look_log") != null) {
			has_study = 1;
			study_log = (List<Map<String, Object>>) study.get("look_log");

		}

		model.put("study_log", study_log);

		model.put("dy", dy);
		model.put("sc", sc);

		// model.put("special_list", show_special_list);
		// model.put("discuss_list", discuss_list);

		model.put("special", special);
		model.put("user", user);
		sendjson(model, response);

	}

	@RequestMapping("show_collection")
	public void show_collection(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");
		Document doc = new Document();
		doc.append("open_id", open_id);
		
		Document doc2=new Document();
		doc2.put("open_id", open_id);
		doc2.put("state", 1);
		Document user = cgbdb.findwxfamilyinfo("user", doc);// 查询用户表
		// Document course = cgbdb.findwxfamilyinfo("course", doc);//查询用户表
		List<Document> collect_list = cgbdb.show_collect("collection", doc2);// 查询收藏表
		List<Document> course_order = cgbdb.find_course_order_list2(
				"course_order", doc);// 查询订单表

		for (Document d : collect_list) {
			if (checkIsBugy3(course_order, d)) {

				d.append("isBuy", 1);// 1已购买

			}

		}

		Document uu_openid = new Document();
		uu_openid.put("openid", open_id);

		List<Document> study_log = cgbdb
				.find_study_list("study_log", uu_openid);
		if (study_log.size() > 0) {
			model.put("study_log", study_log);
		}

		model.put("collect_list", collect_list);
		model.put("user", user);
		sendjson(model, response);
	}

	@RequestMapping("pay_money")
	public void pay_money(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		String open_id = req.getParameter("open_id");
		String c_no = req.getParameter("c_no");
		String special_no = req.getParameter("special_no");
		String date = DateTool.fromDateY();
		String order_id = String.valueOf(System.currentTimeMillis());
		String company = req.getParameter("company");
		String job = req.getParameter("job");
		String phone = req.getParameter("phone");
		int zzz = 1;
		int type = 0;
		Document map = new Document();
		map.put("c_no", c_no);
		map.put("open_id", open_id);
		int course_count = cgbdb.count_course_order("course_order", map);// 用课程号和openid去课程订单表确定是否已经购买

		Document user = cgbdb.query_customer(open_id);

		if (special_no != null && !special_no.equals("")) {
			type = 2;
			Document ss = new Document();
			ss.put("special_no", special_no);

			Document special = cgbdb.findc_special_no("special", ss);// 查询专题表
			if (course_count == 0) {// 表示未购买 插入数据

				Document config_s = cgbdb.query_config();
				Document course_order = new Document();
				course_order.put("open_id", open_id);
				course_order.put("c_no", c_no);
				course_order.put("type", type);
				course_order.put("c_name", special.getString("special_name"));
				course_order.put("img", special.getString("img"));
				course_order.put("c_date", special.getString("date_1"));
				course_order.put(
						"safe_date",
						DateTool.addDate(date,
								config_s.getInteger("video_date")));
				course_order.put("date", new Date());
				course_order.put("date_1", DateTool.fromDateY());
				course_order.put("teacher", special.getString("teacher"));
				course_order.put("text", special.getString("text"));
				course_order.put("order_id", order_id);
				course_order.put("watch", special.getInteger("watch"));
				course_order.put("name", user.getString("name"));
				course_order.put("role", user.getInteger("role"));
				course_order.put("price", special.getInteger("price"));
				course_order.put("pay_money", special.getInteger("price"));
				course_order.put("is_order", 0);
				// course_order.put("rebate",
				// special.getInteger("price")*config_s.getInteger("percentage")/100);
				// course_order.put("is_order", 1);

				cgbdb.insertOne("course_order", course_order);

				/*
				 * Document schopalship =new Document(); Map<String,Object>
				 * topid=topdb.query_my_top(open_id); if(topid!=null){
				 * 
				 * schopalship.put("top_openid", topid.get("top"));
				 * schopalship.put("c_name", special.getString("special_name"));
				 * schopalship.put("open_id", open_id); schopalship.put("name",
				 * user.getString("name")); schopalship.put("pay_money",
				 * special.getInteger("price")*100); //
				 * schopalship.put("rebate",
				 * special.getInteger("price")*config_s
				 * .getInteger("percentage")/100); schopalship.put("date",
				 * DateTool.fromDateY());
				 * 
				 * cgbdb.insertOne("scholship_log", schopalship); }else{
				 * 
				 * schopalship.put("c_name", special.getString("special_name"));
				 * schopalship.put("open_id", open_id); schopalship.put("name",
				 * user.getString("name")); schopalship.put("pay_money",
				 * special.getInteger("price")*100); //
				 * schopalship.put("rebate",
				 * special.getInteger("price")*config_s
				 * .getInteger("percentage")/100); schopalship.put("date",
				 * DateTool.fromDateY());
				 * 
				 * cgbdb.insertOne("scholship_log", schopalship); }
				 */

				Document docc = new Document();
				docc.put("order_id", order_id);

				Document table_course_order = cgbdb.find_course_order(
						"course_order", docc);
				// user.put("purse",user.getInteger("purse")+table_course_order.getInteger("rebate"));
				user.put("company", company);
				user.put("job", job);
				user.put("phone", phone);
				// user.put("scholarship",
				// user.getInteger("scholarship")+table_course_order.getInteger("rebate"));//应该是在支付完成时候添加现在是测试
				// special.put("is_order", 1);
				// special.put("order_count",
				// special.getInteger("order_count")+zzz);

				// cgbdb.replaceOne("special", Filters.eq("special_no",
				// special_no), special);

				cgbdb.replaceOne("user", Filters.eq("open_id", open_id), user);

				model.put("result", "success");
				model.put("order_id", order_id);
			} else {//
				model.put("order_id", order_id);
				model.put("result", "is_have");
			}

		}

		Document doc = new Document();

		if (c_no != null && !c_no.equals("")) {
			type = 1;

			doc.put("c_no", c_no);
			Document course = cgbdb.findcourse_detail("course", doc);

			if (course_count == 0) {// 表示未购买 插入数据

				Document config_s = cgbdb.query_config();
				Document course_order = new Document();
				course_order.put("open_id", open_id);
				course_order.put("c_no", c_no);
				course_order.put("type", type);
				course_order.put("c_name", course.getString("c_name"));
				course_order.put("img", course.getString("img"));
				course_order.put("c_date", course.getString("date"));
				course_order.put(
						"safe_date",
						DateTool.addDate(date,
								config_s.getInteger("video_date")));
				course_order.put("date", new Date());
				course_order.put("date_1", DateTool.fromDateY());
				course_order.put("teacher", course.getString("teacher"));
				course_order.put("text", course.getString("text"));
				course_order.put("order_id", order_id);
				course_order.put("watch", course.getInteger("watch"));
				course_order.put("name", user.getString("name"));
				course_order.put("role", user.getInteger("role"));
				course_order.put("price", course.getInteger("price"));
				course_order.put("pay_money", course.getInteger("price"));
				course_order.put("is_order", 0);
				// course_order.put("rebate",
				// course.getInteger("price")*config_s.getInteger("percentage")/100);
				// course_order.put("is_order", 1);

				cgbdb.insertOne("course_order", course_order);

				/*
				 * Document schopalship =new Document(); Map<String,Object>
				 * topid=topdb.query_my_top(open_id); if(topid!=null){
				 * 
				 * schopalship.put("top_openid", topid.get("top"));
				 * schopalship.put("c_name", course.getString("special_name"));
				 * schopalship.put("open_id", open_id); schopalship.put("name",
				 * user.getString("name")); schopalship.put("pay_money",
				 * course.getInteger("price")*100); schopalship.put("rebate",
				 * course
				 * .getInteger("price")*config_s.getInteger("percentage")/100);
				 * schopalship.put("date", DateTool.fromDateY());
				 * 
				 * cgbdb.insertOne("scholship_log", schopalship); }else{
				 * 
				 * schopalship.put("c_name", course.getString("special_name"));
				 * schopalship.put("open_id", open_id); schopalship.put("name",
				 * user.getString("name")); schopalship.put("pay_money",
				 * course.getInteger("price")*100); // schopalship.put("rebate",
				 * course
				 * .getInteger("price")*config_s.getInteger("percentage")/100);
				 * schopalship.put("date", DateTool.fromDateY());
				 * 
				 * cgbdb.insertOne("scholship_log", schopalship); }
				 */

				Document docc = new Document();
				docc.put("order_id", order_id);

				Document table_course_order = cgbdb.find_course_order(
						"course_order", docc);
				// user.put("purse",user.getInteger("purse")+course.getInteger("price"));
				user.put("company", company);
				user.put("job", job);
				user.put("phone", phone);
				// user.put("scholarship",
				// user.getInteger("scholarship")+table_course_order.getInteger("rebate"));//应该是在支付完成时候添加现在是测试
				// course.put("is_order", 1);
				// course.put("order_count",
				// course.getInteger("order_count")+zzz);
				// cgbdb.replaceOne("course", Filters.eq("c_no", c_no), course);

				cgbdb.replaceOne("user", Filters.eq("open_id", open_id), user);

				model.put("result", "success");
				model.put("order_id", order_id);
			} else {//

				model.put("result", "is_have");
				model.put("order_id", order_id);
			}

		} else {
			model.put("result", "error");
		}

		sendjson(model, response);
	}
	
	 
	@RequestMapping("shows_vip")
	public String shows_vip(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String order_id = req.getParameter("order_id");

		String mindate = req.getParameter("mindate");
		String maxdate = req.getParameter("maxdate");
		String psht = req.getParameter("ps");
		int pn = 0;
		if (StringUtil.bIsNotNull(mindate)) {
			map.put("mindate", mindate);
		}
		if (StringUtil.bIsNotNull(maxdate)) {
			map.put("maxdate", maxdate);
		}

		if (StringUtil.bIsNotNull(psht)) {
			map.put("ps", StringUtil.toNum(psht));
		} else {
			map.append("ps", ps);
		}

		if (StringUtil.bIsNotNull(page)) {
			pn = StringUtil.toNum(page);
		}
		if (StringUtil.bIsNotNull(order_id)) {
			map.append("order_id", order_id);
		}

		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		map.append("is_order", 1);
		map.append("is_over", 0);
		int rc = (int) cgbdb.countUser("vip_order", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb("vip_order", map);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		model.put("course_order", pb);
		model.put("map", map);

		return "/pt/course/vip_order";
	}
	
	
	
	@RequestMapping("shows_course_order")
	public String shows_course_order(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		Document map = new Document();
		PageBean<Document> pb = new PageBean<Document>();
		String page = req.getParameter("page");
		String order_id = req.getParameter("order_id");

		String mindate = req.getParameter("mindate");
		String maxdate = req.getParameter("maxdate");
		String psht = req.getParameter("ps");
		int pn = 0;
		if (StringUtil.bIsNotNull(mindate)) {
			map.put("mindate", mindate);
		}
		if (StringUtil.bIsNotNull(maxdate)) {
			map.put("maxdate", maxdate);
		}

		if (StringUtil.bIsNotNull(psht)) {
			map.put("ps", StringUtil.toNum(psht));
		} else {
			map.append("ps", ps);
		}

		if (StringUtil.bIsNotNull(page)) {
			pn = StringUtil.toNum(page);
		}
		if (StringUtil.bIsNotNull(order_id)) {
			map.append("order_id", order_id);
		}

		if (pn < 1) {
			pn = 1;
		}

		map.append("pn", pn);

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		map.append("is_order", 1);

		int rc = (int) cgbdb.countUser("course_order", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb("course_order", map);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		model.put("course_order", pb);
		model.put("map", map);

		return "/pt/course/course_order";
	}

	@RequestMapping("show_before_order")
	public void show_before_order(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");
		String c_no = req.getParameter("c_no");
		String special_no = req.getParameter("special_no");

		if (c_no != null && !c_no.equals("")) {

			Document doc = new Document();

			doc.put("open_id", open_id);

			Document user = cgbdb.findwxfamilyinfo("user", doc);// 查询用户表
			Document doc1 = new Document();
			doc1.put("c_no", c_no);
			if (c_no != null && !c_no.equals("")) {

				Document course = cgbdb.findcourse_detail("course", doc1);

				model.put("course", course);
			} else {

				Document course = cgbdb.findcourse_detail("course", doc1);
				model.put("course", course);
			}

			model.put("user", user);
		}

		if (special_no != null && !special_no.equals("")) {

			Document doc = new Document();

			doc.put("open_id", open_id);

			Document user = cgbdb.findwxfamilyinfo("user", doc);// 查询用户表
			Document doc1 = new Document();
			doc1.put("special_no", special_no);
			if (c_no != null && !c_no.equals("")) {

				Document course = cgbdb.findspecial_detail("special", doc1);

				model.put("special", course);
			} else {

				Document course = cgbdb.findspecial_detail("special", doc1);
				model.put("special", course);
			}

			model.put("user", user);
		}

		sendjson(model, response);
	}

	@RequestMapping("downloadexcel")
	public void downloadexcel(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		String mindate = req.getParameter("mindate");
		String maxdate = req.getParameter("maxdate");
		String psht = req.getParameter("ps");
		String page = req.getParameter("page");
		int pn = 0;
		PageBean<Document> pb = new PageBean<Document>();
		Document map = new Document();

		if (StringUtil.bIsNotNull(mindate)) {
			map.put("mindate", mindate);
		}
		if (StringUtil.bIsNotNull(maxdate)) {
			map.put("maxdate", maxdate);
		}

		if (StringUtil.bIsNotNull(psht)) {
			map.put("ps", StringUtil.toNum(psht));
		} else {
			map.append("ps", ps);
		}

		if (StringUtil.bIsNotNull(page)) {
			pn = StringUtil.toNum(page);

		}
		if (pn < 1) {
			pn = 1;
		}

		map.put("pn", pn);

		pb.setPageNum(map.getInteger("pn", pn));// 第几页
		pb.setPageSize(map.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("course_order", map);
		pb.setRowCount(rc);

		List<Document> list = cgbdb.findByPb("course_order", map);

		if (list != null && list.size() > 0) {
			pb.setList(list);
		}

		HSSFWorkbook wb = Download_Excel.export(list);
		response.setContentType("applicationnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=order.xls");
		OutputStream ouputStream;
		try {
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("query_isbuy")
	public void query_isbuy(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");
		String special_no = req.getParameter("special_no");

		Document  doc2=new Document();
		doc2.put("open_id", open_id);
		
		Document user = cgbdb.findwxfamilyinfo("user", doc2);// 查询用户表
		Document doc = new Document();
		doc.put("open_id", open_id);
		doc.put("c_no", special_no);

		int rc = cgbdb.count_course_order("course_order", doc);

		if (rc > 0) {

			model.put("isbuy", 1);// 已经买过
		} else {
			model.put("isbuy", -1);// 未购买
		}
		model.put("user", user);
		sendjson(model, response);
	}

	@RequestMapping("query_isbuy2")
	public void query_isbuy2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String open_id = req.getParameter("open_id");
		String c_no = req.getParameter("c_no");

		Document doc = new Document();
		doc.put("open_id", open_id);
		doc.put("c_no", c_no);

		Document doc2 = new Document();

		doc2.put("c_no", c_no);

		Document course = cgbdb.findcourse_detail("course", doc2);

		int rc = cgbdb.count_course_order("course_order", doc);

		if (rc > 0) {

			model.put("isbuy", 1);// 已经买过
		} else {
			model.put("isbuy", -1);// 未购买
		}

		model.put("course", course);
		sendjson(model, response);
	}

	/*
	 * 看完视频加分
	 */
	@RequestMapping("add_score")
	public void add_score(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		String open_id = req.getParameter("open_id");
		Document dd = new Document();

		if (open_id != null && !open_id.equals("")) {
			dd.put("open_id", open_id);

			Document config_s = cgbdb.query_config();

			Document user = cgbdb.findwxfamilyinfo("user", dd);

			user.put(
					"score",
					user.getInteger("score")
							+ config_s.getInteger("sign_score"));

			cgbdb.replaceOne("user",
					Filters.eq("open_id", user.getString("open_id")), user);

			Document da = new Document();
			da.put("open_id", open_id);
			da.put("date", DateTool.fromDateY());
			da.put("score", config_s.getInteger("video_score"));
			da.put("name", user.getString("name"));
			da.put("type", 1);
			cgbdb.insertOne("sign_log", da);

			model.put("result", "success");
		} else {
			model.put("result", "error");
		}

	}

	/**
	 * 
	 * 关注
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("watched")
	@ResponseBody
	public String watched(HttpServletRequest req) {
		String id = req.getParameter("id");

		cgbdb.addWatch(id);
		return "200";
	}

	@RequestMapping("watched2")
	@ResponseBody
	public String watched2(HttpServletRequest req) {
		String id = req.getParameter("id");

		cgbdb.addWatch2(id);
		return "200";
	}

	@RequestMapping("huan_huan")
	public void huan_huan(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		String page = req.getParameter("page");


		String open_id = req.getParameter("open_id");
		Document doc2=new Document();
		doc2.put("open_id", open_id);
		Document user=cgbdb.findwxfamilyinfo("user", doc2);
		PageBean<Document> pb = new PageBean<Document>();

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 50;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);
		map1.append("kind", 1);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		List<Document> special = cgbdb.find_special_lmilt2("special", map1);// 查询所有专题

		List<Document> course_order2 = cgbdb.find_course_order_list(open_id, 2);// 查询所有专题订单

		for (Document d : special) {
			if (checkIsBugy(course_order2, d, 2)) {

				d.append("isBuy", 1);// 1已购买

			}
		}

		List<Document> special_all = cgbdb.find_special();

		List<Document> vid_member = (List<Document>) special.get(0).get("url");

		List<Document> list2 = new ArrayList<Document>();
		pb.setList(special);

		for (Document z : special) {

			list2.add(z);

		}
		Collections.shuffle(list2);// 混乱排序

		List<Document> list = new ArrayList<Document>();

		int num = list2.size();
		if (num > 4) {
			num = 4;
		}
		for (int j = 0; j < num; j++) {
			list.add(list2.get(j));

		}

		model.put("list", list);
		model.put("user", user);
		model.put("special", pb);
		model.put("vid_size", vid_member.size());
		model.put("special_all", special_all);

		sendjson(model, response);
	}

	@RequestMapping("huan_huan2")
	public void huan_huan2(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String page = req.getParameter("page");

		String open_id = req.getParameter("open_id");
		
		Document doc2=new Document();
		doc2.put("open_id", open_id);
		Document user=cgbdb.findwxfamilyinfo("user", doc2);

		PageBean<Document> pb = new PageBean<Document>();

		int pn = 0;

		if (page != null && !page.equals("")) {
			pn = Integer.valueOf(page);
		}
		if (pn < 1) {
			pn = 1;
		}

		int ps = 50;
		Document map1 = new Document();
		map1.append("pn", pn);
		map1.append("ps", ps);
		map1.append("kind", 2);

		pb.setPageNum(map1.getInteger("pn", pn));// 第几页
		pb.setPageSize(map1.getInteger("ps", ps));// 每页多少

		int rc = (int) cgbdb.countUser("special", map1);
		pb.setRowCount(rc);

		List<Document> special = cgbdb.find_special_lmilt8("special", map1);// 查询所有专题

		List<Document> course_order2 = cgbdb.find_course_order_list(open_id, 2);// 查询所有专题订单

		for (Document d : special) {
			if (checkIsBugy(course_order2, d, 2)) {

				d.append("isBuy", 1);// 1已购买

			}
		}

		List<Document> special_all = cgbdb.find_special();

		List<String> vid_member = (List<String>) special.get(0).get("url");

		Document doc = new Document();
		if (vid_member.size() > 0) {

			for (int k = 0; k < vid_member.size(); k++) {
				String c_no = vid_member.get(k);
				doc.put("c_no", c_no);
				Document course = cgbdb.findcourse_detail("course", doc);
				if (course != null) {
					model.put("time", course.getInteger("time"));
				}
			}

		}

		List<Document> list2 = new ArrayList<Document>();
		pb.setList(special);

		for (Document z : special) {

			list2.add(z);

		}
		Collections.shuffle(list2);// 混乱排序

		List<Document> list = new ArrayList<Document>();

		int num = list2.size();
		if (num > 3) {
			num = 3;
		}
		for (int k = 0; k < num; k++) {
			list.add(list2.get(k));
		}
		model.put("user", user);
		model.put("list", list);
		model.put("special", pb);
		model.put("vid_size", vid_member.size());
		model.put("special_all", special_all);

		sendjson(model, response);
	}

	@RequestMapping("is_pullof")
	public void is_pullof(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		String c_no = req.getParameter("c_no");

		String type2 = req.getParameter("type");

		int type = 0;

		Document doc = new Document();
		Document doc1 = new Document();
		Document  doc2=new Document();
		
		doc2.put("c_no", c_no);
		if (type2 != null && !equals("")) {

			type = Integer.valueOf(type2);
			doc.put("type", type);
			doc.put("c_no", c_no);
		}

		if (type == 1) {

			doc1.put("special_no", c_no);

		}
		
		if(type == 2){
			doc1.put("special_no", c_no);
			
		}
		
		
		if(type == 0){
			doc1.put("special_no", c_no);
			
		}
		
		Document special = cgbdb.findspecial_detail("special", doc1);
		Document course = cgbdb.findcourse_detail("course", doc);
		Document course2 = cgbdb.findcourse_detail("course", doc2);
		Document set_page = art_db.query_set_page(1);

		model.put("set_page", set_page);

		model.put("special", special);
		model.put("course", course);
		model.put("course2", course2);
		sendjson(model, response);

	}

	@RequestMapping("to_send")
	public void to_send(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		int msg_type = Integer.valueOf(req.getParameter("msg_type"));
		String url = req.getParameter("url");

		Document msg = new Document();
		msg.put("msg_type", msg_type);

		if (msg_type == 5) {
			Map<String, Object> data = new HashMap<String, Object>();

			Map<String, String> first = new HashMap<String, String>();
			first.put("value", req.getParameter("frist"));
			first.put("color", "#333333");

			Map<String, String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", req.getParameter("keyword1"));
			keyword1.put("color", "#2dc158");

			Map<String, String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", req.getParameter("keyword2"));
			keyword2.put("color", "#2dc158");

			Map<String, String> keyword3 = new HashMap<String, String>();
			keyword3.put("value", req.getParameter("keyword3"));
			keyword3.put("color", "#2dc158");

			Map<String, String> keyword4 = new HashMap<String, String>();
			keyword4.put("value", req.getParameter("keyword4"));
			keyword4.put("color", "#2dc158");

			Map<String, String> remark = new HashMap<String, String>();
			remark.put("value", req.getParameter("remark"));
			remark.put("color", "#333333");

			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);
			data.put("keyword3", keyword3);
			data.put("keyword4", keyword4);

			data.put("remark", remark);

			msg.put("data", data);

		} else if (msg_type == 6 || msg_type == 12) {

			Map<String, Object> data = new HashMap<String, Object>();

			Map<String, String> first = new HashMap<String, String>();
			first.put("value", req.getParameter("frist"));
			first.put("color", "#333333");

			Map<String, String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", req.getParameter("keyword1"));
			keyword1.put("color", "#2dc158");

			Map<String, String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", req.getParameter("keyword2"));
			keyword2.put("color", "#2dc158");

			Map<String, String> remark = new HashMap<String, String>();
			remark.put("value", req.getParameter("remark"));
			remark.put("color", "#333333");

			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);

			data.put("remark", remark);

			msg.put("data", data);
		} else if (msg_type == 7) {

			Map<String, Object> data = new HashMap<String, Object>();

			Map<String, String> userName = new HashMap<String, String>();
			userName.put("value", req.getParameter("userName"));
			userName.put("color", "#333333");

			Map<String, String> courseName = new HashMap<String, String>();
			courseName.put("value", req.getParameter("courseName"));
			courseName.put("color", "#2dc158");

			Map<String, String> date = new HashMap<String, String>();
			date.put("value", req.getParameter("date"));
			date.put("color", "#2dc158");

			Map<String, String> remark = new HashMap<String, String>();
			remark.put("value", req.getParameter("remark"));
			remark.put("color", "#333333");

			data.put("userName", userName);
			data.put("courseName", courseName);
			data.put("date", date);

			data.put("remark", remark);

			msg.put("data", data);
		}

		Document mould = msg_db.find_mould(msg_type);

		msg.put("template_id", mould.getString("template_id"));
		msg.put("url", url);
		msg.put("c_time", DateTool.fromDate24H());

		area_db.add_db_info("message_second", msg);

		List<Document> user = cgbdb.find_user_list();

		JSONArray json = JSONArray.fromObject(msg.get("data"));

		if (user.size() > 0) {

		for (int i = 0; i < user.size(); i++) {

				if (user.get(i).getString("open_id") != null
						&& !user.get(i).getString("open_id").equals("")) {
						
						try {
							Thread.sleep(10);
							int wx_error_code=Msg_tools.send_wx_msg(user.get(i).getString("open_id"),
									msg.getString("template_id"), msg.getString("url"),
									json.getJSONObject(0).toString());
							
							if(wx_error_code!=0){
								
								Document toc=new Document();
									toc.put("openid", user.get(i).getString("open_id"));
									toc.put("template_id", msg.getString("template_id"));
									toc.put("url", msg.getString("url"));
									toc.put("json", json.getJSONObject(0).toString());
									toc.put("state", -1);
									toc.put("error", wx_error_code);
									cgbdb.insertOne("error_message", toc);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				
					 
				}
			}
					
		}

		model.put("back_code", 200);

		sendjson(model, response);
	}

	@RequestMapping("to_send_activity")
	public void to_send_activity(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {

		Map<String, Object> map = new HashMap<String, Object>();

		String title = req.getParameter("title");
		String name = req.getParameter("name");
		String time = req.getParameter("time");
		String place = req.getParameter("place");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String note = req.getParameter("note");

		map.put("msg_type", 7);
		map.put("title", title);
		map.put("name", name);
		map.put("time", time);
		map.put("place", place);
		map.put("phone", phone);
		map.put("address", address);
		map.put("note", note);

		map.put("back_title", title);
		map.put("back_name", name);
		map.put("back_time", time);
		map.put("back_place", place);
		map.put("back_phone", phone);

		map.put("back_address", address);
		map.put("back_note", note);
		msg_db.add_msg4(map);

		List<Document> user = cgbdb.find_user_list();

		List<Document> list = msg_db.query_no_send3(7);
		if (list.size() > 0) {

			for (int x = 0; x < list.size(); x++) {
				Document msg = list.get(x);
				String _id = msg.get("_id").toString();

				if (user.size() > 0) {

					for (int z = 0; z < user.size(); z++) {

						if (user.get(z).getString("open_id") != null
								&& !user.get(z).getString("open_id").equals("")) {

							JSONArray json = JSONArray.fromObject(msg
									.get("data"));
							String open_id =  user.get(z).getString("open_id"); //
							int error = Msg_tools.send_wx_msg(open_id, msg
									.getString("template_id"), msg
									.getString("url"), json.getJSONObject(0)
									.toString());

							if (error == 0) {
								msg.put("is_send", 1);
								msg.put("send_time", DateTool.fromDate24H());

								msg_db.update_message2(_id, msg);
								model.put("result", "success");
							}

						}

					}

				} else {
					msg.put("is_send", -1);
					msg_db.update_message2(_id, msg);

				}

			}

		}
		sendjson(model, response);
	}

	@RequestMapping("course_dt")
	public void course_dt(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {
		String c_no = req.getParameter("c_no");
		String open_id = req.getParameter("open_id");

		Document doc = new Document();
		doc.append("special_no", c_no);

		Document doc2 = new Document();
		doc2.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", doc2);// 查询用户表

		Document special = cgbdb.findspecial_detail("special", doc);// 根据专题号查询专题

		model.put("special", special);

		if (special != null) {
			List<String> url_list = (List<String>) special.get("url");

			model.put("url_list", url_list);
			List<String> list_string = new ArrayList<String>();
			List<Document> ccourse_list = new ArrayList<Document>();
			// 获取专题中课程的视频id

			if (url_list.size() > 0) {
				for (int cc = 0; cc < url_list.size(); cc++) {

					Document zxc = new Document();
					zxc.put("c_no", url_list.get(cc));
					zxc.put("state", 1);
					Document one_course = cgbdb
							.findcourse_detail("course", zxc);
					ccourse_list.add(one_course);
					if (one_course != null) {
						list_string.add(one_course.getString("url"));
					}

				}
			}

			model.put("list_string", list_string);
			model.put("ccourse_list", ccourse_list);
		}
		Document map = new Document();
		if (c_no != null && !c_no.equals("")) {
			map.append("c_no", c_no);
			Document course = cgbdb.findcourse_detail("course", map);
			if (course != null && !course.equals("")) {

				model.put("course", course);
			}
		}

		int dy = top_db.query_is_buy(open_id, c_no);
		model.put("dy", dy);

		model.put("user", user);
		sendjson(model, response);
	}

	@RequestMapping("hi_xufei")
	public void hi_xufei(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		String open_id = req.getParameter("open_id");
		Document doc2 = new Document();
		doc2.append("open_id", open_id);

		Document user = cgbdb.findwxfamilyinfo("user", doc2);// 查询用户表

		Document doc = new Document();
		doc.put("open_id", open_id);
		doc.put("is_order", 1);

		Document vip_safe_date = area_db.find_vip_order("vip_order", doc);

		if (user != null) {

			Map<String, Object> message_map = new HashMap<String, Object>();

			if (user.getInteger("role") == 1) {

				message_map.put("title", "亲，您的订购内容快到期啦！");
				message_map.put("nr", "到期内容为全年VIP");
				message_map.put("end_time",
						vip_safe_date.getString("safe_date"));
				message_map.put("note", "学管理，上到答！点击<详情>立即续费，到答伴您成长！");
				message_map.put("msg_type", 9);

				message_map.put("touser", open_id);
				msg_db.add_msg9(message_map);

				List<Document> list = top_db.query_moudle(9);
				Document msg = null;
				String _id2 = "";
				if (list.size() > 0) {

					for (int x = 0; x < list.size(); x++) {

						msg = list.get(x);
						_id2 = msg.get("_id").toString();
						JSONArray json = JSONArray.fromObject(msg.get("data"));

						int error = Msg_tools.send_wx_msg(msg
								.getString("touser"), msg
								.getString("template_id"),
								msg.getString("url"), json.getJSONObject(0)
										.toString());
						if (error == 0) {
							msg.put("is_send", 1);
							msg.put("send_time", DateTool.fromDate24H());

							msg_db.update_message(_id2, msg);
							model.put("result", "success");

						}

					}

				} else {
					msg.put("is_send", -1);
					msg_db.update_message(_id2, msg);
					model.put("result", "error");
				}

			} else {
				
				
				Document zxc = new Document();
				zxc.put("open_id", user.getString("top_openid"));
				Document top_user = cgbdb.findwxfamilyinfo("user", zxc);

				message_map.put("title", "学管理，上到答。");

				message_map.put("name", user.getString("name"));

				message_map.put("number", DateTool.fromDate24H());
				message_map.put("note", "您的好友" + top_user.getString("name")
						+ ",推荐您立即加入最前沿的管理知识分享平台—到答课堂，移动学习，随时随地，边学边赚钱！");

				message_map.put("msg_type", 10);

				message_map.put("touser", open_id);
				msg_db.add_msg10(message_map);
				List<Document> list = top_db.query_moudle(10);
				Document msg = null;
				String _id2 = "";
				if (list.size() > 0) {

					for (int x = 0; x < list.size(); x++) {

						msg = list.get(x);
						_id2 = msg.get("_id").toString();
						JSONArray json = JSONArray.fromObject(msg.get("data"));

						int error = Msg_tools.send_wx_msg(msg
								.getString("touser"), msg
								.getString("template_id"),
								msg.getString("url"), json.getJSONObject(0)
										.toString());
						if (error == 0) {
							msg.put("is_send", 1);
							msg.put("send_time", DateTool.fromDate24H());

							msg_db.update_message(_id2, msg);
							model.put("result", "success");

						}

					}

				} else {
					msg.put("is_send", -1);
					msg_db.update_message(_id2, msg);
					model.put("result", "error");
				}
			}

		}
		sendjson(model, response);
	}

	@RequestMapping("to_choose_course")
	public void to_choose_course(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String c_type=req.getParameter("c_type");
			
		Document doc = new Document();
		doc.put("is_free", "1");
		doc.put("type", 1);
		doc.put("state", 1);
		doc.put("c_type", c_type);
		doc.put("is_show", 1);
		List<Document> course_list = cgbdb.show_collect("course", doc);// 查询所有课程

		model.put("course_list", course_list);

		sendjson(model, response);

	}

	@RequestMapping("to_choose_viedo")
	public void to_choose_viedo(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		
		String  c_type=req.getParameter("c_type");
		
		Document doc = new Document();
		doc.put("is_free", "1");
		doc.put("type", 0);
		doc.put("state", 1);
		doc.put("is_show", 1);		
		doc.put("c_type", c_type);
		List<Document> course_list = cgbdb.show_collect("course", doc);// 查询所有课程
		
		

		model.put("course_list", course_list);

		sendjson(model, response);

	}

	@RequestMapping("is_judge")
	public void is_judge(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model) {

		String open_id = req.getParameter("open_id");
		String c_no = req.getParameter("c_no");

		Document doc = new Document();

		doc.put("open_id", open_id);
		Document user = cgbdb.findwxfamilyinfo("user", doc);// 查询用户表

		if (user != null) {
			model.put("user", user);

			Document ddc = new Document();

			ddc.put("url", c_no);

			List<Document> special_list = cgbdb.show_collect("special", ddc);// 查询所有包含课程的专题
			List<Document> course_order = cgbdb.find_course_order_list(open_id,
					2);// 查询所有课程订单
			for (Document d : special_list) {
				if (checkIsBugy(course_order, d, 2)) {

					d.append("isBuy", 1);// 1已购买
				}
			}

			model.put("special_list", special_list);

		}
		sendjson(model, response);
	}

	@RequestMapping("show_course_type")
	public  void  show_course_type(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model){
		
		List<Document> course_type_list = cgbdb.show_collect("course_type", null);	
		
		model.put("course_type_list", course_type_list);
		
		sendjson(model, response);
			
	}
	@RequestMapping("show_course_type_voice")

	public   void  show_course_type_voice(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model){
		List<Document> course_type_list = cgbdb.show_collect("course_type_voice", null);	
		
		model.put("course_type_list", course_type_list);
		
		sendjson(model, response);
	}
	
	
	@RequestMapping("too_choose_tp")
	public  void  too_choose_tp(HttpServletRequest req, HttpServletResponse response,
			Map<String, Object> model){
		
		String  c_no=req.getParameter("c_no");
		Document docc = new Document();
		docc.put("c_no", c_no);
 
		Document bdb2 = cgbdb.findcourse_detail("course", docc);
			
		int type=bdb2.getInteger("type");
		
		if(type==1){
			
			List<Document> course_type_list = cgbdb.show_collect("course_type", null);
			model.put("course_type_list", course_type_list);
		}else{
			
			List<Document> course_type_list = cgbdb.show_collect("course_type_voice", null);
			model.put("course_type_list", course_type_list);
		}
		sendjson(model, response);
	}
	
	@RequestMapping("to_choose_course_all")
	public void to_choose_course_all(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		
		String  c_type=req.getParameter("c_type");
		String  c_no=req.getParameter("cc_no");
		Document docc = new Document();
		docc.put("c_no", c_no);
 
		Document bdb2 = cgbdb.findcourse_detail("course", docc);
			
		int type=bdb2.getInteger("type");
		
		Document doc = new Document();
		doc.put("is_free", "1");
		doc.put("type", type);
		doc.put("state", 1);
		doc.put("is_show", 1);		
		doc.put("c_type", c_type);
		List<Document> course_list = cgbdb.show_collect("course", doc);// 查询所有课程
		
		

		model.put("course_list", course_list);

		sendjson(model, response);

	}
	
	@RequestMapping("show_log")
	public  void  show_log(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		String open_id=req.getParameter("openid");
		int page = 1; 
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		
		int limit = 20; 
		int skip = (page - 1)*limit;
		
		Document doc=new Document();
		doc.put("share_openid", open_id);
		doc.put("is_add", 1);
		int count=cgbdb.count_course_order("share_log", doc);
		
		List<Document> list = cgbdb.query_share("share_log",open_id);
		
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
		  
		List<Document> list_list=new ArrayList<Document>();
		
		if(list!=null){
			
			for(int i=0;i<list.size();i++){
				Document doc2=new Document();
				doc2.put("open_id", list.get(i).get("share_openid").toString());
				
				Document user=cgbdb.findwxfamilyinfo("user", doc2);
				list_list.add(user);
			}
			
		}
		List<Document> log_list = top_db.query_scholship_log(open_id, skip, limit);
		Document config_s = cgbdb.query_config();
		
		model.put("page_num", page_num);
		model.put("page_count", page_count);
		model.put("log_list", log_list);
		model.put("list", list_list);
		model.put("config_s", config_s);
		model.put("list_2", list);
		sendjson(model, response);
	}
	
	@RequestMapping("tanchu")
	public String  tanchu(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		List<Document> lunbo_list = cgbdb.query_tanchu();
		
		model.put("lunbo_list", lunbo_list);

		return "/pt/config/tanchu";
	}
	
	
	@RequestMapping("show_tanchu")
	public void  show_tanchu(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
		
		List<Document> lunbo_list = cgbdb.query_tanchu();
		
		model.put("tanchu", lunbo_list);

		sendjson(model, response);
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
	 
			
			Document doc = cgbdb.query_one_lunbo(l_id);
			
			
			if( has_img ==1 && res == 1 ){
				doc.put("img", "img/art/"+ file_code + ext);
			} 
			doc.put("link", l_link);
			doc.put("cdate", DateTool.fromDateY());
			
			cgbdb.update_lunbo(l_id, doc);
			
			back_code ="success";
		
		 
		sendMassage(back_code, response);
		 
	}
	
	
	@RequestMapping("chanage_state")
	public  void chanage_state(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model){
		String l_id = request.getParameter("id"); 
		int state =Integer.valueOf(request.getParameter("state")) ; 
		Document doc = cgbdb.query_one_lunbo(l_id);
		
			if(state==1){
				doc.put("state", -1);
				cgbdb.update_lunbo(l_id, doc);
			}else{
				doc.put("state", 1);
				cgbdb.update_lunbo(l_id, doc);
			}
			
			sendjson(model, response);
	}
	
	 
	@RequestMapping("one_price")	
	public String one_price(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		
		/*String phone = req.getParameter("phone");
 
		Document  doc= new Document();
		doc.put("phone", phone);*/
		 
		List<Document> list = cgbdb.find_phone(null);

		

		model.put("phone", list);


		return "/pt/phone/phone_list";
	}
	
	@RequestMapping("dele_phone")	
	public String dele_phone(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
		String phone = req.getParameter("phone");
		if (phone != null && !phone.equals("")) {
			MongoUtil mu = MongoUtil.getThreadInstance();
			Document doc = new Document();
			doc.put("phone", phone);
			mu.deleteMany("phone_list", doc);

			model.put("result", "success");

		} else {
			model.put("result", "error");

		}
		
		
		return "redirect:/course/one_price.do";
	}
	
	
	@RequestMapping(value = "insertphone", method = { RequestMethod.POST })
	public void insertphone(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model) {
 
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		
		
		Document  doc=new Document();
		
		doc.put("phone", phone);
		
		int count  =cgbdb.count_course_order("phone_list", doc);
		
		if(count>0){
			
			model.put("result", "err");
		}else{
			
			MongoUtil mu = MongoUtil.getThreadInstance();

			BasicDBObject bdb2 = new BasicDBObject();
			bdb2.put("name",name);
			bdb2.put("phone",phone);
			bdb2.put("date", DateTool.fromDate24H());
			
			mu.insertOne("phone_list", bdb2);
			model.put("result", "success");
			
		} 
		
	
		sendjson(model, response);
	}
}
