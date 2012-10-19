package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PostalCodeServiceAsync {	
	public void getCode(AsyncCallback<String[]> async);
	public void clearCode(AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void addCode(String pCode, AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void getRange(int radius, AsyncCallback<SchoolValue[]> async);
}
