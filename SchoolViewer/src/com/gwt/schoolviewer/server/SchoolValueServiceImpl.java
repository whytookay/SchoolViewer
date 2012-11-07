package com.gwt.schoolviewer.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.NotLoggedInException;
import com.gwt.schoolviewer.client.PostalCodeValue;
import com.gwt.schoolviewer.client.SchoolValue;
import com.gwt.schoolviewer.client.SchoolValueService;

public class SchoolValueServiceImpl extends RemoteServiceServlet implements SchoolValueService {
	private PostalCode code = new PostalCode(getUser(), "V5J2C4");
	private Double latitude = 49.25;
	private Double longitude = -123.11;
	
	@Override
	public PostalCodeValue getCode() {
		return new PostalCodeValue(code.getCode()); //codes;
	}
	
	@Override
	public Boolean clearCode() {
		code = null;
		return true;
	}
	
	@Override
	public Boolean setCode(PostalCodeValue pCode) throws NotLoggedInException {
		code = new PostalCode(getUser(), pCode.getCode());
		
		if (code != null) {
		// some simple code inspired by http://stackoverflow.com/questions/7467568/parsing-json-from-url
		try {
			JSONObject geoCodeJSON = new JSONObject(readUrl("http://maps.googleapis.com/maps/api/geocode/json?address=" + code.getCode() + "&sensor=false"));
			String latit = geoCodeJSON.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
			String longit = geoCodeJSON.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
			latitude = Double.parseDouble(latit);
			longitude = Double.parseDouble(longit);
			System.out.println("latitude, longitude are" + latitude + " " + longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return true;
		} else {
			return false; // invalid code
		}
			
	}
	
	// returns all schoolvalues within a given range of this class's set postal code. If postal code is null, returns false.
	@Override
	public ArrayList<SchoolValue> getValuesRange(Double radius) {
		// populate a list of districts 
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();

		// populate a list of schools from districts
		ArrayList<School> schools = new ArrayList<School>();
		for (int i = 0; i<districts.size();i++) {
			schools.addAll(districts.get(i).schools);
		}

		// populate a list of schoolvalues from schools
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		Random randomGenerator = new Random();
		for (int i = 0; i<schools.size();i++) {
			School school = schools.get(i);
			if (isWithinRange(latitude, longitude, school.getLat(), school.getLon(), radius)) {
			schoolValues.add(new SchoolValue(school.getName(),
					school.getValues(),
					school.getLocation(),
					school.getDistrict().getName(),
					school.getPCode(),
					school.getLat(),
					school.getLon(),
					randomGenerator.nextInt(100)));
			}
		}
		return schoolValues;
	}
	
	// helper for getValuesRange: determines whether two co-ordinates are within a given kilometer range
	private Boolean isWithinRange(Double latOne, Double longOne, Double latTwo, Double longTwo, Double kiloRange) {
		double calcDistance;
		calcDistance = (Math.sqrt(Math.pow((latOne - latTwo), 2) + Math.pow((longOne - longTwo), 2)) * 111.3); // c^2 = a^2 + b^2, a = y_1 - y_2, b = x_1 - x_2, 111.3 km per degree
		if (calcDistance < kiloRange) {
			return true;
		} else {
			return false;
		}
	}
	
	// some simple code from http://stackoverflow.com/questions/7467568/parsing-json-from-url
	// helper for setCode, to get the Google geocode API's JSON object
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	@Override
	public ArrayList<SchoolValue> getValues() {
		// populate a list of districts 
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();
		
		// populate a list of schools from districts
		ArrayList<School> schoolList = new ArrayList<School>();
		for (int i = 0; i<districts.size();i++) {
			schoolList.addAll(districts.get(i).schools);
		}
		
		// populate a list of schoolvalues from schools
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		Random randomGenerator = new Random();
		for (int i = 0; i<schoolList.size();i++) {
			School school = schoolList.get(i);
			schoolValues.add(new SchoolValue(school.getName(),
					school.getValues(),
					school.getLocation(),
					school.getDistrict().getName(),
					school.getPCode(),
					school.getLat(),
					school.getLon(),
					randomGenerator.nextInt(100)));
		}
		//TODO: construct schoolvalues from schools
		return schoolValues;
	}
	
	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

}
