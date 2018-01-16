package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.jxy.utils.JdbcUtils;
import cn.jxy.utils.RowMapper;

public class BaseDaoImpl<T> {

	public List<T> queryByName(String sql, Object[] param,
			RowMapper<T> rowMapper) {
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
			while (rs.next()) {
				// 不同sql语句查询结果集不同,因此此处代码并不是共性代码,因此在父类定义一个抽象方法,由子类去实现
				// this,永远指向的是当前调用的对象
				tList.add(rowMapper.mapRow(rs));
			}
			return tList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源(数据库访问、IO、Properties、文件上传、下载都需要关闭相关操作)
			JdbcUtils.close(rs, pre, conn);
		}
	}

	// T 是不确定类型(不是Object),由子类决定具体的类型
	protected T getById(String sql, Object id, RowMapper<T> rowMapper) {
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
				model = rowMapper.mapRow(rs);
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
