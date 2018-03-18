package com.hysm.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Exercise_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	/**
	 * 查询试题
	 * @param c_no
	 * @return
	 */
	public List<Document> query_exercise_list(String c_no){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		MongoCursor<Document> cursor = mu.find("exercise", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			  
 			doc.put("p_id", _id);
			 
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public List<Document> query_exercise_list2(String c_no){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		MongoCursor<Document> cursor = mu.find("exercise", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next();
			 
			 String _id = post.get("_id").toString(); 
			 post.put("p_id", _id);
			 
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public Document query_one_exercise(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		
		Document ex = mu.findOne("exercise", doc);
		
		return ex;
	}
	
	/**
	 * 修改试题
	 * @param _id
	 * @param doc
	 */
	public void update_exercise(String _id,Document doc){
		mu.replaceOne("exercise", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	/**
	 * 删除试题
	 */
	public void delete_exercise(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		
		mu.deleteMany("exercise", doc);
	}
	
	
	public int query_discuss_count(String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		long count = mu.count("discuss", doc);
		
		return (int)count; 
	}
	
	public List<Document> query_discuss_page(String c_no,int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		Document sort = new Document();
		sort.put("_id", -1); //按_id 倒序
		 
		MongoCursor<Document> cursor = mu.findLimitSort("discuss", doc, skip, limit, sort);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public void update_discuss(String _id,Document doc){
		mu.replaceOne("discuss", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	
	public void update_post(String _id,Document doc){
		mu.replaceOne("post", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public Document query_one_discuss(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		
		Document ex = mu.findOne("discuss", doc);
		
		return ex;
	}
	
	public Document query_one_post(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		
		Document ex = mu.findOne("post", doc);
		
		return ex;
	}
	
	public int query_discuss_at_count(){
		
		Document doc = new Document();
		doc.put("is_at", 1);
		doc.put("at_state", 0);
		
		long count = mu.count("discuss", doc);
		
		return (int)count; 
	}
	
public int query_discuss_at_post(){
		
		Document doc = new Document();
		doc.put("is_at", 0);
		doc.put("at_state", 0);
		
		long count = mu.count("post", doc);
		
		return (int)count; 
	}
	
	public List<Document> query_discuss_at_page(int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_at", 1);
		doc.put("at_state", 0);
		
		Document sort = new Document();
		sort.put("_id", -1); //按_id 倒序
		 
		MongoCursor<Document> cursor = mu.findLimitSort("discuss", doc, skip, limit, sort);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	
	public List<Document> query_post_at_page(int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_at", 0);
		doc.put("at_state", 0);
		
		Document sort = new Document();
		sort.put("_id", -1); //按_id 倒序
		 
		MongoCursor<Document> cursor = mu.findLimitSort("post", doc, skip, limit, sort);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public Document query_one_course(String c_no){
		
		Document doc = new Document();
		doc.put("c_no", c_no);
		
		Document course = mu.findOne("course", doc);
		
		return course;
	}
	
	 
}
