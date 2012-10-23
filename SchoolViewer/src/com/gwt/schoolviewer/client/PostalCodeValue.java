package com.gwt.schoolviewer.client;

import java.io.Serializable;

public class PostalCodeValue implements Serializable {
	String code;
	
	PostalCodeValue(){}

	public PostalCodeValue(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
