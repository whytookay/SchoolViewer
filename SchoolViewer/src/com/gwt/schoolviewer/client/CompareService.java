package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("compare")
public interface CompareService extends RemoteService {
	  public void addSchoolValue(SchoolValue schoolVal) throws NotLoggedInException;
	  public void removeSchoolValue(SchoolValue schoolVal) throws NotLoggedInException;
	  public ArrayList<SchoolValue> getSchoolValues() throws NotLoggedInException;
}
