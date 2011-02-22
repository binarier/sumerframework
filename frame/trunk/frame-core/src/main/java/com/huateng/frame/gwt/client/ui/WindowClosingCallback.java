package com.huateng.frame.gwt.client.ui;

import com.smartgwt.client.widgets.Window;

public class WindowClosingCallback<T> extends SuccessMessageCallback<T>
{
	private Window window;
	
	public WindowClosingCallback(Window window)
	{
		this.window = window;
	}
	
	public WindowClosingCallback(Window window, String successMessage)
	{
		super(successMessage);
		this.window = window;
	}
	
	@Override
	public void onOK(Boolean okPressed)
	{
		window.destroy();
	}
}
