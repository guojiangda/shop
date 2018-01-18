package cn.jxy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cn.jxy.model.Product;
import cn.jxy.utils.RowMapper;

public class ProductDaoImpl extends BaseDaoImpl<Product> {
	
	public List<Product> queryByName(String keyword) {
		String sql = "select * from product where name like ?";
//		jdbcTemplate.query(sql, args, rowMapper)
		return super.queryByName(sql, new Object[] { "%" + keyword + "%" },Product.class);
	}

	public Product getById(int id) {
		// Product product = null;
		String sql = "select id,name,remark,price from product where id = ?";
		return super.getById(sql, id,Product.class);
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
