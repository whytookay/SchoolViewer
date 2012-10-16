package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name;
	
	ArrayList <String[]>values = new ArrayList<String[]> ();
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name, ArrayList<String[]> values) {
		this.name = name;
		this.values = values;
		buildSchools();
	}
	
	private void buildSchools()
	{
		return;
	}
	
	public ArrayList<String[]> getValues()
	{
		return values;
	}

}
