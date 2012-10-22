package com.gwt.schoolviewer.server;

import java.util.ArrayList;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.schoolviewer.client.PostalCodeService;
import com.gwt.schoolviewer.client.SchoolValue;
import com.gwt.schoolviewer.client.NotLoggedInException;

public class PostalCodeServiceImpl extends RemoteServiceServlet implements PostalCodeService {
	  private static final PersistenceManagerFactory PMF =
		      JDOHelper.getPersistenceManagerFactory("transactions-optional");// TODO: verify what "transactions-optional" means
	
	private ArrayList<String> codes = new ArrayList<String>();
	
	
	//TODO: add persistence support to getCode, clearCode and addCode
	@Override
	public String[] getCode() {
		return (String[]) codes.toArray(); //codes;
	}

	@Override
	public Boolean clearCode() {
		codes.clear();
		return true;
	}

	@Override
	public Boolean addCode(String pCode) {
		//checkLoggedIn();
	    //PersistenceManager pm = getPersistenceManager();
	    		codes.add(pCode);
	    		//pm.makePersistent(new <some class>(getUser(), pCode));
	    //} finally {
		//      	pm.close();
	    //}
	    return true;
	}
	
	@Override
	public ArrayList<SchoolValue> getRange(int radius) {
		// populate a list of districts 
		ArrayList<District> districts = BCDistricts.getInstance().getDistricts();
		
		// populate a list of schools from districts
		ArrayList<School> schools = new ArrayList<School>();
		for (int i = 0; i<districts.size();i++) {
			schools.addAll(districts.get(i).schools);
		}
		
		//TODO: need to do the distance calculation on schools
		//for that, schools.get(1).getLocation() is the kind of syntax you'll need
		
		// populate a list of schoolvalues from schools
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		for (int i = 0; i<schools.size();i++) {
			School school = schools.get(i);
			schoolValues.add(new SchoolValue(school.getName(),
												school.getValues(),
												school.getLocation(),
												school.getDistrict().getName()));
		}
		//TODO: construct schoolvalues from schools
		return schoolValues;
	}
	
	  private void checkLoggedIn() throws NotLoggedInException {
		    if (getUser() == null) {
		      throw new NotLoggedInException("Not logged in.");
		    }
	  }

	  private User getUser() {
		  	UserService userService = UserServiceFactory.getUserService();
		  	return userService.getCurrentUser();
	  }

	  private PersistenceManager getPersistenceManager() {
		    return PMF.getPersistenceManager();
	  }
		  
}
