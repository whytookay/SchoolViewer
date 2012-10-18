package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name;
	String city;
	String phone;
	String website;
	
	ArrayList <String>values = new ArrayList<String> ();
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name, String city, String phone, String website) {
		this.name = name;
		this.city = city;
		this.phone = phone;
		this.website = website;
		buildSchools();
	}
	
	public String getName(){
		return name;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getWeb(){
		return website;
	}
	
//	public District(String name, ArrayList<String> values) {
//		this.name = name;
//		this.values = values;
//		buildSchools();
//	}
	
	private void buildSchools()
	{
		return;
	}
	
	public ArrayList<String> getValues()
	{
		return values;
	}

}
