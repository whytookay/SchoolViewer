package com.gwt.schoolviewer.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.gwt.schoolviewer.client.CompareService;
import com.gwt.schoolviewer.client.NotLoggedInException;
import com.gwt.schoolviewer.client.SchoolValue;
import com.gwt.schoolviewer.shared.FieldVerifier;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 * This class borrows from StockServiceImpl's persistence code.
 */
@SuppressWarnings("serial")
public class CompareServiceImpl extends RemoteServiceServlet implements
		CompareService {
	private static final Logger LOG = Logger.getLogger(CompareServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = 
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public void addSchoolValue(SchoolValue schoolVal) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new PersistentSchool(getUser(),
													schoolVal.getName(),
													schoolVal.getLocation(),
													schoolVal.getDistrict(),
													schoolVal.getDistrictWebsite(),
													schoolVal.getpCode(),
													schoolVal.getLatitude(),
													schoolVal.getLongitude(),
													schoolVal.getClassSize(),
													schoolVal.getPhone(),
													schoolVal.getPubOrInd(),
													schoolVal.getEduLevel()));
		} finally {
			pm.close();
		}
	}

	@Override
	public void removeSchoolValue(SchoolValue schoolVal) // TODO: IMPLEMENT
			throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(PersistentSchool.class, "user == u"); // "user == u" ?
			q.declareParameters("com.google.appengine.api.users.User u"); // is this the right declaration?
			List<PersistentSchool> pSchools = (List<PersistentSchool>) q.execute(getUser());
			for (PersistentSchool pSchool : pSchools) {
				if (schoolVal.getLocation().equals(pSchool.getLocation())) { // TODO: is checking by location for this OK?
					deleteCount++;
					pm.deletePersistent(pSchool);
				}
			}
			if (deleteCount != 1) {
				LOG.log(Level.WARNING, "removeStock deleted "+deleteCount+" Stocks");
			}
		} finally {
			pm.close();
		}
	}

	@Override
	public ArrayList<SchoolValue> getSchoolValues() throws NotLoggedInException { // TODO: IMPLEMENT
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		try {
			Query q = pm.newQuery(PersistentSchool.class, "user == u"); // "user == u" ?
			q.declareParameters("com.google.appengine.api.users.User u"); // is this the right declaration?
			q.setOrdering("createDate"); // NOTE: THESE WILL BE RETURNED IN ORDER THEY WERE ADDED. THE TABLE SHOULD BE SMALL ENOUGH THAT THIS SHOULD BE FINE.
			List<PersistentSchool> pSchools = (List<PersistentSchool>) q.execute(getUser());
			for (PersistentSchool pSchool : pSchools) {
				schoolValues.add(pSchool.getEquivSchoolValue());
			}
		} finally {
			pm.close();
		}
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
