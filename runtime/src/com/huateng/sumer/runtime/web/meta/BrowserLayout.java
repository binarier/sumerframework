package com.huateng.sumer.runtime.web.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p> 定义浏览布局 </p> <ul> <li>该layout需要与ht:browser-layout标签配合使用</li> <li>在指定了
 * {@link #multiline}属性为<code>true</code>的情况下， columns属性才生效。</li> </ul>
 * 
 * @author chenjun.li
 * 
 */
public class BrowserLayout extends AbstractLayout {
	private static final long serialVersionUID = 6991748088855108270L;
	/**
	 * 数据绑定对象中数据所在的属性，默认为"data"。
	 */
	private String dataPath = "data";
	/**
	 * 数据绑定对象中{@link Pagination}对象所在的属性。默认为"pagination"。
	 */
	private String paginationPath = "pagination";
	/**
	 * 是否显示分页控制
	 */
	private boolean paginated = true;
	/**
	 * 表格列定义列表
	 */
	protected List<AbstractField> fields;
	/**
	 * 是否支持多行表头，默认为false，此时表格的列数由注入的字段列表决定，以简化配置。
	 */
	protected boolean multiline = false;
	
	public List<List<Cell>> getBodyCells()
	{
		// 处理multiline问题
		if (!multiline)
			setColumns(fields.size());
		return calculateCells(fields);
	}

	public List<List<Cell>> getHeaderCells()
	{
		// 处理multiline问题
		if (!multiline)
			setColumns(fields.size());

		List<AbstractField> headers = new ArrayList<AbstractField>(fields.size());
		for (AbstractField af : fields)
		{
			LabelField lf = new LabelField();
			if (af instanceof AbstractInputField)
			{
				AbstractInputField aif = (AbstractInputField)af;
				lf.setLabel(aif.getLabel());
				lf.setColSpan(aif.getColSpan());
			}
			
			headers.add(lf);
		}
		
		return calculateCells(headers);
	}

	public boolean isPaginated() {
		return paginated;
	}

	public void setPaginated(boolean paginated) {
		this.paginated = paginated;
	}

	public boolean isMultiline() {
		return multiline;
	}

	public void setMultiline(boolean multiline) {
		this.multiline = multiline;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public String getPaginationPath() {
		return paginationPath;
	}

	public void setPaginationPath(String paginationPath) {
		this.paginationPath = paginationPath;
	}

	public List<AbstractField> getFields() {
		return fields;
	}

	public void setFields(List<AbstractField> fields) {
		this.fields = fields;
	}

	@Override
	public Map<AbstractField, Object> getReference(Object context) {
		Map<AbstractField, Object> map = new HashMap<AbstractField, Object>();
		for (AbstractField af : fields)
		{
			Object obj = af.getReference(null);
			if (obj != null)
				map.put(af, obj);
		}
		return map;
	}

}
