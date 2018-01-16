package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.jxy.model.Product;
import cn.jxy.utils.JdbcUtils;

public class ProductDaoImpl {
	
	// main测试缺点: 不能并行测试, 有侵入性
//	public static void main(String[] args) {
//	
//	}

	public Product getById(int id) {
		Product product = null;
		String sql = "select * from product where id = ?";
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			pre.setInt(1, id);
			// 查询返回的是结果集
			rs = pre.executeQuery();
			if(rs.next()){ // 当前行存在记录.DB中的一条记录对应Java中的一个对象
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setRemark(rs.getString("remark"));
			}
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(rs,pre, conn);
		}
	}

	// 数据入库操作
	public int save(Product product) {
		String sql = "insert into product (name,price,remark) values (?,?,?)";
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			pre.setString(1, product.getName());
			pre.setDouble(2, product.getPrice());
			pre.setString(3, product.getRemark());
			// 3: 真正执行sql语句,executeUpdate:负责update、delete、save操作
			return pre.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(pre, conn);
		}
	}

	// 数据更新操作
	public int update(Product product) {
		String sql = "update product set name=?,price=?,remark=? where id=?";
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			pre.setString(1, product.getName());
			pre.setDouble(2, product.getPrice());
			pre.setString(3, product.getRemark());
			pre.setInt(4, product.getId());
			// 3: 真正执行sql语句
			return pre.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(pre, conn);
		}
	}

	// 数据删除操作
	public int delete(int id) {
		String sql = "delete from product where id=?";
		// 1: 获取connection连接对象
		Connection conn = null;
		PreparedStatement pre = null;
		try {
			// 2: 给参数赋值,下标从1开始
			conn = JdbcUtils.getConnection();
			pre = conn.prepareStatement(sql);
			pre.setInt(1, id);
			// 3: 真正执行sql语句
			return pre.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// 4： 释放资源
			JdbcUtils.close(pre, conn);
		}
	}
}
