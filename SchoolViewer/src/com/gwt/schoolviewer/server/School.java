package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class School {
	private String name; //
	private String location; //
	private String phone; //
	private String pubOrInd; //
	private String city; // remove
	private String eduLevel; //
	private String grades; // remove
	private String pCode; //
	private District district; //
	private Double latitude; //
	private Double longitude; //
	private Double classSize = -1.0; //
	
	public School(String name, String location, String phone, String pubOrInd,
						String city, String eduLevel, String grades, String pCode,
							District district, Double latitude, Double longitude) {
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.pubOrInd = pubOrInd;
		this.city = city;
		this.eduLevel = eduLevel;
		this.grades = grades;
		this.district = district;
		this.pCode = pCode;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	
	public Double getLat()
	{
		return latitude;
	}
	
	public Double getLon()
	{
		return longitude;
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
	
	public String getPCode()
	{
		return pCode;
	}
	
	public District getDistrict()
	{
		return district;
	}
	
	public void setClassSize(double size)
	{
		this.classSize = size;
	}
	
	public double getClassSize()
	{
		return classSize;
	}
	
}
