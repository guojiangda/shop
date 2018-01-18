package cn.jxy.ref;

import java.io.File;
import java.util.Arrays;

// 此类用来理解反射的概念
public class ClassDemo {

	public static void main(String[] args) throws Exception {
		// 如果想删除D盘的一个 1.txt文件,java中如何处理
		File file = new File("D:/Product.class"); // 系统中的文件转化为File类型的对象
		file.delete(); // 删除当前文件
		// java中定义了一个Class类型.专门用来表示 *.class文件/对象
		// java一切皆为对象
		Class clazz = Class.forName("cn.jxy.model.Product");
		// 可以通过反射来获取类中所有信息
		System.out.println("当前类的父类:" + clazz.getSuperclass());
		System.out.println("当前类的接口:" + Arrays.toString(clazz.getInterfaces()));
		ClassLoader classLoader = clazz.getClassLoader();
		System.out.println("加载当前类的类加载器:" + classLoader);
	}
}
