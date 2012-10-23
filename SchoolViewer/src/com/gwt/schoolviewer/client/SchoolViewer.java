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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;
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

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	  private LoginInfo loginInfo = null;
	  private VerticalPanel loginPanel = new VerticalPanel();
	  private Label loginLabel = new Label("Please sign in to your Google Account to access the StockWatcher application.");
	  private Anchor signInLink = new Anchor("Sign In");
	  private Anchor signOutLink = new Anchor("Sign Out");
	  private ArrayList<String> schools = new ArrayList<String>();	
	  private SchoolValueServiceAsync schoolValueSvc = GWT.create(SchoolValueService.class);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	    // Check login status using login service.
	    LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) {
	      }

	      public void onSuccess(LoginInfo result) {
	        loginInfo = result;
	        if(loginInfo.isLoggedIn()) {
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
	
	private void loadschoolviewer(){
	    // Set up sign out hyperlink.
	    signOutLink.setHref(loginInfo.getLogoutUrl());
		
		
		
	    final FlexTable schoolFlexTable = new FlexTable();
		final Button sendButton = new Button("Search");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();
		
	    // Create table for stock data.
	    schoolFlexTable.setText(0, 0, "Name");
	    schoolFlexTable.setText(0, 1, "Value");
	    schoolFlexTable.setText(0, 2, "Location");
	    schoolFlexTable.setText(0, 3, "District");
	    schoolFlexTable.setText(0, 4, "Postal Code");
	    schoolFlexTable.setText(4, 4, "V6K 2J0");
		

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		
	    // Add styles to elements in the stock list table.
	    schoolFlexTable.setCellPadding(6);
	    schoolFlexTable.getRowFormatter().addStyleName(0, "schoolListHeader");
	    schoolFlexTable.addStyleName("schoolList");


		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("flexTableContainer").add(schoolFlexTable);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

/*		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});*/

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendDatatoServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendDatatoServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendDatatoServer() {
				// add schools to the table
				refreshSchoolList();
				
				
				
/*				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter valid search terms");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});*/
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	private void refreshSchoolList(){
	    // Initialize the service proxy.
	    if (schoolValueSvc == null) {
	      schoolValueSvc = GWT.create(SchoolValueService.class);
	    }
	    
	    // Set up the callback object.
	    AsyncCallback <ArrayList<SchoolValue>> callback = new AsyncCallback <ArrayList<SchoolValue>>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(ArrayList<SchoolValue> result) {
	        updateTable(result);
	      }
	    };
	    
	    // Make the call to the stock price service.
	    schoolValueSvc.getValues(schools.toArray(new String[0]), callback);
	}
	
	  /**
	   * Update the Price and Change fields all the rows in the school table.
	   *
	   * @param values School data for all rows.
	   */
	  private void updateTable(ArrayList<SchoolValue> values) {
	    for (int i = 0; i < values.size(); i++) {
	      updateTable(values.get(i));
	    }
	  }
	  
	  /**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	  private void updateTable(SchoolValue value) {
	  
	  }
}
