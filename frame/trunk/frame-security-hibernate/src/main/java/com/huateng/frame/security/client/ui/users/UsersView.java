package com.huateng.frame.security.client.ui.users;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.huateng.frame.gwt.client.datasource.DataSourceCallback;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.ui.BrowseView;
import com.huateng.frame.security.client.home.TblSecUserClientHome;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.layout.Layout;

@Singleton
public class UsersView extends BrowseView {
	
	@Inject
	private UsersInterAsync server;

	@Override
	protected void fetchData(FetchRequest fetchRequest, DataSourceCallback callback) {
		server.listUsers(fetchRequest, callback);
	}

	@Override
	protected void setupDataSource(DataSource ds) {
		ds.setFields(
			TblSecUserClientHome.UserId().createField(),
			TblSecUserClientHome.UserName().createField());
	}

	@Override
	protected void setupButtons(Layout layout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView(boolean clientSideOnly, Object hint) {
		
	}

	@Override
	public void setupView(Place place) {
		// TODO Auto-generated method stub
		
	}

}
