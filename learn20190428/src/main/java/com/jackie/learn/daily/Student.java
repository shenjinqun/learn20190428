package com.jackie.learn.daily;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Observable;

import com.jackie.learn.daily.MyAnnotation.ProcessType;

/**
 * @author Jackie.Shen
 *
 * @param <T>
 */
public class Student<T extends Number> extends Observable implements Cloneable, Serializable, IStudent<T>, INameFacade {

	private static final long serialVersionUID = 1L;
	T id;
	String name;
	transient String password;
	Teacher teacher = new Teacher();

	public Student() {
		//todo ---
		
	}

	public Student(T id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement ste = null;
		for (int i = 0; i < stackTrace.length; i++) {
			if( stackTrace[i].getClassName().equals("com.jackie.learn.daily.Student")) {
				ste = stackTrace[i+1];
				break;
			}
		}
		try {
			Class<?> forName = Class.forName(ste.getClassName());
//			String methodName = ste.getMethodName();
			MyAnnotation annotation = forName.getAnnotation(MyAnnotation.class);
			if ( annotation != null && ProcessType.JUMP == annotation.process()) {
				System.out.println("自定义注解触发");
			}
			else {
				this.id = id;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		setChanged();
		notifyObservers();
		return password;
	}

	public void setPassword(String password) {
		if ("123".equals(password)) {
			throw new MyRuntimeException("密码太简单了");
		}
		this.password = password;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		IStudent<Number> student = (IStudent<Number>) super.clone();
		student.setTeacher((Teacher) student.getTeacher().clone());
		return student;
	}

	@Override
	public String toString() {
		return "\nStudent [id=" + id + ", name=" + name + ", password=" + password + ", teacher=" + teacher + "]";
	}

}

class Teacher implements Cloneable, Serializable,INameFacade {
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String password;
	String subject;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("门面模式触发了");
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", password=" + password + ", subject=" + subject + "]";
	}
}

class DescStrategy implements Comparator<IStudent<Number>> {

	public int compare(IStudent<Number> o1, IStudent<Number> o2) {
		// TODO 测试22
		return (int) (o2.getId().doubleValue() - o1.getId().doubleValue());
	}

}

// TODO 测阿受到法律框架水电费垃圾
class Aaa implements Runnable{

	public void run() {
		
	}
	
}