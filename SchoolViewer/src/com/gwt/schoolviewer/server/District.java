package com.gwt.schoolviewer.server;

import java.util.ArrayList;

public class District {

	String name;
	
	ArrayList <School>schools = new ArrayList<School> ();
	
	public District(String name) {
		this.name = name;
	}

}
