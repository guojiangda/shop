package cn.jxy.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.jxy.utils.JdbcUtils;
import cn.jxy.utils.RowMapper;

public class BaseDaoImpl<T> {

	public List<T> queryByName(String sql, Object[] param,Class<T> clazz) {
		List<T> tList = new ArrayList<T>();
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pre.setObject(i + 1, param[i]);
			}
			// 查询返回的是结果集
			rs = pre.executeQuery();
			// 获取结果集的列名称
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				// 说明有结果集合,根据class类型动态创建一个对象
				T model = (T)clazz.newInstance();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 获取列的名称
					String colName = metaData.getColumnLabel(i);
					System.out.println("列名称为:" + colName);
					// 根据列的名称获取相应的属性名称
					Field name = clazz.getDeclaredField(colName);
					// 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
					name.setAccessible(true);
					// 通过反射进行赋值
					name.set(model, rs.getObject(colName));
				}
				// 把动态赋值的每个对象存放到list集合中
				tList.add(model);
			}
			return tList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源(数据库访问、IO、Properties、文件上传、下载都需要关闭相关操作)
			JdbcUtils.close(rs, pre, conn);
		}
	}

	// T 是不确定类型(不是Object),由子类决定具体的类型
	protected T getById(String sql, Object id, Class<T> clazz) {
		T model = null;
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			pre.setObject(1, id);
			// 查询返回的是结果集
			rs = pre.executeQuery();
			if (rs.next()) {
				// 说明有结果集合,根据class类型动态创建一个对象
				model = clazz.newInstance();
				// 获取结果集的列名称
				ResultSetMetaData metaData = rs.getMetaData();
				System.out.println(metaData.getColumnCount());
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 获取列的名称
					String colName = metaData.getColumnLabel(i);
					System.out.println("列名称为:" + colName);
					// 根据列的名称获取相应的属性名称
					Field name = clazz.getDeclaredField(colName);
					// 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
					name.setAccessible(true);
					// 通过反射进行赋值
					name.set(model, rs.getObject(colName));
				}
			}
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(rs, pre, conn);
		}
	}

	// update方法用来操作 delete、update、insert
	protected int update(String sql, Object[] param) {
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pre.setObject(i + 1, param[i]);
			}
			// 3: 真正执行sql语句,executeUpdate:负责update、delete、save操作
			return pre.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(pre, conn);
		}
	}

}
