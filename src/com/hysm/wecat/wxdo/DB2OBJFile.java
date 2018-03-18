package com.hysm.wecat.wxdo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import com.hysm.wecat.wxdb.DBManager;



public class DB2OBJFile
{
	Connection conn = null;
	Statement state = null;
	ResultSet rs = null;
	Vector<String> vec = new Vector<String>();

	Properties  typeMap = new Properties();
	public DB2OBJFile()
	{
		init();
	}

	public String getColumTypeMap(String dbColumnType)
	{
		String type = typeMap.getProperty(dbColumnType);
		if(type == null)
		{
			if(dbColumnType.startsWith("DECIMAL("))
					{
				type = "double";
					}
			else if(dbColumnType.startsWith("INT(")
					|| dbColumnType.startsWith("NUMBER(")
					|| dbColumnType.startsWith("INTEGER("))
			{
				type = "int";
			}
			else if(dbColumnType.startsWith("CHAR(")
					|| dbColumnType.startsWith("VARCHAR(")
					|| dbColumnType.startsWith("VARCHAR2("))
			{
				type = "String";
			}
			else if(dbColumnType.startsWith("TIMESTAMP("))
			{
				type = "String";
			}
		}
		return type;
	}


	public void init()
	{
		try
		{
			typeMap.load(new FileInputStream("./dataTypeMap.txt"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void convertDB2OBJ(String driverName,String dbUrl,String dbName,String user,String passwd)
	{
		if((conn = DBManager.getConnection(driverName,dbUrl,dbName,user,passwd)) != null)
		{
			convertDB2OBJ();
		}
	}
	public void convertDB2OBJ()
	{
		try
		{
			if(conn == null)
			{
				conn = DBManager.getConnection();
			}
			state = conn.createStatement();
			rs = state.executeQuery("show tables");
			while(rs.next())
			{
				vec.add(rs.getString(1).toUpperCase());
			}

			for(int i=0;i<vec.size();i++)
			{
				convertTable2OBJ(vec.elementAt(i));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{conn.close();}catch(Exception ex){}
		}
	}

	String curTableName = null;

	String[][] fields = new String[100][7];
	int fieldNum = 0;

	private void convertTable2OBJ(String tableName) throws Exception
	{
		if(conn == null)
		{
			conn = DBManager.getConnection();
		}
		if(state == null)
		{
			state = conn.createStatement();
		}
		fieldNum = 0;
		rs = state.executeQuery("desc " + tableName);
		while(rs.next())
		{
			fields[fieldNum][0] = rs.getString(1).toUpperCase();
			fields[fieldNum][1] = rs.getString(2).toUpperCase();
			fields[fieldNum][2] = rs.getString(3).toUpperCase();
			fields[fieldNum][3] = rs.getString(4);
			fields[fieldNum][4] = rs.getString(5);
			fields[fieldNum][5] = rs.getString(6).toUpperCase();
			fields[fieldNum][6] = getColumTypeMap(fields[fieldNum][1]);
			fieldNum++;

		}
		curTableName = tableName;
		dumpOBJFile();
	}

	private void dumpOBJFile()
	{
		try
		{
			File file = new File(curTableName+".java");
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			pw.println("package net.chichenglan.db;");
			pw.println("public class "+curTableName);
			pw.println("{");
			for(int i = 0;i<fieldNum;i++)
			{
				pw.print("private "+fields[i][6] +" "+fields[i][0]);
				if(fields[i][4]!= null && !fields[i][4].equals(""))
				{
					if(fields[i][6].equals("String"))
					{
						pw.print(" = \""+fields[i][4]+" \"");
					}
					else
					{
						pw.print(" = "+fields[i][4]);
					}
				}
				pw.println(";");
			}

			for(int i = 0;i<fieldNum;i++)
			{
				//set方法
				pw.println("public void set"+fields[i][0]+"("+fields[i][6]+" value)");
				pw.println("{");
				pw.println("    "+fields[i][0]+" = value;");
				pw.println("}");

				//get方法
				pw.println("public " +fields[i][6]+" get"+fields[i][0]+"()");
				pw.println("{");
				pw.println("    return "+fields[i][0]+";");
				pw.println("}");
			}
			pw.println("}");
			pw.flush();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}


	public static void main(String[] args)
	{
		new DB2OBJFile().convertDB2OBJ("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/","pmgm","root","111111");
	}
}
