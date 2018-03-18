package com.hysm.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.hysm.db.mongo.MongoUtil;
import com.hysm.tools.DateTool;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Component
public class Msg_db {

	MongoUtil mu=MongoUtil.getThreadInstance();
	
	/**
	 * 查询模板
	 * @param type
	 * @return
	 */
	public Document get_mould(int type){
		
		Document doc = new Document();
		doc.put("type", type);
		Document mould = mu.findOne("msg_mould", doc);
		
		return mould;
	}
	
	
	public void add_msg(Map<String,Object> map){
		
		int msg_type =Integer.valueOf(map.get("msg_type").toString());
		
		Document doc1 = new Document();
		doc1.put("type", msg_type);
		Document mould = mu.findOne("msg_mould", doc1);
		
		//对方openid 
		String openid = map.get("openid").toString();  
		String c_time = DateTool.fromDate24H();
		
		Document msg = new Document();
		
		msg.put("touser", openid);
		msg.put("template_id", mould.getString("template_id"));  
		msg.put("url", mould.getString("url")); 
		msg.put("is_send", 0);
		msg.put("c_time",c_time );
		  
		if(msg_type == 1){
			//购买课程，或专题 
			String c_name = map.get("c_name").toString(); 
			
			//int c_kind = Integer.valueOf(map.get("c_kind").toString());  // 1 课程  2专题
			 
			int pay_money = Integer.valueOf(map.get("pay_money").toString());
			
			double m = (Double.valueOf(pay_money)/100);
			
			//String order_id = map.get("order_id").toString(); 
			  
			Map<String,String> first = new HashMap<String, String>();
			first.put("value", "恭喜你成功购买"+c_name+",到答课堂欢迎您的加入！");
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", "有效期"+map.get("safe_date").toString());
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", c_name);
			keyword2.put("color", "#2dc158");
			
			Map<String,String> keyword3 = new HashMap<String, String>();
			keyword3.put("value",  m+"元" );
			keyword3.put("color", "#2dc158");
			
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", "学管理，上到答！ 1.内容采用视频＋音频＋图文的形式，定期更新； 2.有效期内，您可随时查看订购的内容进行学习；"
					+"3.本产品内容一经订购成功概不退款，请您理解； 如有任何问题请致电：400 888 0319，到答课堂，让管理从未如此简单！ 点击<详情>开始学习！");
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);
			data.put("keyword3", keyword3);
			data.put("remark", remark);
			
			msg.put("data",data ); 
			mu.insertOne("message", msg);
			
		}else if(msg_type == 2){
			//购买vip
			
			int pay_money = Integer.valueOf(map.get("pay_money").toString());
			
			double m = (Double.valueOf(pay_money)/100);
			
			String c_first = map.get("c_first").toString();
			
			Map<String,String> first = new HashMap<String, String>();
			first.put("value", c_first);
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", "有效期"+map.get("safe_date").toString());
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", "全站vip");
			keyword2.put("color", "#2dc158");
			
			Map<String,String> keyword3 = new HashMap<String, String>();
			keyword3.put("value", m+"元" );
			keyword3.put("color", "#2dc158");
			
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", "学管理，上到答！ 1.内容采用视频＋音频＋图文的形式，定期更新； 2.有效期内，您可随时查看订购的内容进行学习；"
					+"3.本产品内容一经订购成功概不退款，请您理解； 如有任何问题请致电：400 888 0319，到答课堂，让管理从未如此简单！ 点击<详情>开始学习！");
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2); 
			data.put("remark", remark); 
			
			msg.put("data",data ); 
			mu.insertOne("message", msg);
			
		}else if(msg_type == 3){
			//奖学金变化
			
			int cut = Integer.valueOf(map.get("cut").toString()); 
			double m = (Double.valueOf(cut)/100);
			
			int is_add = Integer.valueOf(map.get("is_add").toString()); // 1获得  2消费
			
			String add_str = "扣除";
			if(is_add == 1){
				add_str = "获得";
			}
			
			Map<String,String> first = new HashMap<String, String>();
			
			if(is_add == 1){
				first.put("value", "好友产生消费，您的奖学金发生变化啦！");
			}else{
				first.put("value", "你使用了奖学金，您的奖学金发生变化啦！");
			} 
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", add_str+m+"元");
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", c_time );
			keyword2.put("color", "#2dc158");
			
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", map.get("note").toString());
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2); 
			data.put("remark", remark); 
			
			msg.put("data",data ); 
			mu.insertOne("message", msg);
		}else if(msg_type == 4){
			//导师回复  取消  
			
			String send_name = map.get("send_name").toString();
			String send_text = map.get("send_text").toString();
			
			String back_name = map.get("back_name").toString();
			String back_text = map.get("back_text").toString();
			
			Map<String,String> first = new HashMap<String, String>();
			first.put("value", "导师回复");
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", send_name);
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", send_text );
			keyword2.put("color", "#2dc158");
			
			Map<String,String> keyword3 = new HashMap<String, String>();
			keyword3.put("value", back_text);
			keyword3.put("color", "#2dc158");
			
			Map<String,String> keyword4 = new HashMap<String, String>();
			keyword4.put("value", back_name );
			keyword4.put("color", "#2dc158");
			
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", "时间："+c_time);
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2); 
			data.put("keyword3", keyword3);
			data.put("keyword4", keyword4); 
			data.put("remark", remark); 
			
			msg.put("data",data ); 
			mu.insertOne("message", msg);
			
		}else if(msg_type == 11){ 
			//学分提醒
			
			Map<String,String> first = new HashMap<String, String>();
			first.put("value", map.get("frist").toString());
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", map.get("keyword1").toString());
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", c_time );
			keyword2.put("color", "#2dc158");
			
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", map.get("note").toString());
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);  
			data.put("remark", remark); 
			msg.put("data",data ); 
			mu.insertOne("message", msg);
		}
		
	}
	
	/**
	 *    专题 更新 推送消息
	* XXXXX
	* @param map
	* @return
	 */
	public void add_msg2(Map<String,Object> map){
		
		int msg_type =Integer.valueOf(map.get("msg_type").toString());
		
		Document doc1 = new Document();
		doc1.put("type", msg_type);
		Document mould = mu.findOne("msg_mould", doc1);
		
		 
	 
		String c_time = DateTool.fromDate24H();
		
		Document msg = new Document();
		
		  
		msg.put("template_id", mould.getString("template_id"));  
		msg.put("url", mould.getString("url")); 
		msg.put("is_send", 0);
		msg.put("c_time",c_time );
		msg.put("special_no",map.get("special_no") );
		  
		if(msg_type == 5){
			  
			
			 
			  
			Map<String,String> first = new HashMap<String, String>();
			first.put("value", "你好，你已购买专题"+map.get("send_name")+"更新啦");
			first.put("color", "#333333");
			
			Map<String,String> keyword1 = new HashMap<String, String>();
			keyword1.put("value", map.get("send_name").toString());
			keyword1.put("color", "#2dc158");
			
			Map<String,String> keyword2 = new HashMap<String, String>();
			keyword2.put("value", map.get("send_text").toString() );
			keyword2.put("color", "#2dc158");
			 
			Map<String,String> remark = new HashMap<String, String>();
			remark.put("value", "时间："+c_time);
			remark.put("color", "#333333");
			
			Map<String,Object> data = new HashMap<String, Object>();
			
			data.put("first", first);
			data.put("keyword1", keyword1);
			data.put("keyword2", keyword2);
			 
			data.put("remark", remark);
			
			msg.put("data",data ); 
			mu.insertOne("message_second", msg);
			
		}
		
	}
	
	public List<Document> query_no_send(){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_send", 0); 
		
		MongoCursor<Document> cursor = mu.find("message", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			  
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public void update_message(String _id,Document doc){
		
		mu.replaceOne("message", Filters.eq("_id", new ObjectId(_id)), doc);
	}
	
	public List<Document> query_no_send2(){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_send", 0); 
		
		MongoCursor<Document> cursor = mu.find("message_second", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			  
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	public List<Document> query_s_order(String s_no){
		
		List<Document> list = new ArrayList<Document>();
		
		Document doc = new Document();
		doc.put("is_order", 1);
		doc.put("is_over", 0);
		doc.put("c_no", s_no);
		
		MongoCursor<Document> cursor = mu.find("course_order", doc);
		
		 while(cursor.hasNext()){
			 Document post = cursor.next(); 
			  
			 list.add(post);
		 } 
		 
		 return list;
	}
	
	
public void update_message2(String _id,Document doc){
		
		mu.replaceOne("message_second", Filters.eq("_id", new ObjectId(_id)), doc);
	}


public  Document  query_s_order2(String special_no){
	
	 Document post =null;
	
	Document doc = new Document();
	doc.put("special_no", special_no);
	 
	
	MongoCursor<Document> cursor = mu.find("special", doc);
	
	 while(cursor.hasNext()){
		  post = cursor.next(); 
		  		 
	 } 
	 
	 return post;
}

/**
 *   课程上线 推送消息
* XXXXX
* @param map
* @return
 */
public void add_msg3(Map<String,Object> map){
	
	int msg_type =Integer.valueOf(map.get("msg_type").toString());
	
	Document doc1 = new Document();
	doc1.put("type", msg_type);
	Document mould = mu.findOne("msg_mould", doc1);
	
	 
 
	String c_time = DateTool.fromDate24H();
	
	Document msg = new Document();
	
	  
	msg.put("template_id", mould.getString("template_id"));  
	msg.put("url", map.get("address")); 
	msg.put("is_send", 0);
	msg.put("c_time",c_time );
	/*msg.put("title",map.get("title"));
	msg.put("name",map.get("name"));
	msg.put("note",map.get("note"));
	msg.put("type",msg_type);*/
	if(msg_type == 6){
		   
		Map<String,String> first = new HashMap<String, String>();
		first.put("value", map.get("title").toString());
		first.put("color", "#333333");
		
		Map<String,String> keyword1 = new HashMap<String, String>();
		keyword1.put("value", map.get("name").toString());
		keyword1.put("color", "#2dc158");
		
		Map<String,String> keyword2 = new HashMap<String, String>();
		keyword2.put("value", ""+c_time);
		keyword2.put("color", "#2dc158");
		
		Map<String,Object> data = new HashMap<String, Object>();
		
		if(map.get("note").toString()!=null &&!map.get("note").toString().equals("")){
			
			Map<String,String> keyword3 = new HashMap<String, String>();
			keyword2.put("value", map.get("note").toString());
			keyword2.put("color", "#2dc158");
			
			data.put("keyword3", keyword3);
		}
		 
		Map<String,String> remark = new HashMap<String, String>();
		remark.put("value", map.get("note").toString());
		remark.put("color", "#333333");
		
		
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		 
		data.put("remark", remark);
		
		msg.put("data",data ); 
		mu.insertOne("message_second", msg);
		
	}
	
}

	public Document find_mould(int msg_type){
		
		Document doc1 = new Document();
		doc1.put("type", msg_type);
		Document mould = mu.findOne("msg_mould", doc1);
		
		return mould;
	}

	 
/**
 * 		活动提醒   模板消息
* XXXXX
* @param map
* @return
 */
public void add_msg4(Map<String,Object> map){
	
	int msg_type =Integer.valueOf(map.get("msg_type").toString());
	
	Document doc1 = new Document();
	doc1.put("type", msg_type);
	Document mould = mu.findOne("msg_mould", doc1);
	
	  
	
	Document msg = new Document();
	
	  
	msg.put("template_id", mould.getString("template_id"));  
	msg.put("url", map.get("address")); 
	msg.put("is_send", 0);
	msg.put("time",map.get("time") );
	/*msg.put("title",map.get("title"));
	msg.put("name",map.get("name"));
	msg.put("place",map.get("place"));
	msg.put("phone",map.get("phone"));
	msg.put("note",map.get("note"));
	msg.put("type",msg_type);*/
	if(msg_type == 7){
		   
		Map<String,String> first = new HashMap<String, String>();
		first.put("value", map.get("title").toString());
		first.put("color", "#333333");
		
		Map<String,String> keyword1 = new HashMap<String, String>();
		keyword1.put("value", map.get("name").toString());
		keyword1.put("color", "#2dc158");
		
		Map<String,String> keyword2 = new HashMap<String, String>();
		keyword2.put("value", ""+ map.get("time").toString());
		keyword2.put("color", "#2dc158");
		
		Map<String,Object> data = new HashMap<String, Object>();
		
		/*if(map.get("note").toString()!=null &&!map.get("note").toString().equals("")){
			
			Map<String,String> keyword3 = new HashMap<String, String>();
			keyword2.put("value", "备注："+map.get("note").toString());
			keyword2.put("color", "#2dc158");
			
			data.put("keyword3", keyword3);
		}*/
		
		
		Map<String,String> keyword3 = new HashMap<String, String>();
		keyword3.put("value", ""+map.get("place").toString());
		keyword3.put("color", "#2dc158");
		
		
		Map<String,String> keyword4 = new HashMap<String, String>();
		keyword4.put("value", ""+map.get("phone").toString());
		keyword4.put("color", "#2dc158");
		
		
		if(map.get("note").toString()!=null &&!map.get("note").toString().equals("")){
		
		Map<String,String> keyword5 = new HashMap<String, String>();
		keyword2.put("value", map.get("note").toString());
		keyword2.put("color", "#2dc158");
		
		data.put("keyword5", keyword5);
	}
		
		 
		Map<String,String> remark = new HashMap<String, String>();
		remark.put("value", map.get("note").toString());
		remark.put("color", "#333333");
		
		
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		data.put("keyword3", keyword3);
		data.put("keyword4", keyword4);
		 
		data.put("remark", remark);
		
		msg.put("data",data ); 
		mu.insertOne("message_second", msg);
		
	}
	
}

public List<Document> query_no_send3(int type){
	
	List<Document> list = new ArrayList<Document>();
	
	Document doc = new Document();
	doc.put("is_send", 0); 
	doc.put("type", type);
	MongoCursor<Document> cursor = mu.find("message_second", doc);
	
	 while(cursor.hasNext()){
		 Document post = cursor.next(); 
		  
		 list.add(post);
	 } 
	 
	 return list;
}

/**
 *  成员加入通知
 */

public void add_msg8(Map<String,Object> map){
	
	int msg_type =Integer.valueOf(map.get("msg_type").toString());
	
	Document doc1 = new Document();
	doc1.put("type", msg_type);
	Document mould = mu.findOne("msg_mould", doc1);
	 
	Document msg = new Document();
	msg.put("touser", map.get("touser"));
	msg.put("template_id", mould.getString("template_id"));  
	msg.put("url", mould.get("url")); 
	msg.put("is_send", 0);
	msg.put("time",map.get("time") );
	/*msg.put("title",map.get("title"));
	msg.put("name",map.get("name"));	 
	msg.put("note",map.get("note"));
	msg.put("type",msg_type);*/
	
	if(msg_type == 8){
		   
		Map<String,String> first = new HashMap<String, String>();
		first.put("value", map.get("title").toString());
		first.put("color", "#333333");
		
		Map<String,String> keyword1 = new HashMap<String, String>();
		keyword1.put("value", map.get("name").toString());
		keyword1.put("color", "#2dc158");
		
		Map<String,String> keyword2 = new HashMap<String, String>();
		keyword2.put("value", ""+ map.get("time").toString());
		keyword2.put("color", "#2dc158");
		
		Map<String,Object> data = new HashMap<String, Object>();
		 
		  
		
		if(map.get("note").toString()!=null &&!map.get("note").toString().equals("")){
		
		Map<String,String> keyword3 = new HashMap<String, String>();
		keyword3.put("value",  map.get("note").toString());
		keyword3.put("color", "#2dc158");
		
		data.put("keyword3", keyword3);
	}
		
		 
		Map<String,String> remark = new HashMap<String, String>();
		remark.put("value",  map.get("note").toString());
		remark.put("color", "#333333");
		
		
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2); 
		data.put("remark", remark);
		
		msg.put("data",data ); 
		mu.insertOne("message", msg);
		
	}
	
}

public void add_msg9(Map<String,Object> map){
	int msg_type =Integer.valueOf(map.get("msg_type").toString());
	
	Document doc1 = new Document();
	doc1.put("type", msg_type);
	Document mould = mu.findOne("msg_mould", doc1);
	Document msg = new Document();
	msg.put("touser", map.get("touser"));
	msg.put("template_id", mould.getString("template_id"));  
	msg.put("url", mould.get("url")); 
	msg.put("is_send", 0);
	/*msg.put("begin_time",map.get("begin_time") );
	msg.put("end_time",map.get("end_time") );
	msg.put("title",map.get("title"));	 	 
	msg.put("note",map.get("note"));
	msg.put("type",msg_type);*/
	
	Map<String,String> first = new HashMap<String, String>();
	first.put("value", map.get("title").toString());
	first.put("color", "#333333");
	
	Map<String,String> keyword1 = new HashMap<String, String>();
	keyword1.put("value", map.get("nr").toString());
	keyword1.put("color", "#2dc158");
	
	Map<String,String> keyword2 = new HashMap<String, String>();
	keyword2.put("value", "到期时间为"+ map.get("end_time").toString());
	keyword2.put("color", "#2dc158");
	
	 
	Map<String,String> remark = new HashMap<String, String>();
	remark.put("value",  map.get("note").toString());
	remark.put("color", "#333333");
		
	
	Map<String,Object> data = new HashMap<String, Object>();
	  
	data.put("first", first);
	data.put("keyword1", keyword1);
	data.put("keyword2", keyword2);
	 
	 
	data.put("remark", remark);
	
	msg.put("data",data ); 
	mu.insertOne("message", msg);
	
}

public void add_msg10(Map<String,Object> map){
	int msg_type =Integer.valueOf(map.get("msg_type").toString());
	
	Document doc1 = new Document();
	doc1.put("type", msg_type);
	Document mould = mu.findOne("msg_mould", doc1);
	Document msg = new Document();
	msg.put("touser", map.get("touser"));
	msg.put("template_id", mould.getString("template_id"));  
	msg.put("url", mould.get("url")); 
	msg.put("is_send", 0);
	msg.put("type",msg_type);
	/*msg.put("name",map.get("name") );
	msg.put("number",map.get("number") );
	msg.put("title",map.get("title"));	
	msg.put("tz",map.get("tz") );
	msg.put("note",map.get("note"));
	*/
	
	Map<String,String> first = new HashMap<String, String>();
	first.put("value", map.get("title").toString());
	first.put("color", "#333333");
	
	Map<String,String> keyword1 = new HashMap<String, String>();
	keyword1.put("value", map.get("name").toString());
	keyword1.put("color", "#2dc158");
	
	Map<String,String> keyword2 = new HashMap<String, String>();
	keyword2.put("value", ""+ map.get("number").toString());
	keyword2.put("color", "#2dc158");
	
	Map<String,String> remark = new HashMap<String, String>();
	remark.put("value",  map.get("note").toString());
	remark.put("color", "#333333");
	
	
	Map<String,Object> data = new HashMap<String, Object>();
	  
	data.put("first", first);
	data.put("keyword1", keyword1);
	data.put("keyword2", keyword2); 
	data.put("remark", remark);
	
	msg.put("data",data ); 
	mu.insertOne("message", msg);
	
}


}
