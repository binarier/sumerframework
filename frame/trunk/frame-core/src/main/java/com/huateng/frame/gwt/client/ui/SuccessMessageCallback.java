package com.huateng.frame.gwt.client.ui;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

public abstract class SuccessMessageCallback<T> extends DefaultCallback<T>
{
	private String successMessage = "操作成功";
	
	public SuccessMessageCallback()
	{
	}

	public SuccessMessageCallback(String successMessage)
	{
		this.successMessage = successMessage;
	}

	public void onSuccess(T result)
	{
		SC.say(successMessage, new BooleanCallback()
		{
			public void execute(Boolean value)
			{
				SuccessMessageCallback.this.onOK(value);
			}
		});
	}
	
	public abstract void onOK(Boolean okPressed);

	public String getSuccessMessage()
	{
		return successMessage;
	}

	public void setSuccessMessage(String successMessage)
	{
		this.successMessage = successMessage;
	}

}
