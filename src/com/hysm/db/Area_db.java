package com.hysm.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Area_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	 public Document query_user_byopenid(String openid){
		 
		 Document doc = new Document();
		 doc.put("open_id", openid);
		 
		 Document user = mu.findOne("user", doc);
		 
		 return user;
	 }
	 
	 public int query_post_count(int state,int kind,String input){
		 
		 Document doc = new Document(); 
		 if(state > -1){ //-1 全部  0 禁止  1正常
			 doc.put("state", state);
		 } 
		 
		 if(kind ==1 && !input.equals("")){
			 doc.put("name", input);
		 }else if(kind ==2 && !input.equals("")){
			 
			//完全匹配
			//Pattern pattern = Pattern.compile("^name$", Pattern.CASE_INSENSITIVE);
			//右匹配
			//Pattern pattern = Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE);
			//左匹配
			//Pattern pattern = Pattern.compile("^name.*$", Pattern.CASE_INSENSITIVE);
			//模糊匹配
			//Pattern pattern = Pattern.compile("^.*name8.*$", Pattern.CASE_INSENSITIVE);
			 
			 Pattern pattern = Pattern.compile("^.*" + input + ".*$", Pattern.CASE_INSENSITIVE);
			 
			 
			 doc.put("title", pattern);
		 }
		  
		 
		 long c = mu.count("post", doc);
		 
		 return (int)c;
	 }
	 
	 public List<Document> query_post_page(int state,int skip,int limit,int kind,String input){
		 
		 List<Document> post_list = new ArrayList<Document>();
		 
		 Document doc = new Document();
		 if(state > -1){ //-1 全部  0 禁止  1正常
			 doc.put("state", state);
		 } 
		 
		 Document sort = new Document();
		 sort.put("_id", -1); //按_id 倒序
		 
		 if(kind ==1 && !input.equals("")){
			 doc.put("name", input);
		 }else if(kind ==2 && !input.equals("")){
			 
			//完全匹配
			//Pattern pattern = Pattern.compile("^name$", Pattern.CASE_INSENSITIVE);
			//右匹配
			//Pattern pattern = Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE);
			//左匹配
			//Pattern pattern = Pattern.compile("^name.*$", Pattern.CASE_INSENSITIVE);
			//模糊匹配
			//Pattern pattern = Pattern.compile("^.*name8.*$", Pattern.CASE_INSENSITIVE);
			 
			 Pattern pattern = Pattern.compile("^.*" + input + ".*$", Pattern.CASE_INSENSITIVE);
			 
			 
			 doc.put("title", pattern);
		 }
		  
		 
		 MongoCursor<Document> cursor = mu.findLimitSort("post", doc, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id); 
			 post.put("zan_state", 0);
			 
			 post_list.add(post);
		 } 
		 
		 return post_list;
	 }
	 
	 public void add_db_info(String table_name,Document bdb){ 
			
		mu.insertOne(table_name, bdb);
	 }
	 
	 public Document query_one_post(String _id){
		 
		 Document doc = new Document();
		 doc.put("_id", new ObjectId(_id));
		 
		 Document post = mu.findOne("post", doc);
		 
		 return post;
	}
	 
	public void zan_post(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post", doc); 
		int zan = post.getInteger("zan") +1;//赞加一
		
		post.put("zan", zan);
		
		mu.replaceOne("post", Filters.eq("_id", new ObjectId(_id)), post);
	}
	
	public void no_zan_post(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post", doc); 
		int zan = post.getInteger("zan")-1;//赞减一
		
		post.put("zan", zan);
		
		mu.replaceOne("post", Filters.eq("_id", new ObjectId(_id)), post);
	}
	
	public int query_post_log_count(int state,String post_id){
		
		 Document doc = new Document();
		 if(state > -1){ //-1 全部  0 禁止  1正常
			 doc.put("state", state);
		 } 
		 doc.put("post_id", post_id);
		 
		 long c = mu.count("post_log", doc);
		 
		 return (int)c;
	}
	
	public List<Document> query_post_log_page(int state,String post_id,int skip,int limit){
		 
		 List<Document> post_list = new ArrayList<Document>();
		 
		 Document doc = new Document();
		 if(state > -1){ //-1 全部  0 禁止  1正常
			 doc.put("state", state);
		 } 
		 doc.put("post_id", post_id);
		 
		 Document sort = new Document();
		 sort.put("_id", -1); //按_id 倒序
		 
		 MongoCursor<Document> cursor = mu.findLimitSort("post_log", doc, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 post.put("zan_state", 0);
			 
			 post_list.add(post);
		 } 
		 
		 return post_list;
	 }
	
	public void zan_post_log(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post_log", doc); 
		int zan = post.getInteger("zan") +1;//赞加一
		
		post.put("zan", zan);
		
		mu.replaceOne("post_log", Filters.eq("_id", new ObjectId(_id)), post);
	}
	
	public void no_zan_post_log(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post_log", doc); 
		int zan = post.getInteger("zan")-1;//赞减一
		
		post.put("zan", zan);
		
		mu.replaceOne("post_log", Filters.eq("_id", new ObjectId(_id)), post);
	}
	
	public void change_post(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post", doc);
		
		int state = 1 - post.getInteger("state");
		
		post.put("state", state);
		 
		
		mu.replaceOne("post", Filters.eq("_id", new ObjectId(_id)), post);
		
	}
	
	public void change_post_log(String _id){
		
		Document doc = new Document();
		doc.put("_id", new ObjectId(_id));
		 
		Document post = mu.findOne("post_log", doc);
		
		int state = 1 - post.getInteger("state");
		
		post.put("state", state);
		
		mu.replaceOne("post_log", Filters.eq("_id", new ObjectId(_id)), post);
		
	}
	
	public int get_user_forbit_count(int kind,String input,int forbit){
		
		 
		Document doc = new Document();
		
		if(kind == 1 && !input.equals("")){
			doc.put("name", input);
		}if(kind == 2 && !input.equals("")){
			doc.put("phone", input);
		}
		
		if(forbit == 1){
			doc.put("forbit", 1);
		}
		
		long c = mu.count("user", doc);
		
		return (int)c;
	}
	
	public List<Document> get_user_forbit_count(int kind,String input,int forbit,int skip,int limit){
		
		List<Document> user_list = new ArrayList<Document>();
		
		Document doc = new Document();
		
		if(kind == 1 && !input.equals("")){
			doc.put("name", input);
		}if(kind == 2 && !input.equals("")){
			doc.put("phone", input);
		}
		
		if(forbit == 1){
			doc.put("forbit", 1);
		}
		
		MongoCursor<Document> cursor = mu.findLimit("user", doc, skip, limit);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 String _id = post.get("_id").toString();
			 post.put("_id", _id);
			 
			 user_list.add(post);
		 } 
		 
		 return user_list;
	}
	
	public void forbit_users(List<ObjectId> id_list,int forbit){
		
		List<Document> user_list = new ArrayList<Document>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("$in", id_list);
		
		Document doc = new Document();
		doc.put("_id", map);
		
		MongoCursor<Document> cursor = mu.find("user", doc);
		 
		while(cursor.hasNext()){
			 Document user = cursor.next();  
			 user_list.add(user);
		}
		
		for(int i=0;i<user_list.size();i++){
			
			String _id = user_list.get(i).get("_id").toString();
			
			user_list.get(i).put("forbit", forbit);
			
			mu.replaceOne("user", Filters.eq("_id", new ObjectId(_id)), user_list.get(i));
			
		} 
	}
	
	
	public List<Document> query_all_word(){
		
		List<Document> word_list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find("sensitive_word", null);
		 
		while(cursor.hasNext()){
			 Document doc = cursor.next();  
			 word_list.add(doc);
		}
		
		return word_list;
	}
	
	public int get_word_count(String word){
		
		Document doc = new Document();
		
		doc.put("word", word);
		
		long c = mu.count("sensitive_word", doc);
		
		return (int)c;
	}
	
	public void delete_word(String _id){
		
		Document doc = new Document();
		
		doc.put("_id", new ObjectId(_id));
		
		mu.deleteMany("sensitive_word", doc);
	}
	
	public List<Document> query_zan_log(String openid, List<String> id_list,int type){
		
		Document doc = new Document();
		
		doc.put("openid", openid);
		doc.put("cdate", DateTool.fromDateY()); 
		doc.put("type", type);
		 
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("$in", id_list);
		
		doc.put("pid", map);
		
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find("zan_log", doc);
		 
		while(cursor.hasNext()){
			 Document aaa = cursor.next();  
			 list.add(aaa);
		}
		
		return list; 
	}
	
	public int query_one_zan(String openid,String pid){
		
		Document doc = new Document();
		
		doc.put("openid", openid);
		doc.put("cdate", DateTool.fromDateY()); 
		doc.put("type", 1);
		doc.put("pid", pid);
		
		long c = mu.count("zan_log", doc);
		
		return (int)c;
	}
	
	public void delete_zan(String pid ,String openid,int type){
		
		Document doc = new Document();
		
		doc.put("pid", pid);
		doc.put("openid", openid);
		doc.put("cdate", DateTool.fromDateY());
		doc.put("type", type);
		
		mu.deleteMany("zan_log", doc);
	}
	
	public Document query_yg(){
		
		Document doc = mu.findOne("yg", null);
		
		return doc;
		
	}
	
	
	
	public void update_yg(String _id,Document doc){
		mu.replaceOne("yg", Filters.eq("_id", new ObjectId(_id)), doc);
		}
	
	
	public  Document find_vip_order(String table, Document map){
		 
		MongoCursor<Document> cursor=null;
		
			 
			cursor=mu.find(table, map);
			 
			
		Document vip_order = null;
		
		if(cursor!=null){
			while(cursor.hasNext()){
				vip_order=cursor.next();
				  
			}
		}
		
		return vip_order;
	}
	
public void update_viporder(String table,Document doc){
		
		 
		
		mu.replaceOne(table, Filters.eq("open_id",doc.getString("open_id")), doc);
		
	}
}
