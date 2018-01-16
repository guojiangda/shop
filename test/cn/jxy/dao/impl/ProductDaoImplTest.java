package cn.jxy.dao.impl;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.jxy.model.Product;

public class ProductDaoImplTest {

	private static ProductDaoImpl daoImpl = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("在所有测试方法之前执行,一般用来初始化资源............");
		daoImpl = new ProductDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("在所有测试方法之后执行,一般用来销毁资源............");
		daoImpl = null;
	}

	@Test
	public void testGetById() {
		System.out.println(daoImpl.getById(2));
	}
	
	@Test
	public void testQueryByName() {
		List<Product> proList = daoImpl.queryByName("");
		System.out.println("查询方法已经执行完毕.................");
		for(Product product:proList){
			System.out.println(product);
		}
	}

	@Test
	public void testSave() {
		daoImpl.save(new Product("demo2", 3.14, "测试代码！！！"));
	}

	@Test  // 只有条件@Test注解的方法,才是测试方法
	public void testUpdate() {
		Product product = new Product("demo2", 3.15, "测试代码2！！！");
		product.setId(5);
		daoImpl.update(product);
	}

	@Test
	public void testDelete() {
		daoImpl.delete(5);
	}

}
