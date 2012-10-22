package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.PostalCodeService;
import com.gwt.schoolviewer.shared.School;

public class PostalCodeServiceImpl extends RemoteServiceServlet implements PostalCodeService {
//TODO: add persistence with javax.jdo
	
	private ArrayList<String> codes = new ArrayList<String>(); //TODO: remove dummy values
	
	@Override
	public String[] getCode() {
		return (String[]) codes.toArray(); //codes;
	}

	@Override
	public Boolean clearCode() {
		codes.clear();
		return true;
	}

	@Override
	public Boolean addCode(String pCode) {
		codes.add(pCode);
		return true;
	}
	
	@Override
	public School[] getRange(int radius) {
		ArrayList<School> schools = BCDistricts.getInstance().getSchools();
		//TODO: need to do the distance calculation
		//schools.get(1).getLocation() is the kind of syntax you'll need
		return (School[]) schools.toArray();
	}

}
