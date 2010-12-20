package com.huateng.frame.gwt.client.ui;

import org.synthful.smartgwt.client.LazyCanvas;

import com.smartgwt.client.widgets.Canvas;

public abstract class BasicView<ViewHandler> extends LazyCanvas<Canvas>{
	protected ViewHandler handler;

	public ViewHandler getHandler() {
		return handler;
	}

	public void setHandler(ViewHandler handler) {
		this.handler = handler;
	}
}
