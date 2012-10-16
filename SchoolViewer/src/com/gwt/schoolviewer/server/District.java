package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name;
	
	ArrayList <String[]>values = new ArrayList<String[]> ();
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name) {
		this.name = name;
	}
	
	public ArrayList<String[]> getValues()
	{
		return values;
	}

}
