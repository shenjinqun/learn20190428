package com.jackie.learn.daily;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

	private FileInputStream fileInputStream;

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		String file = this.getResource("").getFile();
		file = file.replace("test-classes", "classes") + "/" + name.replace(".", "/") + ".class";
		System.out.println(file);
		try {
			fileInputStream = new FileInputStream(file);
			byte[] b = new byte[100];
			int len;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while((len=fileInputStream.read(b)) != -1 ) {
				baos.write(b, 0, len);
			}
			byte[] byteArray = baos.toByteArray();
			Class<?> defineClass = defineClass( name, byteArray,0, byteArray.length);
			return defineClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		MyClassLoader myClassLoader = new MyClassLoader();
		myClassLoader.findClass("com.jackie.learn.daily.Teacher");
		Class<?> findClass = myClassLoader.findClass("com.jackie.learn.daily.Student");
		findClass.newInstance();
	}
}