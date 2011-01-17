package com.huateng.frame.gwt.client.ui;

import org.synthful.smartgwt.client.LazyCanvas;

import com.smartgwt.client.widgets.Canvas;

public abstract class BasicView<RootElement extends Canvas, ViewHandler> extends LazyCanvas<RootElement>
{
	protected ViewHandler handler;
	
	protected BasicView()
	{
	}

	public ViewHandler getHandler()
	{
		return handler;
	}

	public void setHandler(ViewHandler handler)
	{
		this.handler = handler;
	}
	
	public abstract void updateView(Object hint);
	
	public abstract void setupView();
}
