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
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;


public abstract class ColumnHelper<DATA_TYPE extends Serializable>
{
	private String name;
	private String title;
	private Integer length;
	
	protected ColumnHelper(String name, String title, Integer length)
	{
		this.name = name;
		this.title = title;
		this.length = length;
	}

	/**
	 * 子类扩展点，用于建立默认的FormItem实例
	 * @return
	 */
	protected abstract FormItem doCreateFormItem();

	/**
	 * 子类扩展点，用于为FormItem添加额外的属性
	 * @param item
	 */
	protected abstract void setupItemAttributes(FormItem item);
	
	/**
	 * 子类扩展点，用于为FormItem添加默认校验
	 * @param item
	 */
	protected abstract void setupItemValidators(FormItem item);
	
	
	public FormItem createFormItem()
	{
		return setupFormItem(doCreateFormItem());
	}
	
	
	public <T extends FormItem> T setupFormItem(T item)
	{
		item.setName(name);
		item.setTitle(title);
		if (length != null)
			item.setAttribute("length", length);
		
		setupItemAttributes(item);
		
		setupItemValidators(item);
		
		return item;
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
	
	public SelectItem createEmptySelectItem()
	{
		return setupFormItem(new SelectItem());
	}
	public SelectItem createSelectItem()
	{
		DomainSupport<DATA_TYPE> ds = getDomain();
		if (ds == null)
			return createEmptySelectItem();
		else
			return createSelectItem(ds.asLinkedHashMap());
	}
	
	public SelectItem createSelectItem(LinkedHashMap<?, String> valueMap)
	{
		SelectItem item = createEmptySelectItem();
		item.setValueMap(valueMap);
		return item;
	}

	public SelectItem createSelectItem(DataSource dataSource)
	{
		SelectItem item = createSelectItem();
		item.setOptionDataSource(dataSource);
		return item;
	}
	
	public DataSourceTextField createField()
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
	
	public DataSourceEnumField createEnumField(Map<?, String> valueMap)
	{
		DataSourceEnumField field = createEmptyEnumField();
		field.setValueMap(valueMap);
		return field;
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
	
	/**
	 * 供带有Domain的字段进行动态子类覆盖
	 * @return
	 */
	public DomainSupport<DATA_TYPE> getDomain()
	{
		return null;
	}
}
