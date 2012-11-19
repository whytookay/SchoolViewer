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
import com.gwt.schoolviewer.client.LatLong;

/**
 * The server side implementation of the RPC service.
 * The purpose of this class is to allow the client to get school data from the server
 * This class also allows some other related internet data to be pulled, such as a list
 * of BC districts, and whether two lat/long pairs are within a given distance.
 */
public class SchoolValueServiceImpl extends RemoteServiceServlet implements SchoolValueService {
	private PostalCode code = new PostalCode(getUser(), "V5J2C4");
	private Double latitude = 49.25;
	private Double longitude = -123.11;
	
	// get stored postalcode (TODO: remove mostly unused functionality)
	@Override
	public PostalCodeValue getCode() {
		return new PostalCodeValue(code.getCode());
	}
	
	// clear stored postalcode (TODO: remove mostly unused functionality)
	@Override
	public Boolean clearCode() {
		code = null;
		return true;
	}
	
	// set stored postalcode (TODO: remove mostly unused functionality)
	@Override
	public Boolean setCode(PostalCodeValue pCode) throws NotLoggedInException {
		code = new PostalCode(getUser(), pCode.getCode());
		
		if (code != null) {
			String placeString = code.getCode();
			LatLong aLatLong = findLatLong(placeString);
			if (aLatLong != null) {
				latitude = aLatLong.getLatitude();
				longitude = aLatLong.getLongitude();
				return true;
			}
		}
			return false;
	}
	
	// given a String representing a location (address or postal code), returns an equivalent LatLong
	@Override
	public LatLong findLatLong(String place) { // place can be a postal code string or an address string
		// some simple code inspired by http://stackoverflow.com/questions/7467568/parsing-json-from-url
		LatLong aLatLong = null;
		try {
			JSONObject geoCodeJSON = new JSONObject(readUrl("http://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&sensor=false"));
			String latit = geoCodeJSON.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
			String longit = geoCodeJSON.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
			aLatLong = new LatLong(Double.parseDouble(latit), Double.parseDouble(longit));
			System.out.println("latitude, longitude are" + latitude + " " + longitude);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aLatLong;
	}
	
	// returns all schoolvalues from server within a given range of this class's set postal code. If postal code is null, returns false.
	// (TODO: remove mostly unused functionality)
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
		for (int i = 0; i<schools.size();i++) {
			School school = schools.get(i);
			if (areWithinRange(latitude, longitude, school.getLat(), school.getLon(), radius)) {
			schoolValues.add(school.getEquivSchoolValue());
			}
		}
		return schoolValues;
	}
	
	// returns all schoolvalues from server
	// (TODO: remove mostly unused functionality)
	@Override
	public ArrayList<SchoolValue> getValues() {
		//Boolean test = stringMatchesTo("Park-ville dr. way st", "Park-Ville Secondary"); //TODO: REMOVE TEST CODE
		//if (test)
		//	System.out.println("string matches");
		
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
			schoolValues.add(school.getEquivSchoolValue());
		}
		return schoolValues;
	}
	
	// returns all schoolvalues that pass the given filters.
	// there are four main groups of filters (represented in the arguments): for each group, if the boolean value is true, then
	// results will be filtered by the passed results. For example, if searchByPcode is true, then results will be narrowed to
	// those within given radius of the given pCode as well as by any other filters activated.
	// note: based on filter setup, even if a bool is false, sensible (albeit useless) values must still be passed in for that group
	@Override
	public ArrayList<SchoolValue> getValuesFiltered(Boolean searchByPcode, String pCode, Double radius, Boolean searchByDistrict, String district, Boolean searchByString, String search, Boolean searchByClassSize, int minSize, int maxSize) {
		// 3 preps here, for defensive coding (not bothering to search for something if it's null) and to fill latitude, longitude
		// pCode prep
		LatLong aLatLong = null;
		Double latitude = 0.0;
		Double longitude = 0.0;
		if (pCode != null) {
			aLatLong = findLatLong(pCode);
			if (aLatLong != null) {
				latitude = aLatLong.getLatitude();
				longitude = aLatLong.getLongitude();
			} else {
				searchByPcode = false;
			}
		} else {
			searchByPcode = false;
		}
		
		// district prep
		if (district == null)
			searchByDistrict = false;
		
		// search prep
		if (search == null)
			searchByString = false;
		
		// populate a list of districts 
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();

		// populate a list of schools from districts
		ArrayList<School> schools = new ArrayList<School>();
		for (int i = 0; i<districts.size();i++) {
			schools.addAll(districts.get(i).schools);
		}
		
		// populate a list of schoolvalues from schools, filtering
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		
		for (int i = 0; i<schools.size();i++) {
			School school = schools.get(i);
			// (!searching || result) && (!searching || result) && (!searching || result)
			// T T => T, T F => F, F T => T, F F => T
			// for a filter component to pass, either we're not searching for something (ignore result)
			// or we are and result is true, thus (!searching || result) && (...) format
			if ((!searchByPcode || areWithinRange(latitude, longitude, school.getLat(), school.getLon(), radius)) && // TODO: EXTRACT TESTING METHOD
				(!searchByDistrict || stringMatchesTo(district, school.getDistrict().name)) &&
				(!searchByString || ( stringMatchesTo(search, school.getName()) ||
										stringMatchesTo(search, school.getLocation()) || 
										stringMatchesTo(search, school.getDistrict().name))) &&
				(!searchByClassSize || ((school.getClassSize() >= minSize) && (school.getClassSize() <= maxSize)))) { 

			schoolValues.add(school.getEquivSchoolValue());
			}
			
			// TODO: ADD SORTING
		}
		
		return schoolValues;
	}
	
	// returns all district names
	public ArrayList<String> getDistrictNames() {
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i<districts.size();i++) {
			result.add(districts.get(i).name);
		}
		return result;
	}
	
	// helper for getValuesRange: determines whether two co-ordinates are within a given kilometer range
	public static Boolean areWithinRange(Double latOne, Double longOne, Double latTwo, Double longTwo, Double kiloRange) {
		double calcDistance;
		calcDistance = (Math.sqrt(Math.pow((latOne - latTwo), 2) + Math.pow((longOne - longTwo), 2)) * 111.3); // c^2 = a^2 + b^2, a = y_1 - y_2, b = x_1 - x_2, 111.3 km per degree
		//System.out.println(calcDistance);
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
	

	// This method takes two strings: searchString and targetString, and figures out if searchString matches loosely to targetString.
	public static Boolean stringMatchesTo(String searchString, String targetString) {
		searchString = searchString.toUpperCase().replaceAll("[.,\\-]", " ").replaceAll("\\b" + "(WAY|DR|ST)" + "\\b", "");  ; // Park-ville dr. way -> PARK VILLE 
		//System.out.println("searchstring is " + searchString + ".");
		String[] searchStrings = searchString.split("\\s+");
		targetString = targetString.toUpperCase().replaceAll("\\W",""); // Park-Ville Secondary -> "PARKVILLESECONDARY"
		//System.out.println("targetString is "  +targetString + ".");
		//System.out.println("searchStrings.length:" + searchStrings.length);
		for (int i = 0; i < searchStrings.length; i++) {
			//System.out.println("searchStrings[i] is \"" + searchStrings[i] + "\"");
			if (targetString.contains(searchStrings[i]) && !searchStrings[i].isEmpty()) {
				//System.out.println("match!"); 
				return true;
			}

		}
		return false;
	}
	
	// returns the current user (TODO: REMOVE UNUSED FUNCTIONALITY)
	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	

}
