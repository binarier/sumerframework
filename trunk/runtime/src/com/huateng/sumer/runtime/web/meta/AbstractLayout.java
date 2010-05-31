package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ����һ�����ֿ�
 * 
 * @author chenjun.li
 * @version $Id$
 */
public abstract class AbstractLayout implements Serializable {
	private static final long					serialVersionUID	= 12342414352345L;
	protected int								columns				= 2;
	protected String							columnRatios;
	protected String							caption;
	protected String							nestedPath;

	/**
	 * ����Ĺ������������γ�Cell���
	 * @param fields
	 * @return ����õ�Cell���
	 */
	protected List<List<Cell>> calculateCells(List<AbstractField> fields)
	{
		//�ȼ�������
		int columnWidths[] = new int[columns];

		// ��columnRates�԰ٷֱȷ����п�
		if (columnRatios == null) {
			// ���columnRatesû���䣬��ʹ��"*"��Ϊ�п�
			for (int i = 0; i < columns; i++) {
				columnWidths[i] = 0;
			}
		} else {
			// �����������п�

			String ratios[] = StringUtils.split(columnRatios, ":");

			int sum = 0;
			for (String s : ratios)
				sum += Integer.valueOf(s);

			int i=0;
			for (String s : ratios)
				columnWidths[i++] = Integer.valueOf(s) * 100 / sum;
		}
		
		//Ȼ�����ɱ��
		List<List<Cell>> table = new ArrayList<List<Cell>>();
		Iterator<AbstractField> iter = fields.iterator();
		while (iter.hasNext())
		{
			//�����
			ArrayList<Cell> cols = new ArrayList<Cell>(columns);
			table.add(cols);
			//�����
			for (int i = 0; (i < columns) && iter.hasNext();)
			{
				AbstractField af = iter.next();
				Cell cell = new Cell();
				cols.add(cell);
				cell.setField(af);
				
				//������
				int width = 0;
				for (int j = 0; j<af.getColSpan(); j++)
					width += columnWidths[i + j];
				
				if (width > 0)
					cell.setWidth(width + "%");
				else
					cell.setWidth("*");
				
				i += af.getColSpan();
			}
		}
		return table;
	}
	
	public abstract Map<AbstractField, Object> getReference(Object context);
	
	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setColumnRatios(String columnRatios) {
		this.columnRatios = columnRatios;
	}

	public String getNestedPath() {
		return nestedPath;
	}

	public void setNestedPath(String nestedPath) {
		this.nestedPath = nestedPath;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

}
