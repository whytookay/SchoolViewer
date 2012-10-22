package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.schoolviewer.shared.School;

@RemoteServiceRelativePath("schoolvalue")
public interface SchoolValueService extends RemoteService {
	public School[] getValues(String[] schools);
}
