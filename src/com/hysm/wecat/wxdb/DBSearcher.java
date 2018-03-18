package com.hysm.wecat.wxdb;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;


public class DBSearcher 
{
	Vector<String> tableVec = new Vector<String>();
	Hashtable<String,Hashtable<String,String>> table_columns = new Hashtable<String,Hashtable<String,String>>();
	StringBuilder sb = new StringBuilder();
	Connection conn = null;
	Statement state = null;
	ResultSet rs = null;
	
	private void getConnect() throws SQLException
	{
		String url="jdbc:oracle:thin:@10.10.102.161:1521:infodb";
		String driverClassName="oracle.jdbc.driver.OracleDriver";
		String dbUser = "isonehisap";
		String dbPasswd = "123456";
		conn = DBManager.getConnection(driverClassName,url,"",dbUser,dbPasswd);	
		state = conn.createStatement();
	}
	private void getAllTables() throws SQLException
	{		
		rs = state.executeQuery("select COLUMN_NAME,DATA_TYPE,TABLE_NAME from user_tab_columns where table_name in(select table_name from user_tables where num_rows > 0)");
		String table_name = null,column_name = null,date_type = null;
		Hashtable<String,String> hash = null;
		while(rs.next())
		{
			table_name = rs.getString("TABLE_NAME");
			hash = table_columns.get(table_name);
			if(hash == null)
			{
				hash = new Hashtable<String,String>();
				table_columns.put(table_name, hash);
			}
			column_name = rs.getString("COLUMN_NAME");
			date_type = rs.getString("DATA_TYPE");
			hash.put(column_name, date_type);
		}
	}	
	
	
	public void search(String[] excludedTables,Object... args)
	{
		try {
			PrintStream fOut = new PrintStream(new FileOutputStream("d:/out.txt"));
			System.setOut(fOut);
			this.getConnect();
			this.getAllTables();
			Enumeration<String> enu = table_columns.keys();
			String table = null;
			Hashtable<String, String> hash = null;
			Enumeration<String> enu2 = null;
			String key = null, dataType = null;
			int columns = 0;

			int numberKeys = 0;
			boolean excluded = false;
			while (enu.hasMoreElements()) {
				excluded = false;
				sb.delete(0, sb.length());
				table = enu.nextElement();
				if(excludedTables != null)
				{
					for(String t : excludedTables)
					{
						if(table.equalsIgnoreCase(t))
						{
							excluded = true;
							break;
						}
					}
					
				}
				if(excluded)
				{
					continue;
				}
				System.out.println("*****:" + table);
				hash = table_columns.get(table);
				sb.append("select * from " + table + " where ");
				enu2 = hash.keys();
				numberKeys = 0;
				while (enu2.hasMoreElements()) {
					key = enu2.nextElement();
					dataType = hash.get(key);
					if(dataType.equalsIgnoreCase("CLOB") || dataType.equalsIgnoreCase("BLOB"))
					{
						continue;
					}
					for (int i = 0; i < args.length; i++) {
						if (args[i] instanceof Number) {

							if (dataType.equalsIgnoreCase("Number")) {
								if (numberKeys > 0) {
									sb.append(" or ");
								}
								numberKeys++;
								sb.append(key);
								sb.append("=");
								sb.append(args[i]);
							}
						} else if (args[i] instanceof String) {
							if (numberKeys > 0) {
								sb.append(" or ");
							}
							numberKeys++;
							//sb.append("to_char(" + key + ") like '%" + args[i] + "%'");
							sb.append("to_char(" + key + ") = '" + args[i] + "'");
						}
					}
				}
				if (numberKeys == 0) {
					continue;
				}
				// System.out.println(sb.toString());
				rs = state.executeQuery(sb.toString());
				columns = hash.size();
				while (rs.next()) {
					for (int i = 1; i <= columns; i++) {
						System.out.print(rs.getString(i));
						System.out.print("  ");
					}
					System.out.println();
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void listTabs()
	{
		try
		{
			DBManager.setDBInfo("jdbc:oracle:thin:@10.10.102.161:1521:infodb", "oracle.jdbc.driver.OracleDriver", "winshare", "000000");
			QueryResult qr = DBManager.queryResult("select table_name from user_tables order by table_name", null);
			QueryResult qr2 = null;
			PrintStream fOut = new PrintStream(new FileOutputStream("d:/dbtabs.txt"));
			System.setOut(fOut);
			for(int i=0;i<qr.getRows();i++)
			{
				System.out.println("****["+qr.get(i,0)+"]****************************");
				qr2 = DBManager.queryResult("desc "+qr.get(i,0),null);
				qr2.dump();				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public static void  main(String[] args)
	{		
		DBSearcher  ds = new DBSearcher();
		ds.search(null,102);
		//ds.listTabs();
	}
}
