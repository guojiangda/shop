package cn.jxy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.jxy.model.Product;
import cn.jxy.utils.JdbcUtils;

public class ProductDaoImpl {
	
	public static void main(String[] args) {
		ProductDaoImpl daoImpl = new ProductDaoImpl();
		daoImpl.save(new Product("demo", 3.14, "测试代码！！！"));
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
			// 3: 真正执行sql语句
			return pre.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			// 4： 释放资源
			JdbcUtils.close(pre, conn);
		}
	}
}
