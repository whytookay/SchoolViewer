package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("schoolvalue")
public interface SchoolValueService extends RemoteService {
	public SchoolValue[] getValues(String[] schools);
}
