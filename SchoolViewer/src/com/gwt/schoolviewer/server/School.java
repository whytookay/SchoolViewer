package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import com.gwt.schoolviewer.client.SchoolValue;

public class School {
	private String name; //
	private String location; //
	private String phone; //
	private String pubOrInd; //
	private String eduLevel; //
	private String pCode; //
	private District district; //
	private Double latitude; //
	private Double longitude; //
	private Double classSize = -1.0; //
	
	public School(String name, String location, String phone, String pubOrInd, 
							String eduLevel, String pCode,
							District district, Double latitude, Double longitude) {
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.pubOrInd = pubOrInd;
		this.eduLevel = eduLevel;
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
	
	public String getLevel()
	{
		return eduLevel;
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
	
	public SchoolValue getEquivSchoolValue() {
		return (new SchoolValue(name,
				location,
				district.name,
				district.website,
				pCode,
				latitude,
				longitude,
				classSize,
				phone,
				pubOrInd,
				eduLevel));
	}
	
}
