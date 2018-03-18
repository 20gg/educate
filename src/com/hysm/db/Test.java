package com.hysm.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
 
import java.util.List;
import java.util.Map;
 



import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.hysm.tools.random4;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Controller

public class Test {
	@Autowired
	private static CbgDB cgbdb;
	
	public static void main(String[] args) {
		 
		MongoUtil mu=MongoUtil.getThreadInstance();
		 
		Document doc =new Document();
		
		doc.put("url", "1507793435252");
		
		long count = mu.count("special", doc);
		
		 int c = (int)count;
		
		
		
		System.out.println("---------------------"+c);
		 
		
	}
	
	public static Date get_the_date(String date_time){
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = null;
		 try {
			 date = df.parse(date_time);
		} catch (ParseException e){ 
			e.printStackTrace();
		}
		 
		return date;
	}
	 
	
	
	/*
	 * 
	 Video_db video_db = new Video_db();
			 
    	 
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find("special", null);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		 
		 for(int i=0;i<list.size();i++){
			 
			 List<Map<String,Object>> url_list = (List<Map<String,Object>> )list.get(i).get("url");
			 
			 for(int j=0;j<url_list.size();j++){
				 
				 
				//生成专题的视频cid  4随机数
				if(url_list.get(j).get("cid") == null){
					url_list.get(j).put("cid",""+random4.getRandomNum4());
				}
				 
			 }
			 
			 String _id = list.get(i).get("_id").toString();
			 
			 list.get(i).put("url", url_list);
			 
			 video_db.update_special(_id, list.get(i));
			 
		 }

	 
	 */

}
