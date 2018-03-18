package com.hysm.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Article_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	public int query_art_count(int state,int sort,String input){
		
		Document doc = new Document(); 
		 
		doc.put("sort", sort);
		doc.put("recommend", 0);
		if(state > -1){
			doc.put("state", state);
		}
		
		if(!input.equals("")){
			//模糊匹配
			Pattern pattern = Pattern.compile("^.*" + input + ".*$", Pattern.CASE_INSENSITIVE); 
			doc.put("title", pattern);
		}
		
		long c = mu.count("article", doc);
		
		return (int)c; 
	}
	
	public List<Document> query_art_page(int state,int sort,int skip,int limit,String input){
		
		List<Document> art_list = new ArrayList<Document>();
		
		Document doc = new Document(); 
		 
		doc.put("sort", sort);
		doc.put("recommend", 0);
		if(state > -1){
			doc.put("state", state);
		}
		
		if(!input.equals("")){
			//模糊匹配
			Pattern pattern = Pattern.compile("^.*" + input + ".*$", Pattern.CASE_INSENSITIVE); 
			doc.put("title", pattern);
		}
		
		
		 Document sort2 = new Document();
			sort2.put("_id", -1); //按_id 倒序
		
		//MongoCursor<Document> cursor =  mu.findLimit("article", doc, skip, limit);
		MongoCursor<Document> cursor =  mu.findLimitSort("article", doc, skip, limit,sort2);
		 while(cursor.hasNext()){
			 Document art = cursor.next(); 
			 String _id = art.get("_id").toString();
			 art.put("_id", _id);
			 
			 art_list.add(art);
		 } 
		 
		 return art_list;
	}
	
	public Document query_one_art(String _id){
		
		Document doc = new Document(); 
		 
		doc.put("_id", new ObjectId(_id));
		
		Document art = mu.findOne("article", doc);
		
		return art;
	}
	
	public Document query_recommend_art(int sort){
		
		Document doc = new Document(); 
		 
		doc.put("sort", sort);
		doc.put("recommend", 1);
		
		Document art = mu.findOne("article", doc);
		
		return art;
	}
	
	public void update_art(String _id,Document doc){
		
		mu.replaceOne("article", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public List<Document> query_lunbo(){
		
		List<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor =  mu.find("lunbo", null);
		
		while(cursor.hasNext()){
			 Document art = cursor.next(); 
			   
			 list.add(art);
		 } 
		 
		 return list;
	}
	
	public Document query_one_lunbo(String _id){
		
		Document doc = new Document();  
		doc.put("_id", new ObjectId(_id)); 
		Document art = mu.findOne("lunbo", doc);
		
		return art;
	}
	
	public void update_lunbo(String _id,Document doc){
		
		mu.replaceOne("lunbo", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public List<Document> query_host_course(){
		
		Document doc = new Document(); 
		 
		doc.put("is_show", 1);
		doc.put("state", 1);
		
	    Document sort = new Document();
		sort.put("watch", -1); //按_id 倒序
		 
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor =  mu.findLimitSort("course", doc, 0, 20, sort);
		
		while(cursor.hasNext()){
			 Document art = cursor.next(); 
			   
			 list.add(art);
		} 
		 
		return list;
	}
	
	public List<Document> query_host_special(){
		
		Document doc = new Document(); 
		 
		//doc.put("is_show", 1);
		doc.put("state", 1);
		
	    Document sort = new Document();
		sort.put("watch", -1); //按_id 倒序
		 
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor =  mu.findLimitSort("special", doc, 0, 20, sort);
		
		while(cursor.hasNext()){
			 Document art = cursor.next(); 
			   
			 list.add(art);
		} 
		 
		return list;
	}
	
	
	public Document query_set_page(int page_id){
		
		Document doc = new Document(); 
		 
		doc.put("page_id", page_id);
		
		Document set_page = mu.findOne("set_page", doc);
		
		return set_page;
	}
	
	public void update_set_page(String _id,Document doc){
		
		mu.replaceOne("set_page", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
}
