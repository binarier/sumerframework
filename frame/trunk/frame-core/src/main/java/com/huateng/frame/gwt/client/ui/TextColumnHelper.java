package com.huateng.frame.gwt.client.ui;

import java.io.Serializable;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class TextColumnHelper<DATA_TYPE extends Serializable> extends ColumnHelper<DATA_TYPE> {
	
	public TextColumnHelper(String name, String title, Integer length)
	{
		super(name, title, length);
	}

	@Override
	protected void setupItemAttributes(FormItem item) {
	}

	@Override
	protected FormItem doCreateFormItem() {
		return new TextItem();
	}

	@Override
	protected void setupItemValidators(FormItem item) {
	}
	
	
}
