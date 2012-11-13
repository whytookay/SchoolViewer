package com.gwt.schoolviewer.server;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

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
	
	public PersistentSchool() {
		this.createDate = new Date();
	}

	public PersistentSchool(User user, String name, ArrayList<String> values, String location, String district, String pCode, double latitude, double longitude) {
		this();
		this.user = user;
		this.name = name;
		this.values = values;
		this.location = location;
		this.district = district;
		this.pCode = pCode;
		this.latitude = latitude;
		this.longitude = longitude;
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
		  
	
	
}
