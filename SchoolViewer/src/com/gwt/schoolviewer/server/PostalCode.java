package com.gwt.schoolviewer.server;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PostalCode implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private Date createDate;
	@Persistent
	private String code;

	public PostalCode() {
		this.createDate = new Date();
	}
	
	//public PostalCode(String code) {
	//	this();
	//	this.code = code;
	//}

	//TODO: HANDLE USER
	public PostalCode(User user, String code) {
		this();
		this.user = user;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCode() {
		return code;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
