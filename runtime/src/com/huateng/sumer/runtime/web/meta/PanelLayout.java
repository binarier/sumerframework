package com.huateng.sumer.runtime.web.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelLayout extends AbstractLayout {

	private static final long serialVersionUID = -2694254112874664040L;

	/**
	 * 按顺序的显示字段列表
	 */
	protected List<AbstractField> fields;
	
	/**
	 * 是否所有字段为只读
	 */
	protected boolean readOnly = false;

	public List<List<Cell>> getExtractedCells() {

		//先把字段过一遍，添到新列表，如果是InputField，则额外添加label字段
		List<AbstractField> extractedFields = new ArrayList<AbstractField>();
		for (AbstractField af : fields)
		{
			if (af instanceof AbstractInputField)
			{
				AbstractInputField aif = (AbstractInputField)af;
				
				LabelField lf = new LabelField();
				lf.setLabel(aif.getLabel());
				lf.setColSpan(aif.getLabelColSpan());
				lf.setMandatoryMark(
					aif.isMandatory()&&
					!(aif.isReadOnly() && readOnly));	//只有在非只读的情况下才显示星标
				extractedFields.add(lf);
			}
			extractedFields.add(af);
		}
		
		return calculateCells(extractedFields);
	}

	public List<AbstractField> getFields() {
		return fields;
	}

	public void setFields(List<AbstractField> fields) {
		this.fields = fields;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public Map<AbstractField, Object> getReference(Object context) {
		Map<AbstractField, Object> map = new HashMap<AbstractField, Object>();
		for (AbstractField af : fields)
		{
			Object obj = af.getReference(context);
			if (obj != null)
				map.put(af, obj);
		}
		return map;
	}
}
