package com.hysm.db.mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;



import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class MongoUtil {
    private MongoDatabase mdb;
    public String threadFlag = null;
    public MongoUtil() {
        if (mdb == null) this.mdb = MongoConn.getConn();
    }
    static ThreadLocal<MongoUtil> threadLocal = new ThreadLocal<MongoUtil>();
	//为了防止对象的多次无谓生成
	public static MongoUtil getThreadInstance()
	{
		if(threadLocal.get() == null)
		{
			MongoUtil u = new MongoUtil();
			u.threadFlag = StringTool.randomPasswd();
			threadLocal.set(u);
		}
		return threadLocal.get().clear();
		
	}
	public MongoUtil clear()
	{
		//清除重复数据
	
		return this;
	}
    public void deleteMany(String collection, Document conditionDoc) {
        MongoCollection<BasicDBObject> mcoll = mdb.getCollection(collection, BasicDBObject.class);
        mcoll.deleteMany(conditionDoc);
    }

    public void insertOne(String collection, BasicDBObject document) {
        MongoCollection<BasicDBObject> mcoll = mdb.getCollection(collection, BasicDBObject.class);
        mcoll.insertOne(document);
    }

    public void insertOne(String collection, Document document) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection, Document.class);
        mcoll.insertOne(document);
    }

    public void insertMany(String collection, List<Document> documents) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.insertMany(documents);
    }

    public void updateOne(String collection, Bson condition, Bson setfields) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.updateOne(condition, setfields);
         
       // Filters.and(Filters.eq("phone", ""),Filters.eq("phone", ""));
        
//        mcoll.updateMany(
//                eq("stars", 2),
//                combine(set("stars", 0), currentUpdatesDate("lastModified")));
        //    updateOne(Filters.eq("i", 1), Updates.set("i", 110));  


    }
    public void updateMany(String collection, Bson condition, Bson setfields) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.updateMany(condition, setfields);
         


    }
     

    public void replaceOne(String collection, Bson condition, Document document) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.replaceOne(condition, document);
       
    }

    public void deleteMany(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        mcoll.deleteMany(condition);
    }

    public MongoCursor<Document> find(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        if (iterable != null) {
            return iterable.iterator();
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }
    
    
    
    public Document findOne(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find();
        } else {
            iterable = mcoll.find(condition);
        }
        if (iterable != null&&iterable.iterator().hasNext()) {
        	
            return iterable.iterator().next();
        	
            
            
        } else {
            return null;
        }
        /*
         * 调用例子
         *
         * MongoCursor<Document> cursor = find(.............);
         * while (cursor.hasNext()) {
         *      Document user = cursor.next();
         *      System.out.println(user.toString());
         * }
         */
    }

    public void find(String collection, Bson condition, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().forEach(block);
        } else {
            mcoll.find(condition).forEach(block);
        }
        /**
         * 调用例子
         * find("collection",new Document("customerid", customerid).append("useracc", user),
         *                    (Block<Document>) document-> {
         *                        System.out.println(document.toJson());
         *                        //System.out.println("aaa");
         *                    });
         */
    }

    /**
     * 获取记录条数
     *
     * @param collection
     * @param condition
     * @return
     */
    public long count(String collection, Bson condition) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            return mcoll.count();
        } else {
            return mcoll.count(condition);
        }
    }

    public MongoCursor<Document> findLimit(String collection, Bson condition, int skip, int limit) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable;
        if (condition == null) {
            iterable = mcoll.find().skip(skip).limit(limit);
        } else {
            iterable = mcoll.find(condition).skip(skip).limit(limit);
        }
        if (iterable != null) {
            return iterable.iterator();
        } else {
            return null;
        }
    }

    public void findLimit(String collection, Bson condition, int skip, int limit, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().skip(skip).limit(limit).forEach(block);
        } else {
            mcoll.find(condition).skip(skip).limit(limit).forEach(block);
        }

    }

    public MongoCursor<Document> findLimitSort(String collection, Bson condition, int skip, int limit, Bson sort) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find().skip(skip).limit(limit).sort(sort);
        } else {
            iterable = mcoll.find(condition).skip(skip).limit(limit).sort(sort);
        }
        if (iterable != null) {
            return iterable.iterator();
        } else {
            return null;
        }
    }

    public void findLimitSort(String collection, Bson condition, int skip, int limit, Bson sort, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().skip(skip).limit(limit).sort(sort).forEach(block);
        } else {
            mcoll.find(condition).skip(skip).limit(limit).sort(sort).forEach(block);
        }
    }

    public MongoCursor<Document> findSort(String collection, Bson condition, Bson sort) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        FindIterable<Document> iterable = null;
        if (condition == null) {
            iterable = mcoll.find().sort(sort);
        } else {
            iterable = mcoll.find(condition).sort(sort);
        }
        if (iterable != null) {
            return iterable.iterator();
        } else {
            return null;
        }
    }

    public void findSort(String collection, Bson condition, Bson sort, Block<Document> block) {
        MongoCollection<Document> mcoll = mdb.getCollection(collection);
        if (condition == null) {
            mcoll.find().sort(sort).forEach(block);
        } else {
            mcoll.find(condition).sort(sort).forEach(block);
        }
    }
    
//    public static void main(String[] args) {
//    	Document bson=new Document();
//		bson.append("user_name", "平台管理员");
//		bson.append("phone", "");
//		MongoUtil mu=MongoUtil.getThreadInstance();
//		System.out.println(mu.count("user", bson));
//		
//	}

}
