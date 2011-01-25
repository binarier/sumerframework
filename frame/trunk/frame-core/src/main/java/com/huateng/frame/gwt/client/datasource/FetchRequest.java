package com.huateng.frame.gwt.client.datasource;

import java.io.Serializable;

public class FetchRequest implements Serializable
{
	private int startRow;
	private int endRow;
	private String sortBy;
	private String criteriaJSON;
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
	public String getSortBy()
	{
		return sortBy;
	}
	public void setSortBy(String sortBy)
	{
		this.sortBy = sortBy;
	}
	public String getCriteriaJSON()
	{
		return criteriaJSON;
	}
	public void setCriteriaJSON(String criteriaJSON)
	{
		this.criteriaJSON = criteriaJSON;
	}
	
}
