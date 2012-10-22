package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SchoolValueServiceAsync {
	public void getValues(String[] schools, AsyncCallback<SchoolValue[]> async); // TODO: remove comment
}