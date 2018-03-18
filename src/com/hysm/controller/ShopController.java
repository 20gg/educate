/**
 * 
 */
package com.hysm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hysm.db.CbgDB;
import com.hysm.tools.PageBean;
import com.mongodb.client.model.Filters;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年5月5日
 */

@Controller
@RequestMapping("shop")
public class ShopController extends BaseController {
	
private int pn = 1;
private int ps = 50;
				
	@Autowired
private CbgDB cgbdb;
	
		
	@RequestMapping("show_shop")
		public String show_shop(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
			Document map = new Document();
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

			int rc = (int) cgbdb.countUser("shop", map);
			pb.setRowCount(rc);

			List<Document> list = cgbdb.findByPb("shop", map);

			if (list != null && list.size() > 0) {
				pb.setList(list);
			}

			model.put("shop_list", pb);
	
	
			return "/pt/shop/shopManager";
		}
				
	@RequestMapping("freeze1")
	public void freeze1(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model ){
				
				String sku_id =req.getParameter("sku_id");
				String state=req.getParameter("state");
				Document doc=new Document();
				
				if(sku_id==null||sku_id.equals("")){
						model.put("result", "error");
				}else{
					doc.append("sku_id", sku_id);
					
					Document user=cgbdb.findsku_id("shop",doc);
					if(user!=null ){
						
						if(state.equals("1")){
							user.put("state", -1);
						}else{
							user.put("state", 1);
						}
						
						cgbdb.replaceOne("shop", Filters.eq("sku_id", user.get("sku_id")), user);
						
						model.put("result", "success");
					}
					
				}
				sendjson(model, response);			
		}
	
	@RequestMapping("query_count_table")
	public void query_count_table(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		Document map=new Document();
		
		int pn =1;
		int ps=10;
		
		map.put("pn", pn);
		map.put("ps", ps);
		map.put("type", 1);
		List<Document> user=cgbdb.find_user_list();//查询用户表
		 List<Document> course=cgbdb.find_course_list(map);//查询所有课程  
		 
		 List<Document> course_order=cgbdb.course_orders(null);//查询订单表
		
		 model.put("user", user);
		 model.put("course", course);
		 model.put("course_order", course_order);
		 
		sendjson(model, response);
	}
				
}
