package com.jackie.learn.daily;
	
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Observer;

import com.jackie.learn.daily.IStudent;
import com.jackie.learn.daily.MyAnnotation;
import com.jackie.learn.daily.MyAnnotation.ProcessType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IStudentTest {

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
	
	//测试是否有clone方法
	@Test
	public void cloneMethodTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("clone", null);
	}
	
	//测试是否有addObserver方法
	@Test
	public void addObserverMethodTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("addObserver", Observer.class);
	}

	//测试toString方法
	@Test
	public void toStringMethodTest() throws NoSuchMethodException, SecurityException{
		Method method = IStudent.class.getMethod("toString", null);
	}
}
