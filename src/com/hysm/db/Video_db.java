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
public class Video_db {
 
	MongoUtil mu=MongoUtil.getThreadInstance();
	
	public Document query_qq_code(){
		Document doc = mu.findOne("qq_code", null);
		return doc;
	}
	
	
	public List<Document> query_no_course(){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_show", 0);
		
		MongoCursor<Document> cursor = mu.find("course", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
	
public List<Document> query_no_special_data(){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_gg", 0);
		
		MongoCursor<Document> cursor = mu.find("special", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
	
	
	public void update_course(String _id,Document doc){
		mu.replaceOne("course", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	

	
	
	public List<Document> query_no_special(){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_show", 0);
		
		MongoCursor<Document> cursor = mu.find("special", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}
	
	public void update_special(String _id,Document doc){
		mu.replaceOne("special", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	
	public List<Document> query_safe_course(String safe_date){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("safe_date", safe_date);
		doc.put("is_free", "-1");
		
		MongoCursor<Document> cursor = mu.find("course", doc);
		
		while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		} 
		 
		return list;
		
	}
	
	public List<Document> query_safe_order(String safe_year){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("safe_year", safe_year);
		doc.put("is_over", 0);
		
		MongoCursor<Document> cursor = mu.find("course_order", doc);
		
		while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		} 
		 
		return list;
		
	}
	
	public void update_order(String _id,Document doc){
		mu.replaceOne("course_order", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public List<Document> query_safe_vip(String safe_date){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("safe_date", safe_date);
		doc.put("is_over", 0);
		
		MongoCursor<Document> cursor = mu.find("vip_order", doc);
		
		while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		} 
		 
		return list;
	}
	
	public void update_vip(String _id,Document doc){
		mu.replaceOne("vip_order", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public Document query_user(String open_id){
		Document doc = new Document();
		doc.put("open_id", open_id);
		
		Document user = mu.findOne("user", doc);
		
		return user;
	}
	
	
	
public List<Document> query_no_message(String table,Document doc){
		
		List<Document> list = new ArrayList<Document>();
		
		
		MongoCursor<Document> cursor = mu.find(table, doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 
			 list.add(post);
		 } 
		 
		 return list;
		
	}


public void update_message(String _id,Document doc){
	mu.replaceOne("error_message", Filters.eq("_id", new ObjectId(_id)), doc);
}

/**
 * 删除一条记录
 */
public void delete_db_one(String db_name,String c_no){
	mu.deleteMany(db_name, Filters.eq("c_no",c_no));
}

}
