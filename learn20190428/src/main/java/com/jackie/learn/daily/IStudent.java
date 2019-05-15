package com.jackie.learn.daily;

import java.util.Observer;

public interface IStudent<T extends Number> {

	T getId();

	void setId(T id);

	String getName();

	void setName(String name);

	String getPassword();

	void setPassword(String password);

	Teacher getTeacher();

	void setTeacher(Teacher teacher);

	String toString();

	public Object clone() throws CloneNotSupportedException ;

    public void addObserver(Observer o) ;
}