package com.huateng.frame.gwt.client.ui;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

public class SimpleCallback<T> extends DefaultCallback<T>
{
	private String successMessage = "操作成功";
	
	private Window window;
	
	private BasicView<?> view;
	
	private boolean refreshClientSideOnly;
	
	private Object refreshHint;
	
	/**
	 * 设置成功消息并且返回本身，以便进行链式调用
	 * @param successMessage
	 * @return
	 */
	public SimpleCallback<T> message(String successMessage)
	{
		this.successMessage = successMessage;
		return this;
	}
	
	public SimpleCallback<T> closeWindow(Window window)
	{
		this.window = window;
		return this;
	}
	
	public SimpleCallback<T> refreshView(BasicView<?> view)
	{
		return refreshView(view, false, null);
	}

	public SimpleCallback<T> refreshView(BasicView<?> view, boolean clientSideOnly)
	{
		return refreshView(view, clientSideOnly, null);
	}
	
	public SimpleCallback<T> refreshView(BasicView<?> view, boolean clientSideOnly, Object hint)
	{
		this.view = view;
		refreshClientSideOnly = clientSideOnly;
		refreshHint = hint;
		return this;
	}

	public void onSuccess(T result)
	{
		if (successMessage != null)
			SC.say(successMessage);
		if (window != null)
			window.destroy();
		if (view != null)
			view.updateView(refreshClientSideOnly, refreshHint);
	}
}
