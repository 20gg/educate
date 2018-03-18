/**
 * 
 */
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

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年5月10日
 */

@Component
public class Sj_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	public Map<String,Object> query_ks_sj(List<Document> answer){
		
		List<Object> list=new ArrayList<Object>();
		
		for(int i=0;i<answer.size();i++){
			 
			list.add(new ObjectId(answer.get(i).getString("p_id")));
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$in", list);
		
		Document doc = new Document();
		doc.put("_id", map);
		
		List<Document> sj_list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find("exercise", doc);
		
		while (cursor.hasNext()) {
			 Document ys = cursor.next();  
			 String _id = ys.get("_id").toString();
			 ys.put("_id", _id); 
			 sj_list.add(ys); 
		}
		
		int all_size=answer.size();
		
		int ok_size = 0;
		
		for(int i=0;i<sj_list.size();i++){
			
			String val ="";
			int is_ok = 0;
			
			for(int j=0;j<answer.size();j++){
				 
				if(answer.get(j).getString("p_id").equals(sj_list.get(i).getString("_id"))){
					val = answer.get(j).getString("val");
				}
			}
			
			if(sj_list.get(i).getString("answer").equals(val)){
				 is_ok = 1;
				 
				 ok_size ++;
			}
			
			sj_list.get(i).put("val", val); 
			sj_list.get(i).put("is_ok", is_ok);
			
			sj_list.get(i).remove("_id");
		}
		
		
		
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		
		map2.put("all_size", all_size); 
		map2.put("ok_size", ok_size); 
		map2.put("sj_list", sj_list);
		
		return map2;
		
	}
}
