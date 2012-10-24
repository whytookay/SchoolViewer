package com.gwt.schoolviewer.server;

public class Value {

	String name;
	Double value;
	
	public Value(String name, Double value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getValue()
	{
		return value;
	}
	
}
