package cn.jxy.ref;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import cn.jxy.model.Product;

// 此类用来理解反射的概念
public class ClassDemo {

	// Class类语法讲解
	public static void classDemo() throws Exception {
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

	public static void methodDemo() throws Exception {
		Product product = new Product();
		product.setName("苹果");
		System.out.println(product.getName());
		// 用反射通过调用方法来实现赋值

		// 1: 获取当前class文件信息
		Class clazz = Class.forName("cn.jxy.model.Product");
		// 2: 根据class文件动态创建一个对象
		Object obj = clazz.newInstance();
		// 3：通过反射机制获取当前class文件中的所有方法(只包括自身和父类public的方法)
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
		System.out.println();
		// 4：通过反射机制获取当前class文件中的所有方法(只获取自身的方法,包括private)
		for (Method method : clazz.getDeclaredMethods()) {
			System.out.println(method);
		}

		// 5: 获取指定的方法,并且通过方法进行赋值
		Method setName = clazz.getMethod("setName", String.class);
		// product.setName("苹果");
		setName.invoke(obj, "葡萄");
		Method getName = clazz.getMethod("getName");
		System.out.println(getName.invoke(obj));
	}

	public static void fieldDemo() throws Exception {
		// 1: 获取当前class文件信息
		Class clazz = Class.forName("cn.jxy.model.Product");
		// 2: 根据class文件动态创建一个对象
		Object obj = clazz.newInstance();
		// 3：通过反射机制获取当前class文件中的所有属性(只包括自身和父类public的方法)
		for (Field field : clazz.getFields()) {
			System.out.println(field);
		}
		System.out.println();
		// 4：通过反射机制获取当前class文件中的所有属性(只包括自身属性)
		for (Field field : clazz.getDeclaredFields()) {
			System.out.println(field);
		}
		
		// 5: 获取指定的属性,并且通过属性进行赋值 
		Field name = clazz.getDeclaredField("name");
		// 值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
		name.setAccessible(true);
		// 反射绕过安全性检查直接给属性赋值
		name.set(obj, "西瓜");
		System.out.println("属性的值为:" + name.get(obj));

	}

	public static void main(String[] args) throws Exception {
		// ClassDemo.classDemo();
		// ClassDemo.methodDemo();
		ClassDemo.fieldDemo();
	}
}
