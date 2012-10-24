package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name;
	String city;
	String phone;
	String website;
	
//	ArrayList <Value>values = new ArrayList<Value> ();
	ArrayList<String> values = new ArrayList<String> ();
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name, String city, String phone, String website) {
		this.name = name;
		this.city = city;
		this.phone = phone;
		this.website = website;
	}
	
//	public void addValue(String nName, Double nVal)
//	{
//		Value val = new Value(nName, nVal);
//		values.add(val);
//	}
	
	public void addSchool(School school)
	{
		schools.add(school);
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
	
	public ArrayList<School> getSchools()
	{
		return schools;
	}
	
//	public ArrayList<Value> getValues()
//	{
//		return values;
//	}
	
	public ArrayList<String> getValues()
	{
		return values;
	}
	
//	public void printSchools()
//	{
//		System.out.print(this.getName() + " district contains: ");
//		for(int i = 0; i < schools.size(); i++)
//		{
//			System.out.print(schools.get(i).getName() + ", ");
//		}
//		System.out.println();
//	}

}
