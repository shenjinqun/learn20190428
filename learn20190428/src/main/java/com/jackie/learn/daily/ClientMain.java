package com.jackie.learn.daily;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jackie.learn.daily.MyAnnotation.ProcessType;

public class ClientMain {

	public static IStudent<Number> student;
	public static List<IStudent<Number>> studentList = new ArrayList<IStudent<Number>>();
	public static volatile boolean completed = false;

	static {
		try {
			Class<?> forName = Class.forName("com.jackie.learn.daily.Student");
			Constructor<?> constructor = forName.getConstructor(Number.class, String.class, String.class);
			student = (IStudent<Number>) constructor.newInstance(1, "zhangsan", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		// student.addObserver(new MyLog());
		// student.addObserver(new MyConsole());
		// student.getPassword();

		student.setId(999);

		 final CountDownLatch countDownLatch = new CountDownLatch(1);
		 new Thread(new Runnable() {
		
		 public void run() {
		 while (ClientMain.completed == false) {
		 }
		 countDownLatch.countDown();
		 }
		 }).start();
		
		 ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		 newFixedThreadPool.execute(new StudentThread(0, student));
		 newFixedThreadPool.execute(new StudentThread(1, student));
		 newFixedThreadPool.execute(new StudentThread(2, student));
		
		 // Thread.sleep(1000);
		 countDownLatch.await();
		
		 System.out.println(studentList);
		 Collections.sort(studentList, new DescStrategy());
		
		 Socket socket = new Socket("localhost", 5000);
		 OutputStream outputStream = socket.getOutputStream();
		
		 // ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		 // objectOutputStream.writeObject(studentList);
		
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
		 objectOutputStream.writeObject(ClientMain.studentList);
		 outputStream.write(baos.toByteArray());
	}
}

class StudentThread implements Runnable {
	int mode;
	IStudent<Number> student;

	public StudentThread(int mode, IStudent<Number> student) {
		this.mode = mode;
		this.student = student;
	}

	public void run() {
		try {
			for (int i = 0; i < 2; i++) {
				synchronized (ClientMain.studentList) {
					while (ClientMain.studentList.size() % 3 != mode) {
						ClientMain.studentList.wait();
					}
					IStudent<Number> addStudent = StudentProcess.addStudent(student);
					ClientMain.studentList.add(addStudent);
					ClientMain.studentList.notifyAll();
				}
			}

			if (mode == 2) {
				ClientMain.completed = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class StudentProcess {
	public static IStudent<Number> addStudent(IStudent<Number> student) {
		try {
			
			// TODO Auto-generated method stub

			@SuppressWarnings("unchecked")
			IStudent<Number> s = (IStudent<Number>) student.clone();
			int id = ClientMain.studentList.size() + 1;
			s.setId(id);
			s.setName("xm_" + id);
			s.setPassword("mm_" + id);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}