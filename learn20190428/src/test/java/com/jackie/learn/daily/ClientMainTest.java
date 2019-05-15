package com.jackie.learn.daily;
	
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientMainTest {

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
	public void studentListTest() throws InterruptedException {
		Student<Number> student = new Student<Number>();
		new Thread( new StudentThread( 0, student )).start();;
		new Thread( new StudentThread( 1, student )).start();;
		new Thread( new StudentThread( 2, student )).start();;
		Thread.sleep(1000);

		String ids = "";
		for (int i = 0; i < 6; i++) {
			ids = ids + ClientMain.studentList.get(i).getId() + ",";
		}
		System.out.println(ids);
		Assert.assertEquals("1,2,3,4,5,6,", ids );
	}


	@Test
	public void studentNameTest() throws InterruptedException {
		Student<Number> student = new Student<Number>();
		new Thread( new StudentThread( 0, student )).start();;
		new Thread( new StudentThread( 1, student )).start();;
		new Thread( new StudentThread( 2, student )).start();;
		Thread.sleep(100);

		Assert.assertEquals("xm_1", ClientMain.studentList.get(0).getName());
	}

	@Test
	public void studentPasswordTest() throws InterruptedException {
		Student<Number> student = new Student<Number>();
		new Thread( new StudentThread( 0, student )).start();;
		new Thread( new StudentThread( 1, student )).start();;
		new Thread( new StudentThread( 2, student )).start();;
		Thread.sleep(100);

		Assert.assertEquals("mm_1", ClientMain.studentList.get(0).getPassword());
	}
}