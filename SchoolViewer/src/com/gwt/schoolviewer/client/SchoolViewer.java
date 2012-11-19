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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

import java.util.ArrayList;

import com.google.api.gwt.client.GoogleApiRequestTransport;
import com.google.api.gwt.client.OAuth2Login;
import com.google.api.gwt.services.plus.shared.Plus;
import com.google.api.gwt.services.plus.shared.Plus.ActivitiesContext.ListRequest.Collection;
import com.google.api.gwt.services.plus.shared.Plus.PlusAuthScope;
import com.google.api.gwt.services.plus.shared.model.Activity;
import com.google.api.gwt.services.plus.shared.model.ActivityFeed;
import com.google.api.gwt.services.plus.shared.model.Person;
import com.google.gwt.core.client.Callback;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.SimpleEventBus;

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

	// data stuff
	/**
	 * Create a remote service proxy to talk to the server-side compare service
	 */
	private final CompareServiceAsync compareService = GWT
			.create(CompareService.class);

	/**
	 * Create a remote service proxy to talk to the server-side SchoolValue
	 * service
	 */
	private final SchoolValueServiceAsync schoolValueSvc = GWT
			.create(SchoolValueService.class);

	// data arrays
	private ArrayList<SchoolValue> ListOfSchools;
	private ArrayList<SchoolValue> ListOfCompSchools = new ArrayList<SchoolValue>();
	// private ArrayList<String> ListOfDistricts = new ArrayList<String>();

	// login stuff
	private final HorizontalPanel loginPanel = new HorizontalPanel();
	private final Anchor signInLink = new Anchor("");
	private final Image loginImage = new Image();
	private final TextBox nameField = new TextBox();

	// main layout stuff
	private final FlexTable compFlexTable = new FlexTable();
	private final FlexTable schoolFlexTable = new FlexTable();
	private final TextBox postalField = new TextBox();
	private final TextBox radiusField = new TextBox();
	private final TextBox queryField = new TextBox();
	private final ListBox districtDropBox = new ListBox();
	private final TextBox minSize = new TextBox();
	private final TextBox maxSize = new TextBox();

	// map stuff
	private MapOptions options = MapOptions.create();
	private GoogleMap theMap;
	private ArrayList<Marker> Markers = new ArrayList<Marker>();
	private InfoWindow IW = InfoWindow.create();

	// google +
	private static final Auth AUTH = Auth.get();
	private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	private static final String GOOGLE_CLIENT_ID = "700088417733.apps.googleusercontent.com";
	private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private static final Plus plus = GWT.create(Plus.class);
	private static final String CLIENT_ID = "700088417733.apps.googleusercontent.com";
	private static final String API_KEY = "AIzaSyDGS6xMhkUEiR7FVCp5lslzcWOKGXWxooc";
	private static final String APPLICATION_NAME = "projecttesting40702";
	private Person Self;

	private void loadLogin(final LoginInfo loginInfo) {
		AUTH.clearAllTokens();
		signInLink.setHref(loginInfo.getLoginUrl());
		signInLink.setText("Please, sign in with your Google Account");
		signInLink.setTitle("Sign in");
	}

	private void loadLogout(final LoginInfo loginInfo) {
		signInLink.setHref(loginInfo.getLogoutUrl());
		signInLink.setText(loginInfo.getName());
		signInLink.setTitle("Sign out");
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		signInLink.getElement().setClassName("login-area");
		signInLink.setTitle("sign out");
		loginImage.getElement().setClassName("login-area");
		loginPanel.add(signInLink);
		RootPanel.get("loginPanelContainer").add(loginPanel);
		final StringBuilder userEmail = new StringBuilder();
		greetingService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					@Override
					public void onFailure(final Throwable caught) {
						GWT.log("login -> onFailure");
						println(caught.toString());
					}

					@Override
					public void onSuccess(final LoginInfo result) {
						if (result.getName() != null
								&& !result.getName().isEmpty()) {
							addGoogleAuthHelper();
							loadLogout(result);
						} else {
							loadLogin(result);
						}
						userEmail.append(result.getEmailAddress());
					}
				});
		try {
			loadschoolviewer();
		} catch (NotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addGoogleAuthHelper() {
		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL,
				GOOGLE_CLIENT_ID).withScopes(PLUS_ME_SCOPE);
		AUTH.login(req, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(final String token) {
				if (!token.isEmpty()) {
					greetingService.loginDetails(token,
							new AsyncCallback<LoginInfo>() {
								@Override
								public void onFailure(final Throwable caught) {
									GWT.log("loginDetails -> onFailure");
								}

								@Override
								public void onSuccess(final LoginInfo loginInfo) {
									signInLink.setText(loginInfo.getName());
									nameField.setText(loginInfo.getName());
									signInLink.setStyleName("login-area");
									loginImage.setUrl(loginInfo.getPictureUrl());
									loginImage.setVisible(false);
									loginPanel.add(loginImage);
									loginImage
											.addLoadHandler(new LoadHandler() {
												@Override
												public void onLoad(
														final LoadEvent event) {
													final int newWidth = 24;
													final com.google.gwt.dom.client.Element element = event
															.getRelativeElement();
													if (element.equals(loginImage
															.getElement())) {
														final int originalHeight = loginImage
																.getOffsetHeight();
														final int originalWidth = loginImage
																.getOffsetWidth();
														if (originalHeight > originalWidth) {
															loginImage
																	.setHeight(newWidth
																			+ "px");
														} else {
															loginImage
																	.setWidth(newWidth
																			+ "px");
														}
														loginImage
																.setVisible(true);
														InitSelf();
													}
												}
											});
								}
							});
				}
			}

			@Override
			public void onFailure(final Throwable caught) {
				GWT.log("Error -> loginDetails\n" + caught.getMessage());
			}
		});
	}

	private void InitSelf() {
		plus.initialize(new SimpleEventBus(), new GoogleApiRequestTransport(
				APPLICATION_NAME, API_KEY));
		OAuth2Login.get().authorize(GOOGLE_CLIENT_ID, PlusAuthScope.PLUS_ME,
				new Callback<Void, Exception>() {
					@Override
					public void onSuccess(Void v) {
						plus.people().get("me").to(new Receiver<Person>() {
							@Override
							public void onSuccess(Person person) {
								Self = person;
							}
						}).fire();
					}

					@Override
					public void onFailure(Exception e) {
						println(e.getMessage());
					}
				});
	}

	private void SetSelfLocationMarker() {
		MarkerOptions markerOptions = MarkerOptions.create();
		markerOptions.setMap(theMap);
		markerOptions.setTitle("this is you");
		markerOptions.setDraggable(false);
		markerOptions
				.setIcon(MarkerImage
						.create("http://google-maps-icons.googlecode.com/files/walking-tour.png"));
		final Marker start = Marker.create(markerOptions);
		AsyncCallback<LatLong> callback = new AsyncCallback<LatLong>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				println("failed to get self Lat Long");
			}

			public void onSuccess(LatLong result) {
				start.setPosition(LatLng.create(result.getLatitude(),
						result.getLongitude()));
			}
		};
		String place = Self.getPlacesLived().get(0).getValue();
		String placeURL = "";
		for (int k = 0; k < place.length() - 1; k++) {
			if (place.substring(k, k + 1).equals(" ")) {
				placeURL = placeURL + "+";
			} else
				placeURL = placeURL + place.substring(k, k + 1);

		}
		println(placeURL);
		schoolValueSvc.findLatLong(placeURL, callback);
		Markers.add(start);
	}

	private void println(String txt) {
		Window.alert(txt);

	}

	private void loadschoolviewer() throws NotLoggedInException {
		// load map
		options.setCenter(LatLng.create(54.826008, -124.848633));
		options.setZoom(5);
		options.setMapTypeId(MapTypeId.ROADMAP);
		options.setDraggable(true);
		options.setMapTypeControl(true);
		options.setScaleControl(true);
		options.setScrollwheel(true);

		SimplePanel mapPanel = new SimplePanel();
		mapPanel.setSize("600px", "600px");
		mapPanel.setVisible(true);

		theMap = GoogleMap.create(mapPanel.getElement(), options);

		// panels for holding widgets
		@SuppressWarnings("deprecation")
		// may rewrite later if I have time
		final VerticalSplitPanel tablePanel = new VerticalSplitPanel(); // TODO:
																		// yo
																		// this
																		// is
																		// deprecated
		final HorizontalPanel layoutPanel = new HorizontalPanel();
		final AbsolutePanel filterPanel = new AbsolutePanel();

		// compare and school table widgets
		final Button refreshButton = new Button("Search");
		final Button compButton = new Button("Compare");
		final Button clearButton = new Button("Clear Checked");
		final CheckBox checkAllComp = new CheckBox("Check All");
		final Button showOnMapButton = new Button("Show on Map");

		// filterPanel widgets
		final Label filterLabel = new Label("Filters");
		final Button postalSearchButton = new Button("Apply Filters");

		// Create table for comparing school data.
		compFlexTable.setText(0, 0, "Name");
		compFlexTable.setText(0, 1, "Location");
		compFlexTable.setText(0, 2, "District");
		compFlexTable.setText(0, 3, "Postal Code");
		compFlexTable.setText(0, 4, "Class Size");
		compFlexTable.setWidget(0, 5, checkAllComp);

		// Create table for stock data.
		schoolFlexTable.setText(0, 0, "Name");
		schoolFlexTable.setText(0, 1, "Location");
		schoolFlexTable.setText(0, 2, "District");
		schoolFlexTable.setText(0, 3, "Postal Code");
		schoolFlexTable.setText(0, 4, "Class Size");
		schoolFlexTable.setText(0, 5, "Select");

		// Add styles to elements in the compare table
		compFlexTable.setCellPadding(6);
		compFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
		compFlexTable.addStyleName("schoolList");

		// Add styles to elements in the school list table.
		schoolFlexTable.setCellPadding(6);
		schoolFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
		schoolFlexTable.addStyleName("schoolList");

		// Use RootPanel.get() to get the entire body element
		filterPanel.setSize("640px", "120px");
		filterPanel.add(filterLabel);
		filterPanel.add(postalField, 50, 15);
		postalField.setText("Enter Postal Code:");
		filterPanel.add(radiusField, 50, 50);
		radiusField.setText("0");
		filterPanel.add(postalSearchButton, 50, 85);
		filterPanel.add(districtDropBox, 250, 50);
		minSize.setVisibleLength(2);
		filterPanel.add(minSize, 500, 50);
		maxSize.setVisibleLength(2);
		filterPanel.add(maxSize, 560, 50);
		filterPanel.addStyleName("filterTable");
		RootPanel.get("filterContainer").add(filterPanel);

		// Setup search and compare buttons and search field
		RootPanel.get("nameFieldContainer").add(refreshButton);
		RootPanel.get("nameFieldContainer").add(queryField);
		RootPanel.get("buttonContainer").add(compButton);
		RootPanel.get("buttonContainer").add(clearButton);
		RootPanel.get("buttonContainer").add(showOnMapButton);

		// Setup Compare and Map panel layout in RootPanel
		tablePanel.setSize("640px", "600px");
		tablePanel.add(compFlexTable);
		tablePanel.add(schoolFlexTable);
		tablePanel.addStyleName("schoolTable");
		layoutPanel.insert(tablePanel, 0);
		layoutPanel.insert(mapPanel, 1);
		RootPanel.get("tableMapContainer").add(layoutPanel);
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Set up the callback object for Schools
		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				println("Search Failed");
			}

			public void onSuccess(ArrayList<String> result) {
				districtDropBox.addItem("None");

				for (int i = 0; i < result.size(); i++) {
					districtDropBox.addItem(result.get(i));
				}
			}
		};

		// Make the call to the school value service.
		schoolValueSvc.getDistrictNames(callback);

		/**
		 * Get Persistent school values commented out until add/remove are
		 * working
		 * 
		 * AsyncCallback<ArrayList<SchoolValue>> comparecallback = new
		 * AsyncCallback<ArrayList<SchoolValue>>() { public void
		 * onFailure(Throwable caught) { // TODO: Do something with errors.
		 * System.out.println("Persistent school get failed"); }
		 * 
		 * public void onSuccess(ArrayList<SchoolValue> result) {
		 * System.out.println("Persistent school get success");
		 * ListOfCompSchools = result; for (int i = 0; i < result.size(); i++) {
		 * final CheckBox checkBox = new CheckBox(); // create new // checkbox
		 * per // row compFlexTable.setText(i + 1, 0, result.get(i).getName());
		 * compFlexTable .setText(i + 1, 1, result.get(i).getLocation());
		 * compFlexTable .setText(i + 1, 2, result.get(i).getDistrict());
		 * compFlexTable.setText(i + 1, 3, result.get(i).getpCode());
		 * compFlexTable.setWidget(i + 1, 5, checkBox); } } };
		 * compareService.getSchoolValues(comparecallback);
		 */

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				filterSchools();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					filterSchools();
				}
			}
		}

		// Create a handler for the compare button
		class CompHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the compButton.
			 */
			public void onClick(ClickEvent event) {
				try {
					addToCompList();
				} catch (NotLoggedInException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Create a handler for the clear checked button
		class ClearHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the clearChecked Button.
			 */
			public void onClick(ClickEvent event) {
				try {
					removeFromCompList();
				} catch (NotLoggedInException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Create a handler for the select all box {
		class CheckHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				checkAllBoxes();
			}

		}

		class PCodeHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				searchByRadius();
			}
		}

		class ShowMapHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				// TODO
				if (Self != null)
					SetSelfLocationMarker();
				else
					println("please login");
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		refreshButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		CompHandler comphandler = new CompHandler();
		compButton.addClickHandler(comphandler);
		ClearHandler clearhandler = new ClearHandler();
		clearButton.addClickHandler(clearhandler);
		CheckHandler checkhandler = new CheckHandler();
		checkAllComp.addClickHandler(checkhandler);
		PCodeHandler phandler = new PCodeHandler();
		postalSearchButton.addClickHandler(phandler);
		ShowMapHandler showhandler = new ShowMapHandler();
		showOnMapButton.addClickHandler(showhandler);

	}

	private void searchByRadius() {
		AsyncCallback<Boolean> setCodeBool = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				println("Failed to setPcode");
			}

			public void onSuccess(Boolean result) {
				if (result) {
					// System.out.println("Successfully set PCode");
					returnInRadius();
				}
			}
		};
		schoolValueSvc.setCode(new PostalCodeValue(postalField.getText()),
				setCodeBool);
	}

	private void returnInRadius() {
		AsyncCallback<ArrayList<SchoolValue>> callback = new AsyncCallback<ArrayList<SchoolValue>>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				println("Postal Search Failed");
			}

			public void onSuccess(ArrayList<SchoolValue> result) {
				ListOfSchools = result;
				populateSchoolTable(result);
			}
		};
		String radius = radiusField.getText();
		schoolValueSvc.getValuesRange(Double.parseDouble(radius), callback);

	}

	private void filterSchools() {

		// Set up the callback object for Schools
		AsyncCallback<ArrayList<SchoolValue>> callback = new AsyncCallback<ArrayList<SchoolValue>>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				println("Search Failed");
			}

			public void onSuccess(ArrayList<SchoolValue> result) {
				// System.out.println(queryField.getText());
				ListOfSchools = result;
				populateSchoolTable(result);
			}
		};

		// Make the call to the school value service.
		String pCode = postalField.getText();
		Boolean isPostal = !pCode.equals("");
		String search = queryField.getText();
		Boolean isSearch = !search.equals("");
		String district = districtDropBox.getItemText(districtDropBox
				.getSelectedIndex()); // or district value
		Boolean isDistrict = !district.equals("None");
		Double radius = Double.parseDouble(radiusField.getText());
		
		String minStr = minSize.getText();
		String maxStr = maxSize.getText();
		Boolean minOrMaxValid = !(minStr.equals("") && maxStr.equals(""));
		int min = Integer.parseInt(minStr);
		int max = Integer.parseInt(maxStr);
		
		schoolValueSvc.getValuesFiltered(isPostal, pCode, radius, isDistrict,
				district, isSearch, search, minOrMaxValid, min, max, callback);

	}

	/**
	 * Update the Name, Location, and District fields all the rows in the school
	 * table.
	 * 
	 * @param values
	 *            School data for all rows.
	 */
	private void populateSchoolTable(ArrayList<SchoolValue> values) {
		// clear rows before adding current list of compared schools
		for (int i = schoolFlexTable.getRowCount() - 1; i > 0; i--)
			schoolFlexTable.removeRow(i);
		for (int i = 0; i < values.size(); i++) {
			final CheckBox checkBox = new CheckBox(); // create new checkbox per
														// row
			schoolFlexTable.setText(i + 1, 0, values.get(i).getName());
			schoolFlexTable.setText(i + 1, 1, values.get(i).getLocation());
			schoolFlexTable.setText(i + 1, 2, values.get(i).getDistrict());
			schoolFlexTable.setText(i + 1, 3, values.get(i).getpCode());
			Double classSize = values.get(i).getClassSize();
			if (classSize == -1.0)
				schoolFlexTable.setText(i + 1, 4, "N/A");
			else
				schoolFlexTable.setText(i + 1, 4,
						Integer.toString(classSize.intValue()));
			schoolFlexTable.setWidget(i + 1, 5, checkBox);

		}
	}

	/**
	 * Add all checked rows to the compare table table
	 * 
	 * @throws NotLoggedInException
	 */
	private void addToCompList() throws NotLoggedInException {
		// int numCompRows = 1;

		for (int i = 1; i < schoolFlexTable.getRowCount(); i++) {
			if (((CheckBox) schoolFlexTable.getWidget(i, 5)).getValue()) {
				SchoolValue currentSchool = ListOfSchools.get(i - 1);
				// check if entry is already in comparison table
				if (notInCompTable(currentSchool)) {
					// add to comp table array
					ListOfCompSchools.add(currentSchool);
					AddMarker(currentSchool.getName());

					// add a schoolValue to compareServiceAsync
					AsyncCallback<Void> callback = new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// TODO: Do something with errors.
							println("Add Failed");
						}

						public void onSuccess(Void result) {
							// System.out.println("Add Success");
						}

					};
					compareService.addSchoolValue(currentSchool, callback);
				}
			}
		}
		// clear rows before adding current list of compared schools
		for (int i = compFlexTable.getRowCount() - 1; i > 0; i--)
			compFlexTable.removeRow(i);
		for (int i = 0; i < ListOfCompSchools.size(); i++) {

			// insert row in comptable and return index of row to insert
			int newRow = compFlexTable.insertRow(i + 1);

			String name = ListOfCompSchools.get(i).getName();
			String location = ListOfCompSchools.get(i).getLocation();
			String district = ListOfCompSchools.get(i).getDistrict();
			String pcode = ListOfCompSchools.get(i).getpCode();
			Double classSize = ListOfCompSchools.get(i).getClassSize();
			CheckBox removeBox = new CheckBox();

			compFlexTable.setText(newRow, 0, name);
			compFlexTable.setText(newRow, 1, location);
			compFlexTable.setText(newRow, 2, district);
			compFlexTable.setText(newRow, 3, pcode);
			if (classSize == -1.0)
				compFlexTable.setText(newRow, 4, "N/A");
			else
				compFlexTable.setText(newRow, 4,
						Integer.toString(classSize.intValue()));
			compFlexTable.setWidget(newRow, 5, removeBox);
		}

	}

	private boolean notInCompTable(SchoolValue school) {
		if (ListOfCompSchools.size() == 0)
			return true;
		for (int i = 0; i < ListOfCompSchools.size(); i++) {
			if (school.equals(ListOfCompSchools.get(i)))
				return false;
		}
		return true;
	}

	/**
	 * Remove all checked rows from compare table
	 * 
	 * @throws NotLoggedInException
	 * 
	 */
	private void removeFromCompList() throws NotLoggedInException {
		// go backward to avoid messed up row indices
		for (int i = compFlexTable.getRowCount() - 1; i > 0; i--) {
			if (((CheckBox) compFlexTable.getWidget(i, 5)).getValue()) {

				// add a schoolValue to compareServiceAsync
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO: Do something with errors.
						System.out.println("Remove Failed");
					}

					public void onSuccess(Void result) {
						System.out.println("Remove Success");
					}

				};
				compareService.removeSchoolValue(ListOfCompSchools.get(i - 1),
						callback);

				ListOfCompSchools.remove(i - 1);
				RemoveMarker(compFlexTable.getText(i, 0));
				compFlexTable.removeRow(i);
			}
		}
		CheckBox checkedBox = (CheckBox) compFlexTable.getWidget(0, 5);
		checkedBox.setValue(false);

	}

	private void checkAllBoxes() {
		int rowCount = compFlexTable.getRowCount();
		if (((CheckBox) compFlexTable.getWidget(0, 5)).getValue()) {
			for (int j = 1; j < rowCount; j++) {
				CheckBox checkedBox = new CheckBox();
				checkedBox.setValue(true);
				compFlexTable.setWidget(j, 5, checkedBox);
			}
		} else {
			for (int i = 1; i < rowCount; i++) {
				compFlexTable.setWidget(i, 5, new CheckBox());
			}
		}
	}

	private void AddMarker(String name) {
		for (SchoolValue s : ListOfSchools) {
			if (name.equals(s.getName())) {
				MarkerOptions markerOptions = MarkerOptions.create();
				markerOptions.setMap(theMap);
				markerOptions.setTitle(s.getName());
				markerOptions.setDraggable(false);
				markerOptions.setPosition(LatLng.create(s.getLatitude(),
						s.getLongitude()));
				Marker marker = Marker.create(markerOptions);
				final String txt = "<div id=\"content\">"
						+ "<p style=\"text-align:left; color:BLACK; font-size: 16pt \"><b>"
						+ s.getName() + "</b></p>"
						+ "<p style=\"text-align:left;color: BLACK;\">"
						+ "District: " + s.getDistrict() + "</b></p>"

						+ "</div>";
				final LatLng pos = LatLng.create(s.getLatitude(),
						s.getLongitude());
				marker.addClickListener(new Marker.ClickHandler() {

					@Override
					public void handle(MouseEvent event) {
						IW.close();
						IW.setPosition(pos);
						IW.setContent(txt);
						IW.open(theMap);

					}
				});
				Markers.add(marker);

			}

		}

	}

	private void RemoveMarker(String name) {
		for (int k = 0; k < Markers.size(); k++) {
			if (name.equals(Markers.get(k).getTitle())) {
				Markers.get(k).setMap((GoogleMap) null);
				if (Markers.get(k).getPosition().equals(IW.getPosition()))
					IW.close();
				Markers.remove(k);

			}
		}
	}
}
