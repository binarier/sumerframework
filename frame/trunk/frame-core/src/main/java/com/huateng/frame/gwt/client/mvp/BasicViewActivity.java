package com.huateng.frame.gwt.client.mvp;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.huateng.frame.gwt.client.ui.BasicView;

public class BasicViewActivity extends AbstractActivity
{
	private BasicView<?> view;
	
	public BasicViewActivity(BasicView<?> view)
	{
		this.view = view;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus)
	{
		view.setWidth100();
		view.setHeight100();
		view.setShowEdges(true);
		view.ensureCanvas();
		view.updateView(false, null);
		panel.setWidget(view);
	}

}
