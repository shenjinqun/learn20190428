package com.jackie.learn.daily;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.jackie.learn.daily.MyConsole;
import com.jackie.learn.daily.MyProxy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
 
public class MyProxyTest {

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

	//测试myConsole函数
	@Test
	public void testMyConsole() {
		PrintStream printStream = System.out;

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream2 = new PrintStream(byteArrayOutputStream);
		System.setOut(printStream2);

		String testString = "test console";
		MyConsole.info(testString);
		System.setOut(printStream);

		String testResult = byteArrayOutputStream.toString();
		if (!testResult.contains("test console"))
		{
			Assert.fail("myconsole failed");
		}
	}
	
	//测试动态代理是否工作正常
	@Test
	public void proxyTest(){
		@SuppressWarnings("unchecked")
		Map<Integer,String> map = (Map<Integer,String>) MyProxy.factory(new HashMap<Integer, String>());
		map.put(1, "A");
		String s = map.get(1);
		Assert.assertEquals("A", s );
	}
}