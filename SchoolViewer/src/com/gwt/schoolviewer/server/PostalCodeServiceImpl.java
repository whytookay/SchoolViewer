package com.gwt.schoolviewer.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.PostalCodeService;
import com.gwt.schoolviewer.client.SchoolValue;

public class PostalCodeServiceImpl extends RemoteServiceServlet implements PostalCodeService {
//TODO: add persistence with javax.jdo
	@Override
	public String[] getCode() {
		// TODO stub
		return new String[] {"V5J 2C4"};
	}

	@Override
	public Boolean clearCode() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean addCode(String pCode) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public SchoolValue[] getRange(int radius) {
		// TODO Auto-generated method stub
		return null; //new SchoolValue[] 
	}

}
