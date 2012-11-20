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
 * The purpose of this class is to provide the backend for the compare table's persistence.
 * Schools can be added to and removed from the server's persistent store.
 */
@SuppressWarnings("serial")
public class CompareServiceImpl extends RemoteServiceServlet implements
		CompareService {
	private static final Logger LOG = Logger.getLogger(CompareServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = 
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	// add a SchoolValue to the persistent store
	@Override
	public void addSchoolValue(SchoolValue schoolVal) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			LOG.log(Level.ALL, "addSchoolValue adding "+ schoolVal.getName());
			System.out.println("addSchoolValue adding "+ schoolVal.getName());
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
			LOG.log(Level.ALL, "addSchoolValue added "+ schoolVal.getName());
			System.out.println("addSchoolValue added "+ schoolVal.getName());
			
		} finally {
			pm.close();
		}
	}

	// remove a SchoolValue from the persistent store
	@Override
	public void removeSchoolValue(SchoolValue schoolVal)
			throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(PersistentSchool.class, "user == u"); // "user == u" ?
			q.declareParameters("com.google.appengine.api.users.User u"); // is this the right declaration?
			List<PersistentSchool> pSchools = (List<PersistentSchool>) q.execute(getUser());
			for (PersistentSchool pSchool : pSchools) {
				if (schoolVal.getName().equals(pSchool.getName())) { // TODO: is checking by location for this OK?
					LOG.log(Level.ALL, "removeSchoolValue deleting "+ schoolVal.getName());
					System.out.println("removeSchoolValue deleting "+ schoolVal.getName());
					deleteCount++;
					pm.deletePersistent(pSchool);
					LOG.log(Level.ALL, "removeSchoolValue deleted "+ schoolVal.getName());
					System.out.println("removeSchoolValue deleted "+ schoolVal.getName());
				}
			}
			if (deleteCount != 1) {
				LOG.log(Level.WARNING, "removeSchoolValue deleted "+deleteCount+" SchoolValues");
			}
		} finally {
			pm.close();
		}
	}

	// retrieve all SchoolValues from the persistent store
	@Override
	public ArrayList<SchoolValue> getSchoolValues() throws NotLoggedInException {
		LOG.log(Level.ALL, "getSchoolValues called");
		System.out.println("getSchoolValues called");
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		ArrayList<SchoolValue> schoolValues = new ArrayList<SchoolValue>();
		try {
			Query q = pm.newQuery(PersistentSchool.class, "user == u"); // "user == u" ?
			q.declareParameters("com.google.appengine.api.users.User u"); // is this the right declaration?
			q.setOrdering("createDate"); // NOTE: THESE WILL BE RETURNED IN ORDER THEY WERE ADDED. THE TABLE SHOULD BE SMALL ENOUGH THAT THIS SHOULD BE FINE.
			List<PersistentSchool> pSchools = (List<PersistentSchool>) q.execute(getUser());
			for (PersistentSchool pSchool : pSchools) {
				LOG.log(Level.ALL, "getSchoolValues getting "+ pSchool.getName());
				System.out.println("getSchoolValues getting "+ pSchool.getName());
				schoolValues.add(pSchool.getEquivSchoolValue());
				LOG.log(Level.ALL, "getSchoolValues got "+ pSchool.getName());
				System.out.println("getSchoolValues got "+ pSchool.getName());
			}
		} finally {
			pm.close();
		}
		return schoolValues;
	}

	// check if the user is logged in
	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("Not logged in.");
		}
	}

	// get the current user
	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	// get a persistencemanager
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
