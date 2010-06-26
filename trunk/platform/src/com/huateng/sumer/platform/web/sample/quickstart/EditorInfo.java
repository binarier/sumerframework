package com.huateng.sumer.platform.web.sample.quickstart;

import java.io.Serializable;

public class EditorInfo implements Serializable{
	private static final long serialVersionUID = -7310128177960065159L;
	private String birthYear;

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
}
