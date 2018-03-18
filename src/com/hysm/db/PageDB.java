package com.hysm.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class PageDB {
	private MongoUtil mu=MongoUtil.getThreadInstance();

	private String TABLE="a_pages";
	
	/**
	 * 获取所有页面
	 * @param map
	 * @return
	 */
	public List<Document> queryUrlList(Map<String, Object> map) {
		int type=(Integer)map.get("type");
		int pn=(Integer)map.get("pn");
		int ps=(Integer)map.get("ps");
		MongoCursor<Document> cur=mu.findLimit(TABLE, Filters.eq("type", type), (pn-1)*ps, ps);
		
		if(cur!=null){
			List<Document> list=new ArrayList<Document>();
			while(cur.hasNext()){
				list.add(cur.next());
			}
			return list;
		}
		
		return null;
	}



	/**
	 * 
	 * 获取页面数量
	 * @param map
	 * @return
	 */
	public int queryUrlListNum(Map<String, Object> map) {
		
		int type=(Integer)map.get("type");
		
		return (int)mu.count(TABLE, Filters.eq("type", type));
	}



	/**
	 * 删除页面
	 * @param id
	 */
	public void delPages(String id) {
		
		mu.deleteMany(TABLE, Filters.eq("_id", new ObjectId(id)));
	}



	/**
	 * 修改页面状态
	 * @param id
	 * @param use
	 * @return
	 */
	public void updatePagesUse(String id, String use) {
		Document du=mu.findOne(TABLE, Filters.eq("_id", new ObjectId(id)));
		du.put("isUse", Integer.valueOf(use));
		 mu.replaceOne(TABLE, Filters.eq("_id", new ObjectId(id)), du);
	}



	/**
	 * 查询单个
	 * @param id
	 * @return
	 */
	public Document queryUrlOne(String id) {
		// TODO Auto-generated method stub
		return mu.findOne(TABLE, Filters.eq("_id", new ObjectId(id)));
	}

	public String queryUrlOneStr(String id) {
		// TODO Auto-generated method stub
		return mu.findOne(TABLE, Filters.eq("_id", new ObjectId(id))).toJson();
	}

	/**
	 *修改
	 * @param ap
	 */
	public void updatePageDetail(Document ap) {
		 mu.replaceOne(TABLE, Filters.eq("_id",ap.get("_id")), ap);
		
	}



	/**
	 * 修改主页
	 * @param id
	 */
	public void mainpage(String id) {
		
		//所有也设置为非主业
		mu.updateMany(TABLE,null, (Bson)new Document().put("isMain", -1));
		
		mu.updateMany(TABLE, Filters.eq("_id", new ObjectId(id)), (Bson)new Document().put("isMain", 1));
	}



	public void createPage(Document json) {
		
		 mu.insertOne(TABLE, json);
	}
	
	
	
	
}
