package com.huateng.frame.security.client.ui.users;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.huateng.frame.gwt.client.datasource.DataSourceCallback;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.ui.BrowseView;
import com.huateng.frame.gwt.client.ui.ModalFormWindow;
import com.huateng.frame.gwt.client.ui.SimpleCallback;
import com.huateng.frame.security.client.home.TblSecUserClientHome;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.Layout;

@Singleton
public class UsersView extends BrowseView {
	
	@Inject
	private UsersInterAsync server;
	
	private Button btnRefresh;
	
	private Button btnCreate;
	
	private Button btnEdit;
	
	private Button btnLock;
	
	private Button btnUnlock;
	
	private Button btnReset;

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
		btnRefresh = new Button("刷新");
		btnRefresh.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.updateView(false, null);
			}
		});
		layout.addMember(btnRefresh);
		
		btnCreate = new Button("新建用户");
		btnCreate.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.onBtnCreateClick();
			}
		});
		layout.addMember(btnCreate);
		
		btnEdit = new Button("编辑用户");
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.onBtnEditClick();
			}
		});
		layout.addMember(btnCreate);

	}

	@Override
	public void updateView(boolean clientSideOnly, Object hint) {
		if (!clientSideOnly)
		{
			listGrid.invalidateCache();
		}
	}

	@Override
	public void setupView(Place place) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onBtnCreateClick()
	{
		final DynamicForm form = new DynamicForm();
		form.setItems(
			TblSecUserClientHome.UserId().createFormItem(),
			TblSecUserClientHome.UserName().createFormItem(),
			TblSecUserClientHome.Email().createFormItem());
		
		ModalFormWindow window = new ModalFormWindow(form) {
			@Override
			public void onOK() {
				if (form.validate())
				{
					Map values = getForm().getValues();
					server.createUser(values, new SimpleCallback<Void>().closeWindow(this).refreshView(UsersView.this));
				}
			}
		};
		
		window.show();
	}
	
	protected void onBtnEditClick()
	{
		
		final DynamicForm form = new DynamicForm();
		form.setItems(
			TblSecUserClientHome.UserId().createFormItem(),
			TblSecUserClientHome.UserName().createFormItem(),
			TblSecUserClientHome.Email().createFormItem());

		
		ModalFormWindow window = new ModalFormWindow(form) {
			@Override
			public void onOK() {
				if (form.validate())
				{
					Map values = getForm().getValues();
					server.createUser(values, new SimpleCallback<Void>().closeWindow(this).refreshView(UsersView.this));
				}
			}
		};
		
		window.show();
	}
}
