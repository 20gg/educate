package com.hysm.wecat.wxdb;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import java.sql.Statement;
import javax.naming.InitialContext;

import org.bson.BasicBSONObject;




import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class DBManager
{
	private static DataSource dbPool = null;
	
	
	/*
	private static String dbPoolName = "jdbc/coolmuch";
	private static String url="jdbc:mysql://211.149.158.169:3306/coolmuch";
	private static String driverClassName="com.mysql.jdbc.Driver";
	private static String dbUser = "root";
	private static String dbPasswd = "dofeinet";
	

    
	private static String url="jdbc:mysql://localhost:3306/coolmuch";
	private static String driverClassName="com.mysql.jdbc.Driver";
	private static String dbUser = "root";
	private static String dbPasswd = "86jpU22mRX";
	//private static String dbPasswd = "dofeinet";
	*/
	
	private static String dbPoolName = "jdbc/dbpool";
	
	//private static String url="jdbc:mysql://localhost:3306/countrysidemarket";
	private static String url="jdbc:mysql://127.0.0.1:3306/cg?useUnicode=true&amp;&characterEncoding=utf-8";
	private static String driverClassName="com.mysql.jdbc.Driver";
	private static String dbUser = "root";
	private static String dbPasswd = "dofeinet";
	
	public static void setDB(String host,int port,String db,String user,String passwd)
	{
		url = "jdbc:mysql://"+host+":"+port+"/"+db;
		dbUser = user;
		dbPasswd = passwd;
	}
	
	/*
	private static String url="jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=" +     
	        "(ADDRESS=(PROTOCOL=TCP)(HOST=10.10.102.184)(PORT=1521))" +     
	        "(ADDRESS=(PROTOCOL=TCP)(HOST=10.10.102.185)(PORT=1521))" +     
	        "(LOAD_BALANCE=yes)(FAILOVER=on))" +     
	        "(CONNECT_DATA=(SERVICE_NAME=hisapec)))";
    
	private static String driverClassName="oracle.jdbc.driver.OracleDriver";
	private static String dbUser = "huimaiec01";
    private static String dbPasswd = "huimaiec01";
    */

	
	public static void setDBPool(String poolName)
	{
		dbPoolName = poolName;
	}
	
	public static void  setDBInfo(String _url,String driverName,String _dbUser,String _passwd)
	{
		url = _url;
		driverClassName = driverName;
		dbUser = _dbUser;
		dbPasswd = _passwd;
	}

	public static Connection getConnection()
	{
		try
		{
			if (dbPool == null)
			{
				Context env = new InitialContext();
				dbPool = (DataSource) env.lookup("java:comp/env/"+dbPoolName);
				//Context namContext = (javax.naming.Context)new InitialContext().lookup("java:comp/env");
				//dbPool = (DataSource) namContext.lookup("jdbc/redob");
				System.out.println("DB Pool inited");

			}
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			//GMLogger.getLogger().error(ex);
		}


		try
		{
			if(dbPool != null)
			{
				return dbPool.getConnection();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			
			Class.forName(driverClassName);
			return DriverManager.getConnection(url,dbUser,dbPasswd);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//GMLogger.getLogger().warn("create db connection error!",ex);
		}
		System.out.println("db is null!");
		return null;
	}

	public static Connection getConnection(String driverName,String dbUrl,String dbName,String dbUser,String dbPasswd)
	{
		try
		{
			Class.forName(driverName);
			return DriverManager.getConnection(dbUrl+dbName,dbUser,dbPasswd);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	/*
   return value:
   -1: user info not complete.
   -2: programmer error or exception.
   0/1-N :db oper result.
	 */
	public static int executeDBUpdate(String sql)
	{
		System.out.println("[SQL]:"+sql);

		if(StringTool.isSpace(sql)){return 0;}
		Connection dbConn = null;
		Statement state = null;
		int result = -1;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeUpdate(sql);
		}
		catch(Exception ex)
		{
			//Trace.SEVERE("update DB failed:"+ sql);
			ex.printStackTrace();
			//GMLogger.getLogger().error(ex);
			result = -2;
			
		}
		finally
		{
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return result;
	}
	
	
	 /*
	   return value: last_insert_id();
	   -2:error.
	 */
		public static int insertReturnID(String insertSQL)
		{
			//Trace.DEBUG(insertSQL);

			System.out.println("[SQL]:"+insertSQL);

			if(StringTool.isSpace(insertSQL)){return 0;}
			Connection dbConn = null;
			Statement state = null;
			int result = -1;
			try
			{
				dbConn = DBManager.getConnection();
				state = dbConn.createStatement();
				state.executeUpdate(insertSQL);
				ResultSet rs = state.executeQuery("select last_insert_id()");
				if(rs.next())
				{
				   result = rs.getInt(1);
				}			
			}
			catch(Exception ex)
			{
				//Trace.SEVERE("update DB failed:"+ sql);
				ex.printStackTrace();
				//GMLogger.getLogger().error(ex);
				result = -2;
			}
			finally
			{
				try{state.close();state=null;}catch(Exception stateex){state = null;}
				try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			}
			return result;
		}
//		查询有几条记录
		public static int querymount(String sql){
			System.out.println("[SQL]:"+sql);
			Connection dbConn=null;
			Statement state=null;
			ResultSet result=null;
			int rowcount =0;
			try {
				dbConn=DBManager.getConnection();
				state=dbConn.createStatement();
				result=state.executeQuery(sql);
				
				while(result.next()){
					rowcount=result.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{   try{result.close();result=null;}catch(Exception x){result=null;}
				try{state.close();state=null;}catch(Exception stateex){state = null;}
				try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			}
			return rowcount;
		}
		 
		/*
		 * static insert方法
		 * args: tableName,returnID(boolean),keys(如:"(id,num,name)"),值....
		 * example: insert("tble_test,"(id,num,name)",102,123.45,"桂橡");
		 * */

		public static int insert(Object... args)
		{		
			if(args.length < 2){return 0;}
			StringBuilder sb = new StringBuilder();
			sb.append("insert into ");
			sb.append(args[0]);
			sb.append(' ');
			sb.append(args[2]);
			sb.append("values(");
			String str = null;
			for(int i = 3;i<args.length;i++)
			{
				//处理sql函数
				str = String.valueOf(args[i]);
				if(str.startsWith("!#"))
				{
					sb.append(str.substring(2));
					sb.append(',');
					continue;
				}
				sb.append('\'');
				sb.append(args[i]);
				sb.append("',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");

			System.out.println("[SQL]:"+sb.toString());
			if((Boolean) args[1])
			{
				return DBManager.insertReturnID(sb.toString());
			}
			return DBManager.executeDBUpdate(sb.toString());
		}
		
	public static int executeUpdateSQLS(Object... args)
	{
		if(args == null || args.length==0)
		{
			return -1;
		}
		Connection dbConn = null;
		Statement state = null;
		int result = 0;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			for(int i = 0;i<args.length;i++)
			{
				if(args[i] == null){continue;}
				if(args[i] instanceof String[])
				{
					String[]  a = (String[])args[i];
					for(int k =0; k<a.length;k++)
					{
						if(StringTool.isSpace(a[k])){continue;}
						
						System.out.println("[SQL]:"+a[k]);
						result += state.executeUpdate(a[k]);
					}
					continue;
				}
				else if(args[i] instanceof Collection)
				{
					Iterator<?> enu =((Collection<?>)args[i]).iterator();
					while(enu.hasNext())
					{
						String sql = enu.next().toString();
						System.out.println("[SQL]:"+sql);
						result += state.executeUpdate(sql);
						
					}
					continue;
				}
				
				if(StringTool.isSpace(args[i].toString())){continue;}
				System.out.println("[SQL]:"+args[i]);
				result += state.executeUpdate(args[i].toString());
			}
		}
		catch(Exception ex)
		{
			//Trace.SEVERE("update DB failed:");
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
			result = -2;
		}
		finally
		{
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return result;
	}


	/*
   return value:
   -1: user info not complete.
   -2: programmer error or exception.
   0/1-N :db oper result.
	 */
	
	public static int executeUpdateSQLArrat(String[] sqlArray)
	{
		if(sqlArray == null || sqlArray.length==0)
		{
			return -1;
		}
		Connection dbConn = null;
		Statement state = null;
		int result = 0;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			for(int i = 0;i<sqlArray.length;i++)
			{
				System.out.println("[SQL]"+sqlArray[i]);
				if(StringTool.isSpace(sqlArray[i])){continue;}
				result += state.executeUpdate(sqlArray[i]);
			}
		}
		catch(Exception ex)
		{
			//Trace.SEVERE("update DB failed:");
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
			result = -2;
		}
		finally
		{
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return result;
	}
	


	public static int updateOrInsert(String updateStr,String insertStr)
	{
		

		System.out.println("[update SQL]:"+updateStr);
		System.out.println("[insert SQL]:"+insertStr);

		Connection dbConn = null;
		Statement state = null;
		int result = 0;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeUpdate(updateStr);
			if(result == 0)
			{
				result = state.executeUpdate(insertStr);
			}
		}
		catch(Exception ex)
		{
			//Trace.SEVERE("update DB failed:");
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
			result = -2;
		}
		finally
		{
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return result;
	}
	
	public  static  Vector<String> selectIntoSingleArray(String sql)
	{   
	    System.out.println("[SQL]:"+sql);
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		Vector<String> vec = new Vector<String>();
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);

			
			while(result.next())
			{
				vec.addElement(result.getString(1));
			}
			return vec;
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}

	public  static  Vector<String[]> selectIntoArray(String sql)
	{   
	    System.out.println("[SQL]:"+sql);
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		Vector<String[]> vec = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);

			int numColumns = result.getMetaData().getColumnCount();
			String[] aRow;
			vec = new Vector<String[]>();
			while(result.next())
			{
				aRow = new String[numColumns];
				for (int i = 0; i < numColumns; i++)
				{
					aRow[i] = result.getString(i+1); 
				}
				vec.addElement(aRow);
			}
			return vec;
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}

	/*
	 * 把查询的结果组成MAP以便于应用
	 * */

	public  static Map<String,String[]> selectIntoMap(String sql,String key,Statement ...stateArr)
	{   
	    System.out.println("[SQL]:"+sql); 
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		Map<String,String[]> map = null;
		try
		{
			if(stateArr.length == 0)
			{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			}
			else
			{
				state = stateArr[0];
			}

			int numColumns = result.getMetaData().getColumnCount();
			String[] aRow;
			map = new TreeMap<String,String[]>();
			while(result.next())
			{
				aRow = new String[numColumns];
				for (int i = 0; i < numColumns; i++)
				{
					aRow[i] = result.getString(i+1);
				}
				map.put(result.getString(key), aRow);
			}
			return map;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{if(stateArr.length == 0)state.close();state=null;}catch(Exception stateex){state = null;}
			try{if(dbConn != null)dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}



	public static String selectSingleValue(String sql)
	{  
	    System.out.println("[SQL]:"+sql);  
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			if(result.next())
			{
			   return result.getString(1);
			}  
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}


	public static QueryResult queryResult(String sql,String key)
	{   
	    System.out.println("[SQL]:"+sql); 
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			return QueryResult.newQueryResult(result, key);
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			System.out.println(sql);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}    

	public static <T> T map2Obj(String className,String sql)
	{   
	    System.out.println("[SQL]:"+sql); 
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			if(!result.next()){ return null; }
			Class<?> _class = Class.forName(className);
			Field[] fieldArr = _class.getFields();
			String temp = null;
			T obj = (T) _class.newInstance();
			for(int i=0;i<fieldArr.length;i++)
			{
				String name = fieldArr[i].getName();
				String type = fieldArr[i].getType().getSimpleName();
				try{temp = result.getString(name);}catch(Exception er){temp = null;}
				if ( temp!= null) 
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
						System.out.println(temp);
						fieldArr[i].set(obj,result.getDate(name));
					}
				} 
			}
			return obj;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}


	public  static  Vector<Object> map2ObjVec(String className,String sql)
	{   
	    System.out.println("[SQL]:"+sql); 
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			Class<?> _class = Class.forName(className);
			Field[] fieldArr = _class.getFields();
			String temp = null;
			Vector<Object> destVec = new Vector<Object>();
			while(result.next())
			{
				Object obj = _class.newInstance();
				for (int i = 0; i < fieldArr.length; i++) 
				{
					String name = fieldArr[i].getName();
					String type = fieldArr[i].getType().getSimpleName();
					try{temp = result.getString(name);}catch(Exception er){temp = null;}
					if ( temp!= null) 
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
						else if (type.equals("Date"))
						{
							fieldArr[i].set(obj,result.getDate(name));
						}
					}
				}
				destVec.add(obj);
			}
			return destVec;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}
	public static <T> List<T>  map2Objlist (String className,String sql){
		  System.out.println("[SQL]:"+sql); 
			Connection dbConn = null;
			Statement state = null;
			ResultSet result = null;
			try
			{
				dbConn = DBManager.getConnection();
				state = dbConn.createStatement();
				result = state.executeQuery(sql);
				Class<?> _class = Class.forName(className);
				Field[] fieldArr = _class.getFields();
				String temp = null;
				List<T> destVec = new ArrayList<T>();
				while(result.next())
				{
					T obj = (T) _class.newInstance();
					for (int i = 0; i < fieldArr.length; i++) 
					{
						String name = fieldArr[i].getName();
						String type = fieldArr[i].getType().getSimpleName();
						try{temp = result.getString(name);}catch(Exception er){temp = null;}
						if ( temp!= null) 
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
							else if (type.equals("Date"))
							{
								fieldArr[i].set(obj,result.getDate(name));
							}
						}
						
					}
					destVec.add(obj);
				}
				return destVec;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				try{result.close();result = null;}catch(Exception e){result = null;}
				try{state.close();state=null;}catch(Exception stateex){state = null;}
				try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			}
			return null;
	}
	public static Hashtable<String, Object> map2ObjHash(String className,String sql, String key)
	{   
	    System.out.println("[SQL]:"+sql); 
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			result = state.executeQuery(sql);
			Class<?> _class = Class.forName(className);
			Field[] fieldArr = _class.getFields();
			String temp = null;
			Hashtable<String, Object> destHash = new Hashtable<String, Object>();
			while(result.next())
			{
				Object obj = _class.newInstance();
				for (int i = 0; i < fieldArr.length; i++) 
				{
					String name = fieldArr[i].getName();
					String type = fieldArr[i].getType().getSimpleName();
					try{temp = result.getString(name);}catch(Exception er){temp = null;}
					if ( temp!= null) 
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
						else if (type.equals("Date"))
						{
							fieldArr[i].set(obj,result.getDate(name));
						}
					}
				}
				destHash.put(result.getString(key),obj);
			}
			return destHash;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}
	
	
	public static int mapBSON2DB(String table,BasicBSONObject bson,boolean returnID)
	{

		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			
			//先把BSON的所有key大写化。
			BasicBSONObject bson2 = new BasicBSONObject();
			for(String key : bson.keySet())
			{
				bson2.append(key.toUpperCase(), bson.get(key));
			}
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			StringBuffer  props = new StringBuffer();
			props.append("insert into "+table+"(");
			StringBuffer  values = new StringBuffer();
			values.append(" values(");
			
			result = state.executeQuery("show columns from "+table);
			String column = null;
			while(result.next())
			{
				column = result.getString("field").toUpperCase();
				if(bson2.containsField(column))
				{
					props.append(column);
					props.append(',');
					
					values.append("'");
					values.append(StringTool.convertStrQuote(String.valueOf(bson2.get(column))));
					values.append("',");	
				}				
			}
			
			props.deleteCharAt(props.length()-1);
			props.append(')');
			values.deleteCharAt(values.length()-1);
			values.append(')');
			
			String sql = props.append(values.toString()).toString();
			
			System.out.println("[sql]"+sql);
			
			int retNum = state.executeUpdate(sql);
			
			if(retNum >0 &&returnID)
			{
			    ResultSet rs = state.executeQuery("select last_insert_id()");
			    if(rs.next())
			    {
			       return rs.getInt(1);
			    }
			}	
			return retNum;  
			
			
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();if(ex.getMessage().indexOf("Duplicate") != -1)
			{
			    return -1062;
			}
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return -1;
	}
	
	public static int mapBSON2UpdateOrInsert(String table,BasicBSONObject bson,String... keys)
	{
		if(mapBSON2Update(table,bson,keys) == 0)
		{
			mapBSON2DB(table,bson,false);
		}
		
		return 1;
	}
	
	public static int mapBSON2Update(String table,BasicBSONObject bson,String... keys)
	{

		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{			
			//先把BSON的所有key大写化。
			BasicBSONObject bson2 = new BasicBSONObject();
			for(String key : bson.keySet())
			{
				bson2.append(key.toUpperCase(), bson.get(key));
			}
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			StringBuffer  props = new StringBuffer();
			props.append("update "+table+" set ");
			
			
			Set<String> columnSet = new TreeSet<String>();
			result = state.executeQuery("show columns from "+table);
			String column = null;
			while(result.next())
			{
				column = result.getString("field").toUpperCase();
				if(bson2.containsField(column))
				{
					columnSet.add(column);
					props.append(column)
					     .append("='").append(StringTool.convertStrQuote(String.valueOf(bson2.get(column))))
					     .append("',");	
				}				
			}
			
			props.deleteCharAt(props.length()-1);
			props.append(" where " );
			
			for(String str : keys)
			{
				int ind = str.indexOf('=');
				int ind2 = str.indexOf('\'');
				
				if(ind != -1 &&(ind2 == -1 || ind2>ind) )
				{
					props.append(str).append(" and ");
					continue;
				}
			
				if(columnSet.contains(str.toUpperCase()))
				{
					props.append(str)
					     .append("='")
					     .append(String.valueOf(bson2.get(str.toUpperCase())))
					     .append("' and ");
					
				}
			}
			props.delete(props.length()-5, props.length());
			
			String sql = props.toString();
			
			System.out.println("[sql]"+sql);
			
			int retNum = state.executeUpdate(sql);
			
			return retNum;  
			
			
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return -1;
	}
	
	
	public  static BasicBSONObject mapDB2BSONObject(String sql)
	{
		System.out.println("[SQL:]"+sql);
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			
			result = state.executeQuery(sql);
			ResultSetMetaData md = result.getMetaData();
			if(result.next())
			{
				BasicBSONObject bson = new BasicBSONObject();
				for(int i = 1;i<=md.getColumnCount();i++)
				{
					bson.append(md.getColumnName(i).toUpperCase(),result.getString(i));
				}
				return bson;
			}
			else
			{
				return null;
			}	
			
			
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}
	
	public  static List<BasicBSONObject> mapDB2BSOList(String sql)
	{
	    System.out.println("[SQL]:"+sql);
		Connection dbConn = null;
		Statement state = null;
		ResultSet result = null;
		List<BasicBSONObject>  list = new ArrayList<BasicBSONObject>();
		try
		{
			
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			
			result = state.executeQuery(sql);
			ResultSetMetaData md = result.getMetaData();
			while(result.next())
			{
				BasicBSONObject bson = new BasicBSONObject();
				for(int i = 1;i<=md.getColumnCount();i++)
				{
					bson.append(md.getColumnName(i).toUpperCase(),result.getString(i));
				}
				list.add(bson);
			}
			return list;	
		}
		catch(Exception ex)
		{
			//GMLogger.getLogger().error(ex);
			ex.printStackTrace();
		}
		finally
		{
			try{result.close();result = null;}catch(Exception e){result = null;}
			try{state.close();state=null;}catch(Exception stateex){state = null;}
			try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
		}
		return null;
	}





	/**************************************/
	//以下为测试代码。
	public static void main1(String[] args)
	{
		//int res = DBManager.insert("c1.test2",true,"(d,v,c,e)","!#now()",100,12,"that is true");
		
		//System.out.println(DBManager.executeDBUpdate("delete from c1.temp2 where id=0"));
		System.out.println(DBManager.executeDBUpdate("insert into c1.temp2 values(0,1)"));
	}

	public static void main2(String[] args)
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

			for(int i=0; i<500000;i++)
			{
				state.executeUpdate("insert into test(name,info) values('name"+i
						+"','test info"+i+"')");
			}
			state.execute("commit");
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
	}   




	public static void test1(String sql)
	{
		System.out.println("test1-1:"+System.nanoTime());
		Connection dbConn = null;
		Statement state = null;
		ResultSet rs = null;
		try
		{
			dbConn = DBManager.getConnection();
			state = dbConn.createStatement();
			rs = state.executeQuery(sql);
			System.out.println("test1-2:"+System.nanoTime());
			while(rs.next())
			{
				rs.getInt(1);
				rs.getString(2);
				rs.getString(3);
				rs.getString(4);	      	    	
			}
			System.out.println("test1-3:"+System.nanoTime());            
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
		}	
	}

	public static void test2(String sql,String key)
	{
		System.out.println("test2-1:"+System.nanoTime());
		System.out.println("selectIntoArray");
		//DBManager.selectIntoArray(sql);  	    
		System.out.println("test2-2:"+System.nanoTime());
		System.out.println("test3-1:"+System.nanoTime());
		System.out.println("queryResult");
		QueryResult qr = DBManager.queryResult(sql, key);  	    
		System.out.println("test3-2:"+System.nanoTime());
		System.out.println("qr.get(19000,2");
		System.out.println(qr.get(19000,2));
		System.out.println("test3-3:"+System.nanoTime());
		System.out.println("qr.get(19000,\"info\")");
		System.out.println(qr.get(19000,"info"));
		System.out.println("test3-4:"+System.nanoTime());
		System.out.println("qr.mapping(\"69000\",2)");
		System.out.println(qr.mapping("69000",2));
		System.out.println("test3-5:"+System.nanoTime());
		System.out.println("qr.mapping(\"69000\",\"info\")");
		System.out.println(qr.mapping("69000","info"));
		System.out.println("test3-6:"+System.nanoTime());
	}
	//根据传过来的对象和ResultSet自动给对象赋值
      private static <T> List<T>  getBean(ResultSet rs, T object) throws Exception {
        Class<?> classType = object.getClass();
        ArrayList<T> objList = new ArrayList<T>();
        //SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
        Field[] fields = classType.getDeclaredFields();//得到对象中的私有字段
        while (rs.next()) {
            //每次循环时，重新实例化一个与传过来的对象类型一样的对象
            T objectCopy = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
            for (int i = 0; i < fields.length; i++) {
                  Field field = fields[i];
                  String fieldName = field.getName();
                  Object value = null;
                  String type=field.getType().getSimpleName();
                //根据字段类型决定结果集中使用哪种get方法从数据中取到数据
                if (field.getType().equals(String.class)) {
                	 try{
                        value = rs.getString(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                    if(value==null){
                        value="";
                    }
                }
                if (field.getType().equals(int.class)) {
                	 try{
                        value = rs.getInt(fieldName);
                	 }catch(SQLException e){
                   	    value="";
                     }
                }
                if (field.getType().equals(java.util.Date.class)) {
                	 try{
                        value = rs.getDate(fieldName);
                	 }catch(SQLException e){
                      	  value="";
                        }
                }
                if(field.getType().equals(double.class)){
                	 try{
                	     value=rs.getDouble(fieldName);
                	 }catch(SQLException e){
                     	  value="";
                       }
                }
                if(field.getType().equals(long.class)){
                	 try{
                	    value=rs.getLong(fieldName);
                	 }catch(SQLException e){
                    	   value="";
                      }
                }
                if(field.getType().equals(float.class)){
                	try{
                	  value=rs.getFloat(fieldName);
                	}catch(SQLException e){
                  	  value="";
                    }
                }
                if(field.getType().equals(byte.class)){
                	try{
                	value=rs.getByte(fieldName);
                	}catch(SQLException e){
                    	  value="";
                      }
                	
                }
                if(field.getType().equals(boolean.class)){
                	try{
                	value=rs.getBoolean(fieldName);
                	  }catch(SQLException e){
                  	  value="";
                     }
              	
                }
                // 获得属性的首字母并转换为大写，与setXXX对应
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String setMethodName = "set" + firstLetter
                        + fieldName.substring(1);
                Method setMethod = classType.getMethod(setMethodName,
                        new Class[] { field.getType() });
                if(value!=null&&!value.equals("")){
                setMethod.invoke(objectCopy, new Object[] { value });//调用对象的setXXX方法
                   }
                }
            
            objList.add(objectCopy);
        }
        
        return objList;
    }
      //映射 成list<javabean>
      public static <T> List<T>  map2ObjlistTest (String className,String sql){
		  System.out.println("[SQL]:"+sql); 
			Connection dbConn = null;
			Statement state = null;
			ResultSet result = null;
			try
			{
				dbConn = DBManager.getConnection();
				state = dbConn.createStatement();
				result = state.executeQuery(sql);
				Class<?> _class = Class.forName(className);
				T obj = (T) _class.newInstance();
				return getBean(result, obj);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				try{result.close();result = null;}catch(Exception e){result = null;}
				try{state.close();state=null;}catch(Exception stateex){state = null;}
				try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			}
			return null;
	}
      //映射java bean
      public static <T> T maptoObjbean(String className,String sql){
    	  System.out.println("[SQL]:"+sql); 
			Connection dbConn = null;
			Statement state = null;
			ResultSet result = null;
			try
			{
				dbConn = DBManager.getConnection();
				state = dbConn.createStatement();
				result = state.executeQuery(sql);
				Class<?> _class = Class.forName(className);
				T obj = (T) _class.newInstance();
				return getobjBean(result,obj);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				try{result.close();result = null;}catch(Exception e){result = null;}
				try{state.close();state=null; }catch(Exception stateex){state = null;}
				try{dbConn.close(); dbConn = null;}catch(Exception connex){dbConn = null;}
			}
			return null;
      }
      
      
      private static <T> T  getobjBean(ResultSet rs, T object) throws Exception {
          Class<?> classType = object.getClass();

          Field[] fields = classType.getDeclaredFields();//得到对象中的私有字段
          
          if (!rs.next()) {return null;}
              
          T obj = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
              for (int i = 0; i < fields.length; i++) {
                  Field field = fields[i];
                  String fieldName = field.getName();
                  Object value = null;
                  String type=field.getType().getSimpleName();
                  //根据字段类型决定结果集中使用哪种get方法从数据中取到数据
                  if (field.getType().equals(String.class)) {
                	  try{
                      value = rs.getString(fieldName);
                      }catch(SQLException e){
                    	  value="";
                      }
                      if(value==null){
                          value="";
                      }
                  }
                  if (field.getType().equals(int.class)){
                	  try{
                       value = rs.getInt(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if (field.getType().equals(java.util.Date.class)){
                	  try{
                       value = rs.getDate(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if(field.getType().equals(double.class)){
                	  try{
                  	 value=rs.getDouble(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if(field.getType().equals(long.class)){
                	  try{
                  	 value=rs.getLong(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if(field.getType().equals(float.class)){
                	  try{
                  	  value=rs.getFloat(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if(field.getType().equals(byte.class)){
                	  try{
                  	value=rs.getByte(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  if(field.getType().equals(boolean.class)){
                	  try{
                  	value=rs.getBoolean(fieldName);
                	  }catch(SQLException e){
                    	  value="";
                      }
                  }
                  // 获得属性的首字母并转换为大写，与setXXX对应
                  String firstLetter = fieldName.substring(0, 1).toUpperCase();
                  String setMethodName = "set" + firstLetter
                          + fieldName.substring(1);
                  Method setMethod = classType.getMethod(setMethodName,
                          new Class[] { field.getType() });
                  if(value.equals("")){setMethod.invoke(obj, new Object[] { value });}//调用对象的setXXX方法
              }
              
             
          
          
          return obj;
      }
}



