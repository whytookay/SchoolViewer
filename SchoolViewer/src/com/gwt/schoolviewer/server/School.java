package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class School {

	String name;
	String location;
	District district;
	
	public School() {
		// TODO Auto-generated constructor stub
	}

	public String getName()
	{
		return name;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public District getDistrict()
	{
		return district;
	}
	
	public ArrayList<String[]> getValues()
	{
		return district.getValues();
	}
	
}
