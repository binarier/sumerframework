package com.huateng.frame.gwt.client.ui;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public abstract class ModalFormWindow extends Window
{
	private IButton btnOK;
	private IButton btnCancel;
	
	private DynamicForm form;
	
	protected ModalFormWindow()
	{
	}
	
	public ModalFormWindow(DynamicForm form)
	{
		setForm(form);
		setAutoSize(true);
		setTitle("Modal Window");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		addItem(form);
		
		HLayout layout = new HLayout();
		layout.setWidth100();
		addItem(layout);
		
		btnOK = new IButton("确定");
		layout.addMember(btnOK);

		btnCancel = new IButton("取消");
		layout.addMember(btnCancel);
		
		btnOK.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onOK();
			}
		});
		
		btnCancel.addClickHandler(new ClickHandler()
		{
			
			public void onClick(ClickEvent event)
			{
				ModalFormWindow.this.destroy();
			}
		});
	}
	
	public abstract void onOK();

	public DynamicForm getForm()
	{
		return form;
	}

	public void setForm(DynamicForm form)
	{
		this.form = form;
	}
}
