package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.SchoolValueService;
import com.gwt.schoolviewer.shared.School;

public class SchoolValueServiceImpl extends RemoteServiceServlet implements SchoolValueService {

	@Override
	public School[] getValues(String[] schools) {
		ArrayList<School> schoolList = BCDistricts.getInstance().getSchools();
		return (School[]) schoolList.toArray();
	}

}
