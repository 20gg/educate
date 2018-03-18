/**
 * 
 */
package com.hysm.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hysm.db.Area_db;
import com.hysm.db.CbgDB;
import com.hysm.db.Top_db;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.CommonUtil;
import com.hysm.tools.DateTool;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;




@Controller   
@RequestMapping("/user")
public class UserController extends BaseController{
	
					private int pn = 1;
					private int ps = 50;
					
					@Autowired
					private CbgDB cgbdb;
					@Autowired
					private Top_db topdb;
	
					@Autowired
					private Top_db top_db;
					
					@Autowired
					private Area_db area_db;
					

		@RequestMapping("show_user")			
		public String show_user(HttpServletRequest req,
		HttpServletResponse response, Map<String, Object> model){
			Document map = new Document();
			PageBean<Document> pb = new PageBean<Document>();
			String page = req.getParameter("page");
			String name = req.getParameter("name");

			if (page != null && !page.equals("")) {
				pn = Integer.valueOf(page);
			}
			if (pn < 1) {
				pn = 1;
			}

			map.append("pn", pn);
			map.append("ps", ps);
			if (name != null && !name.equals("")) {
				map.append("name", name);
			}

			pb.setPageNum(map.getInteger("pn", pn));// 第几页
			pb.setPageSize(map.getInteger("ps", ps));// 每页多少

			int rc = (int) cgbdb.countUser("user", map);
			pb.setRowCount(rc);

			List<Document> list = cgbdb.findByPb2("user", map);

			if (list != null && list.size() > 0) {
				pb.setList(list);
			}

			model.put("user_list", pb);
	
	
			return "/pt/userManager/user";
		}

		@RequestMapping(value = { "/show_all_user_page" }, method = { RequestMethod.GET })
		public String select_usermanager_page(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model) {

			int page = Integer.valueOf(req.getParameter("page"));

			List<Map<String, Object>> user_list = new ArrayList<Map<String, Object>>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", (page - 1) * 50);

			MongoUtil mu = MongoUtil.getThreadInstance();
			MongoCursor<Document> cursor = mu.find("user", null);

			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Map<String, Object> it_usermanager = doc;
				user_list.add(it_usermanager);
			}

			long count1 = mu.count("user", null);

			int page_num = 1;
			int page_count = 0;
			int count = (int) count1;
			if (count % 50 == 0) {
				page_count = count / 50;
			} else {
				page_count = (count / 50) + 1;
			}

			model.put("course_order", user_list);
			model.put("count", count);
			model.put("page_count", page_count);
			model.put("page_num", page_num);

			return "/wx/userManager/my_packge";
		}
		
		@RequestMapping("show_congif_s")
		public void show_congif_s(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			
			String  open_id=req.getParameter("open_id");
			if(open_id==null||open_id.equals("")){
				
				model.put("result", "error");
				
			}else{
				
				Document config_s=cgbdb.query_config();
				
				model.put("config_s", config_s);								
			}
			
			sendjson(model, response);
		}
				
		
		@RequestMapping("updateuser")
		public void updateuser(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			
			 
			String updatename=req.getParameter("updatename");
			String updatepasswd=req.getParameter("updatepasswd");
			 String id=req.getParameter("id");
			
				Document phone=new Document();
				
				phone.append("name", updatename);
				int count=cgbdb.countUserm("user_m", phone);
				if(count>0){
						model.put("result", "errname");
				}else{
					Document u_no=new Document();
					u_no.append("_id", new ObjectId(id));
					Document 	updateu=cgbdb.find_scholarship22("user_m", u_no);
					if(updateu!=null){					
						updateu.put("name", updatename);
						updateu.put("passwd", updatepasswd);
						updateu.put("state",updateu.getInteger("state") );
						cgbdb.replaceOne("user_m", Filters.eq("_id", updateu.get("_id")), updateu);
							model.put("result", "success");
					}else{
						model.put("result", "error");
					}
				}	
				sendjson(model, response);	
		}
		
		@RequestMapping("userinfo")
		public String userdetaiinfo(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model) {
			String u_no = req.getParameter("no");
			String classmate_id = req.getParameter("c_id");
			String open_id=req.getParameter("open_id");
				Document  docc=new Document();
				Document  docc2=new Document();
				docc.put("top_openid", open_id);
				
				int count=cgbdb.count_topid("user", docc);
				docc2.put("open_id", open_id);
				
				Document user=cgbdb.findwxfamilyinfo("user", docc2);
					int i=0; //代表上级    
					int j=1;//代表自己
				if(user.getString("top_openid")!=null){
					i=1;
				}
				
				user.put("classmatecircle", count+i+j);
				 
				
				cgbdb.replaceOne("user", Filters.eq("open_id", open_id), user);
				
				
					Document vno=new Document();
						vno.put("open_id", open_id);
						vno.put("is_order", 1);
						 
				
				Document  vip_log=cgbdb.find_scholarship22("vip_order", vno);
					
					if(vip_log!=null){
						
						model.put("vip_log", vip_log);
					}
				
				
			Document map = new Document();
			if (u_no != null && !u_no.equals("")) {
				map.append("u_no", u_no);
				List<Document> list = cgbdb.findudetailinfo("user", map); // 按会员编号查询所有信息
				
				if (list != null && list.size() > 0) {
					model.put("userinfo", list.get(0));
				}
			}
			
				Document u_order=new Document();
					u_order.put("open_id", open_id);
					u_order.put("is_order", 1);
					u_order.put("is_over", 0);
					List<Document> order= cgbdb.course_orders(u_order);// 按会员编号查询所有订单信息
					
					if (order != null && order.size() > 0) {
						model.put("orderinfo", order);
					}
					
					int top_count =0;
					  
					Map<String,Object> top_map =  topdb.query_my_top(open_id);//我的上级
							
						
							Document shangj_ip=new Document();
							Document top=(Document) top_map.get("top");
							if(top!=null){
								top_count =1;
								shangj_ip.put("open_id", top.get("open_id"));
								
								Document shangji_map=cgbdb.findwxfamilyinfo("user", shangj_ip);
							
								if(shangji_map!=null){
									
									model.put("shangji_map", shangji_map);
								}
								
							}
							
							
						
					
						
					 
					List<Document> xia_ji=topdb.query_my_xia(open_id);//我的下级
					List<Document> xiaji_list=new ArrayList<Document>();
					
						if(xia_ji.size()>0){
							
							Document xiaji_user=null;
							for(int z=0;z<xia_ji.size();z++){
								
							
							Document xiaji_op=new Document();
							xiaji_op.put("open_id", xia_ji.get(z).getString("open_id"));
							
							  xiaji_user=cgbdb.findwxfamilyinfo("user", xiaji_op);
							  xiaji_list.add(xiaji_user);
								
							}model.put("xiaji_user", xiaji_list);
						}
					
					
				//Map<String,Object> list=new HashMap<String, Object>();
			
					
					model.put("xia_ji", xia_ji);
					model.put("user", user);
				  
				// model.put("list", list);
				 model.put("user", user);
				 
				 model.put("c_num", xia_ji.size()+top_count+1);
			

			return "/pt/userManager/userinfo";
		}
		
		@RequestMapping("insertP2")
		public void insertP2(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String date = req.getParameter("date");
			String open_id=req.getParameter("open_id");
			
			int i=Integer.valueOf(date);
			
				Document doc=new Document();
				  
					doc.put("open_id", open_id);
				Document user =cgbdb.findwxfamilyinfo("user", doc);	
						if(user!=null){
							user.put("p2_date", DateTool.addDate(user.getString("p1_date"), i));
							user.put("p1_date",DateTool.addDate(user.getString("p1_date"), i));
						
							cgbdb.replaceOne("user", Filters.eq("open_id", open_id), user);
						}
					
					model.put("result", "success");
				 
			 
			
			sendjson(model, response);
			
				
		}
		
		@RequestMapping("user_m")
		public String user_m(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
					/*Document map = new Document();
					PageBean<Document> pb = new PageBean<Document>();
					String page = req.getParameter("page");
					String name = req.getParameter("name");

					if (page != null && !page.equals("")) {
						pn = Integer.valueOf(page);
					}
					if (pn < 1) {
						pn = 1;
					}

					map.append("pn", pn);
					map.append("ps", ps);
					if (name != null && !name.equals("")) {
						map.append("name", name);
					}

					pb.setPageNum(map.getInteger("pn", pn));// 第几页
					pb.setPageSize(map.getInteger("ps", ps));// 每页多少

					int rc = (int) cgbdb.countUserm("user_m", map);
					pb.setRowCount(rc);

					List<Document> list = cgbdb.findByPb2("user_m", map);

					if (list != null && list.size() > 0) {
						pb.setList(list);
					}

					model.put("user_list", pb);*/
			
			List<Document> list = cgbdb.query_manager();
			
			model.put("user_list", list);
			
			
			return "/pt/userManager/user_m";
		}
		
		@RequestMapping(value = "insertuser", method = { RequestMethod.POST }) 
		public void insertuser(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model) {

			String name = req.getParameter("name");
			String passwd = req.getParameter("passwd"); 
			//String ustate = req.getParameter("ustate");
			 
			Map<String, String> map = new HashMap<String, String>();
			MongoUtil mu = MongoUtil.getThreadInstance();

			if (name == null || name.equals("")
					|| mu.count("user_m", new Document().append("name", name)) > 0) {
				map.put("result", " errname");

			} else {

				BasicDBObject bdb2 = new BasicDBObject();
				bdb2.put("name", name);
				bdb2.put("passwd", passwd);
				bdb2.put("password", passwd);
				bdb2.put("state", 0);
				bdb2.put("role_id", 1);
				mu.insertOne("user_m", bdb2);
				map.put("result", "success");

			}

			sendjson(map, response);

		}
		
		@RequestMapping("resetpwd")
		public void resetpwd(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			
			 
			String name=req.getParameter("inputname");
			String passwd=req.getParameter("pwd");
			 
			
				Document phone=new Document();
				
				phone.append("name", name);
				int count=cgbdb.countUserm("user_m", phone);
				if(count>0){
					Document u_no=new Document();
					u_no.append("name", name);
					Document 	updateu=cgbdb.finduserm("user_m", u_no);
					if(updateu!=null){					
						updateu.put("name", name);
						updateu.put("passwd", passwd);
						cgbdb.replaceOne("user_m", Filters.eq("name", updateu.getString("name")), updateu);
							model.put("result", "success");
					}else{
						model.put("result", "error");
					}
					
					
				}else{
					model.put("result", "errname");
				}	
				sendjson(model, response);	
		}
		
		
		@RequestMapping("deletebyname")	 
		public void deletebyname(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model) {
			String name = req.getParameter("name");
				if(name!=null&&!name.equals("")){
					MongoUtil mu = MongoUtil.getThreadInstance();
					Document doc = new Document();
					doc.put("name", name);
					mu.deleteMany("user_m", doc);
				

					model.put("result", "success");
					
				}else{
					model.put("result", "error");
					
				}
					sendjson(model, response);
		}
		
		@RequestMapping("blacklist1")
		public void blacklist1(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model ){
				
				String u_no =req.getParameter("u_no");
				String state=req.getParameter("state");
				Document doc=new Document();
				
				if(u_no==null||u_no.equals("")){
						model.put("result", "error");
				}else{
					doc.append("u_no", u_no);
					
					Document user=cgbdb.finduser("user",doc);
					if(user!=null ){
						
						if(state.equals("0")){
							user.put("state", -1);
						}else{
							user.put("state", 0);
						}
						
						cgbdb.replaceOne("user", Filters.eq("u_no", user.get("u_no")), user);
						
						model.put("result", "success");
					}
					
				}
				sendjson(model, response);			
		}
		
			@RequestMapping("freeze1")
	public void freeze1(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model ){
				
				String name =req.getParameter("name");
				String state=req.getParameter("state");
				Document doc=new Document();
				
				if(name==null||name.equals("")){
						model.put("result", "error");
				}else{
					doc.append("name", name);
					
					Document user=cgbdb.findbobyinfo("user_m",doc);
					if(user!=null ){
						
						if(state.equals("0")){
							user.put("state", -1);
						}else{
							user.put("state", 0);
						}
						
						cgbdb.replaceOne("user_m", Filters.eq("name", user.get("name")), user);
						
						model.put("result", "success");
					}
					
				}
				sendjson(model, response);			
		}
		
			@RequestMapping("join_vip")
			public void join_vip(HttpServletRequest req,
					HttpServletResponse response, Map<String, Object> model){
				String open_id=req.getParameter("open_id");
				String name=req.getParameter("name");
				String phone=req.getParameter("phone");
				String company=req.getParameter("company");
				String job=req.getParameter("job");
				 
				
					Document openid=new Document();
					
					openid.append("open_id", open_id);
					Document   count=cgbdb.findwxfamilyinfo("user", openid);
					
					model.put("count", count);
					if(count==null||count.equals("")){
							model.put("result", "error");
					}else{		 			
						count.put("name", name);
						count.put("phone", phone);
						count.put("company",company );
						count.put("job",job );
							cgbdb.replaceOne("user", Filters.eq("open_id", count.getString("open_id")), count);
								model.put("result", "success");
					 
					}	
					sendjson(model, response);	
			}		
			
			
			@RequestMapping("shcolarship_info")
			public void shcolarship_info(HttpServletRequest req,
					HttpServletResponse response, Map<String, Object> model){
					String open_id=req.getParameter("open_id");
					String page = req.getParameter("page");
					PageBean<Document> pb = new PageBean<Document>();
					 	int pn = 1;
					   int ps = 10;
					Document map=new Document();
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
		
					
					Document doc=new Document();
					
					doc.append("open_id", open_id);
					
					Document user=	cgbdb.findwxfamilyinfo("user", doc);//查询用户表
					Document config_s=cgbdb.query_config();
				
				if(user!=null&&!user.equals("")){
					 
					model.put("user", user);
					
					 
					List<Document> xia_ji=topdb.query_my_xia(open_id);//我的下级
					
					Document all_open_id=new Document();//用来存 数组中openid
						if(xia_ji!=null ){
							List<String> list=new ArrayList<String>();
				 	
			  
							for(int i=0;i<xia_ji.size();i++){
							
								
							list.add(xia_ji.get(i).getString("open_id"));
							
							Map<String, Object> map3 = new HashMap<String, Object>();
							map3.put("$in", list);
							
							all_open_id.put("open_id", map3);
							
							List<Document> course_order=cgbdb.course_orders(all_open_id);//用同学圈中的openid去查询课程订单表中的数据
							model.put("course_order", course_order);
						
					}							 
							model.put("bottom_member", xia_ji);
							 
						}
				}
				model.put("config_s", config_s);
				sendjson(model, response);
				
			}		
			
			@RequestMapping("scholarship_sort")
			public void scholarship_sort(HttpServletRequest req,
					HttpServletResponse response, Map<String, Object> model){
						Document map = new Document();
					
						String open_id=req.getParameter("open_id");
						PageBean<Document> pb = new PageBean<Document>();
						String page = req.getParameter("page");
						String phone = req.getParameter("phone");

						if (page != null && !page.equals("")) {
							pn = Integer.valueOf(page);
						}
						if (pn < 1) {
							pn = 1;
						}

						map.append("pn", pn);
						map.append("ps", ps);
						if (phone != null && !phone.equals("")) {
							map.append("phone", phone);
						}

						pb.setPageNum(map.getInteger("pn", pn));// 第几页
						pb.setPageSize(map.getInteger("ps", ps));// 每页多少

						int rc = (int) cgbdb.countUser("scholarship", map);
						pb.setRowCount(rc);

						
						Document openid=new Document();
						if(open_id!=null&&!open_id.equals("")){
							openid.append("open_id", open_id);
							Document scholarship=cgbdb.find_scholarship("scholarship",openid);
							
							model.put("scholarship", scholarship);
							
						}		
				 
						  
				
						List<Document> list = cgbdb.find_scholarship_list("scholarship", map);
						 pb.setList(list);

						model.put("scholarship_list", pb);
				
				
					 sendjson(model, response);
		}
			
		@RequestMapping("user_sign")
		public void user_sgin(HttpServletRequest req,
	HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			Document doc =new Document();
			doc.put("open_id", open_id);
			doc.put("type", 0);
			doc.put("date",DateTool.fromDateY());

		int sign_log=cgbdb.find_sign_log("sign_log", doc);
		
		
		
		
		Document is_sign=new Document();
			is_sign.append("open_id", open_id);	
		
		
			Document vip_log=area_db.find_vip_order("vip_order", is_sign);
			
			model.put("vip_log", vip_log);
			
			if(sign_log!=0){
						
				Document user =cgbdb.findwxfamilyinfo("user", is_sign);
				model.put("user", user);
				model.put("sign_log", sign_log);
				
			}else{
				Document user =cgbdb.findwxfamilyinfo("user", is_sign);
				model.put("user", user);
				model.put("sign_log", sign_log);
			}
			sendjson(model, response);			
	}	
		
		@RequestMapping("go_sign")
	public void go_sign(HttpServletRequest req,
			HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			Document doc =new Document();
			doc.put("open_id", open_id);
			doc.put("date",DateTool.get_yesterday());
			
			Document dc=new Document();	
			 dc.append("open_id", open_id);
			Document user =cgbdb.findwxfamilyinfo("user", dc);	
			Document config_s=cgbdb.query_config();
		int sign_log=cgbdb.find_sign_log("sign_log", doc);	
			if(sign_log==0){//说明他昨天没有签到 那么今天就是已签到1天
				MongoUtil mu = MongoUtil.getThreadInstance();
				Document da=new Document();
				da.put("open_id", open_id);
				da.put("date", DateTool.fromDateY());
				da.put("score", config_s.getInteger("sign_score"));
				da.put("name", user.getString("name"));
				da.put("type", 0);
				mu.insertOne("sign_log", da);
				
		  
			 	if(user!=null){
			 		int a=1;
			 		user.put("is_sign", 1);
			 		user.put("score", user.getInteger("score")+a);
			 cgbdb.replaceOne("user", Filters.eq("open_id", user.getString("open_id")), user);
			 
			 model.put("user", user);	
	 	}		
	}else if(sign_log%7==0){
				MongoUtil mu = MongoUtil.getThreadInstance();
				int i=5;
				Document da=new Document();
				da.put("open_id", open_id);
				da.put("date", DateTool.fromDateY());
				da.put("score", config_s.getInteger("sign_score"));
				da.put("name", user.getString("name"));
				da.put("type", 0);
				mu.insertOne("sign_log", da);
	  
			 	if(user!=null){
			 		user.put("is_sign", user.getInteger("is_sign")+i);
			 		user.put("score", user.getInteger("score")+i);
			 cgbdb.replaceOne("user", Filters.eq("open_id", user.getString("open_id")), user);
			 
			 model.put("user", user);	
					}
	}else if(sign_log%30==0){
			
		MongoUtil mu = MongoUtil.getThreadInstance();
		int i=10;
		Document da=new Document();
		da.put("open_id", open_id);
		da.put("date", DateTool.fromDateY());
		da.put("score", config_s.getInteger("sign_score"));
		da.put("name", user.getString("name"));
		da.put("type", 0);
		mu.insertOne("sign_log", da);

	 	if(user!=null){
	 		user.put("is_sign", user.getInteger("is_sign")+i);
	 		user.put("score", user.getInteger("score")+i);
	 cgbdb.replaceOne("user", Filters.eq("open_id", user.getString("open_id")), user);
	 
	 model.put("user", user);	
			}
		
	}else{
			MongoUtil mu = MongoUtil.getThreadInstance();
				int i=1;
			Document da=new Document();
			da.put("open_id", open_id);
			da.put("date", DateTool.fromDateY());
			da.put("score", config_s.getInteger("sign_score"));
			da.put("name", user.getString("name"));
			da.put("type", 0);
			mu.insertOne("sign_log", da);
  
		 	if(user!=null){
		 		user.put("is_sign", user.getInteger("is_sign")+i);
		 		user.put("score", user.getInteger("score")+i);
		 cgbdb.replaceOne("user", Filters.eq("open_id", user.getString("open_id")), user);
		 
		 model.put("user", user);	
				}
		}	
			
			sendjson(model, response);
}
		@RequestMapping("show_my_classmate")
		public void show_my_classmate(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			
			Document doc =new Document();
			doc.put("open_id", open_id);
		 
			Map<String,Object> top_map =  topdb.query_my_top(open_id);//我的上级
			 
			Document user = cgbdb.find_scholarship("user",doc); 
			
		 
			List<Document> xia_ji=topdb.query_my_xia(open_id);//我的下级
			
			int page = 1; 
			if(req.getParameter("page") != null){
				page = Integer.valueOf(req.getParameter("page"));
			}
			
			int limit = 20; 
			int skip = (page - 1)*limit;
			 
			int pt_num = top_db.query_xia_count(open_id, 0); 
			int vip_num = top_db.query_xia_count(open_id, 1);
			
			int count = top_db.query_scholship_log_count(open_id);
			
			List<Document> log_list = top_db.query_scholship_log(open_id, skip, limit);
			
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
			
			 
			model.put("pt_num", pt_num);
			model.put("vip_num", vip_num);
			
			model.put("log_list", log_list);
			
			
		 model.put("top_map", top_map);
		 model.put("xia_ji", xia_ji);
		 model.put("user", user);
			sendjson(model, response);
		}
		
		@RequestMapping("score_sort")
		public void score_sort(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
					Document map = new Document();
				
					String open_id=req.getParameter("open_id");
					
					Document openid=new Document();
					if(open_id!=null&&!open_id.equals("")){
						openid.append("open_id", open_id);
						Document user=cgbdb.find_scholarship("user",openid);
						
						model.put("user", user);
						
					}		
			 
					 
			
					List<Document> list = cgbdb.find_score_list("user", map);

					model.put("score_list", list);
			
			
				 sendjson(model, response);
	}
		
		@RequestMapping("show_my_info")
		public void show_my_info(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			Document doc =new Document();
			doc.put("open_id", open_id);
			
		Document user =cgbdb.findwxfamilyinfo("user", doc);
				 if(user!=null&&!user.equals("")){
					 model.put("result", "success");
					 model.put("user",user);
					 
				 }else{
					 
					 model.put("result", "error");//用户不存在
				 }
			 
			
			sendjson(model, response);
		}
		
		@RequestMapping("update_my_info")
		public void update_my_info(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			 
			String phone=req.getParameter("phone");
			String company=req.getParameter("company");
			String job=req.getParameter("job");
			String name=req.getParameter("name");
			String  yzm_cc=req.getParameter("yzm_cc");
			
				String str=getSession().getAttribute("yzphone").toString();
				String str2=getSession().getAttribute("yzcode").toString();
				
				Document docc=new Document();
				docc.put("phone", phone);
				
				int cont=cgbdb.count_course_order("user", docc);
				
					if(cont>1){
						
						model.put("result", "have_phone");
					}else{
						
						if(!phone.equals(str) || !yzm_cc.equals(str2)){
							
							model.put("result", "e_phone");
						
						}else{
							
							Document openid=new Document();
							
							openid.append("open_id", open_id);
							Document   count=cgbdb.findwxfamilyinfo("user", openid);
							if(count==null||count.equals("")){
									model.put("result", "error");
							}else{		 			
							 
								count.put("phone", phone);
								count.put("company",company );
								count.put("job",job );
								count.put("u_name",name );
								cgbdb.replaceOne("user", Filters.eq("open_id", count.getString("open_id")), count);
								model.put("result", "success");
							 
							}	
							
						}
					}
				
			 	 	
				sendjson(model, response);	
		}		
		
		@RequestMapping("show_course_order")
		public void show_course_order(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			Document doc =new Document();
			
			doc.put("open_id", open_id);
			
			List<Document> course_order=cgbdb.show_course_order("course_order", doc);
			
			Document user  =cgbdb.findwxfamilyinfo("user", doc);
			if(course_order!=null&&!course_order.equals("")){
				
				model.put("course_order", course_order);
			}
			model.put("user", user);
			sendjson(model, response);
			
		}
		
		
		@RequestMapping("show_course_order_jsp")
		public String  show_course_order_jsp(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			
			String page = req.getParameter("page");
			
			PageBean<Document> pb = new PageBean<Document>();
			Document map=new Document();
			
			
			
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
			int rc = (int) cgbdb.countUser("course_order", map);
			pb.setRowCount(rc);
			Document doc =new Document();
			
			doc.put("open_id", open_id);
			
			List<Document> course_order=cgbdb.show_course_order("course_order", doc);
			
			pb.setList(course_order);
		
				
			model.put("course_order", pb);
			 
			
			return "/pt/userManager/my_packge";
			
		}
		
		@RequestMapping("show_scholaership_jsp")
		public String  show_scholaership_jsp(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			
			 
			int page = 1; 
			if(req.getParameter("page") != null){
				page = Integer.valueOf(req.getParameter("page"));
			}
			int limit = 50; 
			int skip = (page - 1)*limit;
				
			int count = top_db.query_scholship_log_count(open_id);
			
			List<Document> log_list = top_db.query_scholship_log(open_id, skip, limit);

		 
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
			
			
			List<Document> list = cgbdb.query_share("share_log",open_id);
			
			
			List<Document> list_list=new ArrayList<Document>();
			
			if(list!=null){
				
				for(int i=0;i<list.size();i++){
					Document doc2=new Document();
					doc2.put("open_id", list.get(i).get("share_openid").toString());
					
					Document user=cgbdb.findwxfamilyinfo("user", doc2);
					list_list.add(user);
				}
				
			}
			List<Document> log_list2 = top_db.query_scholship_log(open_id, skip, limit);
			model.put("count", count);
			model.put("page_num", page_num);
			model.put("page_count", page_count);
			model.put("list", list_list);
			model.put("list_2", list);
			model.put("log_list2", log_list2);
			model.put("log_list", log_list);
			 
			 
			return "/pt/userManager/my_scholarship_record";
			
		}
		@RequestMapping("show_score_jsp")
		public String  show_score_jsp(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			String open_id=req.getParameter("open_id");
			
			String page = req.getParameter("page");
			
			PageBean<Document> pb = new PageBean<Document>();
			Document map=new Document();
			
			
			
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
				
			Document doc =new Document();
			
			doc.put("open_id", open_id);
			List<Document> score=cgbdb.show_course_order("sign_log", doc);//查询签到表
			int rc= cgbdb.count_collection("sign_log", doc);
			pb.setRowCount(rc);

			pb.setList(score); 
		
				
			model.put("course_order", pb);
			 
			 
			return "/pt/userManager/my_score";
			
		}
		
		@RequestMapping("sendmessage_phone")
		public  void sendmessage_phone(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model){
			
			String phone=req.getParameter("phone");
			
			String phone_url="http://106.wx96.com/webservice/sms.php";
			
			int random=CommonUtil.random_code();
			
				
			
			String str=sendGet(phone_url, "method=Submit&account=C12005726&password=3ba4310932cb5bd74aaba61a5c2717db&mobile="+phone+"&content=您的验证码是："+random+"。请不要把验证码泄露给其他人。");
			
			System.out.println(str);
			
			req.getSession().setAttribute("yzphone", phone);
			req.getSession().setAttribute("yzcode", random);
			
			model.put("random", random);
			
			sendjson(model, response);
			
		}
		
		
		
		
		 public static String sendGet(String url, String param) {
		        String result = "";
		        BufferedReader in = null;
		        try {
		            String urlNameString = url + "?" + param;
		            URL realUrl = new URL(urlNameString);
		            // 打开和URL之间的连接
		            URLConnection connection = realUrl.openConnection();
		            // 设置通用的请求属性
		            connection.setRequestProperty("accept", "*/*");
		            connection.setRequestProperty("connection", "Keep-Alive");
		            connection.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		            // 建立实际的连接
		            connection.connect();
		            // 获取所有响应头字段
		            Map<String, List<String>> map = connection.getHeaderFields();
		            // 遍历所有的响应头字段
		            for (String key : map.keySet()) {
		                System.out.println(key + "--->" + map.get(key));
		            }
		            // 定义 BufferedReader输入流来读取URL的响应
		            in = new BufferedReader(new InputStreamReader(
		                    connection.getInputStream()));
		            String line;
		            while ((line = in.readLine()) != null) {
		                result += line;
		            }
		        } catch (Exception e) {
		            System.out.println("发送GET请求出现异常！" + e);
		            e.printStackTrace();
		        }
		        // 使用finally块来关闭输入流
		        finally {
		            try {
		                if (in != null) {
		                    in.close();
		                }
		            } catch (Exception e2) {
		                e2.printStackTrace();
		            }
		        }
		        return result;
		    }
		 
		@RequestMapping("to_load") 
		 public void to_load(HttpServletRequest req,
					HttpServletResponse response, Map<String, Object> model){
			 
			String open_id = req.getParameter("openid");
			String c_no = req.getParameter("c_no");
			Document doc2 = new Document();
			doc2.append("open_id", open_id);
			Document user = cgbdb.findwxfamilyinfo("user", doc2);// 查询用户表
			
			int dy = top_db.query_is_buy(open_id, c_no); // 是否订阅
			
			int sc = top_db.query_is_collection(open_id, c_no); // 是否收藏
			
			
			Document  doc3=new Document();
			doc3.put("special_no", c_no);
			Document special = cgbdb.findspecial_detail("special", doc3);
			model.put("dy", dy);
			model.put("sc", sc);
			model.put("user", user);
			model.put("special", special);
			sendjson(model, response);
			 
		 }
		
		@RequestMapping("shows_wx_user")
		public void shows_wx_user(HttpServletRequest req,
				HttpServletResponse response, Map<String, Object> model) {
			String open_id=req.getParameter("openid");
				Document  docc2=new Document();				
				docc2.put("open_id", open_id);				
				Document user=cgbdb.findwxfamilyinfo("user", docc2);
				model.put("user", user);
				sendjson(model, response);
		}
		
}
