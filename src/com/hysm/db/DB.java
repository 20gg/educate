package com.hysm.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DB {
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	private Connection getConnection() throws Exception {
		String url1 = this.getClass().getResource("").getPath().replaceAll(
				"%20", " ");
		String path = url1.substring(0, url1.indexOf("WEB-INF"))
				+ "WEB-INF/classes/properties/jdbc.properties";
		Properties prop = new Properties();// 属性集合对象
		FileInputStream fis = new FileInputStream(path);// 属性文件流
		prop.load(fis);// 将属性文件流装载到Properties对象中
		String url = prop.getProperty("jdbc.url");
		String username = prop.getProperty("jdbc.username");
		String password = prop.getProperty("jdbc.password");
		String driverClassName = prop.getProperty("jdbc.driverClassName");
		Class.forName(driverClassName);
		conn = DriverManager.getConnection(url, username, password);
		return conn;

	}

	private ResultSet executeQueryRS(String sql) {
		try {
			statement = getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public List<Object> excuteQuery(String sql) {
		ResultSet rs = executeQueryRS(sql);
		ResultSetMetaData rsmd = null;
		List<Object> list = new ArrayList<Object>();
		// 结果集列数
		int columnCount = 0;
		String cols_name = "";// 列名
		DecimalFormat decimalFormatter = new DecimalFormat("0.##");// 将数字格式化为保留小数点两位，但不补齐小数点后两位，也就是不会因为本身是整数而补小数点后的零
		try {
			rsmd = rs.getMetaData();
			// 获得结果集列数
			columnCount = rsmd.getColumnCount();
			// 将ResultSet的结果保存到List中
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {// 遍历此行的所有列
					cols_name = rsmd.getColumnName(i);// 取出列名
					Object val = rs.getObject(cols_name);
					if (val instanceof Number)
					// 如果是数字类型(可以转化为数字objtoint的意思)则格式化为标准数字格式
					{
						val = decimalFormatter.format(val);
					}
					val = (val == null) ? "" : val;// 如果返回值为null则格式化空值
					map.put(cols_name, "" + val);
				}
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return list;
	}

	/**
	 * 关闭所有资源
	 */
	private void closeAll() {
		// 关闭结果集对象
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 关闭PreparedStatement对象
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 关闭Connection 对象
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 事务批量执行sql
	 * 
	 * @param sqls
	 * @return
	 * @throws Exception
	 */
	public String StartTransaction(String[] sqls) throws Exception {
		String info = "";
		Connection conn = getConnection();
		if (sqls == null) {
			info = "nullsql";
		}
		Statement sm = null;
		try {
			// 事务开始
			conn.setAutoCommit(false); // 设置连接不自动提交，即用该连接进行的操作都不更新到数据库
			sm = conn.createStatement(); // 创建Statement对象
			// 依次执行传入的SQL语句
			for (int i = 0; i < sqls.length; i++) {
				sm.execute(sqls[i]);// 执行添加事物的语句
			}
			conn.commit(); // 提交给数据库处理
			// 事务结束
			// 捕获执行SQL语句组中的异常
			info = "success";
		} catch (SQLException e) {
			try {
				conn.rollback(); // 若前面某条语句出现异常时，进行回滚，取消前面执行的所有操作
				info = "failure";
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			sm.close();
			conn.close();
		}

		return info;
	}

}
