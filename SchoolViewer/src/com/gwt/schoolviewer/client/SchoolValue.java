package com.gwt.schoolviewer.client;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolValue implements Serializable {
	String name;
	ArrayList<String> values;
	String location;
	String district;
	String pCode;
	double latitude;
	double longitude;

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public SchoolValue() {
	}

	public SchoolValue(String name, ArrayList<String> values, String location,
			String district, String pCode, double latitude, double longitude) {
		this.name = name;
		this.values = values;
		this.location = location;
		this.district = district;
		this.pCode = pCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	// If either name,location, district, or pCode don't match then return
	// false. Else true
	public boolean equals(SchoolValue school) {
		if (this.name != school.getName()
				|| this.location != school.getLocation()
				|| this.district != school.getDistrict()
				|| this.pCode != school.getpCode()) {
			return false;
		} else
			return true;

	}
}
