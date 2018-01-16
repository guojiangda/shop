package cn.jxy.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

// 为每个查询方法量身定做一个RowMapper对象
public interface RowMapper<T> {

	public abstract T mapRow(ResultSet rs) throws SQLException;
}
