package com.huateng.frame.gwt.client.ui;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;


public class ColumnHelper
{
	private String name;
	private String title;
	private Integer length;
	
	public ColumnHelper(String name, String title, Integer length)
	{
		this.name = name;
		this.title = title;
		this.length = length;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Integer getLength()
	{
		return length;
	}

	public void setLength(Integer length)
	{
		this.length = length;
	}
	
	public TextItem createTextItem()
	{
		TextItem item = new TextItem();
		return applyFormItem(item);
	}
	
	public <T extends FormItem> T applyFormItem(T item)
	{
		item.setName(name);
		item.setTitle(title);
		if (length != null)
			item.setAttribute("length", length);
		return item;
	}
}
