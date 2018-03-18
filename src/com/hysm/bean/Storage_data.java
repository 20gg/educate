package com.hysm.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import org.bson.Document;
 

import com.hysm.db.Storage_db;

public class Storage_data {
	
	  
	public static Map<String,Object> Common_Json =null;
	
	/**
	 * 从数据库中查询数据，保存到内存空间 
	 * @param num
	 */
	public static void set_storage(int num){
		
		if(Common_Json == null){ 
			Common_Json = new HashMap<String, Object>();
		}
		
		Storage_db storage_db = new Storage_db();
		
		//轮播
		if(num == 1 || num == 99){ 
			List<Document> lunbo_list = storage_db.query_db_all("lunbo", null); 
			Common_Json.put("lunbo_list", lunbo_list);
		}
		
		if(num == 2 || num == 99){ //首页限时免费  
			
			 Document doc = new Document();
			 doc.put("type", 0);
			 doc.put("state", 1);
			 doc.put("is_free", "-1");
			 doc.put("is_show", 1);
			List<Document> list = storage_db.query_db_all("course", doc);
			
			Common_Json.put("free_voice", list);
			
		}
		
		if(num ==3 || num ==99){ //首页限时免费  视频
			
			Document doc2 = new Document();
			 doc2.put("type", 1);
			 doc2.put("state", 1);
			 doc2.put("is_free", "-1");
			 doc2.put("is_show", 1);
			
			 Document sort = new Document();
			 sort.put("watch", -1); //按_id 倒序
			List<Document> free_video = storage_db.query_db_page_sort("course", doc2, 0, 3, sort);
			
			
			
			Common_Json.put("free_video", free_video);
			
		}
		
		
		storage_db = null;
	}
	
	public static Object get_storage(int num){
		
		if(Common_Json == null){ 
			Common_Json = new HashMap<String, Object>();
			
			set_storage(99);
		}
		
		if(num == 1){
			if(Common_Json.get("lunbo_list") == null){
				set_storage(1);
			}
			
			return Common_Json.get("lunbo_list");
		}
		
		
		if(num == 2){
			
			if(Common_Json.get("free_voice") == null){
				set_storage(2);
			}
			
			return Common_Json.get("free_voice");
		}
		
		
			if(num == 3){
			
			if(Common_Json.get("free_video") == null){
				set_storage(3);
			}
			
			return Common_Json.get("free_video");
		}
		
		
		else {
			return null; 
		}
		 
	}
}
