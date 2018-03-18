/**
 * 
 */
package com.hysm.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoConn;
import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年4月12日
 */
@Component
public class CbgDB {
private 	MongoUtil mu=MongoUtil.getThreadInstance();

	/**
	* XXXXX
	* @param map
	* @return
	*/
	public  List<Document> findByPb(String table, Document map){
		
    	int pn=map.getInteger("pn",map.getInteger("pn"));
    	int ps=map.getInteger("ps",map.getInteger("ps"));
    	MongoCursor<Document> cursor=null;
    	List<Bson> tjlist=new ArrayList<Bson>();
    	if(map.get("mindate")!=null){
    		//大于等于
    		tjlist.add(Filters.gte("date", DateUtil.StringToDate(map.getString("mindate"))));
    	}
    	
    	if(map.get("maxdate")!=null){
    		//小于等于
    		tjlist.add(Filters.lte("date",DateUtil.StringToDate(map.getString("maxdate"))));
    	}
    	
    	
    	if(map.get("is_order")!=null){
    		tjlist.add(Filters.eq("is_over", 0));
    		tjlist.add(Filters.eq("is_order", 1));
    	}
    	
    	
    	
    	
    	cursor=mu.findLimit(table, Filters.and(tjlist), (pn-1)*ps, ps);
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			
    			String p_id = doc.get("_id").toString();
    			doc.put("p_id", p_id);
    			
    			list.add(doc);
    		}
    	}
		
		return list;
    }
	
	
	public  List<Document> findexercise(String table, Document map){
		
    	int pn=map.getInteger("pn",map.getInteger("pn"));
    	int ps=map.getInteger("ps",map.getInteger("ps"));
    	MongoCursor<Document> cursor=null;
    	if(map.getString("c_no")!=null){
    	
    		cursor=mu.findLimit(table, Filters.eq("c_no", map.getString("c_no")), (pn-1)*ps, ps);
    		
    		
   		
    	}else{
    		
    		cursor=mu.findLimit(table, null, (pn-1)*ps, ps);
    		
    	}
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			
    			String p_id = doc.get("_id").toString();
    			doc.put("p_id", p_id);
    			
    			list.add(doc);
    		}
    	}
		
		return list;
    }

	public  List<Document> findByPb2(String table, Document map){
		
    	int pn=map.getInteger("pn",map.getInteger("pn"));
    	int ps=map.getInteger("ps",map.getInteger("ps"));
    	MongoCursor<Document> cursor=null;
    	if(map.getString("name")!=null){
    	
    	Pattern pattern = Pattern.compile("^.*" + map.getString("name") + ".*$", Pattern.CASE_INSENSITIVE);
    	
    	
    		
    	Document doc1=new Document();
    		doc1.put("name", pattern);
    		
    	
    		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
    		
    		
   		
    	}else{
    		
    		cursor=mu.findLimit(table, null, (pn-1)*ps, ps);
    		
    	}
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			list.add(doc);
    		}
    	}
		
		return list;
    }
	
public  List<Document> findByPb3(String table, Document map,int type){
		
    	int pn=map.getInteger("pn",map.getInteger("pn"));
    	int ps=map.getInteger("ps",map.getInteger("ps"));
    	
    	
    	Document doc1=new Document();
    	MongoCursor<Document> cursor=null;
    	if(map.getString("c_name")!=null){
    		
    		Pattern pattern = Pattern.compile("^.*" + map.getString("c_name") + ".*$", Pattern.CASE_INSENSITIVE);
    		
    		if(type!=3){
    		doc1.put("type", type);
    		}
    		
    		doc1.put("c_name", pattern);
    		cursor=mu.findLimit(table, Filters.eq("c_name", map.getString("c_name")), (pn-1)*ps, ps);
    		 cursor =  mu.findLimit(table, doc1, (pn-1)*ps, ps);
    		
   			
    	}else if(type==1){
    		doc1.put("type", type);
    				
    		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
    	}else if(type==0){
    		doc1.put("type", type);
    		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
    	}else if(type==-1){
    		doc1.put("is_free", "-1");
    		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
    		
    	}else{
    		
    		cursor=mu.findLimit(table, null, (pn-1)*ps, ps);
    	}
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			list.add(doc);
    		}
    	}
		
		return list;
    }

public  List<Document> findByPb4(String table, Document map){
	
	int pn=map.getInteger("pn",map.getInteger("pn"));
	int ps=map.getInteger("ps",map.getInteger("ps"));
	MongoCursor<Document> cursor=null;
	if(map.getString("special_name")!=null){
		Document doc1=new Document();

		Pattern pattern = Pattern.compile("^.*" + map.getString("special_name") + ".*$", Pattern.CASE_INSENSITIVE);
		
		doc1.put("special_name", pattern);	
		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
		
		
		
	}else if(map.getInteger("kind")!=0){
		Document doc1=new Document();
		doc1.put("kind", map.getInteger("kind"));	
		cursor=mu.findLimit(table, doc1, (pn-1)*ps, ps);
		
	}else{
		
		cursor=mu.findLimit(table, null, (pn-1)*ps, ps);
		
	}
	

	List<Document> list=new ArrayList<Document>();
	if(cursor!=null){
		while(cursor.hasNext()){
			Document doc=cursor.next();
			list.add(doc);
		}
	}
	
	return list;
}
	
	
	public List<Document> query_manager(){
		
		List<Document> list = new ArrayList<Document>();
		
		MongoCursor<Document> cursor = mu.find("user_m", null);
		
		if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			list.add(doc);
    		}
    	}
		
		return list;
	}
	
	/**
	* XXXXX
	* @param map
	* @return
	*/
	public int countUser(String string, Document map) {
		List<Bson> tjlist=new ArrayList<Bson>();
    	if(map.get("mindate")!=null){
    		//大于等于
    		tjlist.add(Filters.gte("date", DateUtil.StringToDate(map.getString("mindate"))));
    	}
    	
    	if(map.get("maxdate")!=null){
    		//小于等于
    		tjlist.add(Filters.lte("date", DateUtil.StringToDate(map.getString("maxdate"))));
    	}
    	
    	if(map.getInteger("kind")!=null){
    		
    		tjlist.add(Filters.eq("kind", map.getInteger("kind")));
    	}
    	
    	if(map.get("is_order")!=null){
    		
    		tjlist.add(Filters.eq("is_order", 1));
    		tjlist.add(Filters.eq("is_over", 0));
    	}
		
    	return  (int)mu.count(string, Filters.and(tjlist));
	}
	
	public int countUserm(String string, Document map) {
		if(map.getString("name")!=null){
    	return  (int)mu.count(string, Filters.eq("name", map.getString("name")));
    		
    	}else{
    		
    		return  (int)mu.count(string, null);
    		
    	}
	}
	
	public int count_collection(String string, Document map) {
		 
    	return  (int)mu.count(string, Filters.eq("open_id", map.getString("open_id")));  		
	}
	
	public int count_topid(String string, Document map) {
		 
    	return  (int)mu.count(string, Filters.eq("open_id", map.getString("top_openid")));  		
	}
	
	public int count_course_order(String string, Document map) {
		 
    	return  (int)mu.count(string, map);  	 	
	}
	
	public int count_collection2(String string, Document map) {
		 
    	return  (int)mu.count(string, Filters.eq("c_no", map.getString("c_no")));  		
	}
	
	public int countfamily(String string, Document map) {
		if(map.getString("family_id")!=null){
    	return  (int)mu.count(string, Filters.eq("family_id", map.getString("family_id")));
    		
    	}else{
    		
    		return  (int)mu.count(string, null);
    		
    	}
	}
	
	public int countwxfamily(String string, Document map) {
		if(map.getString("open_id")!=null){
    	return  (int)mu.count(string, Filters.eq("open_id", map.getString("open_id")));
    		
    	}else{
    		
    		return  (int)mu.count(string, null);
    		
    	}
	}
	
	public  List<Document> findudetailinfo(String table, Document map){
		
    	int pn=map.getInteger("pn", 1);
    	int ps=map.getInteger("ps", 30);
    	MongoCursor<Document> cursor=null;
    	if(map.getString("u_no")!=null){
    	
    		cursor=mu.findLimit(table, Filters.eq("u_no", map.getString("u_no")), (pn-1)*ps, ps);
    		
    		
   		
    	}
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			list.add(doc);
    		}
    	}
		
		return list;
    }
	
public  List<Document> finduser_od(String table, Document map){
		
    	int pn=map.getInteger("pn", 1);
    	int ps=map.getInteger("ps", 30);
    	MongoCursor<Document> cursor=null;
    	if(map.getString("classmate_id")!=null){
    	
    		cursor=mu.findLimit(table, Filters.eq("classmate_id", map.getString("classmate_id")), (pn-1)*ps, ps);
    		
    		
   		
    	}
    	
    
    	List<Document> list=new ArrayList<Document>();
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			list.add(doc);
    		}
    	}
		
		return list;
    }
public  List<Document> findByPb1(String table, Document map){
		
    	int pn=map.getInteger("pn", 1);
    	int ps=map.getInteger("ps", 30);
    	MongoCursor<Document> cursor=null;
    	if(map.get("family_id")!=null){
    		cursor=mu.findLimit(table, Filters.eq("family_id", map.getString("family_id")), (pn-1)*ps, ps);
    	}
    	else{
    		cursor=mu.findLimit(table, null, (pn-1)*ps, ps);
    	}
    	List<Document> list=new ArrayList<Document>();
    	
    	if(cursor!=null){
    		while(cursor.hasNext()){
    			Document doc=cursor.next();
    			
    			list.add(doc);
    			
    		}
    	}
		
		return list;
    }
			
public  Document findbobyinfo(String table, Document map){
	


	MongoCursor<Document> cursor=null;
	
		if(map.get("name")!=null){
			cursor=mu.find(table, Filters.eq("name", map.getString("name")));
		}
		
	Document family = null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			family=cursor.next();
			  
		}
	}
	
	return family;
}

public  Document findsku_id(String table, Document map){
	


	MongoCursor<Document> cursor=null;
	
		if(map.get("sku_id")!=null){
			cursor=mu.find(table, Filters.eq("sku_id", map.getString("sku_id")));
		}
		
	Document family = null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			family=cursor.next();
			  
		}
	}
	
	return family;
}

public  Document findc_no(String table, Document map){
	


	MongoCursor<Document> cursor=null;
	
		if(map.get("sku_id")!=null){
			cursor=mu.find(table, Filters.eq("c_no", map.getString("sku_id")));
		}
		
	Document family = null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			family=cursor.next();
			  
		}
	}
	
	return family;
}

public  Document findc_special_no(String table, Document map){
	


	MongoCursor<Document> cursor=null;
	
		if(map.get("special_no")!=null){
			cursor=mu.find(table, Filters.eq("special_no", map.getString("special_no")));
		}
		
	Document family = null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			family=cursor.next();
			  
		}
	}
	
	return family;
}

public  Document findwxfamilyinfo(String table, Document map){
	
	MongoCursor<Document> cursor=null;
	if(map.get("open_id")!=null){
		cursor=mu.find(table, Filters.eq("open_id", map.getString("open_id")));
	}	
	
	Document doc=null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			 doc=cursor.next();
		}
	}
	
	return doc;
}	

public  Document find_course_order(String table, Document map){
	
	MongoCursor<Document> cursor=null;
	if(map.get("order_id")!=null){
		cursor=mu.find(table, Filters.eq("order_id", map.getString("order_id")));
	}	
	
	Document doc=null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			 doc=cursor.next();
		}
	}
	
	return doc;
}

public  Document find_classmate(String table, Document map){
	
	MongoCursor<Document> cursor=null;
	if(map.get("classmate_id")!=null){
		cursor=mu.find(table, Filters.eq("classmate_id", map.getString("classmate_id")));
	}	
	
	Document doc=null;
	
	if(cursor!=null){
		while(cursor.hasNext()){
			 doc=cursor.next();
		}
	}
	
	return doc;
}
	public Document close(String table,Document map){
		
		MongoCursor<Document> cursor=null;
		
		if(map.get("open_id")!=null){
			
			cursor=mu.find(table, Filters.eq("family_member.open_id", map.getString("open_id")));
		}
				
		
				Document family = null;
				
				if(cursor!=null){
					while(cursor.hasNext()){
						family=cursor.next();
					
			  
		}
	}
	
	return family;
	}
	public void replaceOne(String collection, Bson condition, Document document) {
		mu.replaceOne(collection, condition, document);
       
    }
	
	
	   public void insertOne(String collection, Document document) {
	        mu.insertOne(collection, document);
	    }
	   
	   
	   public Document query_setting(){
		   MongoCursor<Document> cursor=mu.find("setting", null);
			
			Document set = null;
			 
			while (cursor.hasNext()) {
	               set = cursor.next(); 
	        }
			
			return set;
	   }
	   
	   public Document query_config(){
		   MongoCursor<Document> cursor=mu.find("config_s", null);
			
			Document set = null;
			 
			while (cursor.hasNext()) {
	               set = cursor.next(); 
	        }
			
			return set;
	   }
	   
	   public List<Document> query_order_baby(String openid){
		   
		   List<Document> order_list = new ArrayList<Document>();
		   
		   			Document doc = new Document();
		   			doc.put("openid", openid);
		   			
		   			List<Integer> list=new ArrayList<Integer>();
				//	list.add(3);
					list.add(4);
					list.add(5); 
					
					Map<String, Object> map3 = new HashMap<String, Object>(); 
					map3.put("$in", list);
					
					doc.put("state", map3);
					
					doc.put("service_kind", 0);
					
					MongoCursor<Document> cursor = mu.find("order", doc);
					
					while (cursor.hasNext()) {
						order_list.add(cursor.next()); 
					}
					
					return order_list;
	   }
	   
	   public List<Document> query_item(){
		   MongoCursor<Document> cursor=mu.find("item", null);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }
	   public List<Document> course_orders(Document map){
		   MongoCursor<Document> cursor=mu.find("course_order", map);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }
	   
	   
	   public List<Document> find_phone(Document map){
		   MongoCursor<Document> cursor=mu.find("phone_list", null);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }
	   
	   public  Document finditem(String table, Document map){
			
			MongoCursor<Document> cursor=null;
			if(map.get("id")!=null){
				cursor=mu.find(table, Filters.eq("_id", new ObjectId(map.getString("id"))));
			}	
			
			Document doc=null;
			
			if(cursor!=null){
				while(cursor.hasNext()){
					 doc=cursor.next();
				}
			}
			
			return doc;
		}
	   
	   public  Document findcontent(String table, Document map){
			
				MongoCursor<Document> cursor=null;
				if(map.get("table_id")!=null){
					cursor=mu.find(table, Filters.eq("_id", new ObjectId(map.getString("table_id"))));
				}	
				
				Document doc=null;
				
				if(cursor!=null){
					while(cursor.hasNext()){
						 doc=cursor.next();
					}
				}
				
				return doc;
			}
	   
	   /**
		 * 根据open_id查询用户
		 */
		public Document query_customer(String open_id){
			
			Document customer = new Document();
			
			BasicDBObject bdb = new BasicDBObject();
			bdb.put("open_id", open_id);
			
			customer = mu.findOne("user", bdb);
			
			return customer;
		}
		
		
		public Document find_scholarship(String table, Document map){
			
			MongoCursor<Document> cursor=null;
			if(map.get("open_id")!=null){
				cursor=mu.find(table, Filters.eq("open_id", map.getString("open_id")));
			}	
			
			Document doc=null;
			
			if(cursor!=null){
				while(cursor.hasNext()){
					 doc=cursor.next();
				}
			}
			
			return doc;
		}
		
	public Document find_scholarship22(String table, Document map){
			
			MongoCursor<Document> cursor=null;
		 
				cursor=mu.find(table, map);
			 	
			
			Document doc=null;
			
			if(cursor!=null){
				while(cursor.hasNext()){
					 doc=cursor.next();
				}
			}
			
			return doc;
		}
	   
		/**
		 * 根据用户open_id更新user: phone
		 */
		public void update_user_phone(String phone,String open_id ,String kh_name){
			
			Document user = mu.findOne("user", new Document().append("open_id", open_id));
			user.put("phone",phone);
			user.put("name",kh_name);
			mu.replaceOne("user", Filters.eq("open_id", open_id), user);
			
		}
		
		  public void deleteMany(String collection, Document document) {
			  mu.insertOne(collection, document);
		    }
		  
		  
		  public  Document finduser(String table, Document map){
				
				MongoCursor<Document> cursor=null;
				if(map.get("u_no")!=null){
					cursor=mu.find(table, Filters.eq("u_no", map.getString("u_no")));
				}	
				
				Document doc=null;
				
				if(cursor!=null){
					while(cursor.hasNext()){
						 doc=cursor.next();
					}
				}
				
				return doc;
			}
		  

		  public  Document finduserm(String table, Document map){
				
				MongoCursor<Document> cursor=null;
				if(map.get("name")!=null){
					cursor=mu.find(table, Filters.eq("name", map.getString("name")));
				}	
				
				Document doc=null;
				
				if(cursor!=null){
					while(cursor.hasNext()){
						 doc=cursor.next();
					}
				}
				
				return doc;
			}
			public int countphone(String string, Document map) {
				if(map.getString("phone")!=null){
		    	return  (int)mu.count(string, Filters.eq("phone", map.getString("phone")));
		    		
		    	}else{
		    		
		    		return  (int)mu.count(string, null);
		    		
		    	}
			}
			/**
			 * 查询用户的角色信息
			 */
			public Map<String,Object> query_role_info(int role_id){
				
				Map<String,Object> map = null;
				
				BasicDBObject bdb = new BasicDBObject(); 
				bdb.put("role_id", role_id);
				
				MongoCursor<Document> cursor=mu.find("role", bdb);
				
				while (cursor.hasNext()) {
		            Document ddd = cursor.next(); 
		            map = (Map<String,Object>)ddd;  
				}
				
				return map;
			}
			
			public List<Map<String,Object>> query_menu(){
				
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				
				MongoCursor<Document> cursor=mu.find("menu", null);
				
				while (cursor.hasNext()) {
		            Document ddd = cursor.next(); 
		            Map<String,Object> it = (Map<String,Object>)ddd; 
		            list.add(it);
				}
				
				return list;
			}
			
			public List<Map<String,Object>> query_user_name(String name){
				
				List<Map<String,Object>> user_list = new ArrayList<Map<String,Object>>();
				
				//根据姓名查询用户 
				BasicDBObject bdb = new BasicDBObject(); 
				bdb.put("name", name); 
				MongoCursor<Document> cursor=mu.find("user_m", bdb); 
				while (cursor.hasNext()) {
		             Document user = cursor.next(); 
		             Map<String,Object> it_user = (Map<String,Object>)user; 
		             user_list.add(it_user);
				}
				
				return user_list;
			}
			
			 public List<Document> find_course_list(Document map){
				 
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    	
			    	/*Document sort = new Document();
					 sort.put("watch", -1); //按 倒序
*/			    	
			    	
			    	Document doc =new Document();
			    	
			    	doc.put("type", map.getInteger("type"));
			    	
				   MongoCursor<Document> cursor=mu.findLimit("course", doc, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               set.put("isBuy", 0);
			               list.add(set);
			        }
					
					return list;
			   }	
			 
			 
	 public List<Document> find_course_list_c(Document map){
				 
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    	
			    	/*Document sort = new Document();
					 sort.put("watch", -1); //按 倒序
*/			    	
			    	
			    	Document doc =new Document();
			    	
			    	doc.put("type", map.getInteger("type"));
			    	doc.put("state", 1);
			    	doc.put("is_free", "-1");
			    	
				   MongoCursor<Document> cursor=mu.findLimit("course", doc, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               set.put("isBuy", 0);
			               list.add(set);
			        }
					
					return list;
			   }	
			 
			 public List<Document> find_user_list(){
				   MongoCursor<Document> cursor=mu.find("user", null);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_study_list(String table,Document map){
				   MongoCursor<Document> cursor=mu.find(table, map);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			             
			               list.add(set);
			        }
					
					return list;
			   }
			 public List<Document> find_course_list_limt(String table,Document map,String type2){
				 int type =0;
				 if(type2!=null&&!type2.equals("")){
					 	type=Integer.valueOf(type2);
				 }
				 
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    	Document tj=new Document();
			    	if(map.getString("is_free")!=null &&!map.getString("is_free").equals("")){
			    		tj.put("is_free", map.getString("is_free"));
			    		
			    	}
			    	
			    	tj.put("type", type);
			    	tj.put("state", 1);
			    	 MongoCursor<Document> cursor=null;
			    	 
			    	
			    	cursor=mu.findLimit(table, tj, (pn-1)*ps, ps);
			    	 
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 
			 public List<Document> find_course_list_limt222(String table,Document map,String type2){
				 int type =0;
				 if(type2!=null&&!type2.equals("")){
					 	type=Integer.valueOf(type2);
				 }
				 
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    	Document tj=new Document();
			    	
			    	tj.put("type", type);
			    	tj.put("state", 1);
			    	tj.put("is_sepecial", 1);
			    	tj.put("is_free", "1");
			    	 MongoCursor<Document> cursor=null;
			    	 
			    		Document sort =new Document();
			    		sort.put("_id", -1);
			    	cursor=mu.findLimitSort(table, tj, (pn-1)*ps, ps,sort);
			    	 
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_special_list(){
				   MongoCursor<Document> cursor=mu.find("special", null);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }	
			 
			 
			 public List<Document> find_scholarship_list(String table, Document map){
				
				 map.put("scholarship", -1);
				 
				   MongoCursor<Document> cursor=mu.findLimitSort("scholarship", null, 0,30, map);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_score_list(String table, Document map){
					
				 map.put("score", -1);
				 
				   MongoCursor<Document> cursor=mu.findLimitSort("user", null, 0,30, map);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_course_order_list(){
				   MongoCursor<Document> cursor=mu.find("course_order", null);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_course_order_list2(String table,Document doc){
				 
				 
				   MongoCursor<Document> cursor=mu.find("course_order", Filters.eq("open_id", doc.getString("open_id")));
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_special(){
				 
				 
				   MongoCursor<Document> cursor=mu.find("special",null);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_course_order22(String table,Document doc){
				 
				 
				   MongoCursor<Document> cursor=mu.find(table,doc);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_special_lmilt(String table,Document map){
				
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    		Document tj=new Document();
			      
			    	tj.put("state", 1);
				   MongoCursor<Document> cursor=mu.findLimit(table,tj, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_special_lmilt4(String table,Document map){
					
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    		Document tj=new Document();
			    	tj.put("type", 1);
			    	tj.put("state", 1);
				   MongoCursor<Document> cursor=mu.findLimit(table,tj, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_special_lmilt5(String table,Document map){
					
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    		Document tj=new Document();
			    	tj.put("type", 0);
			    	tj.put("state", 1);
				   MongoCursor<Document> cursor=mu.findLimit(table,tj, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 public List<Document> find_special_lmilt2(String table,Document map){
					
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    		Document tj=new Document();
			      
			    	tj.put("state", 1);
			    	tj.put("kind", 1);
				   MongoCursor<Document> cursor=mu.findLimit(table,tj, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 public List<Document> find_special_lmilt8(String table,Document map){
					
				 int pn=map.getInteger("pn",map.getInteger("pn"));
			    	int ps=map.getInteger("ps",map.getInteger("ps"));
			    		Document tj=new Document();
			      
			    	tj.put("state", 1);
			    	tj.put("kind", 2);
				   MongoCursor<Document> cursor=mu.findLimit(table,tj, (pn-1)*ps, ps);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
			 
			 
			 
			 public  Document  find_config(String table){
				   MongoCursor<Document> cursor=mu.find(table, null);
					
					Document set = null;
				 
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			                
			        }
					
					return set;
			   }
			 
			 
			 public List<Document> find_discuss_list(){
				   MongoCursor<Document> cursor=mu.find("discuss", null);
					
					Document set = null;
					List<Document> list=new ArrayList<Document>();
					while (cursor.hasNext()) {
			               set = cursor.next(); 
			               list.add(set);
			        }
					
					return list;
			   }
	 public  Document findcourse_detail(String table, Document map){
					
			MongoCursor<Document> cursor=null;
			if(map.get("c_no")!=null){
				cursor=mu.find(table, Filters.eq("c_no", map.getString("c_no")));
			}	
			
			Document doc=null;
			
			if(cursor!=null){
				while(cursor.hasNext()){
					 doc=cursor.next();
				}
			}
			
			return doc;
		}
	 
	 
	 
	 
	 
	 public  Document findspecial_detail(String table, Document map){
			
			MongoCursor<Document> cursor=null;
			if(map.get("special_no")!=null){
				cursor=mu.find(table, Filters.eq("special_no", map.getString("special_no")));
			}	
			
			Document doc=null;
			
			if(cursor!=null){
				while(cursor.hasNext()){
					 doc=cursor.next();
				}
			}
			
			return doc;
		}
	 
	   
	   
	   public int find_sign_log(String string, Document map) {
	 		return  (int)mu.count(string, map);
 	 
		}
	   
	   public List<Document> show_collect(String table, Document map){
		   MongoCursor<Document> cursor=mu.find(table, map);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }
	   
	   
	   public List<Document> show_collect_2(String table, Document map){
		   int pn=map.getInteger("pn",map.getInteger("pn"));
	    	int ps=map.getInteger("ps",map.getInteger("ps"));
	    		Document tj=new Document();
	    		
	    	tj.put("is_free", "-1");	
	    	tj.put("state", 1);
	    	
	    	Document sort = new Document();
			sort.put("_id", -1); //按_id 倒序
	    MongoCursor<Document> cursor = mu.findLimitSort(table, tj, (pn-1)*ps, ps, sort);
		 //  MongoCursor<Document> cursor=mu.findLimitSort(table,tj, (pn-1)*ps, ps,sort);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }
	   
	  
	   
	   public List<Document> show_course_order(String table, Document map){
		   MongoCursor<Document> cursor=mu.find(table, map);
			
		   List<Document> list=new ArrayList<Document>();
	    	if(cursor!=null){
	    		while(cursor.hasNext()){
	    			Document doc=cursor.next();
	    			list.add(doc);
	    		}
	    	}
			
			return list;
	   }


	/**
	* 根据openid获取用户课程/专题
	* @param map
	* @return
	*/
	public List<Document> find_course_order_list(String open_id,int type) {
		List<Bson> tj=new ArrayList<Bson>();
		
		tj.add(Filters.eq("open_id", open_id));
		if(type==1){
			//课程
			tj.add(Filters.ne("c_no", null));
		}else{
			//专题
			tj.add(Filters.ne("c_no", null));
		}
		
		
		
		MongoCursor<Document> cursor=mu.find("course_order", Filters.and(tj));
		
		Document set = null;
		List<Document> list=new ArrayList<Document>();
		while (cursor.hasNext()) {
               set = cursor.next(); 
               list.add(set);
        }
		
		return list;
	}


	/**
	* XXXXX
	* @param map
	* @return
	*/
	public void addWatch(String id) {
		Bson B=Filters.eq("$inc", Filters.eq("watch", 1));
		mu.updateOne("course", Filters.eq("c_no", id),B);
	
	}
	
	public void addWatch2(String id) {
		Bson B=Filters.eq("$inc", Filters.eq("watch", 1));
		mu.updateOne("special", Filters.eq("special_no", id),B);
	
	}
	
	public List<Document> query_share_page(int skip,int limit){
		
		List<Document> list = new ArrayList<Document>();
		
		Document sort = new Document();
		sort.put("is_add", -1); //按_id 倒序
		
		MongoCursor<Document> cursor = mu.findLimitSort("share_log", null, skip, limit, sort);
		 
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			 list.add(post);
		 } 
		return list;
	}
	
	
public List<Document> query_share(String table,String openid){
		
		
	List<Document> list = new ArrayList<Document>();
	Document sort = new Document();
		sort.put("is_add", 1);
		sort.put("open_id", openid);
		MongoCursor<Document> cursor =  mu.find(table, sort);
		
		while(cursor.hasNext()){
			 Document art = cursor.next(); 
			   
			 list.add(art);
		 } 
		 
		 return list;
	}
	
public List<Document> query_tanchu(){
		
		List<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor =  mu.find("tanchu", null);
		
		while(cursor.hasNext()){
			 Document art = cursor.next(); 
			   
			 list.add(art);
		 } 
		 
		 return list;
	}

public void update_lunbo(String _id,Document doc){
	
	mu.replaceOne("tanchu", Filters.eq("_id", new ObjectId(_id)), doc);
}

public Document query_one_lunbo(String _id){
	
	Document doc = new Document();  
	doc.put("_id", new ObjectId(_id)); 
	Document art = mu.findOne("tanchu", doc);
	
	return art;
}
}
