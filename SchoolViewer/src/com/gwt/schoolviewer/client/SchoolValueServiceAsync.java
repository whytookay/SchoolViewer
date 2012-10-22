package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.schoolviewer.shared.School;

public interface SchoolValueServiceAsync {
	public void getValues(String[] schools, AsyncCallback<School[]> async); // TODO: remove comment
}