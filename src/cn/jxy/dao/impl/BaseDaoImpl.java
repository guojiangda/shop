package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cn.jxy.model.Product;
import cn.jxy.utils.JdbcUtils;

public abstract class BaseDaoImpl<T> {

	// 不同的子类根据查询sql语句实现此方法
	public abstract T getRow(ResultSet rs) throws SQLException;

	// T 是不确定类型(不是Object),由子类决定具体的类型
	protected T getById(String sql, Object id) {
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
				// 不同sql语句查询结果集不同,因此此处代码并不是共性代码,因此在父类定义一个抽象方法,由子类去实现
				// this,永远指向的是当前调用的对象
				model = this.getRow(rs);
			}
			return model;
		} catch (SQLException e) {
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
			System.out.println(pre);
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
