 package com.hysm.wecat.wxdb;

import java.util.Collection;
import java.util.Iterator;

import org.bson.BSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class DBObjectImpl extends BasicDBObject
{

	/**
	 * 该类用于简化BasicDBObject的处理。
	 */
	private static final long serialVersionUID = 1L;
	
	public DBObjectImpl()
	{
		super();
	}
	
	public DBObjectImpl(String key,Object val)
	{
		super();
		this.put(key, val);
	}
	public Object put(String key,Object val)
	{
		String[] arrs = key.split("\\.");
		if(arrs.length <= 1)
		{
			super.put(key, val);
			return val;
		}
		BasicDBObject c = this;
		BasicDBObject temp = null;
		for(int i=0;i<arrs.length;i++)
		{
			if(i == arrs.length-1)
			{
				c.put(arrs[i], val);
			}
			else
			{
				temp = (BasicDBObject)c.get(arrs[i]);
				if(temp == null)
				{
					temp = new BasicDBObject();
					c.put(arrs[i], temp);
				}

				c = temp;
			}
		}
		return val;
	}
	
	public BasicDBObject append(String key,Object val)
	{
		String[] arrs = key.split("\\.");
		if(arrs.length <= 1)
		{
			super.put(key, val);
			return this;
		}
		BasicDBObject c = this;
		BasicDBObject temp = null;
		for(int i=0;i<arrs.length;i++)
		{
			if(i == arrs.length-1)
			{
				c.put(arrs[i], val);
			}
			else
			{
				temp = (BasicDBObject)c.get(arrs[i]);
				if(temp == null)
				{
					temp = new BasicDBObject();
					c.put(arrs[i], temp);
				}

				c = temp;
			}
		}
		return this;
	}
	
	public void copy(BSONObject source,String ...keys)
	{
		for(int i=0;i<keys.length;i++)
		{
			if(source.get(keys[i]) != null)
			{
			    this.put(keys[i],source.get(keys[i]));
			}
		}
	}
	
	
	
	public static DBObjectImpl fromBSON(BSONObject bson)
	{
		DBObjectImpl di = new DBObjectImpl();
		di.putAll(bson.toMap());
		return di;
	}
	
	public static DBObjectImpl fromJSONString(String str)
	{
		try
		{
			return fromBSON((BSONObject)JSON.parse(str));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public static DBObjectImpl fromJSON(JSONObject json)
	{
		DBObjectImpl di = new DBObjectImpl();
        Iterator<?> it = json.keys();
        String key = null;
        Object val = null;
        while(it.hasNext())
        {
        	key = (String)it.next();
        	try {val = json.get(key);} 
        	catch (JSONException e1) 
        	{
        		e1.printStackTrace();
        		continue;
        	}
        	if(val instanceof JSONObject)
        	{
        		di.put(key, DBObjectImpl.fromJSON((JSONObject)val));
        	}
        	else if(val instanceof Collection)
        	{
        		di.put(key, listFromCollection((Collection<?>)val));
        	}
        	else if(val instanceof JSONArray)
        	{
        		di.put(key, listfromJSONArray((JSONArray)val));
        	}
        	else
        	{
        		di.put(key, val);
        	}
        }
		return di;
	 }
	
	public static BasicDBList listFromCollection(Collection<?> val)
	{
		if(val == null)
		{
			return null;
		}
		BasicDBList list = new BasicDBList();
		for(Object o : val)
		{
			if(o instanceof JSONObject)
			{
				list.add(fromJSON((JSONObject)o));
			}
			else if(o instanceof Collection)
			{
				list.add(listFromCollection((Collection<?>)o));
			}
			else if(o instanceof JSONArray)
			{
				list.add(listfromJSONArray((JSONArray)o));
			}
        	else if(o instanceof JSONObject)
        	{
        		list.add(fromJSON((JSONObject)o));
        	}
			else
			{
				list.add(o);
			}
			
		}
		list.addAll(val);
		return list;
	}
	public static BasicDBList listfromJSONArray(JSONArray val)
	{
		if(val == null)
		{
			return null;
		}
		BasicDBList list = new BasicDBList();
		Object o = null;
		for(int i=0;i<val.length();i++)
		{
			try {
				o = val.get(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			if(o instanceof JSONObject)
			{
				list.add(fromJSON((JSONObject)o));
			}
			else if(o instanceof Collection)
			{
				list.add(listFromCollection((Collection<?>)o));
			}
			else if(o instanceof JSONArray)
			{
				list.add(listfromJSONArray((JSONArray)o));
			}
        	else if(o instanceof JSONObject)
        	{
        		list.add(fromJSON((JSONObject)o));
        	}
			else
			{
				list.add(o);
			}
		}
		return list;
	}
	
	
	public static void main(String[] args)
	{		
		/*
		String test = "this is a test";
		System.out.println(test.split("\\.").length);
		DBObjectImpl dbo = new DBObjectImpl();
		dbo.put("name","zhaoyi");
		dbo.put("address.province", "江苏");
		dbo.put ("address.city", "南京");
		dbo.append ("address.detail", "detail")
		.append("nickname", "aaren")
		.append("state",1);
		System.out.println(dbo);
		*/
		String test2 = "{a1:['南京','苏州'],b2:{c:1,d:2}}";
		/*
		try {
			JSONObject json = new JSONObject(test2);
			System.out.println(json);
			System.out.println(DBObjectImpl.fromJSON(json));
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try 
		{
			Object obj = JSON.parse(test2);
			System.out.println(obj.getClass().getName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	

}
