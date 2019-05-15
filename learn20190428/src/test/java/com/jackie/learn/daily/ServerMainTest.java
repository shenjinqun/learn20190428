package com.jackie.learn.daily;
	
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerMainTest {

	FileInputStream fileInputStream;

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
	public void inputStreamThreadRunTest() throws IOException, InterruptedException, ClassNotFoundException, NoSuchMethodException {

		PrintStream printStream = System.out;

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream2 = new PrintStream(byteArrayOutputStream);
		System.setOut(printStream2);
		
		IStudent<Number> student = new Student(1,"xm_1","mm_1");
		 
		ServerMain.parseStudent(student);

		String testResult = byteArrayOutputStream.toString();
		System.out.println("testresult:"+testResult);
		System.setOut(printStream);
		boolean contains1 = testResult.contains("门面模式");
		boolean contains2 = testResult.contains("观察者模式");
		boolean contains3 = testResult.contains("密码太简单");
		boolean contains4 = testResult.contains("注解触发");
		if (!(contains1 && contains2 && contains3 && contains4)) {
			Assert.fail("not contain in");
		}
	}
}