package com.huateng.frame.gwt.client.mvp;

import com.google.gwt.place.shared.Place;

public interface SimplePlace
{
	String getToken();
	
	Place getPlace(String token);
}
