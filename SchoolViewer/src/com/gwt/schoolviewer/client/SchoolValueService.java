package com.gwt.schoolviewer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("schoolvalue")
public interface SchoolValueService extends RemoteService {
	public PostalCodeValue getCode();
	public Boolean clearCode(); // previously void, bool as placeholder for callback
	public Boolean setCode(PostalCodeValue pCode) throws NotLoggedInException; // previously void, bool as placeholder for callback
	public ArrayList<SchoolValue> getValuesRange(Double radius);
	public ArrayList<SchoolValue> getValues();
	public ArrayList<SchoolValue> getValuesFiltered(Boolean searchByPcode,
			String pCode, Double radius, Boolean searchByDistrict,
			String district, Boolean searchByString, String search,
			Boolean searchByClassSize, int minSize, int maxSize);
	public ArrayList<String> getDistrictNames();
	public LatLong findLatLong(String place);

}
