package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SchoolValueServiceAsync {
	public void getValues(String[] schools, AsyncCallback<ArrayList<SchoolValue>> async); // TODO: remove comment
}