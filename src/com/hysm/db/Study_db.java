package com.hysm.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Study_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	public Document query_state_study(String openid,String c_no,int state){
		
		Document doc = new Document();
		doc.put("openid", openid);
		doc.put("c_no", c_no);
		doc.put("state", state);
		
		
		Document s = mu.findOne("study_log", doc);
		
		return s;
	}
	
	public Document query_the_study(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("openid", openid);
		doc.put("c_no", c_no);
		 
		Document s = mu.findOne("study_log", doc);
		
		return s;
	}
	
	public void update_study(String _id,Document doc){
		
		mu.replaceOne("study_log", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public int query_study_count(String openid){
		
		Document doc = new Document();
		doc.put("openid", openid);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$gte",1);
		map.put("$lte",3); 
		doc.put("state", map);
		
		long c = mu.count("study_log", doc);
		
		return (int)c;
	}
	
	public List<Document> query_study_page(String openid,int skip, int limit){
		
		Document doc = new Document();
		doc.put("openid", openid);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$gte",1);
		map.put("$lte",3); 
		doc.put("state", map);
		
		 Document sort = new Document();
		sort.put("state", 1); //按state 序
		
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.findLimitSort("study_log", doc, skip,limit,sort);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
	
	}
	
	public Document query_one_course(String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		Document s = mu.findOne("course", doc);
		
		return s;
	}
	
	public Document quert_special(String c_no){
		
		Document doc = new Document();
		doc.put("special_no", c_no);
		
		Document c = mu.findOne("special", doc);
		
		return c;
	}
	
	public void study_exercise(String openid,String c_no){
		
		Document doc = new Document();
		doc.put("openid", openid);
		doc.put("c_no", c_no);
		doc.put("state", 2);
		 
		Document s = mu.findOne("study_log", doc);
		
		if(s != null){
			
			String _id = s.get("_id").toString(); 
			s.put("state", 3);
			
			mu.replaceOne("study_log", Filters.eq("_id", new ObjectId(_id)), s);
		}
		
	}
	
	public int query_user_count(){
		
		long c = mu.count("user", null);
		
		return (int)c;
	}
	
	public int query_more_count(int score){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$gt",score); 
		
		Document doc = new Document();
		doc.put("score", map);
		
		long c = mu.count("user", doc);
		
		return (int)c;
	}
	
	public int query_dy_count(int score){
		
		 
		Document doc = new Document();
		doc.put("score", score);
		
		long c = mu.count("user", doc);
		
		return (int)c;
	}
	
	public List<Document> query_s_page(int skip,int limit){
		
		 Document sort = new Document();
		 sort.put("score", -1); //按_id 倒序
		 
		 List<Document> list = new ArrayList<Document>();
			
			MongoCursor<Document> cursor = mu.findLimitSort("user", null, skip,limit,sort);
			
			 while(cursor.hasNext()){
				 Document post = cursor.next(); 
				 
				 list.add(post);
			 } 
			 
			 return list;
		
	}
	
	public List<Document> get_free_music(){
		
		 List<Document> list = new ArrayList<Document>();
		 
		 Document doc = new Document();
		 doc.put("type", 0);
		 doc.put("state", 1);
		 doc.put("is_free", "-1");
		 doc.put("is_show", 1);
		 
		 Document sort = new Document();
		 sort.put("_id", -1); //按_id 倒序
		 
		 MongoCursor<Document> cursor = mu.findLimitSort("course", doc, 0, 5, sort);
		
		 while(cursor.hasNext()){
			 Document aaa = cursor.next(); 
			 
			 list.add(aaa);
		 } 
		 
		 return list;
		
	}
	
	public List<Document> get_free_video(){
		
		 List<Document> list = new ArrayList<Document>();
		 
		 Document doc = new Document();
		 doc.put("type", 1);
		 doc.put("state", 1);
		 doc.put("is_free", "-1");
		 doc.put("is_show", 1);
		
		 Document sort = new Document();
		 sort.put("_id", -1); //按_id 倒序
		 
		 MongoCursor<Document> cursor = mu.findLimitSort("course", doc, 0, 3, sort);
		 

		 while(cursor.hasNext()){
			 Document aaa = cursor.next(); 
			 
			 list.add(aaa);
		 } 
		 
		 return list;
	}
	 
}
