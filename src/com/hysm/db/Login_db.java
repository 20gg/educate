package com.hysm.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.hysm.bean.MessageType;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.hysm.tools.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Login_db {
	
	MongoUtil mu = MongoUtil.getThreadInstance();
	
	/**
	 * 根据用户名查询用户
	 * @param name
	 * @return
	 */
	public List<Map<String,Object>> query_user_name(String name){
		
		List<Map<String,Object>> user_list = new ArrayList<Map<String,Object>>();
		
		//根据姓名查询用户 
		BasicDBObject bdb = new BasicDBObject(); 
		bdb.put("user_id", name); 
		MongoCursor<Document> cursor=mu.find("user", bdb); 
		while (cursor.hasNext()) {
             Document user = cursor.next(); 
             Map<String,Object> it_user = (Map<String,Object>)user; 
             user_list.add(it_user);
		}
		
		return user_list;
	}
	
	/**
	 * 根据open_id查询用户
	 * @param name
	 * @return
	 */
	public List<Map<String,Object>> query_user_openid(String name){
		
		List<Map<String,Object>> user_list = new ArrayList<Map<String,Object>>();
		
		//根据姓名查询用户 
		BasicDBObject bdb = new BasicDBObject(); 
		bdb.put("open_id", name); 
		MongoCursor<Document> cursor=mu.find("user", bdb); 
		while (cursor.hasNext()) {
             Document user = cursor.next(); 
             Map<String,Object> it_user = (Map<String,Object>)user; 
             user_list.add(it_user);
		}
		
		return user_list;
	}
	/**
	 * 根据open_id查询用户
	 * @param name
	 * @return
	 */
	public Document query_userByopenid(String openid){
		 
		//根据姓名查询用户 
		BasicDBObject bdb = new BasicDBObject(); 
		bdb.put("open_id", openid); 
		return mu.findOne("user", bdb);  
		
	}
	
	/**
	 * 根据手机号查询用户
	 * @param name
	 * @return
	 */
	public List<Map<String,Object>> query_user_phone(String name){
		
		List<Map<String,Object>> user_list = new ArrayList<Map<String,Object>>();
		
		//根据姓名查询用户 
		BasicDBObject bdb = new BasicDBObject(); 
		bdb.put("phone", name); 
		MongoCursor<Document> cursor=mu.find("user", bdb); 
		while (cursor.hasNext()) {
             Document user = cursor.next(); 
             Map<String,Object> it_user = (Map<String,Object>)user; 
             user_list.add(it_user);
		}
		
		return user_list;
	}
	
	/**
	 * 数据库插入一条记录
	 */
	
	public void add_db_data(String table_name,BasicDBObject bdb){ 
		
		mu.insertOne(table_name, bdb);
	}
	
	/**
	 * 查询用户的角色信息
	 */
	public Map<String,Object> query_role_info(int role_id){
		
		Map<String,Object> map = null;
		
		BasicDBObject bdb = new BasicDBObject(); 
		bdb.put("role_id", role_id);
		
		MongoCursor<Document> cursor=mu.find("role", bdb);
		
		while (cursor.hasNext()) {
            Document ddd = cursor.next(); 
            map = (Map<String,Object>)ddd;  
		}
		
		return map;
	}
	
	public List<Map<String,Object>> query_menu(){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		MongoCursor<Document> cursor=mu.find("menu", null);
		
		while (cursor.hasNext()) {
            Document ddd = cursor.next(); 
            Map<String,Object> it = (Map<String,Object>)ddd; 
            list.add(it);
		}
		
		return list;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */

	public int insertUser(Document user) {
		
		mu.insertOne("user", user);
		int count=(int)mu.count("user", Filters.ne("open_id", user.get("open_id")));
		
		return count;
	}

	
	/**
	 * 根据手机号获取月嫂
	 * @param mobile
	 * @return
	 */
	public Document query_userByMobile(String mobile) {
		 
		return null;
	}
	 
	
	public void check_user(String openid, Document doc){
		
		Document doc1 = new Document();
		doc1.put("open_id", openid);  
		
		//BasicDBObject bdb = new BasicDBObject(); 
		//bdb.put("open_id", openid);  
		
		/*MongoCursor<Document> cursor=mu.find("user", bdb);
		
		int c =0;
		
		while (cursor.hasNext()) {
			c ++;
		}*/
		
		Document user = mu.findOne("user", doc1);
		
		if(user == null){
			
			 
			doc.put("appid", MessageType.appid); 
			doc.put("state", 0);
			doc.put("role", 0); 
			doc.put("scholarship", 0);
			doc.put("score", 0);
			doc.put("count_scholarship", 0);
			doc.put("is_sign", 0);
			doc.put("forbit", 0);
			doc.put("classmatecircle", 0);
			doc.put("CreateTime", DateTool.fromDate24H());
			
			if(doc.get("MsgType") == null){
				doc.append("MsgType","get_info");
			}else{
				doc.append("MsgType","guanzhu");
			} 
			doc.put("u_no", System.currentTimeMillis()+"");
			doc.append("create_date", new Date()); 
			
			mu.insertOne("user", doc);
		}else{
			
			user.put("sex", doc.getInteger("sex"));
			user.put("open_id", doc.getString("open_id"));
			user.put("province", doc.getString("province"));
			user.put("city", doc.getString("city"));
			user.put("country", doc.getString("country"));
			user.put("head", doc.getString("head"));
			user.put("name", doc.getString("name"));
			user.put("state", 0); 
			
			mu.replaceOne("user", Filters.eq("open_id", openid), user);
		}
		 
	}
}
