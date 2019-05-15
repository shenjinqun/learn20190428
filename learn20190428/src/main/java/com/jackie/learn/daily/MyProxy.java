package com.jackie.learn.daily;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Observable;
import java.util.Observer;

public class MyProxy implements InvocationHandler {

	Object object;

	public MyProxy(Object object) {
		this.object = object;
	}

	public static Object factory(Object o) {
		return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new MyProxy(o));
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("代理模式触发了");
		try {
			Object invoke = method.invoke(object, args);
			return invoke;
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
		}
		return null;
	}
}

class MyConsole implements Observer {
	public static void info(String message) {
		System.out.println(message);
	}

	public void update(Observable o, Object arg) {
		info("观察者模式触发了");
	}
}

class MyLog implements Observer {
	public static FileWriter fileWriter;
	static {
		try {
			fileWriter = new FileWriter("d:/log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void info(String message)  {
		try {
			fileWriter.write(message);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(Observable o, Object arg) {
		info("观察者模式触发了");
	}
}