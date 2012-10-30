package com.gwt.schoolviewer.client;

import com.gwt.schoolviewer.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
	private ArrayList<String> schools = new ArrayList<String>();
	private SchoolValueServiceAsync schoolValueSvc = GWT
			.create(SchoolValueService.class);
	private PostalCodeServiceAsync postalCodeSvc = GWT
			.create(PostalCodeService.class);
	final FlexTable schoolFlexTable = new FlexTable();
	MapOptions options  = MapOptions.create() ;
	final SimplePanel mapSimplePanel = new SimplePanel();

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
	    options.setZoom( 6 ) ;
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    mapSimplePanel.setSize("40%","40%");

	    

	    //RootLayoutPanel.get().add( mapSimplePanel ) ;
		RootPanel.get("simplePanelContainer").add(mapSimplePanel);
	    
	    GoogleMap theMap = GoogleMap.create( mapSimplePanel.getElement(), options ) ;
		
		//final TextBox testBox = new TextBox();
		//testBox.setText("this is a test");
		//RootPanel.get("simplePanelContainer").add(testBox);
		
		
		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());

		final Button sendButton = new Button("Search");
		final TextBox nameField = new TextBox();
		nameField.setText("Enter school information:");
		final Button compButton = new Button("Compare");
		

		// Create table for stock data.
		schoolFlexTable.setText(0, 0, "Name");
		schoolFlexTable.setText(0, 1, "Location");
		schoolFlexTable.setText(0, 2, "District");
		schoolFlexTable.setText(0, 3, "Postal Code");
		schoolFlexTable.setText(0,4, "Select");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		compButton.addStyleName("compButton");

		// Add styles to elements in the stock list table.
		schoolFlexTable.setCellPadding(6);
		schoolFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
		schoolFlexTable.addStyleName("schoolList");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("compButtonContainer").add(compButton);
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

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}

	private void refreshSchoolList() {

		// Set up the callback object for Schools
		AsyncCallback<ArrayList<SchoolValue>> callback = new AsyncCallback<ArrayList<SchoolValue>>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
			}

			public void onSuccess(ArrayList<SchoolValue> result) {
				updateTableSchool(result);
			}
		};

		// Make the call to the school price service.
		schoolValueSvc.getValues(callback);
	}

	/**
	 * Update the Name, Location, and District fields all the rows in the school table.
	 * 
	 * @param values
	 *            School data for all rows.
	 */
	private void updateTableSchool(ArrayList<SchoolValue> values) {
		for (int i = 0; i < 100; i++) {
			final CheckBox checkBox = new CheckBox(); // create new checkbox for each row
			updateTableSchoolRow(values.get(i), i, checkBox);
		}
	}

	/**
	 * Update a single row in the school table.
	 * 
	 * @param value
	 *            School data for a single row.
	 * @param index
	 * 			  the row to be updated
	 */
	private void updateTableSchoolRow(SchoolValue value, int index, CheckBox checkBox) {

		int row = index + 1;

		// Populate name, location and district
		schoolFlexTable.setText(row, 0, value.getName());
		schoolFlexTable.setText(row, 1, value.getLocation());
		schoolFlexTable.setText(row, 2, value.getDistrict());
		schoolFlexTable.setText(row, 3, value.getpCode());// TODO: CHECK IF THIS IS FINE
		schoolFlexTable.setWidget(row, 4, checkBox);


		
	}
}
