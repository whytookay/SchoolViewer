package com.gwt.schoolviewer.client;

import com.gwt.schoolviewer.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.gwt.schoolviewer.client.SchoolValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SchoolViewer implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private LoginInfo loginInfo = null;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private SchoolValueServiceAsync schoolValueSvc = GWT
			.create(SchoolValueService.class);
	final FlexTable compFlexTable = new FlexTable();
	final FlexTable schoolFlexTable = new FlexTable();
	private MapOptions options  = MapOptions.create() ;
	private ArrayList<SchoolValue> ListOfSchools;
	private GoogleMap theMap;
	private ArrayList<Marker> Markers = new ArrayList<Marker>();
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							loadschoolviewer();
						} else {
							loadLogin();
						}
					}
				});

	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get(null).add(loginPanel);

	}

	private void loadschoolviewer() {
		// load map 
	    options.setCenter(LatLng.create( 49.242931,-123.184547));   
	    options.setZoom( 12 ) ;
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;
	    
	    SimplePanel mapPanel = new SimplePanel() ;
	    mapPanel.setSize("500px","500px");
	    mapPanel.setVisible(true);

	    theMap = GoogleMap.create( mapPanel.getElement(), options ) ;
	   
	    //sample of how to add marker-------------
	    MarkerOptions markerOptions = MarkerOptions.create(); 
	    markerOptions.setMap(theMap); 
	    markerOptions.setTitle("this is you"); 
	    markerOptions.setDraggable(false); 
	    markerOptions.setPosition(LatLng.create( 49.242931,-123.184547));
	    Marker start = Marker.create(markerOptions);
	    
	    Markers.add(start);
	    //----------------------------------------
	    
		
		
		
		
		
	        // Set up sign out hyperlink.
	 		signOutLink.setHref(loginInfo.getLogoutUrl());

	 		final Button searchButton = new Button("Search");
	 		final TextBox nameField = new TextBox();
	 		nameField.setText("Enter school information:");
	 		final Button compButton = new Button("Compare");
	 		final Button clearButton = new Button("Clear Checked");
	 		final CheckBox checkAllComp = new CheckBox("Check All");

	 		// Create table for comparing school data.
	 		compFlexTable.setText(0, 0, "Name");
	 		compFlexTable.setText(0, 1, "Location");
	 		compFlexTable.setText(0, 2, "District");
	 		compFlexTable.setText(0, 3, "Postal Code");
	 		compFlexTable.setWidget(0, 4, checkAllComp);

	 		// Create table for stock data.
	 		schoolFlexTable.setText(0, 0, "Name");
	 		schoolFlexTable.setText(0, 1, "Location");
	 		schoolFlexTable.setText(0, 2, "District");
	 		schoolFlexTable.setText(0, 3, "Postal Code");
	 		schoolFlexTable.setText(0, 4, "Select");

	 		// Add styles to elements in the compare table
	 		compFlexTable.setCellPadding(6);
	 		compFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
	 		compFlexTable.addStyleName("schoolList");

	 		// Add styles to elements in the school list table.
	 		schoolFlexTable.setCellPadding(6);
	 		schoolFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
	 		schoolFlexTable.addStyleName("schoolList");

	 		// Add the nameField and sendButton to the RootPanel
	 		// Use RootPanel.get() to get the entire body element
	 		RootPanel.get("mapPanelContainer").add(mapPanel);
	 		RootPanel.get("nameFieldContainer").add(nameField);
	 		RootPanel.get("sendButtonContainer").add(searchButton);
	 		RootPanel.get("compButtonContainer").add(compButton);
	 		RootPanel.get("clearButtonContainer").add(clearButton);
	 		RootPanel.get("compTableContainer").add(compFlexTable);
	 		RootPanel.get("flexTableContainer").add(schoolFlexTable);
	 		// Focus the cursor on the name field when the app loads
	 		nameField.setFocus(true);
	 		nameField.selectAll();

	 		// Create a handler for the sendButton and nameField
	 		class MyHandler implements ClickHandler, KeyUpHandler {
	 			/**
	 			 * Fired when the user clicks on the sendButton.
	 			 */
	 			public void onClick(ClickEvent event) {
	 				refreshSchoolList();
	 			}

	 			/**
	 			 * Fired when the user types in the nameField.
	 			 */
	 			public void onKeyUp(KeyUpEvent event) {
	 				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
	 					refreshSchoolList();
	 				}
	 			}
	 		}

	 		// Create a handler for the compare button
	 		class CompHandler implements ClickHandler {
	 			/**
	 			 * Fired when the user clicks on the compButton.
	 			 */
	 			public void onClick(ClickEvent event) {
	 				addToCompList();
	 			}
	 		}

	 		// Create a handler for the clear checked button
	 		class ClearHandler implements ClickHandler {
	 			/**
	 			 * Fired when the user clicks on the clearChecked Button.
	 			 */
	 			public void onClick(ClickEvent event) {
	 				removeFromCompList();
	 			}
	 		}

	 		// Create a handler for the select all box {
	 		class CheckHandler implements ClickHandler {
	 			public void onClick(ClickEvent event) {
	 				checkAllBoxes();
	 			}

	 		}

	 		/*
	 		 * // Create a hndler for the checkAll boxes class CheckAllHandler
	 		 * implements
	 		 */

	 		// Add a handler to send the name to the server
	 		MyHandler handler = new MyHandler();
	 		searchButton.addClickHandler(handler);
	 		nameField.addKeyUpHandler(handler);
	 		CompHandler comphandler = new CompHandler();
	 		compButton.addClickHandler(comphandler);
	 		ClearHandler clearhandler = new ClearHandler();
	 		clearButton.addClickHandler(clearhandler);
	 		CheckHandler checkhandler = new CheckHandler();
	 		checkAllComp.addClickHandler(checkhandler);

	 	}

	 	private void refreshSchoolList() {

	 		// Set up the callback object for Schools
	 		AsyncCallback<ArrayList<SchoolValue>> callback = new AsyncCallback<ArrayList<SchoolValue>>() {
	 			public void onFailure(Throwable caught) {
	 				// TODO: Do something with errors.
	 			}

	 			public void onSuccess(ArrayList<SchoolValue> result) {
	 				ListOfSchools = result;
	 				updateTableSchool(result);
	 			}
	 		};

	 		// Make the call to the school price service.
	 		schoolValueSvc.getValues(callback);
	 	}

	 	/**
	 	 * Update the Name, Location, and District fields all the rows in the school
	 	 * table.
	 	 * 
	 	 * @param values
	 	 *            School data for all rows.
	 	 */
	 	private void updateTableSchool(ArrayList<SchoolValue> values) {
	 		for (int i = 0; i < 25; i++) {
	 			final CheckBox checkBox = new CheckBox(); // create new checkbox per
	 														// row
	 			updateTableSchoolRow(values.get(i), i, checkBox);
	 		}
	 	}

	 	/**
	 	 * Update a single row in the school table.
	 	 * 
	 	 * @param value
	 	 *            School data for a single row.
	 	 * @param index
	 	 *            the row to be updated
	 	 */
	 	private void updateTableSchoolRow(SchoolValue value, int index,
	 			CheckBox checkBox) {

	 		int row = index + 1;

	 		// Populate name, location and district
	 		schoolFlexTable.setText(row, 0, value.getName());
	 		schoolFlexTable.setText(row, 1, value.getLocation());
	 		schoolFlexTable.setText(row, 2, value.getDistrict());
	 		schoolFlexTable.setText(row, 3, value.getpCode());// TODO: CHECK IF THIS
	 															// IS FINE
	 		schoolFlexTable.setWidget(row, 4, checkBox);

	 	}

	 	/**
	 	 * Add all checked rows to the compare table table
	 	 */
	 	private void addToCompList() {
	 		int numCompRows = 1;
	 		int rowCount = schoolFlexTable.getRowCount();
	 		
	 		// go through all rows in school table
	 		for (int i = 1; i < rowCount; i++) {
	 			if (((CheckBox) schoolFlexTable.getWidget(i, 4)).getValue()) {
	 				// check if entry is already in comparison table
	 				if (notInCompTable(schoolFlexTable.getText(i, 0))) {
	 					// make an ArrayList to hold row data
	 					ArrayList<String> schooldata = new ArrayList<String>();
	 					for (int j = 0; j < 4; j++) {
	 						// add current row data to ArrayList
	 						schooldata.add(schoolFlexTable.getText(i, j));
	 					}
	 					// insert row in comptable and return index of row to insert
	 					int newRow = compFlexTable.insertRow(numCompRows);
	 					// increment next index position for insert
	 					numCompRows++;
	 					for (int k = 0; k < 4; k++) {
	 						// add data to comp table
	 						compFlexTable.setText(newRow, k, schooldata.get(k));
	 						if (k == 0){
	 							AddMarker(schooldata.get(k));
	 						}
	 							
	 					}
	 					// Add a checkbox for removal
	 					CheckBox removeBox = new CheckBox();
	 					compFlexTable.setWidget(newRow, 4, removeBox);
	 				}
	 			}
	 		}
	 	}

	 	private boolean notInCompTable(String school) {
	 		for (int i = 1; i < compFlexTable.getRowCount(); i++) {
	 			if (school.equals(compFlexTable.getText(i, 0)))
	 				return false;
	 		}
	 		return true;
	 	}

	 	/**
	 	 * Remove all checked rows from compare table
	 	 * 
	 	 */
	 	private void removeFromCompList() {
	 		int rowCount = compFlexTable.getRowCount();
	 		// go backward to avoid messed up row indices
	 		for (int i = rowCount - 1; i > 0; i--) {
	 			if (((CheckBox) compFlexTable.getWidget(i, 4)).getValue()) {
                    RemoveMarker(compFlexTable.getText(i, 0));
	 				compFlexTable.removeRow(i);
	 			}
	 		}
	 		CheckBox checkedBox = (CheckBox) compFlexTable.getWidget(0, 4);
	 		checkedBox.setValue(false);
			
	 		
	 	}

	 	private void checkAllBoxes() {
	 		int rowCount = compFlexTable.getRowCount();
	 		if (((CheckBox) compFlexTable.getWidget(0, 4)).getValue()) {
	 			for (int j = 1; j < rowCount; j++) {
	 				CheckBox checkedBox = new CheckBox();
	 				checkedBox.setValue(true);
	 				compFlexTable.setWidget(j, 4, checkedBox);
	 			}
	 		} else {
	 			for (int i = 1; i < rowCount; i++) {
	 				compFlexTable.setWidget(i, 4, new CheckBox());
	 			}
	 		}
	 	}
	 	private void AddMarker(String name){
	 		for(SchoolValue s : ListOfSchools){
	 			if(name.equals(s.getName())){
	 			    MarkerOptions markerOptions = MarkerOptions.create(); 
	 			    markerOptions.setMap(theMap); 
	 			    markerOptions.setTitle(s.getName()); 
	 			    markerOptions.setDraggable(false); 
	 			    markerOptions.setPosition(LatLng.create( s.getLatitude(),s.getLongitude()));
	 			    Marker marker = Marker.create(markerOptions);
	 			    Markers.add(marker);
	 			    
	 			}
	 				
	 		}
	 		
	 	}
	 	private void RemoveMarker(String name){
	 		for(int k = 0; k < Markers.size(); k++){
	 			if(name.equals(Markers.get(k).getTitle())){
	 				//Markers.get(k).setVisible(false);
	 				Markers.get(k).setMap((GoogleMap) null);
	 				Markers.remove(k);
	 			}
	 		}
	 	}
	 }
