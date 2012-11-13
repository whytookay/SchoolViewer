package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CompareService</code>.
 */
public interface CompareServiceAsync {
	  public void addSchoolValue(SchoolValue schoolVal, AsyncCallback<Void> async) throws NotLoggedInException;
	  public void removeSchoolValue(SchoolValue schoolVal, AsyncCallback<Void> async) throws NotLoggedInException;
	  public void getSchoolValues(AsyncCallback<ArrayList<SchoolValue>> async) throws NotLoggedInException;
}
