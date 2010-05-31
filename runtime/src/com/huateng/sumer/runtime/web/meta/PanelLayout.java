package com.huateng.sumer.runtime.web.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelLayout extends AbstractLayout {

	private static final long serialVersionUID = -2694254112874664040L;

	/**
	 * ��˳�����ʾ�ֶ��б�
	 */
	protected List<AbstractField> fields;
	
	/**
	 * �Ƿ������ֶ�Ϊֻ��
	 */
	protected boolean readOnly = false;

	public List<List<Cell>> getExtractedCells() {

		//�Ȱ��ֶι�һ�飬�����б������InputField����������label�ֶ�
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
					!(aif.isReadOnly() && readOnly));	//ֻ���ڷ�ֻ��������²���ʾ�Ǳ�
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
