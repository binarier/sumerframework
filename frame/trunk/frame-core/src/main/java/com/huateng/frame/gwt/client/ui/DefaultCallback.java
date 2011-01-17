package com.huateng.frame.gwt.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public class DefaultCallback<T> implements AsyncCallback<T>
{
	private String successMessage = "操作成功";

	public DefaultCallback()
	{
	}
	
	public DefaultCallback(String successMessage)
	{
		this.successMessage = successMessage;
	}
	
	public void onFailure(Throwable caught)
	{
		SC.warn(caught.getLocalizedMessage());
	}

	public void onSuccess(T result)
	{
		SC.say(successMessage);
	}
}
