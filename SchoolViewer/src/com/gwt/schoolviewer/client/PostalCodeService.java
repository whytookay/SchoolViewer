package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("postalcode")
public interface PostalCodeService extends RemoteService {
	public String[] getCode();
	public Boolean clearCode(); // previously void, bool as placeholder for callback
	public Boolean addCode(String pCode) throws NotLoggedInException; // previously void, bool as placeholder for callback
	public ArrayList<SchoolValue> getRange(int radius);
}
