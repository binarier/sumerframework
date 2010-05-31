package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 定义一个布局块
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
	 * 子类的公共服务，用于形成Cell表格
	 * @param fields
	 * @return 计算好的Cell表格
	 */
	protected List<List<Cell>> calculateCells(List<AbstractField> fields)
	{
		//先计算表格宽度
		int columnWidths[] = new int[columns];

		// 按columnRates以百分比分配列宽
		if (columnRatios == null) {
			// 如果columnRates没有配，则使用"*"作为列宽
			for (int i = 0; i < columns; i++) {
				columnWidths[i] = 0;
			}
		} else {
			// 按比例分配列宽

			String ratios[] = StringUtils.split(columnRatios, ":");

			int sum = 0;
			for (String s : ratios)
				sum += Integer.valueOf(s);

			int i=0;
			for (String s : ratios)
				columnWidths[i++] = Integer.valueOf(s) * 100 / sum;
		}
		
		//然后生成表格
		List<List<Cell>> table = new ArrayList<List<Cell>>();
		Iterator<AbstractField> iter = fields.iterator();
		while (iter.hasNext())
		{
			//添加行
			ArrayList<Cell> cols = new ArrayList<Cell>(columns);
			table.add(cols);
			//添加列
			for (int i = 0; (i < columns) && iter.hasNext();)
			{
				AbstractField af = iter.next();
				Cell cell = new Cell();
				cols.add(cell);
				cell.setField(af);
				
				//计算宽度
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
