package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name; //
	String website; //
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name, String website) {
		this.name = name;
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
	
	public String getWeb(){
		return website;
	}
	
	public ArrayList<School> getSchools()
	{
		return schools;
	}

}
