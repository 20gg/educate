/**
 * 
 */
package com.hysm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

 


import com.hysm.db.Tj_db;
import com.hysm.tools.DateTool;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年6月19日
 */


@Controller
@RequestMapping("tongji")
public class TongjiController extends BaseController{
		
	@Autowired
	private Tj_db tj;
	
	@RequestMapping("show_today")
	public void show_today(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		
		String date =req.getParameter("date");
		
		int type=0;
		 
		Document doc=new Document();
		
		Document doc1=new Document();
		Document doc2=new Document();
		
		Document newdate=new Document();
		Document    order_date=new Document();
		
		 Date start =null;
		 Date end=null;
		 Date time,time1,time2,time3,time4=null;
		 Map<String,Object> map2 = new HashMap<String, Object>();
		 Map<String,Object> map3 = new HashMap<String, Object>();
		 Map<String,Object> map4 = new HashMap<String, Object>();
		 Map<String,Object> map5 = new HashMap<String, Object>();
		 Map<String,Object> map6 = new HashMap<String, Object>();
		 Map<String,Object> map7 = new HashMap<String, Object>();
		 Map<String,Object> map8 = new HashMap<String, Object>();
		 Map<String,Object> map9 = new HashMap<String, Object>();
		 Map<String,Object> map10 = new HashMap<String, Object>();
		 Map<String,Object> map11 = new HashMap<String, Object>();
		 Map<String,Object> map12 = new HashMap<String, Object>();
		 Map<String,Object> map13 = new HashMap<String, Object>();
		 
		 Map<String,Object> map14 = new HashMap<String, Object>();
		 Map<String,Object> map15 = new HashMap<String, Object>();
		 Map<String,Object> map16 = new HashMap<String, Object>();
		 Map<String,Object> map17 = new HashMap<String, Object>();
		 Map<String,Object> map18 = new HashMap<String, Object>();
		 Map<String,Object> map19 = new HashMap<String, Object>();
		
		 
		 
		 List<Integer> aa=new ArrayList<Integer>();
		List<Integer> dd=new ArrayList<Integer>();
		List<Integer> cc=new ArrayList<Integer>();
		List<Integer> ee=new ArrayList<Integer>();
		List<Integer>ff=new ArrayList<Integer>();
			
							
				 start = DateTool.get_the_date(date+" 00:00:00");
				 time=DateTool.get_the_date(date+" 04:00:00");
				 time1=DateTool.get_the_date(date+" 08:00:00");
				 time2=DateTool.get_the_date(date+" 12:00:00");
				 time3=DateTool.get_the_date(date+" 16:00:00");
				 time4=DateTool.get_the_date(date+" 20:00:00");
				 end = DateTool.get_the_date(date+" 23:59:59");
				 
				 if(type==0){
					 type=1;
					 map2.put("$gte", start); 
					    map2.put("$lt", time);
					 doc.put("create_date", map2);
					 int create_date=tj.find_today("user", doc);//一天中普通会员人数
					 dd.add(create_date);
					 
					 map3.put("$gte", time); 
					    map3.put("$lt", time1);
					 doc.put("create_date", map3);
					 int create_date_1=tj.find_today("user", doc);					
					 dd.add(create_date_1);
					 
					 map4.put("$gte", time1); 
					    map4.put("$lt", time2);
					 doc.put("create_date", map4);
					 int create_date_2=tj.find_today("user", doc);		 					  
					 dd.add(create_date_2);
					 
					 map5.put("$gte", time2); 
					    map5.put("$lt", time3);
					 doc.put("create_date", map5);
					 int create_date_3=tj.find_today("user", doc);						
					 dd.add(create_date_3);
					 
					 map6.put("$gte", time3); 
					    map6.put("$lt", time4);
					 doc.put("create_date", map6);
					 int create_date_4=tj.find_today("user", doc);		 
					 dd.add(create_date_4);
					 
					 map7.put("$gte", time4); 
					    map7.put("$lt", end);
					 doc.put("create_date", map7);
					 int create_date_5=tj.find_today("user", doc);						
					 dd.add(create_date_5);
					 
				 }
				
				  if(type==1){
					  type=2;
					  
					  map2.put("$gte", start); 
					    map2.put("$lt", time);
					    doc1.put("vip_date", map2);
					 int create_date=tj.find_today("user", doc1);//一天中成为全站VIP人数
					 
					 aa.add(create_date);
					 
					 map3.put("$gte", time); 
					    map3.put("$lt", time1);
					    doc1.put("vip_date", map3);
					 int create_date_1=tj.find_today("user", doc1);
					 aa.add(create_date_1);
					 
					 map4.put("$gte", time1); 
					    map4.put("$lt", time2);
					    doc1.put("vip_date", map4);
					 int create_date_2=tj.find_today("user", doc1);		 
					 aa.add(create_date_2);
					 
					 map5.put("$gte", time2); 
					    map5.put("$lt", time3);
					    doc1.put("vip_date", map5);
					 int create_date_3=tj.find_today("user", doc1);		 
					 aa.add(create_date_3);
					 
					 map6.put("$gte", time3); 
					    map6.put("$lt", time4);
					    doc1.put("vip_date", map6);
					 int create_date_4=tj.find_today("user", doc1);		 
					 aa.add(create_date_4);
					 
					 map7.put("$gte", time4); 
					    map7.put("$lt", end);
					    doc1.put("vip_date", map7);
					 int create_date_5=tj.find_today("user", doc1);	 
					 aa.add(create_date_5);
					  
					  
				  }
				 
			 if(type==2){
				  type=3;
				 map2.put("$gte", start); 
				    map2.put("$lt", time);
				    doc2.put("member_date", map2);
				 int create_date=tj.find_today("user", doc2);//一天中成为会员人数
				 
				 cc.add(create_date);
				 
				 map3.put("$gte", time); 
				    map3.put("$lt", time1);
				    doc2.put("member_date", map3);
				 int create_date_1=tj.find_today("user", doc2);//一天中成为会员人数
				 cc.add(create_date_1);
				 
				 map4.put("$gte", time1); 
				    map4.put("$lt", time2);
				    doc2.put("member_date", map4);
				 int create_date_2=tj.find_today("user", doc2);//一天中成为会员人数			 
				 cc.add(create_date_2);
				 
				 map5.put("$gte", time2); 
				    map5.put("$lt", time3);
				    doc2.put("member_date", map5);
				 int create_date_3=tj.find_today("user", doc2);//一天中成为会员人数			 
				 cc.add(create_date_3);
				 
				 map6.put("$gte", time3); 
				    map6.put("$lt", time4);
				    doc2.put("member_date", map6);
				 int create_date_4=tj.find_today("user", doc2);//一天中成为会员人数				 
				 cc.add(create_date_4);
				 
				 map7.put("$gte", time4); 
				    map7.put("$lt", end);
				    doc2.put("member_date", map7);
				 int create_date_5=tj.find_today("user", doc2);		 
				 cc.add(create_date_5);
				 
			 }
			 
			 if(type==3){
				 type=4;
				 map8.put("$gte", start); 
				    map8.put("$lt", time);
				 newdate.put("new_date", map8);
				 int new_date=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date);
				 
				 
				 map9.put("$gte", time); 
				    map9.put("$lt", time1);
				 newdate.put("new_date", map9);
				 int new_date_2=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date_2);
				 
				 
				 map10.put("$gte", time1); 
				    map10.put("$lt", time2);
				 newdate.put("new_date", map10);
				 int new_date_3=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date_3);
				 
				 map11.put("$gte", time2); 
				    map11.put("$lt", time3);
				 newdate.put("new_date", map11);
				 int new_date_4=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date_4);
				 
				 map12.put("$gte", time3); 
				    map12.put("$lt", time4);
				 newdate.put("new_date", map12);
				 int new_date_5=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date_5);
				 
				 map13.put("$gte", time4); 
				    map13.put("$lt", end);
				 newdate.put("new_date", map13);
				 int new_date_6=tj.find_today("course", newdate);//一天中课程
				 ee.add(new_date_6);
			 }
			 
			 if(type==4){
				 type=5;
				 map14.put("$gte", start); 
				    map14.put("$lt", time);
				    order_date.put("date", map14);
				 int new_date=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date);
				 
				 
				 map15.put("$gte", time); 
				    map15.put("$lt", time1);
				    order_date.put("date", map15);
				 int new_date_2=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date_2);
				 
				 
				 map16.put("$gte", time1); 
				    map16.put("$lt", time2);
				    order_date.put("date", map16);
				 int new_date_3=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date_3);
				 
				 map17.put("$gte", time2); 
				    map17.put("$lt", time3);
				    order_date.put("date", map17);
				 int new_date_4=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date_4);
				 
				 map18.put("$gte", time3); 
				    map18.put("$lt", time4);
				    order_date.put("order_date", map18);
				 int new_date_5=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date_5);
				 
				 map19.put("$gte", time4); 
				    map19.put("$lt", end);
				    order_date.put("new_date", map19);
				 int new_date_6=tj.find_today("course_order", order_date);//一天中课程
				 ff.add(new_date_6);
				 
			 }
			
			
		
			model.put("create_date", dd);
			model.put("vip_date", aa);
			model.put("member_date", cc);
			model.put("course", ee);
			model.put("course_order", ff);
			sendjson(model, response);
			
		}
		
		 
	@RequestMapping("show_month")
 public void show_month(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		String date=req.getParameter("date");
		
		Date start=null;		
		Date end=null;
		Document doc=new Document();
		 
		Document doc1=new Document();
		Document doc2=new Document();
		Document doc3=new Document();
		Document doc4=new Document();
	 
		
		
		 String[] sr =date.split("-");
		// String date1="";
		/* if(date!=null&&!date.equals("")){
			 date1=date.substring(0,7);
			 
		 }*/
		
		List<Integer> list=new ArrayList<Integer>();
		List<Integer> dd=new ArrayList<Integer>();
		List<Integer> aa=new ArrayList<Integer>();
		List<Integer> cc=new ArrayList<Integer>();
		List<Integer> ee=new ArrayList<Integer>();
		List<Integer> fff=new ArrayList<Integer>();
		
		List<Integer> g=new ArrayList<Integer>();
		
		 
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		Map<String,Object> map3 = new HashMap<String, Object>();
		Map<String,Object> map4 = new HashMap<String, Object>();
		Map<String,Object> map5 = new HashMap<String, Object>();
		Map<String,Object> map6 = new HashMap<String, Object>();
		 
		 
		
		 for (int i=0;i<sr.length;i++){ 
			 int a=Integer.valueOf(sr[i]);
			 list.add(a);
		 }
		 	
		 
		 int year=list.get(0);
		 int month=list.get(1);
		  
	 int size=DateTool.getLastDayOfMonth(year, month);
	 int create_date=0,vip_date=0,member_date=0, course=0,course_order=0;
	 
	 	for (int z=1;z<=size;z++){
	 		 
	 		g.add(z);
	 		
	 		String str_day ="";
	 		if(z< 10){
	 			str_day ="-0"+z;
	 		}else{
	 			str_day ="-"+z;
	 		}
	 			
	 			 start = DateTool.get_the_date(date+str_day+" 00:00:00");
	 			end = DateTool.get_the_date(date+str_day+" 23:59:59");
	 			map2.put("$gte", start); 
			    map2.put("$lt", end);
			 doc.put("create_date", map2);
			 create_date =tj.find_today("user", doc);//一天中普通会员人数
			 dd.add(create_date);
			 start = DateTool.get_the_date(date+str_day+" 00:00:00");
	 			end = DateTool.get_the_date(date+str_day+" 23:59:59");
	 			map3.put("$gte", start); 
			    map3.put("$lt", end);
			 doc1.put("vip_date", map3);
			 vip_date =tj.find_today("user", doc1);//一天中VIP会员人数
				aa.add(vip_date);
				
				start = DateTool.get_the_date(date+str_day+" 00:00:00");
	 			end = DateTool.get_the_date(date+str_day+" 23:59:59");
				map4.put("$gte", start); 
			    map4.put("$lt", end);
			 doc2.put("member_date", map4);
			 member_date =tj.find_today("user", doc2);//一天中会员人数
			 cc.add(member_date);
			 
			 start = DateTool.get_the_date(date+str_day+" 00:00:00");
	 			end = DateTool.get_the_date(date+str_day+" 23:59:59");
			 map5.put("$gte", start); 
			    map5.put("$lt", end);
			 doc3.put("new_date", map5);
			 course =tj.find_today("course", doc3);//一天中课程人数
			 ee.add(course);
			 
			 
			 start = DateTool.get_the_date(date+str_day+" 00:00:00");
	 			end = DateTool.get_the_date(date+str_day+" 23:59:59");
			 map6.put("$gte", start); 
			    map6.put("$lt", end);
			 doc4.put("new_date", map6);
			 course_order =tj.find_today("course_order", doc4);//一天中订单人数
			 fff.add(course_order);
	 		 
	 }
	  
		 
		 model.put("vip_date", aa);
		 model.put("create_date", dd);
		 model.put("member_date", cc);
		 model.put("course", ee);
		 model.put("course_order", fff);
		 model.put("size", g);
		 sendjson(model, response);
 }
	
	@RequestMapping("show_week")
	public void show_week(HttpServletRequest req,HttpServletResponse response, Map<String, Object> model){
		String date =req.getParameter("date");
		 String[] sr =date.split("-");
		 List<Integer> list=new ArrayList<Integer>();
		 
		 List<Integer> dd=new ArrayList<Integer>();
			List<Integer> aa=new ArrayList<Integer>();
			List<Integer> cc=new ArrayList<Integer>();
			List<Integer> ee=new ArrayList<Integer>();
			List<Integer> fff=new ArrayList<Integer>();
			
			List<Integer> g=new ArrayList<Integer>();
			
			 
			
			Map<String,Object> map0 = new HashMap<String, Object>();
			Map<String,Object> map3 = new HashMap<String, Object>();
			Map<String,Object> map4 = new HashMap<String, Object>();
			Map<String,Object> map5 = new HashMap<String, Object>();
			Map<String,Object> map6 = new HashMap<String, Object>();
			
			int create_date=0,vip_date=0,member_date=0, course=0,course_order=0;
			Date start=null;		
			Date end=null;
			Document doc=new Document();
			 
			Document doc1=new Document();
			Document doc2=new Document();
			Document doc3=new Document();
			Document doc4=new Document();
		 for (int j=0;j<sr.length;j++){ 
			 int a=Integer.valueOf(sr[j]);
			 list.add(a);
		 }
		 	
		 
		 int year=list.get(0);
		 int month=list.get(1);
		
		Map<String, Object> week=DateTool.get_month_week(year, month);
		
			List<Map>	map1= (List<Map>) week.get("min");
			List<Map>	map2= (List<Map>) week.get("max");
			
			for(int i=0;i<map1.size();i++){
				g.add(i);
				 start = DateTool.get_the_date(map1.get(i).get("date")+" 00:00:00");
		 			end = DateTool.get_the_date(map2.get(i).get("date")+" 23:59:59");
		 			
		 			map0.put("$gte", start); 
				    map0.put("$lt", end);
				 doc.put("create_date", map2);
				 create_date =tj.find_today("user", doc);//一天中普通会员人数
				 dd.add(create_date);
				 start = DateTool.get_the_date(map1.get(i).get("date")+" 00:00:00");
		 			end = DateTool.get_the_date(map2.get(i).get("date")+" 23:59:59");
		 			map3.put("$gte", start); 
				    map3.put("$lt", end);
				 doc1.put("vip_date", map3);
				 vip_date =tj.find_today("user", doc1);//一天中VIP会员人数
					aa.add(vip_date);
					
					start = DateTool.get_the_date(map1.get(i).get("date")+" 00:00:00");
		 			end = DateTool.get_the_date(map2.get(i).get("date")+" 23:59:59");
					map4.put("$gte", start); 
				    map4.put("$lt", end);
				 doc2.put("member_date", map4);
				 member_date =tj.find_today("user", doc2);//一天中会员人数
				 cc.add(member_date);
				 
				 start = DateTool.get_the_date(map1.get(i).get("date")+" 00:00:00");
		 			end = DateTool.get_the_date(map2.get(i).get("date")+" 23:59:59");
				 map5.put("$gte", start); 
				    map5.put("$lt", end);
				 doc3.put("new_date", map5);
				 course =tj.find_today("course", doc3);//一天中课程人数
				 ee.add(course);
				 
				 
				 start = DateTool.get_the_date(map1.get(i).get("date")+" 00:00:00");
		 			end = DateTool.get_the_date(map2.get(i).get("date")+" 23:59:59");
				 map6.put("$gte", start); 
				    map6.put("$lt", end);
				 doc4.put("new_date", map6);
				 course_order =tj.find_today("course_order", doc4);//一天中订单人数
				 fff.add(course_order);
				
				
			}
		
		
			 model.put("vip_date", aa);
			 model.put("create_date", dd);
			 model.put("member_date", cc);
			 model.put("course", ee);
			 model.put("course_order", fff);
			 model.put("size", g);
			 sendjson(model, response);
		
	}
	
}
