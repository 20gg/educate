package com.hysm.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.PageBean;
import com.hysm.tools.StringUtil;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

@Component
public class GoodsDB {
	private MongoUtil mu=MongoUtil.getThreadInstance();
	private String TABLE_1="course";//课程
	private String TABLE_2="special";//专题
	/**
	 * 免费课程总数
	 * @param pb
	 * @param likename
	 * @param goodsstr
	 * @return
	 */
	public int countNumForCourse(String likename,
			String goodsstr,int type,String kind) {
		List<Bson> list=new ArrayList<Bson>();
		
		if(StringUtil.bIsNotNull(likename)){
			 Pattern pattern = Pattern.compile("^.*" +  likename.trim()+ ".*$", Pattern.CASE_INSENSITIVE);
			list.add((Bson)new Document().put("c_name", pattern));
			
		}
		if(StringUtil.bIsNotNull(goodsstr)){
			
			JSONArray ja=JSONArray.fromObject(goodsstr);
			list.add(Filters.nin("c_no", ja));
		}
		
		if(type==2){
			list.add(Filters.eq("is_free", "-1"));//免费
		}
		if(StringUtil.bIsNotNull(kind)){
			
			list.add(Filters.eq("type", Integer.valueOf(kind)));//
		}
		
		
		
		return (int)mu.count(TABLE_1, Filters.and(list));
	}
	/**
	 * 课程
	 * @param pb
	 * @param likename
	 * @param goodsstr
	 * @return
	 */
	public List<JSONObject> queryForCourse(PageBean<JSONObject> pb,
			String likename, String goodsstr,int type,String kind) {
	List<Bson> list=new ArrayList<Bson>();
		
		if(StringUtil.bIsNotNull(likename)){
			 Pattern pattern = Pattern.compile("^.*" +  likename.trim()+ ".*$", Pattern.CASE_INSENSITIVE);
			list.add((Bson)new Document().put("c_name", pattern));
			
		}
		if(StringUtil.bIsNotNull(goodsstr)){
			
			JSONArray ja=JSONArray.fromObject(goodsstr);
			list.add(Filters.nin("c_no", ja));
		}
		
		if(type==2){
			list.add(Filters.eq("is_free", "-1"));//免费
		}
		if(StringUtil.bIsNotNull(kind)){
			
			list.add(Filters.eq("type", Integer.valueOf(kind)));//
		}

		int  pn=pb.getPageNum();
		int ps=pb.getPageSize();
		
		
		
		MongoCursor<Document>  cur=mu.findLimit(TABLE_1,  Filters.and(list), (pn-1)*ps, ps);
		List<JSONObject> ja=new ArrayList<JSONObject>();
		
		while(cur.hasNext()){
			Document dou=cur.next();
			if(dou!=null){
				JSONObject one=JSONObject.fromObject(dou.toJson());
				one.put("id", one.get("c_no"));
				one.put("name", one.get("c_name"));
				ja.add(one);
			}
			
			
		}
		
		return ja;
		
	}
	
	/**
	 * 专题数
	 * @param likename
	 * @param goodsstr
	 * @return
	 */
	public int countNumForSpecial(String likename, String goodsstr) {
		List<Bson> list=new ArrayList<Bson>();
		
		if(StringUtil.bIsNotNull(likename)){
			 Pattern pattern = Pattern.compile("^.*" +  likename.trim()+ ".*$", Pattern.CASE_INSENSITIVE);
			list.add((Bson)new Document().put("special_name", pattern));
			
		}
		if(StringUtil.bIsNotNull(goodsstr)){
			
			JSONArray ja=JSONArray.fromObject(goodsstr);
			list.add(Filters.nin("special_no", ja));
		}
		
		list.add(Filters.eq("kind", 1));
		
		
		return (int)mu.count(TABLE_2, Filters.and(list));
	}
	/**
	 * 专题列表
	 * @param pb
	 * @param likename
	 * @param goodsstr
	 * @return
	 */
	public List<JSONObject> queryForSpecial(PageBean<JSONObject> pb,
			String likename, String goodsstr) {
List<Bson> list=new ArrayList<Bson>();
		
		if(StringUtil.bIsNotNull(likename)){
			 Pattern pattern = Pattern.compile("^.*" +  likename.trim()+ ".*$", Pattern.CASE_INSENSITIVE);
			list.add((Bson)new Document().put("special_name", pattern));
			
		}
		if(StringUtil.bIsNotNull(goodsstr)){
			
			JSONArray ja=JSONArray.fromObject(goodsstr);
			list.add(Filters.nin("special_no", ja));
		}
		
		list.add(Filters.eq("kind", 1));
		list.add(Filters.eq("state", 1));
		int  pn=pb.getPageNum();
		int ps=pb.getPageSize();
		
		
		
		MongoCursor<Document>  cur=mu.findLimit(TABLE_2,  Filters.and(list), (pn-1)*ps, ps);
		List<JSONObject> ja=new ArrayList<JSONObject>();
		
		while(cur.hasNext()){
			Document dou=cur.next();
			if(dou!=null){
				JSONObject one=JSONObject.fromObject(dou.toJson());
				one.put("id", one.get("special_no"));
				one.put("name", one.get("special_name"));
				ja.add(one);
			}
			
			
		}
		
		return ja;
		
	}
	
	/**
	 * 前10课程
	 * @return
	 */
	public List<JSONObject> queryForCourseTop10() {
		
		MongoCursor<Document>  cur=mu.findLimitSort(TABLE_1,  null, 0, 10,(Bson)new Document().put("watch", "1"));
		
List<JSONObject> ja=new ArrayList<JSONObject>();
		
		while(cur.hasNext()){
			Document dou=cur.next();
			if(dou!=null){
				JSONObject one=JSONObject.fromObject(dou.toJson());
				one.put("id", one.get("c_no"));
				one.put("name", one.get("c_name"));
				one.remove("text");
				ja.add(one);
			}
			
			
		}
		
		return ja;
		
		
	}
	/**
	 * 前10专题
	 * @return
	 */
	public List<JSONObject> queryForSpecialTop10() {
		MongoCursor<Document>  cur=mu.findLimitSort(TABLE_2,  null, 0, 10,(Bson)new Document().put("watch", "1"));
		
		List<JSONObject> ja=new ArrayList<JSONObject>();
				
		while(cur.hasNext()){
			Document dou=cur.next();
			if(dou!=null){
				JSONObject one=JSONObject.fromObject(dou.toJson());
				one.put("id", one.get("special_no"));
				one.put("name", one.get("special_name"));
				ja.add(one);
			}
			
			
		}
		
		return ja;
	}
	
	

}
