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
import com.huateng.frame.gwt.client.ui.DefaultCallback;
import com.huateng.frame.gwt.client.ui.ModalFormWindow;
import com.huateng.frame.gwt.client.ui.SimpleCallback;
import com.huateng.frame.security.client.home.TblSecUserClientHome;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGridRecord;
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
			TblSecUserClientHome.UserName().createField(),
			TblSecUserClientHome.Email().createField(),
			TblSecUserClientHome.Status().createEnumField());
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
				UsersView.this.createUser();
			}
		});
		layout.addMember(btnCreate);
		
		btnEdit = new Button("编辑用户");
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.editUser();
			}
		});
		listGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				UsersView.this.editUser();
			}
		});
		layout.addMember(btnEdit);

		btnLock = new Button("锁定用户");
		btnLock.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.lockUser();
			}
		});
		layout.addMember(btnLock);

		btnUnlock = new Button("解锁用户");
		btnUnlock.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.onBtnUnlockClick();
			}
		});
		layout.addMember(btnUnlock);
		
		btnReset = new Button("重置密码");
		btnReset.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UsersView.this.onBtnUnlockClick();
			}
		});
		layout.addMember(btnReset);
	}

	@Override
	public void updateView(boolean clientSideOnly, Object hint) {
		if (!clientSideOnly)
		{
			//刷新列表
			listGrid.invalidateCache();
		}
		
		int selection = listGrid.getSelection().length;
		
		btnEdit.setEnabled(selection == 1);
		btnLock.setEnabled(selection >= 1);
		btnUnlock.setEnabled(selection >= 1);
	}

	@Override
	public void setupView(Place place) {
		// TODO Auto-generated method stub
		
	}
	
	protected void createUser()
	{
		final DynamicForm form = new DynamicForm();
		form.setItems(
			TblSecUserClientHome.UserId().required().createFormItem(),
			TblSecUserClientHome.UserName().required().createFormItem(),
			TblSecUserClientHome.Email().createFormItem(),
			TblSecUserClientHome.Status().createSelectItem());
		
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
	
	protected void editUser()
	{
		
		ListGridRecord record = listGrid.getSelectedRecord();
		final String userId = TblSecUserClientHome.UserId().fromRecord(record);
		
		server.getUser(userId, new DefaultCallback<Map>(){
			@Override
			public void onSuccess(Map result)
			{
				final DynamicForm form = new DynamicForm();
				form.setItems(
					TblSecUserClientHome.UserId().readOnly().createFormItem(),
					TblSecUserClientHome.UserName().required().createFormItem(),
					TblSecUserClientHome.Email().createFormItem(),
					TblSecUserClientHome.Status().createSelectItem());

				form.setValues(result);

				ModalFormWindow window = new ModalFormWindow(form) {
					@Override
					public void onOK() {
						if (form.validate())
						{
							Map values = getForm().getValues();
							server.updateUser(userId, values, new SimpleCallback<Void>().closeWindow(this).refreshView(UsersView.this));
						}
					}
				};
				window.show();
			}
		});
	}
	
	protected void lockUser()
	{
		final String userId = TblSecUserClientHome.UserId().fromRecord(listGrid.getSelectedRecord());
		server.lockUser(userId, new DefaultCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				SC.say("操作成功");
			}
		});
	}
	
	protected void onBtnUnlockClick()
	{
		final String userId = TblSecUserClientHome.UserId().fromRecord(listGrid.getSelectedRecord());
		server.unlockUser(userId, new DefaultCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				SC.say("操作成功");
			}
		});
	}
	
	protected void resetPassword()
	{
		
	}
}
