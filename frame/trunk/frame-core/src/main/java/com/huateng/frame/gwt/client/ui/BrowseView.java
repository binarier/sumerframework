package com.huateng.frame.gwt.client.ui;

import com.huateng.frame.gwt.client.datasource.DataSourceCallback;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FrameDataSource;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class BrowseView extends BasicView<Layout> {
	protected DynamicForm searchForm;
	
	protected ListGrid listGrid;
	
	protected HLayout buttonsLayout;

	@Override
	protected Layout createCanvas() {
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();

		//数据
		FrameDataSource fds = new FrameDataSource() {
			@Override
			public void fetchData(FetchRequest fetchRequest, DataSourceCallback callback) {
				BrowseView.this.fetchData(fetchRequest, callback);
			}
		};
		
		listGrid = new ListGrid();
		listGrid.setAutoFetchData(true);
		setupDataSource(fds);
		listGrid.setDataSource(fds);

		layout.addMember(searchForm = new DynamicForm());
		layout.addMember(listGrid);
		layout.addMember(buttonsLayout = new HLayout());

		return layout;
	}
	
	
	//子类实现点
	
	protected abstract void fetchData(FetchRequest fetchRequest, DataSourceCallback callback);

	protected abstract void setupDataSource(DataSource ds);
	
	protected abstract void setupButtons(Layout layout);
	
	//get/set方法
	public DynamicForm getSearchForm() {
		return searchForm;
	}

	public void setSearchForm(DynamicForm searchForm) {
		this.searchForm = searchForm;
	}

	public ListGrid getListGrid() {
		return listGrid;
	}

	public void setListGrid(ListGrid listGrid) {
		this.listGrid = listGrid;
	}

	public HLayout getButtonsLayout() {
		return buttonsLayout;
	}

	public void setButtonsLayout(HLayout buttonsLayout) {
		this.buttonsLayout = buttonsLayout;
	}

}
