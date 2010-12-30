package com.huateng.frame.gwt.client.ui;

import org.synthful.smartgwt.client.LazyCanvas;

import com.smartgwt.client.widgets.layout.Layout;

public abstract class BasicView<ViewHandler> extends LazyCanvas<Layout>
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
}
