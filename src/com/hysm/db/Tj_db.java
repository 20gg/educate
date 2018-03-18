/**
 * 
 */
package com.hysm.db;

 

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
 

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年6月19日
 */


@Component
public class Tj_db {
 
private 	MongoUtil mu=MongoUtil.getThreadInstance();
	
	 public int find_today(String string, Document doc) {
		   
	 		return  (int)mu.count(string, doc);
	 
		}
	
}
