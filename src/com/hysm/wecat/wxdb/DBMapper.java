package com.hysm.wecat.wxdb;

import java.util.Enumeration;
import java.util.Hashtable;


public class DBMapper 
{
	private Hashtable<String,String> hash = null;
	public DBMapper()
	{
		this(null);
	}
	public DBMapper(Hashtable<String,String> hashtable)
	{
		if(hashtable == null)
		{
			hash = new Hashtable<String,String>();
		}
		else
		{
			hash = hashtable;
		}
	}
	
	public DBMapper set(String key, Object value,String def)
	{
		if(value == null)
		{
			hash.put(key, def);
		}
		else
		hash.put(key, String.valueOf(value));
		return this;
	}
	public DBMapper set(String key, int v,int def)
	{
		if(v == 0){v = def;}
		hash.put(key, String.valueOf(v));
		return this;
	}
	
	
	public DBMapper set(String key, Object value)
	{
		hash.put(key, String.valueOf(value));
		return this;
	}
	public DBMapper set(String key, int v)
	{
		hash.put(key, String.valueOf(v));
		return this;
	}
	
	public String insertSQL(String table)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb.append("insert into ");
		sb.append(table);
		sb.append('(');
		
		Enumeration<String> it = hash.keys();
		String key = null;
		while(it.hasMoreElements())
		{
			key = it.nextElement();
			sb.append(key);
			sb.append(',');
			sb2.append('\'');
			sb2.append(StringTool.addSlash(hash.get(key)));
			sb2.append('\'');
			sb2.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb2.deleteCharAt(sb2.length()-1);
		sb.append(")values(");
		sb.append(sb2.toString());
		sb.append(')');
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	
	public int insert(String table,boolean needID)
	{
		
		if(needID)
		{
			return DBManager.insertReturnID(insertSQL(table));
		}
		return DBManager.executeDBUpdate(insertSQL(table));		
		
	}
	
	public String updateSQL(String table,String key)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(table);
		sb.append(" set  ");
		
		Enumeration<String> it = hash.keys();
		String ikey = null;
		while(it.hasMoreElements())
		{
			ikey = it.nextElement();
			sb.append(ikey);
			sb.append("=\'");
			sb.append(StringTool.addSlash(hash.get(ikey)));
			sb.append("\',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" where ");
		sb.append(key);
		sb.append("=\'");
		sb.append(hash.get(key));
		sb.append("'");
		return sb.toString();
	}
	
	public int update(String table,String key)
	{
		System.out.println(updateSQL(table,key));
		return DBManager.executeDBUpdate(updateSQL(table,key));		
	}
	
	public  void reset()
	{
		hash.clear();
	}
	
	public static void main(String[] args)
	{
		
		DBMapper dm = new DBMapper();
		
		dm.set("d","2011-08-09");
		dm.set("v",100);
		dm.set("c","we");
		dm.set("e","那亚非拉");
		dm.insert("c1.test2",false);
	}
	
	
}