package com.huateng.frame.gwt.client.mvp;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public abstract class EmptyTokenizer<P extends Place> implements PlaceTokenizer<P>
{

	public String getToken(P place)
	{
		return "";
	}

}
