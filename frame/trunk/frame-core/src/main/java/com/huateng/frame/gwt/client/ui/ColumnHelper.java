package com.huateng.frame.gwt.client.ui;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.huateng.frame.gwt.shared.DomainSupport;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;


public class ColumnHelper<DATA_TYPE extends Serializable>
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
		return applyFormItem(new TextItem());
	}
	
	public ComboBoxItem createEmptyComboBoxItem()
	{
		return applyFormItem(new ComboBoxItem());
	}
	public ComboBoxItem createComboBoxItem()
	{
		DomainSupport<DATA_TYPE> ds = getDomain();
		if (ds == null)
			return createEmptyComboBoxItem();
		else
			return createComboBoxItem(ds.asLinkedHashMap());
	}
	
	public ComboBoxItem createComboBoxItem(LinkedHashMap valueMap)
	{
		ComboBoxItem item = createEmptyComboBoxItem();
		item.setValueMap(valueMap);
		return item;
	}

	public ComboBoxItem createComboBoxItem(DataSource dataSource)
	{
		ComboBoxItem item = createComboBoxItem();
		item.setOptionDataSource(dataSource);
		return item;
	}
	
	public DataSourceTextField createTextField()
	{
		return applyDataSourceField(new DataSourceTextField());
	}

	public DataSourceEnumField createEmptyEnumField()
	{
		return applyDataSourceField(new DataSourceEnumField());
	}

	public DataSourceEnumField createEnumField()
	{
		DomainSupport<DATA_TYPE> ds = getDomain();
		if (ds == null)
			return createEmptyEnumField();
		else
			return createEnumField(ds.asMap());
	}
	
	public DataSourceEnumField createEnumField(Map valueMap)
	{
		DataSourceEnumField field = createEnumField();
		field.setValueMap(valueMap);
		return field;
	}
	
	public <T extends FormItem> T applyFormItem(T item)
	{
		item.setName(name);
		item.setTitle(title);
		if (length != null)
			item.setAttribute("length", length);
		return item;
	}
	
	public <T extends DataSourceField> T applyDataSourceField(T field)
	{
		field.setName(name);
		field.setTitle(title);
		if (length != null)
			field.setLength(length);
		return field;
	}
	
	@SuppressWarnings("unchecked")
	public DATA_TYPE fromRecord(Record record)
	{
		return (DATA_TYPE)record.getAttributeAsObject(name);
	}
	
	public DomainSupport<DATA_TYPE> getDomain()
	{
		return null;
	}
}
