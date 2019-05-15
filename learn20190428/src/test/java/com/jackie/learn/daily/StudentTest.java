package com.jackie.learn.daily;
	
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StudentTest {

	IStudent<Integer> student;
	String observerMessage;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		student = new Student<Integer>();
		observerMessage = "";
	}

	@After
	public void tearDown() throws Exception {
		observerMessage = "";
	}

	// 测试泛型是否继承于Number
	@Test
	public void testSetId() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Class<?> forName = Class.forName("com.jackie.learn.daily.Student");

		@SuppressWarnings("unused")
		Constructor<?> constructor = forName.getConstructor(Number.class, String.class, String.class);
	}

	// 测试观察者模式
	@Test
	public void testGetPassword() {
		student.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				// 被触发，对全局变量赋值
				observerMessage = "observer invoked";
			}
		});

		student.getPassword();
		assertEquals("observer invoked", observerMessage);
	}

	// 测试是否能够抛出预期的异常
	@Test
	public void testSetPassword() {
		thrown.expect(MyRuntimeException.class);
		thrown.expectMessage("密码太简单了");
		student.setPassword("123");
	}

	// 测试toString函数
	@Test
	public void testToString() {
		student = new Student<Integer>(1, "name1", "password1");
		String str = student.toString();
		assertEquals(
				"\nStudent [id=1, name=name1, password=password1, teacher=Teacher [id=0, name=null, password=null, subject=null]]",
				str);
	}

	// 测试深克隆
	@Test
	public void testDeepClone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		IStudent<Number> clone = (IStudent<Number>) student.clone();
		System.out.println(clone.getTeacher().hashCode() + "-" + student.getTeacher().hashCode());
		Assert.assertNotEquals(clone.getTeacher().hashCode(), student.getTeacher().hashCode());
	}

	// 测试策略模式
	@Test
	public void testDescStrategy() {
		ArrayList<IStudent<Number>> arrayList = new ArrayList<IStudent<Number>>();
		arrayList.add(new Student<Number>(2, "2", "2"));
		arrayList.add(new Student<Number>(3, "3", "3"));
		arrayList.add(new Student<Number>(1, "1", "1"));
		System.out.println(arrayList);
		Collections.sort(arrayList, new DescStrategy());
		System.out.println(arrayList);

		String ids = "";
		for (int i = 0; i < 3; i++) {
			ids = ids + arrayList.get(i).getId() + ",";
		}
		Assert.assertEquals("3,2,1,", ids);
	}

	// 测试Teacher对象中的toString方法
	@Test
	public void teacherToStringTest() {
		student = new Student<Integer>(1, "name1", "password1");
		student.getTeacher().setId(2);
		student.getTeacher().setName("teacher_li");
		student.getTeacher().setPassword("12345678");
		student.getTeacher().setSubject("English");
		String str = student.getTeacher().toString();
		assertEquals("Teacher [id=2, name=teacher_li, password=12345678, subject=English]", str);
	}
}