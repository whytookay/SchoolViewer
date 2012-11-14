package com.gwt.schoolviewer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.schoolviewer.client.LoginInfo;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    
    String greetServer(String name) throws IllegalArgumentException;
    
     
    LoginInfo login(String requestUri);    
    
    LoginInfo loginDetails(String token);

}
