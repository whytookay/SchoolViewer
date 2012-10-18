package com.gwt.schoolviewer.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class BCDistricts {

	ArrayList<District> districts = new ArrayList<District>();
	
	public BCDistricts() {
		// TODO Auto-generated constructor stub
	}
	
	private void populateDistricts() {
		
	}
	
	private ArrayList<ArrayList<String>> pageToList() throws IOException {
		TxtSplitter splitter = new TxtSplitter("http://www.bced.gov.bc.ca/reporting/odefiles/BoardLocations_Current.txt");
		return splitter.split();
	}

}
