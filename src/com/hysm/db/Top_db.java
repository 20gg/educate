package com.hysm.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Top_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	/**
	 * 修改用户信息
	 * @param _id
	 * @param user
	 */
	public void update_user(String _id,Document user){
		
		mu.replaceOne("user", Filters.eq("_id", new ObjectId(_id)), user);
	}
	
	/**
	 * 查询我的上级  
	 * @param openid
	 * @return
	 */
	public Map<String,Object> query_my_top(String openid){
		
		//查询自己 
		Document doc = new Document();
		doc.put("open_id", openid); 
		Document me = mu.findOne("user", doc);
		
		Document top =null;
		
		int safe = -1;//-1 无上级  0 无效  1有效
		
		if(me.getString("top_openid") != null){
			
			Map<String,Object> map = (Map<String,Object>)me.get("top_member"); 
			//String safe_date = map.get("safe_date").toString();
			//String p2_date=me.getString("p2_date");
			
			//比较是否超出有效期  
			//int c = DateTool.compare_date(safe_date, DateTool.fromDateY());
			
			//int cd = DateTool.compare_date(p2_date, DateTool.fromDateY());
			
			safe = 1;  
			
			/*if( c >= 0&&cd>=0){
				//当前日期有效期 
			 
			}else{
				safe = 0;
			}*/
			
			String top_openid = me.getString("top_openid");
			
			Document doc2 = new Document();
			doc2.put("open_id", top_openid); 
			top = mu.findOne("user", doc2);
			
		}else{
			safe = -1;
		}
		
		Map<String,Object> back = new HashMap<String, Object>();
		back.put("safe", safe);
		back.put("top", top);
		
		return back;
	}
	/**
	 * 查询下级 （查询我的同学  使用 user.open_id  查询我的下级user.top_openid）
	* XXXXX
	* @param map
	* @return
	 */
	public List<Document> query_my_xia(String top_openid){
		
		List<Document> xia_list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("top_openid", top_openid); 
		
		MongoCursor<Document> cursor = mu.find("user", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 xia_list.add(post);
		 } 
		 
		 return xia_list;
	}
	
	/**
	 * 查询有效期设置
	 * @return
	 */
	public Document query_config_s(){
		
		Document doc = mu.findOne("config_s", null);
		
		return doc;
	}
	
	/**
	 * p1截至查询
	 * @param p1_date
	 * @return
	 */
	public List<Document> query_p1_list(String p1_date){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("p1_date", p1_date);
		
		MongoCursor<Document> cursor = mu.find("user", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
	/**
	 * p2截至查询
	 * @param p2_date
	 * @return
	 */
	public List<Document> query_p2_list(String p2_date){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("p2_date", p2_date);
		
		MongoCursor<Document> cursor = mu.find("user", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
	public int query_xia_count(String top_openid,int role){
		
		Document doc = new Document();
		doc.put("top_openid", top_openid);
		doc.put("role", role);
		
		long count = mu.count("user", doc);
		
		return (int)count;
	}
	
	public int query_scholship_log_count(String top_openid){
		
		Document doc = new Document();
		doc.put("top_openid", top_openid);
		
		long count = mu.count("scholship_log", doc);
		
		return (int)count;
		
	}
	
	public List<Document> query_scholship_log(String top_openid, int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("top_openid", top_openid);
		
		Document sort = new Document();
		sort.put("_id", -1); //按_id 倒序
		
		MongoCursor<Document> cursor = mu.findLimitSort("scholship_log", doc, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		return list;
	}
	
	public int query_user_count(){
		
		long count = mu.count("user", null);
		
		return (int)count;
	}
	
	public List<Document> query_user_page(int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document sort = new Document();
		sort.put("count_scholarship", -1); //按_id 倒序
		
		MongoCursor<Document> cursor = mu.findLimitSort("user", null, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		return list;
	}
	
	public int query_order_count(String openid){
		
		Document doc = new Document();
		 
		doc.put("open_id", openid);
		
		doc.put("is_order", 1); //已经付款
		doc.put("is_over", 0);//未过期
		
		long c = mu.count("course_order", doc);
		
		return (int)c;
	}
	
	public List<Document> query_order_page(String openid,int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		 
		doc.put("open_id", openid);
		
		doc.put("is_order", 1); //已经付款
		doc.put("is_over", 0);//未过期

		Document sort = new Document();
		sort.put("_id", -1); //按_id 倒序
		
		MongoCursor<Document> cursor = mu.findLimitSort("course_order", doc, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		return list;
	}
	
	public List<Document> query_order_all(String openid){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		 
		doc.put("open_id", openid);
		
		doc.put("is_order", 1); //已经付款
		doc.put("is_over", 0);//未过期
		
		MongoCursor<Document> cursor = mu.find("course_order", doc);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		return list;

	}
	
	/**
	 * 查询是否购买
	 * @param openid
	 * @param c_no
	 * @return
	 */
	public int query_is_buy(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		
		doc.put("is_order", 1); //已经付款
		doc.put("is_over", 0);//未过期
		
		long c = mu.count("course_order", doc);
		
		return (int)c;
	}
	
	public int query_is_collection(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		
		long c = mu.count("collection", doc);
		
		return (int)c;
		
	}
	
	public void delete_collection(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		doc.put("open_id", openid);
		
		mu.deleteMany("collection", doc);
		
	}
	
	public List<Document> query_moudle(int type){ 
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_send", 0); 
		doc.put("type", type);
		MongoCursor<Document> cursor = mu.find("message", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			  
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
}
