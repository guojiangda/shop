package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.jxy.model.Product;
import cn.jxy.utils.JdbcUtils;

public class BaseDaoImpl {

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
