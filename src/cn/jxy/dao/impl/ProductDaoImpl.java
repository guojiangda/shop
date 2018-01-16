package cn.jxy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.jxy.model.Product;

public class ProductDaoImpl extends BaseDaoImpl<Product> {

	@Override  
	public Product getRow(ResultSet rs) throws SQLException {
		Product product = null;
		product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getDouble("price"));
		product.setRemark(rs.getString("remark"));
		return product;
	}

	public Product getById(int id) {
		// Product product = null;
		String sql = "select * from product where id = ?";
		return super.getById(sql, id);
	}

	// 数据入库操作
	public int save(Product product) {
		String sql = "insert into product (name,price,remark) values (?,?,?)";
		return super.update(
				sql,
				new Object[] { product.getName(), product.getPrice(),
						product.getRemark() });
	}

	// 数据更新操作
	public int update(Product product) {
		String sql = "update product set name=?,price=?,remark=? where id=?";
		return super.update(
				sql,
				new Object[] { product.getName(), product.getPrice(),
						product.getRemark(), product.getId() });
	}

	// 数据删除操作
	public int delete(int id) {
		String sql = "delete from product where id=?";
		return super.update(sql, new Object[] { id });
	}
}
