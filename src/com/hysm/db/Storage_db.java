package com.hysm.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
 
import com.hysm.db.mongo.MongoUtil;
import com.mongodb.client.MongoCursor;
 
public class Storage_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	/**
	 * 查询全部
	 * @param db_name
	 * @param doc
	 * @return
	 */
	public List<Document> query_db_all(String db_name,Document doc){
		
		List<Document>  list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find(db_name, doc);
		
		while (cursor.hasNext()) {
			list.add(cursor.next()); 
		}
		
		return list;
	}
	
	public int query_count(String db_name,Document doc){
		
		long count = mu.count(db_name, doc);
		
		return (int)count;
	}
	
	
	public List<Document> query_db_page(String db_name,Document doc,int page,int limit){
		
		List<Document>  list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.findLimit(db_name, doc, page, limit);
		
		while (cursor.hasNext()) {
			list.add(cursor.next()); 
		}
		
		return list;
	}
	
	public List<Document> query_db_page_sort(String db_name,Document doc,int page,int limit,Document sort){
		
		List<Document>  list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.findLimitSort(db_name, doc, page, limit, sort);
		
		while (cursor.hasNext()) {
			list.add(cursor.next()); 
		}
		
		return list;
	}
	
	public List<Document> query_db_sort(String db_name,Document doc,Document sort){
		
		List<Document>  list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.findSort(db_name, doc, sort);
		
		while (cursor.hasNext()) {
			list.add(cursor.next()); 
		}
		
		return list;
	}
}
