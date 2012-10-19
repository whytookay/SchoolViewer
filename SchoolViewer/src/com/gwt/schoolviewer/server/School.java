package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class School {

	private String name;
	private String location;
	private String phone;
	private String pubOrInd;
	private String city;
	private String eduLevel;
	private String grades;
	private District district;
	
	public School(String name, String location, String phone, String pubOrInd, String city, String eduLevel, String grades, District district) {
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.pubOrInd = pubOrInd;
		this.city = city;
		this.eduLevel = eduLevel;
		this.grades = grades;
		this.district = district;
	}

	public String getName()
	{
		return name;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getType()
	{
		return pubOrInd;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getLevel()
	{
		return eduLevel;
	}
	
	public String getGrades()
	{
		return grades;
	}
	
	public District getDistrict()
	{
		return district;
	}
	
	public ArrayList<String> getValues()
	{
		return district.getValues();
	}
	
}
