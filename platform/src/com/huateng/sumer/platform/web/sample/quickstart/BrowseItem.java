package com.huateng.sumer.platform.web.sample.quickstart;

import java.io.Serializable;

public class BrowseItem implements Serializable{
	private static final long serialVersionUID = 8013768408617053836L;
	private String id;
	private String firstName;
	private String lastName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
