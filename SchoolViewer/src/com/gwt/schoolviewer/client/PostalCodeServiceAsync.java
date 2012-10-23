package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PostalCodeServiceAsync {	
	public void getCode(AsyncCallback<ArrayList<PostalCodeValue>> async);
	public void clearCode(AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void addCode(PostalCodeValue pCode, AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void getRange(int radius, AsyncCallback<ArrayList<SchoolValue>> async);
}
