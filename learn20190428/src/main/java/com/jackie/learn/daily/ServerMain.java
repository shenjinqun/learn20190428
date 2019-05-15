package com.jackie.learn.daily;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jackie.learn.daily.MyAnnotation.ProcessType;

@MyAnnotation(process = ProcessType.JUMP)
public class ServerMain {

	private static ServerSocket serverSocket;
	public static List<IStudent<Number>> studentList = new ArrayList<IStudent<Number>>();

	public static void main(String[] args) throws Exception {
//		serverSocket = new ServerSocket(5000);
//		Socket accept = serverSocket.accept();
//		InputStream inputStream = accept.getInputStream();
//
//		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//		Object readObject = objectInputStream.readObject();
//		System.out.println(readObject);
		
		ReceiveInfoThread receiveInfoThread = new ReceiveInfoThread();
		receiveInfoThread.start();
		receiveInfoThread.join();
		parseStudent(studentList.get(0));
	}
	
	public static void parseStudent( IStudent<Number> student) {

		//门面模式触发了
		INameFacade nameFacade = (INameFacade) student;
		nameFacade.setName("李四");
		INameFacade nameFacade2 = (INameFacade) student.getTeacher();
		nameFacade2.setName("王五");

		//测试观察者模式
		student.addObserver(new MyConsole());
		student.addObserver(new MyLog());
		student.getPassword();
		
		//代理模式触发了
		IStudent<Number> factory = (IStudent<Number>) MyProxy.factory(student);
		factory.setPassword("123");
		
		//自定义注解触发
		student.setId(999);
		System.out.println(student.getId());
	}
}

class ReceiveInfoThread extends Thread {
	@Override
	public void run() {
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(100);
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind( new InetSocketAddress(5000));
			serverSocketChannel.configureBlocking(false);
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while( true ) {
				selector.select();
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				for (SelectionKey selectionKey : selectedKeys) {
					if( selectionKey.isAcceptable()) {
						SocketChannel accept = serverSocketChannel.accept();
						accept.configureBlocking(false);
						accept.register(selector, selectionKey.OP_READ);
					}
					
					if( selectionKey.isReadable()) {
						SocketChannel channel = (SocketChannel) selectionKey.channel();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int len;
						while(( len = channel.read(byteBuffer)) > 0 ) {
							baos.write(byteBuffer.array());
							byteBuffer.flip();
						}
						ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
						ServerMain.studentList = (List<IStudent<Number>>) objectInputStream.readObject();
						System.out.println( ServerMain.studentList ); 
						return;
					}
				}
				selectedKeys.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}