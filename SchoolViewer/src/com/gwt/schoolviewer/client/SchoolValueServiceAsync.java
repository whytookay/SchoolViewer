package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SchoolValueService</code>.
 */
public interface SchoolValueServiceAsync {
	public void getCode(AsyncCallback<PostalCodeValue> async);
	public void clearCode(AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void setCode(PostalCodeValue pCode, AsyncCallback<Boolean> async); // previously void, bool as placeholder for callback
	public void getValuesRange(Double radius, AsyncCallback<ArrayList<SchoolValue>> async);
	public void getValues(AsyncCallback<ArrayList<SchoolValue>> async);
	public void findLatLong(String place, AsyncCallback<LatLong> async);
}