package com.huateng.frame.gwt.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public abstract class DefaultCallback<T> implements AsyncCallback<T>
{
	public void onFailure(Throwable caught)
	{
		SC.warn(caught.getLocalizedMessage());
	}
}
