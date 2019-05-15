package com.jackie.learn.daily;
	
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jackie.learn.daily.MyAnnotation.ProcessType;

@MyAnnotation(process=ProcessType.JUMP)
public class MyAnnotationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void MyAnnotationTest() {
		IStudent<Integer> student = new Student(1,"zhangsan","123456");
		student.setId(1234);
		Assert.assertNotEquals(new Integer(1234), student.getId());
	}
	
	/**
	 * 
	 */
	@Test
	public void ServerMainAnnotationTest() {
		MyAnnotation annotation = ServerMain.class.getAnnotation(MyAnnotation.class);
		System.out.println(annotation.process());
	}
}