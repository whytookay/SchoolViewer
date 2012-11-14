package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.schoolviewer.client.LoginInfo;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

    void greetServer(String input, AsyncCallback<String> callback) 
        throws IllegalArgumentException;

   
    void login(String requestUri, AsyncCallback<LoginInfo> asyncCallback);

    void loginDetails(String token, AsyncCallback<LoginInfo> asyncCallback);
 
}
