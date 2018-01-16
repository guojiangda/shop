package cn.jxy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 工具类: 主要来实现与数据库的连接
 * 静态类: 不想new对象,在java中工具类、Service、Dao、controller等等后面采用spring来管理时都是单例模式
 * filter/servlet/listener:也是单例模式
 * */
public class JdbcUtils {

	private JdbcUtils() {
	} // 构造方法必须私有

	// 在项目开发中,配置文件、jar、class文件通常只需要加载一次,在java中有静态块可以实现此功能
	// static是在当前的class文件被加载的时候执行,且仅仅执行一次
	static {
//		System.out.println("-----只打印一次------");
		// 需要加载数据库驱动(根据类全名,来加载唯一的*.class文件)
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	// 编写一个方法,获取connection连接
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/shop", "root", "root");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Statement pre, Connection conn) {
		try {
			pre.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		// 如果连接失败,考虑：端口是否正确、是否开启数据库服务
		System.out.println(JdbcUtils.getConnection());
		System.out.println(JdbcUtils.getConnection());
		System.out.println(JdbcUtils.getConnection());
	}
}
