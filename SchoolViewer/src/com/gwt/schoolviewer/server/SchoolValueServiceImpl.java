package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.SchoolValue;
import com.gwt.schoolviewer.client.SchoolValueService;

public class SchoolValueServiceImpl extends RemoteServiceServlet implements SchoolValueService {

	@Override
	public ArrayList<SchoolValue> getValues(String[] schools) {
		// populate a list of districts 
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();
		
		// populate a list of schools from districts
		ArrayList<School> schoolList = new ArrayList<School>();
		for (int i = 0; i<districts.size();i++) {
			schoolList.addAll(districts.get(i).schools);
		}
		
		// populate a list of schoolvalues from schools
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		for (int i = 0; i<schoolList.size();i++) {
			School school = schoolList.get(i);
			schoolValues.add(new SchoolValue(school.getName(),
												school.getValues(),
												school.getLocation(),
												school.getDistrict().getName()));
		}
		//TODO: construct schoolvalues from schools
		return schoolValues;
	}

}
