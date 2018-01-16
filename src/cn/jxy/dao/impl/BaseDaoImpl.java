package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.jxy.model.Product;
import cn.jxy.utils.JdbcUtils;

public abstract class BaseDaoImpl<T> {

	// 由于不同的子类,传入的sql语句不同.返回的结果集肯定不同,代码不具备共性.因此此代码父类不能抽取
	protected abstract T getRow(ResultSet rs);

	// T java中的泛型,此类型可以在具体的子类来指定
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
			if (rs.next()) { // 当前行存在记录.DB中的一条记录对应Java中的一个对象
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
