package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import com.gwt.schoolviewer.shared.School;

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
	
	public void addSchool(School school)
	{
		schools.add(school);
	}
	
	public ArrayList<School> getSchools() {
		return schools;
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
	
	private void buildSchools()
	{
		return;
	}
	
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
