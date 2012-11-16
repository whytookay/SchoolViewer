package com.gwt.schoolviewer.client;

import java.io.Serializable;

public class LatLong implements Serializable { // TODO: REFACTOR OUR CODE TO USE LATLONG
	Double latitude;
	Double longitude;
	
	public LatLong() {
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public LatLong(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
