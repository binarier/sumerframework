package com.huateng.sumer.runtime.web.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p> ����������� </p> <ul> <li>��layout��Ҫ��ht:browser-layout��ǩ���ʹ��</li> <li>��ָ����
 * {@link #multiline}����Ϊ<code>true</code>������£� columns���Բ���Ч��</li> </ul>
 * 
 * @author chenjun.li
 * 
 */
public class BrowserLayout extends AbstractLayout {
	private static final long serialVersionUID = 6991748088855108270L;
	/**
	 * ���ݰ󶨶������������ڵ����ԣ�Ĭ��Ϊ"data"��
	 */
	private String dataPath = "data";
	/**
	 * ���ݰ󶨶�����{@link Pagination}�������ڵ����ԡ�Ĭ��Ϊ"pagination"��
	 */
	private String paginationPath = "pagination";
	/**
	 * �Ƿ���ʾ��ҳ����
	 */
	private boolean paginated = true;
	/**
	 * ����ж����б�
	 */
	protected List<AbstractField> fields;
	/**
	 * �Ƿ�֧�ֶ��б�ͷ��Ĭ��Ϊfalse����ʱ����������ע����ֶ��б�������Լ����á�
	 */
	protected boolean multiline = false;
	
	public List<List<Cell>> getBodyCells()
	{
		// ����multiline����
		if (!multiline)
			setColumns(fields.size());
		return calculateCells(fields);
	}

	public List<List<Cell>> getHeaderCells()
	{
		// ����multiline����
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
