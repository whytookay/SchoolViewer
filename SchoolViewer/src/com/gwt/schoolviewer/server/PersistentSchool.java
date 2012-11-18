package com.gwt.schoolviewer.server;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;
import com.gwt.schoolviewer.client.SchoolValue;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PersistentSchool {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private Date createDate;
	  
	@Persistent
	String name;
	@Persistent
	ArrayList<String> values;
	@Persistent
	String location;
	@Persistent
	String district;
	@Persistent
	String pCode;
	@Persistent
	double latitude;
	@Persistent
	double longitude;
	@Persistent
	double classSize;
	@Persistent
	String phone;
	@Persistent
	String pubOrInd;
	@Persistent
	String eduLevel;
	
	public PersistentSchool() {
		this.createDate = new Date();
	}

	public PersistentSchool(User user, String name, ArrayList<String> values, String location, String district, String pCode, double latitude, double longitude, double classSize, String phone, String pubOrInd, String eduLevel) {
		this();
		this.user = user;
		this.name = name;
		this.values = values;
		this.location = location;
		this.district = district;
		this.pCode = pCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.classSize = classSize;
		this.phone = phone;
		this.pubOrInd = pubOrInd;
		this.eduLevel = eduLevel;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
		  
	public String getName() {
		return name;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public String getLocation() {
		return location;
	}

	public String getDistrict() {
		return district;
	}

	public String getpCode() {
		return pCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
		  
	public double getClassSize() {
		return classSize;
	}
	
	public String getPhone() {
		return phone;
	}

	public String getPubOrInd() {
		return pubOrInd;
	}

	public String getEduLevel() {
		return eduLevel;
	}
	
	public SchoolValue getEquivSchoolValue() {
		return (new SchoolValue(name,
				this.getValues(),
				location,
				district,
				pCode,
				latitude,
				longitude,
				classSize,
				phone,
				pubOrInd,
				eduLevel));
	}
	
}
