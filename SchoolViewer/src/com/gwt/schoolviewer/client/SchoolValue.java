package com.gwt.schoolviewer.client;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolValue implements Serializable {
	String name;
	String location;
	String district;
	String districtWebsite;
	String pCode;
	double latitude;
	double longitude;
	Double classSize;
	String phone;
	String pubOrInd;
	String eduLevel;
	
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public SchoolValue() {
	}

	public SchoolValue(String name, String location,
			String district, String districtWebsite, String pCode, double latitude, double longitude, double classSize, String phone, String pubOrInd, String eduLevel) {
		this.name = name;
		this.location = location;
		this.district = district;
		this.districtWebsite = districtWebsite;
		this.pCode = pCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.classSize = classSize;
		this.phone = phone;
		this.pubOrInd = pubOrInd;
		this.eduLevel = eduLevel;
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
	
	public String getDistrictWebsite() {
		return districtWebsite;
	}

	public void setDistrictWebsite(String districtWebsite) {
		this.districtWebsite = districtWebsite;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	
	public Double getClassSize() {
		return classSize;
	}

	public void setClassSize(Double classSize) {
		this.classSize = classSize;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPubOrInd() {
		return pubOrInd;
	}

	public void setPubOrInd(String pubOrInd) {
		this.pubOrInd = pubOrInd;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
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
