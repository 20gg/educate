package com.hysm.wecat.wxdb;


//import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
//import java.util.Properties;
import java.util.Vector;


public class QueryResult
{
	Hashtable<String,Integer> columnMapping = new Hashtable<String,Integer>();
	Vector<String[]> vec = new Vector<String[]>();
	Hashtable<String,String[]> map = null;
	int[] columnTypes = null;

	private QueryResult(){}

	public String get(int row,int columnInd,String nullRet)
	{		
		if(vec == null || row>= vec.size()){return nullRet;}
		String[] rowArr = vec.elementAt(row);
		if(rowArr == null || columnInd >= rowArr.length){return nullRet;}
		return rowArr[columnInd];
	}
	public String get(int row,int columnInd)
	{		
		if(vec == null || row>= vec.size()){return null;}
		String[] rowArr = vec.elementAt(row);
		if(rowArr == null || columnInd >= rowArr.length){return null;}
		return rowArr[columnInd];
	}
	
	
	public void set(int row,String columnName,String value)
	{	
		if(vec == null || row>= vec.size()){return;}
		if(columnMapping == null){return;}
		Integer indObj = columnMapping.get(columnName.toUpperCase());
		if(indObj == null){return;}
		String[] rowArr = vec.elementAt(row);
		int columnInd = indObj.intValue();
		if(rowArr == null || columnInd >= rowArr.length){return;}
		rowArr[columnInd] = value;		
	}
	public void set(String key, String columnName, String value) 
	{
		if ((this.vec == null) || (this.map == null)) return;
		if (this.columnMapping == null) return;
		Integer indObj = (Integer)this.columnMapping.get(columnName.toUpperCase());
		if (indObj == null) return;
		int columnInd = indObj.intValue();
		String[] rowArr = (String[])this.map.get(key);
		if ((rowArr == null) || (columnInd >= rowArr.length)) return;
		rowArr[columnInd] = value;
	}
	public void set(int row,String columnName,Number value)
	{	
	    set(row,columnName,String.valueOf(value));	
	}

	public String get(int row,String columnName)
	{		
		if(vec == null || row>= vec.size()){return null;}
		if(columnMapping == null){return null;}
		Integer indObj = columnMapping.get(columnName.toUpperCase());
		if(indObj == null){return null;}
		String[] rowArr = vec.elementAt(row);
		int columnInd = indObj.intValue();
		if(rowArr == null || columnInd >= rowArr.length){return null;}
		return rowArr[columnInd];
	}
	public String get(int row,String columnName,String nullRet)
	{		
		if(vec == null || row>= vec.size()){return nullRet;}
		if(columnMapping == null){return nullRet;}
		Integer indObj = columnMapping.get(columnName.toUpperCase());
		if(indObj == null){return nullRet;}
		String[] rowArr = vec.elementAt(row);
		int columnInd = indObj.intValue();
		if(rowArr == null || columnInd >= rowArr.length){return nullRet;}
		return rowArr[columnInd];
	}


	public String mapping(String key,int columnInd)
	{
		if(map == null){return null;}
		String[] rowArr = map.get(key);
		if(rowArr == null || columnInd>= rowArr.length){return null;}
		return rowArr[columnInd];
	}
	public String mapping(String key,String columnName)
	{
		if(map == null){return null;}
		String[] rowArr = map.get(key);
		if(rowArr == null){return null;}
		Integer objInd = columnMapping.get(columnName.toUpperCase());
		if(objInd == null){return null;}
		int columnInd = objInd.intValue();
		if(columnInd >= rowArr.length){return null;}
		return rowArr[columnInd];

	}

	public static QueryResult newQueryResult(ResultSet rs,String key) throws Exception
	{
		if(rs == null){return null;}
		QueryResult qr = new QueryResult();
		if(key != null)
		{
			qr.map = new Hashtable<String,String[]>();
		}
		ResultSetMetaData meta =  rs.getMetaData();
		int numColumns = meta.getColumnCount();
		qr.columnTypes = new int[numColumns];
		for(int i = 0;i<numColumns;i++)
		{
			qr.columnMapping.put(meta.getColumnName(i+1).toUpperCase(),Integer.valueOf(i));
			qr.columnTypes[i] = meta.getColumnType(i+1);
		}
		String[] aRow;  	  
		while(rs.next())
		{
			aRow = new String[numColumns];
			for (int i = 0; i < numColumns; i++)
			{
				aRow[i] = rs.getString(i+1);
			}
			qr.vec.add(aRow);
			if(key != null && rs.getString(key) != null)
			{
				qr.map.put(rs.getString(key), aRow);
			}
		}		
		return qr;		
	}

	public int getRows()
	{
		//if(vec == null){return 0;}
		return vec.size();
	}
	public int getColumns()
	{
		//if(columnMapping == null){return 0;}
		return columnMapping.size();
	}
	public int getColumnInd(String column)
	{
		Integer objInd = columnMapping.get(column.toUpperCase());
		if(objInd == null){return -1;}
		return objInd.intValue();
	}

	public void destroy()
	{
		if(map != null)
		{
			//map.clear();
			map = null;
		}
		//columnMapping.clear();
		columnMapping = null;

		//vec.clear();
		vec = null;
	}



	public static void main(String[] args)
	{       	
		Connection dbConn = null;
		Statement state = null;
		try
		{
			System.out.println("test0#1:"+System.currentTimeMillis());
			System.out.println("test0-1:"+System.nanoTime());
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			System.out.println("test0-2:"+System.nanoTime());
			state.executeUpdate("SET AUTOCOMMIT=0");
			for(int j=1;j<100;j++)
			{
				for(int i=0; i<100000;i++)
				{

					int rand = (int)(Math.random()*100000)+(j-1)*100000;
					if(j == 0)
					{
						rand = 0;
					}
					state.executeUpdate("insert into testtree(parent,node) values("+rand
							+",'node"+(i+j*200)+"parent:"+rand+"')");
				}

				state.execute("commit");
			}

			state.executeUpdate("SET AUTOCOMMIT=1");
			System.out.println("test0-2:"+System.nanoTime());
			System.out.println("test0#2:"+System.currentTimeMillis());


		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			//try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			System.out.println("test0-3:"+System.nanoTime());
		}	
		/*
		 System.out.println("test0-4:"+System.nanoTime());
		 QueryResult qr = DBManager.queryResult("select * from testtree limit 0,10000",null);
		 System.out.println("test0-5:"+System.nanoTime());
		 qr.treeSort("parent", "id");
		 System.out.println("test0-6:"+System.nanoTime());
		 //qr.dump();
		 System.out.println("test0-7:"+System.nanoTime());
		 */

	}   


	public Object map2Obj(String className,int ind)
	{
		try
		{  
			if(vec == null || ind>= vec.size()){return null;}
			Class<?> _class = Class.forName(className);
			Field[] fieldArr = _class.getFields();
			String temp = null;

			Object obj = _class.newInstance();
			for(int i=0;i<fieldArr.length;i++)
			{
				String name = fieldArr[i].getName();
				String type = fieldArr[i].getType().getSimpleName();
				if ((temp = get(ind, name)) != null) 
				{
					if (type.equals("int"))
					{
						fieldArr[i].setInt(obj, Integer.parseInt(temp));
					} 
					else if (type.equals("String"))
					{
						fieldArr[i].set(obj, temp);
					} 
					else if (type.equals("byte")) 
					{
						fieldArr[i].setByte(obj, Byte.parseByte(temp));
					}
					else if (type.equals("long"))
					{
						fieldArr[i].setLong(obj, Long.parseLong(temp));
					}
					else if (type.equals("float"))
					{
						fieldArr[i].setFloat(obj, Float.parseFloat(temp));
					} 
					else if (type.equals("double"))
					{
						fieldArr[i].setDouble(obj, Double.parseDouble(temp));
					} 
					else if (type.equals("boolean"))
					{
						fieldArr[i].setBoolean(obj,Integer.parseInt(temp) == 1);
					}
					else if(type.equals("Date"))
					{
						fieldArr[i].set(obj,StringTool.str2date(temp));
					}
				} 
			}
			return obj;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public Vector<Object> Map2ObjVec(String className)
	{
		try
		{
			//System.out.println(vec.size());
			if(vec == null || vec.size() == 0){return null;}
			Class<?> _class = Class.forName(className);
			Field[] fieldArr = _class.getFields();
			String temp = null;
			Vector<Object> destVec = new Vector<Object>();
			for(int ind = 0;ind<vec.size();ind++)
			{
				Object obj = _class.newInstance();
				for(int i=0;i<fieldArr.length;i++)
				{
					String name = fieldArr[i].getName();
					String type = fieldArr[i].getType().getSimpleName();
					if ((temp = get(ind, name)) != null) 
					{
						if (type.equals("int"))
						{
							fieldArr[i].setInt(obj, Integer.parseInt(temp));
						} 
						else if (type.equals("String"))
						{
							fieldArr[i].set(obj, temp);
						} 
						else if (type.equals("byte")) 
						{
							fieldArr[i].setByte(obj, Byte.parseByte(temp));
						}
						else if (type.equals("long"))
						{
							fieldArr[i].setLong(obj, Long.parseLong(temp));
						}
						else if (type.equals("float"))
						{
							fieldArr[i].setFloat(obj, Float.parseFloat(temp));
						} 
						else if (type.equals("double"))
						{
							fieldArr[i].setDouble(obj, Double.parseDouble(temp));
						} 
						else if (type.equals("boolean"))
						{
							fieldArr[i].setBoolean(obj,Integer.parseInt(temp) == 1);
						}
						else if(type.equals("Date"))
						{
							fieldArr[i].set(obj,StringTool.str2date(temp));
						}
					}
				}
				destVec.add(obj);
			}
			return destVec;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 将查询结果输出成为JSON对象.
	 * */
	public String dumpJSONStr(int[] sortedInd)
	{
		try
		{
		  if(vec == null || vec.size() == 0)
		  {
			  return "[]";
		  }
		  StringBuffer  sb = new StringBuffer();
		  
		  sb.append('[');
		  Enumeration<String> keys = null;
		  String key = null;
		  int type = 0;
		  int nums = sortedInd!=null ? sortedInd.length : vec.size();
		  String temp = null;
		  for(int i = 0; i< nums; i++)
		  {  
			  sb.append('{');
			  keys = columnMapping.keys();
			  while(keys.hasMoreElements())
			  {
				  key = keys.nextElement();
				  sb.append('\'');
				  sb.append(key.toLowerCase());
				  sb.append('\'');
				  sb.append(':');
				  type = columnTypes[columnMapping.get(key).intValue()];
				  if(type == java.sql.Types.BIGINT
						  || type == java.sql.Types.FLOAT
						  || type == java.sql.Types.DECIMAL
						  || type == java.sql.Types.NUMERIC
						  || type == java.sql.Types.TINYINT
						  || type == java.sql.Types.SMALLINT
						  || type == java.sql.Types.INTEGER
						  || type == java.sql.Types.BOOLEAN
						  )
				  {
					  temp = get(sortedInd!= null ? sortedInd[i] : i,key,"0");	
					  if(temp == null){temp = "0";}				  
					  sb.append(temp);
				  }
				  else
				  {
					  temp = get(sortedInd!= null ? sortedInd[i] : i,key,"");
					  if(temp == null){temp = "";}
					  if((temp.indexOf('[') == 0 && temp.indexOf(']') == temp.length()-1)
						 || (temp.indexOf('{') == 0 && temp.indexOf('}') == temp.length()-1))
					  {

						  sb.append(temp);
					  }
					  else
					  {
					      sb.append('\'');
					      sb.append(StringTool.addSlash(temp));
					      sb.append('\'');	
					  }				  
				  }
				  sb.append(',');
			  }
			  sb.deleteCharAt(sb.length()-1);
			  sb.append("},");			  
		  } 
		  sb.deleteCharAt(sb.length()-1);
		  sb.append(']');
		  return sb.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public void dump()
	{
		for(int i=0;i<vec.size();i++)
		{
			System.out.print(i+":");
			for(String s : vec.elementAt(i))
			{
				System.out.print(s);
				System.out.print("   ");
			}
			System.out.println();
		}
		System.out.println(map.get("100")[0]);
	}


}
