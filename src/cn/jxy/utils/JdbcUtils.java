package cn.jxy.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * 主要来实现与数据库的连接
 * */
public class JdbcUtils {
	
	// 在项目开发中,配置文件、jar、class文件通常只需要加载一次,在java中有静态块可以实现此功能
	// static是在当前的class文件被加载的时候执行,且仅仅执行一次
	static{
		System.out.println("-----只打印一次------");
		// 需要加载数据库驱动(根据类全名,来加载唯一的*.class文件)
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	// 编写一个方法,获取connection连接
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "root");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		// 如果连接失败,考虑：端口是否正确、是否开启数据库服务
		System.out.println(JdbcUtils.getConnection());
		System.out.println(JdbcUtils.getConnection());
		System.out.println(JdbcUtils.getConnection());
	}
}







