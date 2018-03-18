package com.hysm.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bson.Document; 
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.mongodb.client.model.Filters;

@Component
public class Pay_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	public static Lock lock1 = new ReentrantLock(); 
	
	public Document query_order(String order_id){
		
		Document doc = new Document();
		doc.put("order_id", order_id);
		
		Document order = mu.findOne("course_order", doc);
		
		return order;
	}
	
	public Document query_xf_order(String order_id){
		
		Document doc = new Document();
		doc.put("order_id", order_id);
		
		Document order = mu.findOne("xf_log", doc);
		
		return order;
	}
	
	public void update_xf_order(String order_id,Document doc){ 
		mu.replaceOne("xf_log", Filters.eq("order_id", order_id ), doc);
	}
	

	public void update_order(String order_id,Document doc){ 
		mu.replaceOne("course_order", Filters.eq("order_id", order_id ), doc);
	}
	
	
	public Document query_vip_order(String order_id){
		
		Document doc = new Document();
		doc.put("order_id", order_id);
		
		Document order = mu.findOne("vip_order", doc);
		
		return order;
	}
	
	public void update_vip(String order_id,Document doc){ 
		mu.replaceOne("vip_order", Filters.eq("order_id", order_id ), doc);
	}
	
	public void add_top_scholarship(String top_openid,int pay_money, int rebate,String openid,String name,String c_name){
		 
		lock1.lock(); 
		try{
			
			 Document doc = new Document();
			 doc.put("open_id", top_openid); 
			 Document top = mu.findOne("user", doc);
			 
			 //奖学金添加
			 int scholarship = top.getInteger("scholarship"); 
			 int count_scholarship = top.getInteger("count_scholarship"); 
			 
			 top.put("scholarship", scholarship+rebate); 
			 top.put("count_scholarship", count_scholarship+rebate); 
			 
			 mu.replaceOne("user", Filters.eq("open_id", top_openid ), top);
			 
			 //添加奖学金记录
			 Document s_log = new Document();
			 
			 double money=(Double.valueOf(rebate)/100);
			 
			 s_log.put("top_openid", top_openid);
			 s_log.put("c_name", "您邀请的学员"+name+"成功购买"+c_name+",点击<详情>立即查收");
			 s_log.put("open_id", openid);
			 s_log.put("name", name);
			 s_log.put("pay_money",pay_money);
			 s_log.put("rebate", rebate);
			 s_log.put("date", DateTool.fromDateY());
			 
			 mu.insertOne("scholship_log", s_log);
			 
			 //获得奖学金消息  模板消息
			 
			 Msg_db msg_db = new Msg_db();
			 
			 Map<String,Object> map = new HashMap<String, Object>();
				
			 map.put("msg_type", 3);
			 map.put("openid", top_openid);
			 map.put("is_add", 1);
			 map.put("cut", rebate);
			 map.put("note", "您邀请的学员"+name+"成功购买"+c_name+",点击<详情>立即查收");
			 
			 msg_db.add_msg(map); 
			 
		
		}finally{
			// 释放锁
			lock1.unlock();
		}
	}
	
	public Document query_pay_log(String order_id){
		
		Document doc = new Document();
		doc.put("order_id", order_id);
		
		Document order = mu.findOne("pay_log", doc);
		
		return order;
	}
	
	public void update_pay_log(String order_id,Document doc){ 
		mu.replaceOne("pay_log", Filters.eq("order_id", order_id ), doc);
	}
	
	public Document quert_config(){
		
		Document c = mu.findOne("config_s", null);
		
		return c;
	}
	
	public void update_user(String openid,Document doc){
		
		mu.replaceOne("user", Filters.eq("open_id", openid ), doc);
	}
	
	public Document quert_course(String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		Document c = mu.findOne("course", doc);
		
		return c;
	}
	
	public Document quert_special(String c_no){
		
		Document doc = new Document();
		doc.put("special_no", c_no);
		
		Document c = mu.findOne("special", doc);
		
		return c;
	}
	
	public int query_is_buy(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		
		doc.put("is_order", 1); //已经付款
		doc.put("is_over", 0);//未过期
		
		long c = mu.count("course_order", doc);
		
		return (int)c;
	}
	
	
public int phone_count(String phone){
		
		Document doc = new Document();
		doc.put("phone", phone);
		
		
		long c = mu.count("phone_list", doc);
		
		return (int)c;
	}
	
	
	public void res_scholarship(String openid,int cut,String info){
		
		 Document doc = new Document();
		 doc.put("open_id", openid); 
		 Document user = mu.findOne("user", doc);
		 
		//奖学金减少
		 int scholarship = user.getInteger("scholarship"); 
		 user.put("scholarship", scholarship - cut ); 
		 
		 mu.replaceOne("user", Filters.eq("open_id", openid ), user);
		 
		 //添加奖学金记录
		 Document s_log = new Document();
		 
		 double money=(Double.valueOf(cut)/100);
		 
		 s_log.put("top_openid", openid);
		 s_log.put("c_name", "您使用了奖学金（"+info+"），点击<详情>立即查看");
		 s_log.put("open_id", openid);
		 s_log.put("name", user.getString("name"));
		 s_log.put("pay_money",cut);
		 s_log.put("rebate", - cut);
		 s_log.put("date", DateTool.fromDateY());
		 
		 mu.insertOne("scholship_log", s_log);
		  
		 //获得奖学金消息  模板消息
		 
		 Msg_db msg_db = new Msg_db();
		 
		 Map<String,Object> map = new HashMap<String, Object>();
			
		 map.put("msg_type", 3);
		 map.put("openid", openid);
		 map.put("is_add", -1);
		 map.put("cut", cut);
		 map.put("note", "您使用了奖学金（"+info+"），点击<详情>立即查看");
		 
		 msg_db.add_msg(map);
		 
	}
	
	public void res_score(String openid,int cut,String info){
		
		 Document doc = new Document();
		 doc.put("open_id", openid); 
		 Document user = mu.findOne("user", doc);
		 
		//学分减少
		 int score = user.getInteger("score"); 
		 user.put("score", score - cut ); 
		 
		 mu.replaceOne("user", Filters.eq("open_id", openid ), user);
		 
		 //添加奖学金记录
		 Document s_log = new Document();
		 
		 double money=(Double.valueOf(cut)/100);
		 
		 s_log.put("top_openid", openid);
		 s_log.put("c_name", "您购买"+info+",您扣除学分"+money+"元。");
		 s_log.put("open_id", openid);
		 s_log.put("name", user.getString("name"));
		 s_log.put("pay_money",cut);
		 s_log.put("rebate", - cut);
		 s_log.put("date", DateTool.fromDateY());
		 
		 mu.insertOne("scholship_log", s_log);
		  
		 //获得奖学金消息  模板消息
		 
		/* Msg_db msg_db = new Msg_db();
		 
		 Map<String,Object> map = new HashMap<String, Object>();
			
		 map.put("msg_type", 3);
		 map.put("openid", openid);
		 map.put("is_add", -1);
		 map.put("cut", cut);
		 
		 msg_db.add_msg(map);*/
		 
	}
	
	public void update_course(String _id,Document doc){
		mu.replaceOne("course", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public void update_special(String _id,Document doc){
		mu.replaceOne("special", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	 
	public void update_red_packet(String red_id,String send_xml,String back_xml, int state){
		
		Document doc = new Document();
		doc.put("red_id", red_id);
		
		Document red_packet = mu.findOne("red_packet", doc);
		
		if(red_packet != null){
			
			String _id = red_packet.get("_id").toString();
			
			red_packet.put("state", state);
			red_packet.put("send_xml", send_xml);
			red_packet.put("back_xml", back_xml);
			
			mu.replaceOne("red_packet", Filters.eq("_id", new ObjectId(_id)), red_packet);
			
		}
		 
	}
	
	public void tx_scholarship(String openid,int txmoney){
		
		 Document doc = new Document();
		 doc.put("open_id", openid); 
		 Document user = mu.findOne("user", doc);
		 
		//奖学金减少
		 int scholarship = user.getInteger("scholarship"); 
		 user.put("scholarship", scholarship - txmoney ); 
		 
		 mu.replaceOne("user", Filters.eq("open_id", openid ), user);
		 
		 //添加奖学金记录
		 Document s_log = new Document();
		 
		 double money=(Double.valueOf(txmoney)/100);
		 
		 s_log.put("top_openid", openid);
		 s_log.put("c_name", "奖学金提现,您扣除奖学金"+money+"元。");
		 s_log.put("open_id", openid);
		 s_log.put("name", user.getString("name"));
		 s_log.put("pay_money",txmoney);
		 s_log.put("rebate", - txmoney);
		 s_log.put("date", DateTool.fromDateY());
		 
		 mu.insertOne("scholship_log", s_log);
		 
		 //获得奖学金消息  模板消息
		 
		 Msg_db msg_db = new Msg_db();
		 
		 Map<String,Object> map = new HashMap<String, Object>();
			
		 map.put("msg_type", 3);
		 map.put("openid", openid);
		 map.put("is_add", -1);
		 map.put("cut", txmoney);
		 map.put("note", "奖学金提现,您扣除奖学金"+money+"元。");
		 
		 msg_db.add_msg(map);
		
	}
	
	public Document query_qrcode_order(String c_no,String openid){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		
		doc.put("is_over", 0);//未过期
		
		Document order = mu.findOne("course_order", doc);
		
		return order;
	}
}
