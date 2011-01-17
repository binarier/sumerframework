package com.huateng.frame.gwt.client.ui;

public class ViewRefreshCallback<T> extends DefaultCallback<T>
{
	private BasicView<?, ?> view;
	
	
	public ViewRefreshCallback(BasicView<?, ?> view)
	{
		super();
		this.view = view;
	}
	public ViewRefreshCallback(BasicView<?, ?> view, String successMessage)
	{
		super(successMessage);
		this.view = view;
	}
	
	@Override
	public void onSuccess(T result)
	{
		super.onSuccess(result);
		view.updateView(null);
	};
	
	@Override
	public void onFailure(Throwable caught)
	{
		super.onFailure(caught);
		view.updateView(null);
	}
}
