package com.google.gwt.schoolviewer.server;

import javax.persistence.Tuple;

public class School {
	
	private String name;
	private Tuple[] values;
	private String location;
	private String district;
	
	public School(String sName, String sLocation, String sDistrict) {
		name = sName;
		location = sLocation;
		district = sDistrict;
	}
	
}
