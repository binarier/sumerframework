package com.huateng.frame.gwt.client.datasource;

import java.io.Serializable;

public class FetchResponse implements Serializable
{
	private static final long serialVersionUID = -1539859028073756636L;
	private int startRow;
	private int endRow;
	private int totalRows;
	private String fields[];
	private Serializable data[][];
	public int getStartRow()
	{
		return startRow;
	}
	public void setStartRow(int startRow)
	{
		this.startRow = startRow;
	}
	public int getEndRow()
	{
		return endRow;
	}
	public void setEndRow(int endRow)
	{
		this.endRow = endRow;
	}
	public int getTotalRows()
	{
		return totalRows;
	}
	public void setTotalRows(int totalRows)
	{
		this.totalRows = totalRows;
	}
	public String[] getFields()
	{
		return fields;
	}
	public void setFields(String[] fields)
	{
		this.fields = fields;
	}
	public Serializable[][] getData()
	{
		return data;
	}
	public void setData(Serializable[][] data)
	{
		this.data = data;
	}
}
